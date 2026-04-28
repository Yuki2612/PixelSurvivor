package gameproject.entity;

import gameproject.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class TankBoss extends Enemy {
    public TankBoss(float startX, float startY, int surviveTimeSeconds) {
        super(startX, startY, 80, (int) ((300 + (surviveTimeSeconds * 2)) * 1.5f), 0.5f, Color.DARK_GRAY);
        this.isBoss = true;
    }

    @Override
    public void applyKnockback(float sourceX, float sourceY, float pushForce) {
        // Miễn nhiễm đẩy lùi
    }

    @Override
    public void update(float playerX, float playerY, float speedMultiplier, ArrayList<Enemy> allEnemies, int screenW,
            int screenH) {
        float dx = playerX - x;
        float dy = playerY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float currentSpeed = speed * speedMultiplier;
        float moveX = 0, moveY = 0;
        if (distance > 0) {
            moveX = (dx / distance) * currentSpeed;
            moveY = (dy / distance) * currentSpeed;
        }
        applyPhysicsAndBounds(moveX, moveY, screenW, screenH);
    }

    @Override
    public void draw(Graphics g) {
        java.awt.image.BufferedImage img = ImageManager.get("boss3");
        if (img != null) {
            g.drawImage(img, (int) x - 20, (int) y - 40, size + 40, size + 40, null);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect((int) x, (int) y, size, size);
        }

        g.setColor(Color.RED);
        g.fillRect((int) x, (int) y + size, size, 6);
        g.setColor(Color.GREEN);
        int hpWidth = (int) ((float) hp / maxHp * size);
        g.fillRect((int) x, (int) y + size, hpWidth, 6);
    }
}