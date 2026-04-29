package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.FontManager;

public class SettingsUI {
    public static void draw(Graphics g, int screenWidth, int screenHeight, boolean showDamageText, boolean pendingReset,
            boolean isAdminMode, boolean showAdminInput, String inputStr) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, screenWidth, screenHeight);

        // --- Tiêu đề ---
        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFont(50f));
        g.drawString("SETTINGS", screenWidth / 2 - 150, screenHeight / 2 - 150);

        g.setFont(FontManager.getFont(20f));

        // --- Damage Text Toggle ---
        int btnX = screenWidth / 2 - 180;
        int btnY = screenHeight / 2 - 40;
        g.setColor(Color.WHITE);
        g.drawRect(btnX, btnY, 450, 50);
        g.setFont(FontManager.getFont(24f));
        g.setColor(showDamageText ? Color.GREEN : Color.RED);
        g.drawString("Damage Text: " + (showDamageText ? "ON" : "OFF"), btnX + 25, btnY + 35);

        g.setFont(FontManager.getFont(20f));

        int adminY = screenHeight / 2 + 50;

        if (isAdminMode) {
            // --- Admin: +1000 Gold ---
            int gBtnX = screenWidth / 2 - 200;
            g.setColor(Color.YELLOW);
            g.drawRect(gBtnX, adminY, 180, 40);
            g.drawString("+1000 GOLD", gBtnX + 20, adminY + 28);

            // --- Admin: +100 Souls ---
            int sBtnX = screenWidth / 2 + 20;
            g.setColor(Color.MAGENTA);
            g.drawRect(sBtnX, adminY, 180, 40);
            g.drawString("+100 SOULS", sBtnX + 25, adminY + 28);
        } else if (!showAdminInput) {
            // --- Nút mở Admin Mode ---
            int aBtnX = screenWidth / 2 - 120;
            g.setColor(Color.DARK_GRAY);
            g.fillRect(aBtnX, adminY, 240, 45);
            g.setColor(Color.CYAN);
            g.drawRect(aBtnX, adminY, 240, 45);
            g.drawString("UNLOCK ADMIN MODE", aBtnX + 15, adminY + 30);
        } else {
            // --- TextBox nhập Admin Mode ---
            g.setColor(Color.WHITE);
            g.drawString("Enter Password:", screenWidth / 2 - 220, adminY + 30);

            int boxX = screenWidth / 2 - 40;
            g.setColor(Color.BLACK);
            g.fillRect(boxX, adminY, 200, 45);
            g.setColor(Color.CYAN);
            g.drawRect(boxX, adminY, 200, 45);

            // Mask password if preferred, here we just show the string or masked
            String displayStr = inputStr;
            if (displayStr.length() > 6) {
                displayStr = displayStr.substring(displayStr.length() - 6);
            }
            // Mật khẩu che thành dấu *
            String masked = "*".repeat(displayStr.length());
            g.drawString(masked, boxX + 10, adminY + 30);
        }

        // --- Reset Button ---
        int rBtnX = screenWidth / 2 - 120;
        int rBtnY = screenHeight / 2 + 120;
        g.setColor(new java.awt.Color(200, 50, 50));
        g.fillRect(rBtnX, rBtnY, 240, 45);
        g.setColor(Color.WHITE);
        g.drawRect(rBtnX, rBtnY, 240, 45);
        g.setFont(FontManager.getFont(22f));
        g.drawString("RESET PROGRESS", rBtnX + 15, rBtnY + 31);

        // --- Gợi ý ---
        g.setColor(new java.awt.Color(150, 150, 150));
        g.setFont(FontManager.getFont(14f));
        g.drawString("(Resets Gold, Souls & all stat/skill upgrades)", screenWidth / 2 - 195, rBtnY + 62);

        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFont(20f));
        g.drawString("Press ESC to Return", screenWidth / 2 - 150, screenHeight / 2 + 220);

        // ==========================================================
        // --- Overlay xác nhận Reset ---
        // ==========================================================
        if (pendingReset) {
            // Nền mờ
            g.setColor(new java.awt.Color(0, 0, 0, 200));
            g.fillRect(0, 0, screenWidth, screenHeight);

            // Hộp xác nhận
            int boxW = 500, boxH = 220;
            int boxX = screenWidth / 2 - boxW / 2;
            int boxY = screenHeight / 2 - boxH / 2 - 20;
            g.setColor(new java.awt.Color(40, 20, 20));
            g.fillRect(boxX, boxY, boxW, boxH);
            g.setColor(new java.awt.Color(200, 50, 50));
            g.drawRect(boxX, boxY, boxW, boxH);

            // Tiêu đề
            g.setColor(Color.RED);
            g.setFont(FontManager.getFont(28f));
            g.drawString("⚠  CONFIRM RESET", boxX + 105, boxY + 50);

            // Mô tả
            g.setColor(Color.WHITE);
            g.setFont(FontManager.getFont(17f));
            g.drawString("This will reset: Gold, Souls,", boxX + 110, boxY + 90);
            g.drawString("all Stat levels & Skill soul upgrades.", boxX + 75, boxY + 115);
            g.setColor(new java.awt.Color(150, 255, 150));
            g.drawString("Default unlocked skills stay unlocked.", boxX + 83, boxY + 138);

            // Nút YES
            int yesX = screenWidth / 2 - 180;
            int yesY = screenHeight / 2 + 60;
            g.setColor(new java.awt.Color(180, 40, 40));
            g.fillRect(yesX, yesY, 140, 50);
            g.setColor(Color.WHITE);
            g.drawRect(yesX, yesY, 140, 50);
            g.setFont(FontManager.getFont(22f));
            g.drawString("YES, RESET", yesX + 8, yesY + 33);

            // Nút NO
            int noX = screenWidth / 2 + 40;
            g.setColor(new java.awt.Color(40, 120, 40));
            g.fillRect(noX, yesY, 140, 50);
            g.setColor(Color.WHITE);
            g.drawRect(noX, yesY, 140, 50);
            g.drawString("CANCEL", noX + 22, yesY + 33);
        }
    }
}
