package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.FontManager;
import gameproject.weapon.Weapon;

public class WeaponSelectUI {
    public static void draw(Graphics g, int screenWidth, int screenHeight, Weapon[] options) {
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(0, 0, screenWidth, screenHeight);
        g.setColor(Color.ORANGE);
        g.setFont(FontManager.getFont(45f));
        g.drawString("BOSS REWARD: NEW WEAPON", screenWidth / 2 - 400, screenHeight / 2 - 200);

        int boxWidth = 300, boxHeight = 320, spacing = 50;
        int startX = (screenWidth - (3 * boxWidth + 2 * spacing)) / 2;
        int by = (screenHeight - boxHeight) / 2;

        for (int i = 0; i < 3; i++) {
            int bx = startX + i * (boxWidth + spacing);
            g.setColor(Color.DARK_GRAY);
            g.fillRect(bx, by, boxWidth, boxHeight);
            g.setColor(Color.WHITE);
            g.drawRect(bx, by, boxWidth, boxHeight);
            
            g.setFont(FontManager.getFont(26f));
            g.setColor(Color.CYAN);
            g.drawString(options[i].name, bx + 25, by + 50);

            g.setFont(FontManager.getFont(18f));
            g.setColor(Color.WHITE);
            g.drawString("DMG Multi: x" + options[i].damageMultiplier, bx + 25, by + 120);
            g.drawString("Cooldown: " + options[i].cooldown + "ms", bx + 25, by + 160);
            g.drawString("Range: " + (int)options[i].range, bx + 25, by + 200);

            g.setFont(FontManager.getFont(20f));
            g.setColor(Color.YELLOW);
            g.drawString("CLICK TO PICK", bx + 40, by + 280);
        }
    }
}
