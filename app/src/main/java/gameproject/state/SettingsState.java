package gameproject.state;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.GamePanel;
import gameproject.FontManager;
import gameproject.ui.SettingsUI;

public class SettingsState implements State {
    private boolean pendingReset = false; // Chờ xác nhận reset
    private boolean isAdminMode = false;
    private boolean showAdminInput = false;

    @Override
    public void update(GamePanel game) {
        if (showAdminInput) {
            if (game.input.typedKeySequence.endsWith("010206")) {
                isAdminMode = true;
                showAdminInput = false;
            }
        } else if (game.input.typedKeySequence.endsWith("010206")) {
            isAdminMode = true;
        }

        if (game.input.escPressed) {
            if (pendingReset) {
                pendingReset = false; // Hủy xác nhận
            } else {
                game.input.clearClickAndKey();
                game.changeState(new MenuState());
                return;
            }
            game.input.clearClickAndKey();
            return;
        }

        if (game.input.mouseClicked) {
            int mx = game.input.mouseX;
            int my = game.input.mouseY;

            if (!pendingReset) {
                // --- Nút Damage Text ---
                int btnX = game.screenWidth / 2 - 180;
                int btnY = game.screenHeight / 2 - 40;
                if (mx >= btnX && mx <= btnX + 450 && my >= btnY && my <= btnY + 50) {
                    game.vfxManager.showDamageText = !game.vfxManager.showDamageText;
                }

                // --- Admin logic ---
                int adminY = game.screenHeight / 2 + 50;
                
                if (isAdminMode) {
                    // --- Nút Admin: +1000 Gold ---
                    int gBtnX = game.screenWidth / 2 - 200;
                    if (mx >= gBtnX && mx <= gBtnX + 180 && my >= adminY && my <= adminY + 40) {
                        gameproject.meta.PlayerData.gold += 1000;
                    }

                    // --- Nút Admin: +100 Souls ---
                    int sBtnX = game.screenWidth / 2 + 20;
                    if (mx >= sBtnX && mx <= sBtnX + 180 && my >= adminY && my <= adminY + 40) {
                        gameproject.meta.PlayerData.soulStones += 100;
                    }
                } else if (!showAdminInput) {
                    // --- Nút mở Admin Mode ---
                    int aBtnX = game.screenWidth / 2 - 120;
                    if (mx >= aBtnX && mx <= aBtnX + 240 && my >= adminY && my <= adminY + 45) {
                        showAdminInput = true;
                        game.input.typedKeySequence = "";
                    }
                }

                // --- Nút Reset (mở hộp xác nhận) ---
                int rBtnX = game.screenWidth / 2 - 120;
                int rBtnY = game.screenHeight / 2 + 120;
                if (mx >= rBtnX && mx <= rBtnX + 240 && my >= rBtnY && my <= rBtnY + 45) {
                    pendingReset = true;
                }

            } else {
                // --- Hộp xác nhận: YES ---
                int yesX = game.screenWidth / 2 - 180;
                int yesY = game.screenHeight / 2 + 60;
                if (mx >= yesX && mx <= yesX + 140 && my >= yesY && my <= yesY + 50) {
                    performReset();
                    pendingReset = false;
                }

                // --- Hộp xác nhận: NO ---
                int noX = game.screenWidth / 2 + 40;
                if (mx >= noX && mx <= noX + 140 && my >= yesY && my <= yesY + 50) {
                    pendingReset = false;
                }
            }

            game.input.clearClickAndKey();
        }
    }

    private void performReset() {
        gameproject.meta.PlayerData.gold = 0;
        gameproject.meta.PlayerData.soulStones = 0;
        gameproject.meta.PlayerData.statHealthLevel = 0;
        gameproject.meta.PlayerData.statDamageLevel = 0;
        gameproject.meta.PlayerData.statSpeedLevel = 0;
        gameproject.meta.PlayerData.statDashLevel = 0;
        gameproject.meta.PlayerData.statCritLevel = 0;
        gameproject.meta.PlayerData.statCooldownLevel = 0;
        gameproject.meta.PlayerData.skillSoulLevels.clear(); // Reset soul multiplier về 0, skill vẫn mở khóa
        gameproject.meta.PlayerData.save();
    }

    @Override
    public void render(GamePanel game, Graphics g) {
        String inputStr = showAdminInput ? game.input.typedKeySequence : "";
        SettingsUI.draw(g, game.screenWidth, game.screenHeight, game.vfxManager.showDamageText, pendingReset, isAdminMode, showAdminInput, inputStr);
    }
}
