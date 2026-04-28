package gameproject.state;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.GamePanel;
import gameproject.FontManager;
import gameproject.meta.CharacterClass;
import gameproject.meta.PlayerData;

public class CharacterSelectState implements State {
    
    private int selectedIndex = 0;
    private CharacterClass[] classes = CharacterClass.values();

    public CharacterSelectState() {
        for (int i = 0; i < classes.length; i++) {
            if (classes[i] == PlayerData.selectedClass) {
                selectedIndex = i;
                break;
            }
        }
    }

    @Override
    public void update(GamePanel game) {
        if (game.input.escPressed) {
            game.input.clearClickAndKey();
            game.changeState(new MenuState());
            return;
        }

        if (game.input.mouseClicked) {
            int mx = game.input.mouseX;
            int my = game.input.mouseY;

            if (my >= game.screenHeight / 2 - 50 && my <= game.screenHeight / 2 + 50) {
                if (mx >= 100 && mx <= 200) {
                    selectedIndex = (selectedIndex - 1 + classes.length) % classes.length;
                } else if (mx >= game.screenWidth - 200 && mx <= game.screenWidth - 100) {
                    selectedIndex = (selectedIndex + 1) % classes.length;
                }
            }

            int btnX = game.screenWidth / 2 - 150;
            int btnY = game.screenHeight - 150;
            if (mx >= btnX && mx <= btnX + 300 && my >= btnY && my <= btnY + 60) {
                CharacterClass c = classes[selectedIndex];
                if (PlayerData.unlockedClasses.contains(c)) {
                    PlayerData.selectedClass = c;
                    PlayerData.save();
                    game.startNewGame();
                } else {
                    if (PlayerData.gold >= c.unlockCost) {
                        PlayerData.gold -= c.unlockCost;
                        PlayerData.unlockedClasses.add(c);
                        PlayerData.save();
                    }
                }
            }
            game.input.clearClickAndKey();
        }
    }

    @Override
    public void render(GamePanel game, Graphics g) {
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(0, 0, game.screenWidth, game.screenHeight);
        
        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFont(40f));
        g.drawString("SELECT CHARACTER", game.screenWidth / 2 - 200, 100);

        g.setColor(Color.YELLOW);
        g.setFont(FontManager.getFont(24f));
        g.drawString("Gold: " + PlayerData.gold + "   Souls: " + PlayerData.soulStones, 50, 50);

        CharacterClass c = classes[selectedIndex];

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(100, game.screenHeight / 2 - 50, 100, 100);
        g.fillRect(game.screenWidth - 200, game.screenHeight / 2 - 50, 100, 100);
        g.setColor(Color.BLACK);
        g.drawString("<", 140, game.screenHeight / 2 + 10);
        g.drawString(">", game.screenWidth - 160, game.screenHeight / 2 + 10);

        g.setColor(Color.CYAN);
        g.setFont(FontManager.getFont(50f));
        g.drawString(c.name, game.screenWidth / 2 - 150, game.screenHeight / 2 - 100);

        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFont(24f));
        g.drawString("HP: " + c.baseHp, game.screenWidth / 2 - 150, game.screenHeight / 2 - 20);
        g.drawString("Speed Multi: x" + c.speedMulti, game.screenWidth / 2 - 150, game.screenHeight / 2 + 20);
        g.drawString("Damage Multi: x" + c.damageMulti, game.screenWidth / 2 - 150, game.screenHeight / 2 + 60);
        
        String skillName = c.startingUpgrade == null ? "None" : c.startingUpgrade.name();
        g.drawString("Start Skill: " + skillName, game.screenWidth / 2 - 150, game.screenHeight / 2 + 100);

        int btnX = game.screenWidth / 2 - 150;
        int btnY = game.screenHeight - 150;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(btnX, btnY, 300, 60);
        
        g.setColor(Color.WHITE);
        g.drawRect(btnX, btnY, 300, 60);

        if (PlayerData.unlockedClasses.contains(c)) {
            g.setColor(Color.GREEN);
            g.drawString("PLAY", btnX + 110, btnY + 40);
        } else {
            g.setColor(PlayerData.gold >= c.unlockCost ? Color.YELLOW : Color.RED);
            g.drawString("UNLOCK: " + c.unlockCost + " G", btnX + 40, btnY + 40);
        }
        
        g.setColor(Color.WHITE);
        g.drawString("Press ESC to Back", game.screenWidth / 2 - 120, game.screenHeight - 40);
    }
}
