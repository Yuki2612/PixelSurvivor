package gameproject.entity;

//import gameproject.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ChargerBoss extends Enemy {
    private int actionTimer = 0;
    private boolean isCharging = false;
    private int chargeFrames = 0;
    private float baseSpeed = 1.5f;

    public ChargerBoss(float startX, float startY, int surviveTimeSeconds) {
        super(startX, startY, 60, 400 + (surviveTimeSeconds * 3), 1.2f, Color.RED);
        this.isBoss = true;
    }

    private java.util.List<gameproject.weapon.Projectile> nextShots = new java.util.ArrayList<>();

    @Override
    public void update(float playerX, float playerY, float speedMultiplier, ArrayList<Enemy> allEnemies, int screenW,
            int screenH, gameproject.GamePanel panel) {
        actionTimer--;
        if (actionTimer <= 0) {
            actionTimer = 180;
            isCharging = true;
            chargeFrames = 30;
        }
        if (isCharging) {
            speed = 4.0f;
            chargeFrames--;

            // Bắn đạn xung quanh khi đang lướt (mỗi 5 frame bắn 1 lần burst)
            if (chargeFrames % 5 == 0) {
                for (int i = 0; i < 8; i++) {
                    double angle = Math.toRadians(i * 45);
                    float tx = x + (float) Math.cos(angle) * 100;
                    float ty = y + (float) Math.sin(angle) * 100;
                    gameproject.weapon.Projectile p = new gameproject.weapon.Projectile(x, y, tx, ty, 0.4f, 400f);
                    p.isEnemyBullet = true;
                    p.damage = 1;
                    nextShots.add(p);
                }
            }

            if (chargeFrames <= 0) {
                isCharging = false;
                speed = baseSpeed;
            }
        }

        // Điều khiển di chuyển bằng AI tập trung
        EnemyController.moveEnemy(this, panel, speedMultiplier);
    }

    @Override
    public java.util.List<gameproject.weapon.Projectile> shoot() {
        if (nextShots.isEmpty())
            return null;
        java.util.List<gameproject.weapon.Projectile> result = new java.util.ArrayList<>(nextShots);
        nextShots.clear();
        return result;
    }

    @Override
    public void draw(Graphics g) {
        drawSprite(g, "boss1");
    }
}