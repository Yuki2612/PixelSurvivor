package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import gameproject.FontManager;

public class SettingsUI {
    public static void draw(Graphics g, int screenWidth, int screenHeight, boolean showDamageText, boolean showHitboxes, boolean pendingReset,
            boolean isAdminMode, boolean showAdminInput, String inputStr) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Nền tối mờ
        g2d.setColor(new Color(15, 15, 20, 240));
        g2d.fillRect(0, 0, screenWidth, screenHeight);

        // Khung chính: 1150x700
        int mainW = 1150;
        int mainH = 700;
        int mainX = screenWidth / 2 - mainW / 2;
        int mainY = screenHeight / 2 - mainH / 2;
        
        g2d.setColor(new Color(40, 40, 50));
        g2d.fillRoundRect(mainX, mainY, mainW, mainH, 30, 30);
        g2d.setColor(new Color(100, 100, 120));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(mainX, mainY, mainW, mainH, 30, 30);

        // Tiêu đề đơn giản
        g2d.setColor(Color.WHITE);
        g2d.setFont(FontManager.getFont(45f));
        g2d.drawString("SETTINGS", screenWidth / 2 - 100, mainY + 70);

        // --- CHIA CỘT TRÊN ---
        int colY = mainY + 120;
        int colW = 500;
        int leftX = mainX + 50;
        int rightX = mainX + 600;

        // --- Ô BÊN TRÁI: ÂM THANH ---
        drawBox(g2d, "AUDIO", leftX, colY, colW, 200);
        float sfxVol = gameproject.SoundManager.getSfxVolume();
        float musVol = gameproject.SoundManager.getMusicVolume();
        drawSlider(g2d, "Sound FX", leftX + 40, colY + 60, 420, sfxVol, new Color(0, 180, 255));
        drawSlider(g2d, "Music", leftX + 40, colY + 140, 420, musVol, new Color(180, 100, 255));

        // --- Ô BÊN PHẢI: HIỂN THỊ ---
        drawBox(g2d, "DISPLAY", rightX, colY, colW, 200);
        drawToggle(g2d, "Damage Numbers", rightX + 40, colY + 50, 420, 50, showDamageText);
        drawToggle(g2d, "Show Hitboxes", rightX + 40, colY + 120, 420, 50, showHitboxes);

        // --- PHẦN DƯỚI: ADMIN TOOLS ---
        int adminY = colY + 230;
        drawSectionHeader(g2d, "ADMIN TOOLS", mainX + 50, adminY, mainW - 100);

        if (isAdminMode) {
            int cardW = 250;
            int cardH = 80;
            int cardX = mainX + 65;
            int cardY = adminY + 30;
            int spacing = 20;

            drawAdminCard(g2d, "GOLD", "+1000", cardX, cardY, cardW, cardH, Color.YELLOW);
            drawAdminCard(g2d, "SOULS", "+100", cardX + (cardW+spacing), cardY, cardW, cardH, Color.MAGENTA);
            drawAdminCard(g2d, "WAVE", "Wave: " + gameproject.meta.PlayerData.debugStartWave, cardX + (cardW+spacing)*2, cardY, cardW, cardH, Color.CYAN);
            drawAdminCard(g2d, "LEVEL", "Lv: " + gameproject.meta.PlayerData.debugStartLevel, cardX + (cardW+spacing)*3, cardY, cardW, cardH, Color.ORANGE);
        } else if (!showAdminInput) {
            int aBtnX = screenWidth / 2 - 150;
            int aBtnY = adminY + 40;
            g2d.setColor(new Color(30, 80, 100));
            g2d.fillRoundRect(aBtnX, aBtnY, 300, 50, 15, 15);
            g2d.setColor(Color.CYAN);
            g2d.drawRoundRect(aBtnX, aBtnY, 300, 50, 15, 15);
            g2d.setFont(FontManager.getFont(20f));
            g2d.setColor(Color.WHITE);
            g2d.drawString("UNLOCK ADMIN TOOLS", aBtnX + 35, aBtnY + 33);
        } else {
            int boxX = screenWidth / 2 - 150;
            int boxY = adminY + 30;
            g2d.setColor(Color.WHITE);
            g2d.setFont(FontManager.getFont(18f));
            g2d.drawString("Enter Admin Key:", boxX, boxY + 20);
            g2d.setColor(Color.BLACK);
            g2d.fillRoundRect(boxX, boxY + 35, 300, 50, 10, 10);
            g2d.setColor(Color.CYAN);
            g2d.drawRoundRect(boxX, boxY + 35, 300, 50, 10, 10);
            String masked = "*".repeat(Math.min(inputStr.length(), 10));
            g2d.setFont(FontManager.getFont(24f));
            g2d.setColor(Color.WHITE);
            g2d.drawString(masked, boxX + 15, boxY + 70);
        }

        // --- PHẦN DƯỚI CÙNG: DỮ LIỆU ---
        int dataY = adminY + 160;
        drawSectionHeader(g2d, "DATA MANAGEMENT", mainX + 50, dataY, mainW - 100);

