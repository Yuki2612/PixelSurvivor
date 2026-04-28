package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.FontManager;

public class SettingsUI {
    public static void draw(Graphics g, int screenWidth, int screenHeight, boolean showDamageText) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, screenWidth, screenHeight);
        g.setColor(Color.WHITE);
        
        g.setFont(FontManager.getFont(50f));
        g.drawString("SETTINGS", screenWidth / 2 - 150, screenHeight / 2 - 150);
        
        int btnX = screenWidth / 2 - 180;
        int btnY = screenHeight / 2 - 40;
        g.drawRect(btnX, btnY, 450, 50);
        
        g.setFont(FontManager.getFont(24f));
        g.setColor(showDamageText ? Color.GREEN : Color.RED);
        g.drawString("Damage Text: " + (showDamageText ? "ON" : "OFF"), btnX + 25, btnY + 35);
        
        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFont(20f));
        
        // Admin: +1000 Gold
        int gBtnX = screenWidth / 2 - 200;
        int gBtnY = screenHeight / 2 + 50;
        g.setColor(Color.YELLOW);
        g.drawRect(gBtnX, gBtnY, 180, 40);
        g.drawString("+1000 GOLD", gBtnX + 20, gBtnY + 28);

        // Admin: +100 Souls
        int sBtnX = screenWidth / 2 + 20;
        g.setColor(Color.MAGENTA);
        g.drawRect(sBtnX, gBtnY, 180, 40);
        g.drawString("+100 SOULS", sBtnX + 25, gBtnY + 28);
        
        g.setColor(Color.WHITE);
        g.drawString("Press ESC to Return", screenWidth / 2 - 150, screenHeight / 2 + 200);
    }
}
