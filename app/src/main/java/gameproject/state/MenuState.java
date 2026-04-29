package gameproject.state;

import java.awt.Graphics;
import gameproject.GamePanel;
import gameproject.ui.MenuUI;

public class MenuState implements State {
    @Override
    public void update(GamePanel game) {
        if (game.input.mouseClicked) {
            int mx = game.input.mouseX;
            int my = game.input.mouseY;
            int btnX = game.screenWidth / 2 - 100;
            if (mx >= btnX && mx <= btnX + 200) {
                if (my >= game.screenHeight / 2 - 100 && my <= game.screenHeight / 2 - 50) {
                    game.changeState(new CharacterSelectState());
                } else if (my >= game.screenHeight / 2 - 30 && my <= game.screenHeight / 2 + 20) {
                    game.changeState(new StatsState());
                } else if (my >= game.screenHeight / 2 + 40 && my <= game.screenHeight / 2 + 90) {
                    game.changeState(new SkillsState());
                } else if (my >= game.screenHeight / 2 + 110 && my <= game.screenHeight / 2 + 160) {
                    game.changeState(new SettingsState());
                } else if (my >= game.screenHeight / 2 + 180 && my <= game.screenHeight / 2 + 230) {
                    game.changeState(new GuideState());
                } else if (my >= game.screenHeight / 2 + 250 && my <= game.screenHeight / 2 + 300) {
                    gameproject.meta.PlayerData.save();
                    System.exit(0);
                }
            }
            game.input.clearClickAndKey();
        }
    }

    @Override
    public void render(GamePanel game, Graphics g) {
        MenuUI.draw(g, game.screenWidth, game.screenHeight);
    }
}
