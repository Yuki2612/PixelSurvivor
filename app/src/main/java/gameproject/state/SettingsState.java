package gameproject.state;

import java.awt.Graphics;
import gameproject.GamePanel;
import gameproject.ui.SettingsUI;

public class SettingsState implements State {
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
            int btnX = game.screenWidth / 2 - 180;
            int btnY = game.screenHeight / 2 - 40;
            if (mx >= btnX && mx <= btnX + 450 && my >= btnY && my <= btnY + 50) {
                game.vfxManager.showDamageText = !game.vfxManager.showDamageText;
            }

            int gBtnX = game.screenWidth / 2 - 200;
            int gBtnY = game.screenHeight / 2 + 50;
            if (mx >= gBtnX && mx <= gBtnX + 180 && my >= gBtnY && my <= gBtnY + 40) {
                gameproject.meta.PlayerData.gold += 1000;
            }

            int sBtnX = game.screenWidth / 2 + 20;
            if (mx >= sBtnX && mx <= sBtnX + 180 && my >= gBtnY && my <= gBtnY + 40) {
                gameproject.meta.PlayerData.soulStones += 100;
            }

            game.input.clearClickAndKey();
        }
    }

    @Override
    public void render(GamePanel game, Graphics g) {
        SettingsUI.draw(g, game.screenWidth, game.screenHeight, game.vfxManager.showDamageText);
    }
}
