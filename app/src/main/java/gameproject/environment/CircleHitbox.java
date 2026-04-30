package gameproject.environment;

import java.awt.Graphics;

public class CircleHitbox implements Hitbox {
    public float centerX, centerY, radius;

    public CircleHitbox(float centerX, float centerY, float radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    public boolean contains(float px, float py) {
        float dx = px - centerX;
        float dy = py - centerY;
        return (dx * dx + dy * dy) <= (radius * radius);
    }

    @Override
    public boolean intersects(float rx, float ry, float rw, float rh) {
        // Tìm điểm trên AABB gần tâm hình tròn nhất
        float closestX = Math.max(rx, Math.min(centerX, rx + rw));
        float closestY = Math.max(ry, Math.min(centerY, ry + rh));

        // Tính khoảng cách từ tâm hình tròn đến điểm gần nhất này
        float dx = centerX - closestX;
        float dy = centerY - closestY;

        // Nếu khoảng cách nhỏ hơn bán kính thì có va chạm
        return (dx * dx + dy * dy) < (radius * radius);
    }

    @Override
    public void draw(Graphics g) {
        g.drawOval((int) (centerX - radius), (int) (centerY - radius), (int) (radius * 2), (int) (radius * 2));
    }
}