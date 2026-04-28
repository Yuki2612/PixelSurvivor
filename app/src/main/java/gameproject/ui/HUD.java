package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.FontManager;
import gameproject.ImageManager;
import gameproject.Player;

public class HUD {
    public static void draw(Graphics g, int screenWidth, int screenHeight, int score, int waveCount,
            int playerDamage, long fireRate, Player player, int currentExp, int expToNextLevel, int playerLevel) {
        
        g.setFont(FontManager.getFont(20f));

        // Vẽ chữ đổ bóng đen trước
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 15, 35);
        g.drawString("Wave: " + waveCount, 200, 35); 
        g.drawString("ATK: " + playerDamage, screenWidth - 350, 35);
        g.drawString("Fire Rate: " + fireRate + "ms", screenWidth - 350, 70); 

        // Vẽ chữ trắng đè lên
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 13, 33);
        g.drawString("Wave: " + waveCount, 198, 33);
        g.drawString("ATK: " + playerDamage, screenWidth - 352, 33);
        g.drawString("Fire Rate: " + fireRate + "ms", screenWidth - 352, 68);

        // Chữ HP
        g.setColor(Color.BLACK);
        g.drawString("HP:", 15, 112);
        g.setColor(Color.WHITE);
        g.drawString("HP:", 13, 110);

        // Trái tim
        java.awt.image.BufferedImage heartImg = ImageManager.get("heart");
        for (int i = 0; i < player.getHearts(); i++) {
            int hX = 70 + (i * 30);
            int hY = 90; 
            if (heartImg != null)
                g.drawImage(heartImg, hX, hY, 26, 26, null); 
            else {
                g.setColor(Color.RED);
                g.fillRect(hX, hY, 20, 20);
            }
        }

        // Chữ Dash
        long timeSinceLastDash = System.currentTimeMillis() - player.getLastDashTime();
        g.setColor(Color.BLACK);
        g.drawString("Dash:", 15, 155);
        g.setColor(Color.WHITE);
        g.drawString("Dash:", 13, 153);

        if (timeSinceLastDash >= player.getDashCooldown()) {
            g.setColor(Color.BLACK);
            g.drawString("READY", 102, 155);
            g.setColor(Color.GREEN);
            g.drawString("READY", 100, 153);
        } else {
            g.setColor(Color.BLACK);
            g.drawString("WAIT", 102, 155);
            g.setColor(Color.GRAY);
            g.drawString("WAIT", 100, 153);
        }

        // Thanh EXP
        int barHeight = 26; 
        int barWidth = screenWidth - 60;
        int barX = 30;
        int barY = screenHeight - 60; 

        g.setColor(Color.BLACK);
        g.fillRect(barX - 2, barY - 2, barWidth + 4, barHeight + 4);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(barX, barY, barWidth, barHeight);
        g.setColor(new Color(0, 200, 255));
        int currentExpWidth = (int) (((float) currentExp / expToNextLevel) * barWidth);
        g.fillRect(barX, barY, currentExpWidth, barHeight);

        // Chữ Level
        g.setFont(FontManager.getFont(18f));
        String expText = "Lv." + playerLevel + "  [" + currentExp + " / " + expToNextLevel + " EXP]";
        g.setColor(Color.BLACK);
        g.drawString(expText, screenWidth / 2 - 140 + 2, barY + 20 + 2);
        g.setColor(Color.WHITE);
        g.drawString(expText, screenWidth / 2 - 140, barY + 20);
    }
}