        int rBtnX = screenWidth / 2 - 200;
        int rBtnY = dataY + 40;
        g2d.setColor(new Color(80, 20, 20));
        g2d.fillRoundRect(rBtnX, rBtnY, 400, 60, 15, 15);
        g2d.setColor(new Color(255, 50, 50));
        g2d.drawRoundRect(rBtnX, rBtnY, 400, 60, 15, 15);
        g2d.setFont(FontManager.getFont(22f));
        g2d.setColor(Color.WHITE);
        g2d.drawString("RESET ALL PROGRESS", rBtnX + 75, rBtnY + 38);

        // Footer
        g2d.setFont(FontManager.getFont(18f));
        g2d.setColor(new Color(200, 200, 200));
        g2d.drawString("Press ESC to Save & Return", screenWidth / 2 - 130, mainY + mainH - 30);

        if (pendingReset) drawResetOverlay(g2d, screenWidth, screenHeight);
    }

    private static void drawBox(Graphics2D g2d, String title, int x, int y, int w, int h) {
        g2d.setColor(new Color(50, 50, 65));
        g2d.fillRoundRect(x, y, w, h, 20, 20);
        g2d.setColor(new Color(80, 80, 100));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x, y, w, h, 20, 20);
        
        g2d.setFont(FontManager.getFont(18f));
        g2d.setColor(new Color(150, 150, 200));
        g2d.drawString(title, x + 20, y + 25);
    }

    private static void drawSectionHeader(Graphics2D g2d, String text, int x, int y, int w) {
        g2d.setFont(FontManager.getFont(16f));
        g2d.setColor(new Color(180, 180, 200));
        g2d.drawString(text, x, y);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(x, y + 10, x + w, y + 10);
    }

    private static void drawToggle(Graphics2D g2d, String text, int x, int y, int w, int h, boolean enabled) {
        g2d.setColor(new Color(65, 65, 85));
        g2d.fillRoundRect(x, y, w, h, 10, 10);
        g2d.setColor(enabled ? Color.GREEN : Color.RED);
        g2d.drawRoundRect(x, y, w, h, 10, 10);
        g2d.setFont(FontManager.getFont(20f));
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x + 20, y + 33);
        g2d.setColor(enabled ? Color.GREEN : Color.RED);
        g2d.drawString(enabled ? "ON" : "OFF", x + w - 60, y + 33);
    }

    private static void drawSlider(Graphics2D g2d, String label, int x, int y, int w, float val, Color accent) {
        g2d.setFont(FontManager.getFont(18f));
        g2d.setColor(Color.WHITE);
        g2d.drawString(label + ": " + (int)(val * 100) + "%", x, y - 10);
        g2d.setColor(new Color(60, 60, 75));
        g2d.fillRoundRect(x, y, w, 12, 6, 6);
        g2d.setColor(accent);
        g2d.fillRoundRect(x, y, (int)(w * val), 12, 6, 6);
        int kX = x + (int)(w * val);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(kX - 12, y - 6, 24, 24);
    }

    private static void drawAdminCard(Graphics2D g2d, String title, String val, int x, int y, int w, int h, Color accent) {
        g2d.setColor(new Color(55, 55, 75));
        g2d.fillRoundRect(x, y, w, h, 15, 15);
        g2d.setColor(accent);
        g2d.drawRoundRect(x, y, w, h, 15, 15);
        g2d.setFont(FontManager.getFont(16f));
        g2d.setColor(new Color(200, 200, 200));
        g2d.drawString(title, x + 15, y + 25);
        g2d.setFont(FontManager.getFont(22f));
        g2d.setColor(accent);
        g2d.drawString(val, x + 15, y + 60);
    }

    private static void drawResetOverlay(Graphics2D g2d, int sw, int sh) {
        g2d.setColor(new Color(0, 0, 0, 220));
        g2d.fillRect(0, 0, sw, sh);
        int bw = 600, bh = 300;
        int bx = sw / 2 - bw / 2, by = sh / 2 - bh / 2;
        g2d.setColor(new Color(40, 10, 10));
        g2d.fillRoundRect(bx, by, bw, bh, 30, 30);
        g2d.setColor(Color.RED);
        g2d.drawRoundRect(bx, by, bw, bh, 30, 30);
        g2d.setFont(FontManager.getFont(30f));
        g2d.setColor(Color.WHITE);
        g2d.drawString("⚠ WARNING", bx + 220, by + 70);
        g2d.setFont(FontManager.getFont(20f));
        g2d.drawString("Reset all game progress?", bx + 180, by + 130);
        int btnW = 160, btnH = 50;
        g2d.setColor(new Color(180, 40, 40));
        g2d.fillRoundRect(sw / 2 - 180, by + 210, btnW, btnH, 15, 15);
        g2d.setColor(new Color(40, 150, 40));
        g2d.fillRoundRect(sw / 2 + 20, by + 210, btnW, btnH, 15, 15);
        g2d.setColor(Color.WHITE);
        g2d.setFont(FontManager.getFont(22f));
        g2d.drawString("YES", sw / 2 - 125, by + 243);
        g2d.drawString("NO", sw / 2 + 80, by + 243);
    }
}
