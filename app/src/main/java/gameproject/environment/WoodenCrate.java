package gameproject.environment;

import java.awt.Color;
import java.awt.Graphics2D;

public class WoodenCrate extends Obstacle {
    private int hp = 50;

    public WoodenCrate(int x, int y, int width, int height) {
        super(x, y, width, height);
        // Tăng kích cỡ hitbox để lấp đầy ô lưới
        this.hitbox = new AABBHitbox(x, y, width, height);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void takeDamage(int dmg) {
        this.hp -= dmg;
    }

    @Override
    public boolean isDestroyed() {
        return hp <= 0;
    }

    @Override
    public void render(Graphics2D g) {
        java.awt.image.BufferedImage img = gameproject.ImageManager.get("woodencrate");
        if (img != null) {
            // Vẽ lấp đầy ô lưới
            g.drawImage(img, x, y, width, height, null);
        } else {
            g.setColor(new Color(139, 69, 19)); // Màu nâu gỗ
            g.fillRect(x + 2, y + 2, width - 4, height - 4);
            g.setColor(new Color(101, 50, 14));
            g.drawRect(x + 2, y + 2, width - 4, height - 4);
            // Vẽ thêm chữ X trên thùng cho đẹp
            g.drawLine(x + 10, y + 10, x + width - 10, y + height - 10);
            g.drawLine(x + width - 10, y + 10, x + 10, y + height - 10);
        }

        if (gameproject.GamePanel.showHitboxes && hitbox != null) {
            g.setColor(Color.BLUE);
            hitbox.draw(g);
        }
    }
}