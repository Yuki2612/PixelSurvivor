package gameproject.state;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import gameproject.GamePanel;
import gameproject.FontManager;
import gameproject.meta.PlayerData;
import gameproject.skill.Upgrade;

public class SkillsState implements State {
    private List<Upgrade> breakthroughSkills;
    
    public SkillsState() {
        breakthroughSkills = new ArrayList<>();
        for (Upgrade u : Upgrade.values()) {
            if (u.isBreakthrough) {
                breakthroughSkills.add(u);
            }
        }
    }

    @Override
    public void update(GamePanel game) {
        if (game.input.escPressed) {
            PlayerData.save();
            game.changeState(new MenuState());
            game.input.clearClickAndKey();
            return;
        }

        if (game.input.mouseClicked) {
            int mx = game.input.mouseX;
            int my = game.input.mouseY;

            int columns = 3;
            int boxW = 200;
            int boxH = 220;
            int padding = 40;
            int startX = (game.screenWidth - (columns * boxW + (columns - 1) * padding)) / 2;
            int startY = 150;

            for (int i = 0; i < breakthroughSkills.size(); i++) {
                int row = i / columns;
                int col = i % columns;
                int bx = startX + col * (boxW + padding);
                int by = startY + row * (boxH + padding);

                // Nút Unlock / Upgrade nằm ở phần dưới của Box
                int btnX = bx + 25;
                int btnY = by + boxH - 45;
                int btnW = 150;
                int btnH = 35;

                if (mx >= btnX && mx <= btnX + btnW && my >= btnY && my <= btnY + btnH) {
                    Upgrade u = breakthroughSkills.get(i);
                    int level = PlayerData.skillSoulLevels.getOrDefault(u, 0);
                    int maxSoulLevel = 10;
                    
                    if (level < maxSoulLevel) {
                        int cost = 50 * (level + 1);
                        if (PlayerData.soulStones >= cost) {
                            PlayerData.soulStones -= cost;
                            PlayerData.skillSoulLevels.put(u, level + 1);
                        }
                    }
                }
            }

            // Nút Back
            if (mx >= 50 && mx <= 150 && my >= 50 && my <= 90) {
                PlayerData.save();
                game.changeState(new MenuState());
            }

            game.input.clearClickAndKey();
        }
    }

    @Override
    public void render(GamePanel game, Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, game.screenWidth, game.screenHeight);

        g.setColor(Color.CYAN);
        g.setFont(FontManager.getFont(40f));
        g.drawString("SKILL UNLOCKS", game.screenWidth / 2 - 150, 80);

        g.setColor(Color.MAGENTA);
        g.setFont(FontManager.getFont(20f));
        g.drawString("Souls: " + PlayerData.soulStones, game.screenWidth - 200, 50);

        int columns = 3;
        int boxW = 200;
        int boxH = 220;
        int padding = 40;
        int startX = (game.screenWidth - (columns * boxW + (columns - 1) * padding)) / 2;
        int startY = 150;

        g.setFont(FontManager.getFont(16f));
        for (int i = 0; i < breakthroughSkills.size(); i++) {
            int row = i / columns;
            int col = i % columns;
            int bx = startX + col * (boxW + padding);
            int by = startY + row * (boxH + padding);

            Upgrade u = breakthroughSkills.get(i);
            boolean isUnlocked = true; // First 6 skills are unlocked by default
            int level = PlayerData.skillSoulLevels.getOrDefault(u, 0);
            int maxSoulLevel = 10;

            // Box nền
            g.setColor(new Color(50, 50, 50));
            g.fillRect(bx, by, boxW, boxH);
            g.setColor(isUnlocked ? Color.CYAN : Color.GRAY);
            g.drawRect(bx, by, boxW, boxH);

            // Tên skill
            g.setColor(Color.WHITE);
            String name = u.description.split("\\(")[0].trim();
            g.drawString(name, bx + 15, by + 30);

            // Placeholder ảnh
            g.setColor(Color.BLACK);
            g.fillRect(bx + 50, by + 50, 100, 80);
            g.setColor(Color.WHITE);
            g.drawString("IMAGE", bx + 78, by + 95);

            // Level / Nút Upgrade
            int btnX = bx + 25;
            int btnY = by + boxH - 45;
            int btnW = 150;
            int btnH = 35;

            g.setColor(Color.YELLOW);
            g.drawString("Base Lv: " + level + "/" + maxSoulLevel, bx + 45, by + 155);
            
            if (level < maxSoulLevel) {
                int cost = 50 * (level + 1);
                if (PlayerData.soulStones >= cost) g.setColor(Color.GREEN);
                else g.setColor(Color.GRAY);
                
                g.drawRect(btnX, btnY, btnW, btnH);
                g.drawString("Upgrade: " + cost, btnX + 32, btnY + 23);
            } else {
                g.setColor(Color.GRAY);
                g.drawRect(btnX, btnY, btnW, btnH);
                g.drawString("MAXED", btnX + 48, btnY + 23);
            }
        }
        
        g.setColor(Color.WHITE);
        g.drawRect(50, 50, 100, 40);
        g.drawString("BACK", 70, 75);
    }
}
