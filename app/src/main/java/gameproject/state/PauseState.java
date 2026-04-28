package gameproject.state;

import java.awt.Graphics;
import gameproject.GamePanel;
import gameproject.ui.PauseGameOverUI;

public class PauseState implements State {
    @Override
    public void update(GamePanel game) {
        if (game.input.escPressed) {
            game.input.clearClickAndKey();
            game.changeState(new PlayingState());
            return;
        }

        if (game.input.mouseClicked) {
            int mx = game.input.mouseX;
            int my = game.input.mouseY;
            int btnX = game.screenWidth / 2 - 120;
            if (mx >= btnX && mx <= btnX + 240) {
                if (my >= game.screenHeight / 2 - 50 && my <= game.screenHeight / 2 + 10)
                    game.changeState(new PlayingState());
                else if (my >= game.screenHeight / 2 + 30 && my <= game.screenHeight / 2 + 90) {
                    gameproject.meta.PlayerData.save();
                    game.changeState(new MenuState());
                } else if (my >= game.screenHeight / 2 + 110 && my <= game.screenHeight / 2 + 170) {
                    gameproject.meta.PlayerData.save();
                    System.exit(0);
                }
            }
            game.input.clearClickAndKey();
        }
    }

    @Override
    public void render(GamePanel game, Graphics g) {
        PauseGameOverUI.drawPaused(g, game.screenWidth, game.screenHeight);
    }
}
