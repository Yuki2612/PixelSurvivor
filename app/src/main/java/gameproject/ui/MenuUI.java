package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.FontManager;

public class MenuUI {
    public static void draw(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, screenWidth, screenHeight);
        
        // --- Resources at Top Right ---
        int resX = screenWidth - 280;
        int resY = 20;
        int resW = 250;
        int resH = 80;
        
        g.setColor(new Color(30, 30, 40, 200));
        g.fillRoundRect(resX, resY, resW, resH, 20, 20);
        g.setColor(new Color(100, 100, 150));
        g.drawRoundRect(resX, resY, resW, resH, 20, 20);

        java.awt.image.BufferedImage goldImg = gameproject.ImageManager.get("gold");
        java.awt.image.BufferedImage soulImg = gameproject.ImageManager.get("soul");

        g.setFont(FontManager.getFont(20f));
        if (goldImg != null) {
            g.drawImage(goldImg, resX + 20, resY + 12, 24, 24, null);
            g.setColor(Color.YELLOW);
            g.drawString("" + gameproject.meta.PlayerData.gold, resX + 55, resY + 32);
        } else {
            g.setColor(Color.YELLOW);
            g.drawString("G: " + gameproject.meta.PlayerData.gold, resX + 20, resY + 32);
        }

        if (soulImg != null) {
            g.drawImage(soulImg, resX + 20, resY + 45, 24, 24, null);
            g.setColor(Color.CYAN);
            g.drawString("" + gameproject.meta.PlayerData.soulStones, resX + 55, resY + 65);
        } else {
            g.setColor(Color.CYAN);
            g.drawString("S: " + gameproject.meta.PlayerData.soulStones, resX + 20, resY + 65);
        }

        g.setColor(Color.WHITE);
        
        g.setFont(FontManager.getFont(60f));
        g.drawString("PIXEL SURVIVOR", screenWidth / 2 - 250, screenHeight / 2 - 200);
        
        g.setFont(FontManager.getFont(30f));
        int btnX = screenWidth / 2 - 100;
        g.drawRect(btnX, screenHeight / 2 - 100, 200, 50);
        g.drawString("START", btnX + 35, screenHeight / 2 - 60);
        
        g.drawRect(btnX, screenHeight / 2 - 30, 200, 50);
        g.drawString("STATS", btnX + 45, screenHeight / 2 + 10);
        
        g.drawRect(btnX, screenHeight / 2 + 40, 200, 50);
        g.drawString("SKILLS", btnX + 45, screenHeight / 2 + 80);

        g.drawRect(btnX, screenHeight / 2 + 110, 200, 50);
        g.drawString("SETTINGS", btnX + 15, screenHeight / 2 + 150);
        
        g.drawRect(btnX, screenHeight / 2 + 180, 200, 50);
        g.drawString("GUIDE", btnX + 50, screenHeight / 2 + 220);

        g.drawRect(btnX, screenHeight / 2 + 250, 200, 50);
        g.drawString("QUIT", btnX + 55, screenHeight / 2 + 290);
    }
}
