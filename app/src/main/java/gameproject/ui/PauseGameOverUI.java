package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.FontManager;

public class PauseGameOverUI {
    public static void drawPaused(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, screenWidth, screenHeight);
        
        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFont(65f));
        g.drawString("PAUSED", screenWidth / 2 - 100, screenHeight / 2 - 150);
        
        g.setFont(FontManager.getFont(32f));
        int btnX = screenWidth / 2 - 120;
        
        g.drawRect(btnX, screenHeight / 2 - 50, 240, 60);
        g.drawString("RESUME", btnX + 35, screenHeight / 2 - 10);
        
        g.drawRect(btnX, screenHeight / 2 + 30, 240, 60);
        g.drawString("MENU", btnX + 60, screenHeight / 2 + 70);
        
        g.drawRect(btnX, screenHeight / 2 + 110, 240, 60);
        g.drawString("QUIT", btnX + 70, screenHeight / 2 + 150);
    }

    public static void drawGameOver(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, screenWidth, screenHeight);
        
        g.setColor(Color.RED);
        g.setFont(FontManager.getFont(70f));
        g.drawString("GAME OVER", screenWidth / 2 - 260, screenHeight / 2 - 50);
        
        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFont(35f));
        g.drawString("Press 'R' to Restart", screenWidth / 2 - 250, screenHeight / 2 + 50);
    }
}
