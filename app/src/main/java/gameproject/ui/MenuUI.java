package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.FontManager;

public class MenuUI {
    public static void draw(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, screenWidth, screenHeight);
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
        g.drawString("QUIT", btnX + 50, screenHeight / 2 + 220);
    }
}
