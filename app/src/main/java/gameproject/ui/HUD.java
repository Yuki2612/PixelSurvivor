package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import gameproject.FontManager;
import gameproject.ImageManager;
import gameproject.Player;
import gameproject.entity.Enemy;

public class HUD {
    public static void draw(Graphics g, int screenWidth, int screenHeight, int score, int waveCount,
            int playerDamage, long fireRate, Player player, int currentExp, int expToNextLevel, int playerLevel,
            ArrayList<Enemy> enemies) {
        
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

        // Boss HP bar (ở giữa cạnh trên màn hình)
        for (Enemy e : enemies) {
            if (e.isBoss && !e.isDying) {
                int bBarW = 500;
                int bBarH = 22;
                int bBarX = screenWidth / 2 - bBarW / 2;
                int bBarY = 16;

                g.setColor(new Color(0, 0, 0, 160));
                g.fillRoundRect(bBarX - 4, bBarY - 4, bBarW + 8, bBarH + 8, 8, 8);
                g.setColor(new Color(100, 0, 0));
                g.fillRoundRect(bBarX, bBarY, bBarW, bBarH, 6, 6);
                int hpW = (int)((float) e.getHp() / e.getMaxHp() * bBarW);
                if (hpW > 0) {
                    g.setColor(new Color(220, 50, 50));
                    g.fillRoundRect(bBarX, bBarY, hpW, bBarH, 6, 6);
                }
                g.setColor(Color.WHITE);
                g.drawRoundRect(bBarX, bBarY, bBarW, bBarH, 6, 6);

                g.setFont(FontManager.getFont(14f));
                String bossLabel = "BOSS  " + Math.max(0, e.getHp()) + " / " + e.getMaxHp();
                int labelW = g.getFontMetrics().stringWidth(bossLabel);
                g.setColor(Color.BLACK);
                g.drawString(bossLabel, screenWidth / 2 - labelW / 2 + 1, bBarY + 16);
                g.setColor(Color.WHITE);
                g.drawString(bossLabel, screenWidth / 2 - labelW / 2, bBarY + 15);
                break;
            }
        }
        drawMinimap(g, screenWidth, player, enemies);
    }

    private static void drawMinimap(Graphics g, int screenWidth, Player player, ArrayList<Enemy> enemies) {
        int mapSize = 150;
        int padding = 20;
        int mapX = screenWidth - mapSize - padding;
        int mapY = 20;

        // Vẽ nền minimap
        g.setColor(new Color(0, 0, 0, 150)); // Đen bán trong suốt
        g.fillRect(mapX, mapY, mapSize, mapSize);
        g.setColor(Color.WHITE);
        g.drawRect(mapX, mapY, mapSize, mapSize);

        float scaleX = (float) mapSize / gameproject.GamePanel.WORLD_WIDTH;
        float scaleY = (float) mapSize / gameproject.GamePanel.WORLD_HEIGHT;

        // Vẽ quái (chấm đỏ)
        g.setColor(Color.RED);
        for (Enemy e : enemies) {
            if (e.isDead()) continue;
            int ex = mapX + (int) (e.getX() * scaleX);
            int ey = mapY + (int) (e.getY() * scaleY);
            // Kích thước chấm: 3x3 cho boss, 2x2 cho quái thường
            int dotSize = e.isBoss ? 4 : 2;
            g.fillRect(ex, ey, dotSize, dotSize);
        }

        // Vẽ người chơi (chấm trắng)
        g.setColor(Color.WHITE);
        int px = mapX + (int) (player.getX() * scaleX);
        int py = mapY + (int) (player.getY() * scaleY);
        g.fillRect(px - 1, py - 1, 4, 4);
    }
}
