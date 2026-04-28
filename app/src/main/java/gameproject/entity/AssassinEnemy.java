package gameproject.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.util.ArrayList;
import java.util.Random;

public class AssassinEnemy extends Enemy {
    private Random rand = new Random();
    private int tier;

    private boolean isInvisible = false;
    private int invisTimer = 0;
    private int invisCooldown;

    // Tốc độ lưu trữ để phục hồi sau khi tàng hình
    private float baseSpeed;

    public AssassinEnemy(float startX, float startY, int tier, int surviveTimeSeconds) {
        super(startX, startY, 30, 0, 0, Color.BLACK);
        this.isBoss = false;
        this.tier = tier;

        switch (tier) {
            case 1 -> {
                this.maxHp = 10;
                this.baseSpeed = 1.5f;
                this.invisCooldown = 300;
            } // 5s cooldown
            case 2 -> {
                this.maxHp = 15;
                this.baseSpeed = 1.8f;
                this.invisCooldown = 270;
            }
            case 3 -> {
                this.maxHp = 25;
                this.baseSpeed = 2.1f;
                this.invisCooldown = 240;
            }
            case 4 -> {
                this.maxHp = 35;
                this.baseSpeed = 2.5f;
                this.invisCooldown = 210;
            }
            default -> {
                this.maxHp = 50;
                this.baseSpeed = 3.0f;
                this.invisCooldown = 180;
                this.tier = 5;
            }
        }
        this.maxHp = (int) (this.maxHp * (1.0f + (surviveTimeSeconds / 60.0f) * 0.05f));
        this.hp = this.maxHp;
        this.speed = this.baseSpeed;
        this.invisTimer = rand.nextInt(60); // Bắt đầu đếm ngược tàng hình
    }

    @Override
    public void update(float playerX, float playerY, float speedMultiplier, ArrayList<Enemy> allEnemies, int screenW,
            int screenH) {
        // Logic tàng hình
        if (isInvisible) {
            invisTimer--;
            if (invisTimer <= 0) {
                isInvisible = false;
                speed = baseSpeed; // Phục hồi tốc độ
                invisTimer = invisCooldown; // Chờ lần tàng hình tiếp theo
            }
        } else {
            invisTimer--;
            if (invisTimer <= 0) {
                isInvisible = true;
                speed = baseSpeed * 1.4f; // Tăng 80% tốc độ khi tàng hình
                invisTimer = 45 + (tier * 2); // Thời gian tàng hình tăng theo tier (1s -> 1.5s)
            }
        }

        float dx = playerX - x;
        float dy = playerY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float currentSpeed = speed * speedMultiplier;
        float moveX = 0, moveY = 0;

        if (distance > 0) {
            moveX = (dx / distance) * currentSpeed;
            moveY = (dy / distance) * currentSpeed;
        }

        // Vẫn tản ra để không đè lên nhau
        for (Enemy other : allEnemies) {
            if (other == this || other.isBoss)
                continue;
            float odx = this.x - other.x;
            float ody = this.y - other.y;
            float oDist = (float) Math.sqrt(odx * odx + ody * ody);
            if (oDist > 0 && oDist < 30) {
                moveX += (odx / oDist) * 0.8f;
                moveY += (ody / oDist) * 0.8f;
            }
        }
        applyPhysicsAndBounds(moveX, moveY, screenW, screenH);
    }

    // Ghi đè takeDamage: Kháng 100% sát thương đạn khi đang tàng hình
    @Override
    public void takeDamage(int damage, gameproject.VFXManager vfxManager, long currentTime) {
        if (!isInvisible) {
            super.takeDamage(damage, vfxManager, currentTime);
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isInvisible) {
            // Vẽ mờ đi khi tàng hình
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            drawSprite(g2d, "enemy" + tier);
            g2d.dispose();
        } else {
            drawSprite(g, "enemy" + tier);
        }

        // Đánh dấu sát thủ bằng chấm đen
        g.setColor(Color.BLACK);
        g.fillOval((int) x + size / 2 - 4, (int) y - 12, 8, 8);
    }
}