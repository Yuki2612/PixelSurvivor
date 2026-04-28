package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.FontManager;
import gameproject.Player;
import gameproject.skill.Upgrade;

public class UpgradeUI {
    public static void draw(Graphics g, int screenWidth, int screenHeight, int playerLevel, Upgrade[] currentUpgradeOptions, Player player) {
        g.setColor(new Color(0, 0, 0, 210));
        g.fillRect(0, 0, screenWidth, screenHeight);
        
        g.setColor(playerLevel % 3 == 0 ? Color.MAGENTA : Color.YELLOW);
        g.setFont(FontManager.getFont(45f));
        g.drawString(playerLevel % 3 == 0 ? "BREAKTHROUGH" : "UPGRADE", screenWidth / 2 - 140, screenHeight / 2 - 200);

        int boxWidth = 280, boxHeight = 260, spacing = 40; 
        int startX = (screenWidth - (3 * boxWidth + 2 * spacing)) / 2;
        int by = (screenHeight - boxHeight) / 2;

        for (int i = 0; i < 3; i++) {
            int bx = startX + i * (boxWidth + spacing);
            Upgrade u = currentUpgradeOptions[i];

            g.setColor(Color.DARK_GRAY);
            g.fillRect(bx, by, boxWidth, boxHeight);
            g.setColor(Color.WHITE);
            g.drawRect(bx, by, boxWidth, boxHeight);
            g.drawRect(bx, by, boxWidth, 100);

            g.setFont(FontManager.getFont(20f));
            g.drawString("Click to Pick", bx + 50, by + 135);

            g.setFont(FontManager.getFont(14f)); 
            String[] parts = u.description.split("\\(");
            g.drawString(parts[0].trim(), bx + 15, by + 170);
            if (parts.length > 1) {
                g.setColor(Color.CYAN);
                g.drawString("(" + parts[1], bx + 15, by + 200);
            }

            g.setColor(Color.YELLOW);
            g.drawString("Level: " + player.getUpgradeLevel(u) + "/" + u.maxLevel, bx + 15, by + 240);
        }
    }
}
