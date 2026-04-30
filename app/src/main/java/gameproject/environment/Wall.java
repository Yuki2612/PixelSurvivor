package gameproject.environment;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall extends Obstacle {

    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
        // Tường chiếm trọn diện tích ô lưới
        this.hitbox = new AABBHitbox(x, y, width, height);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void takeDamage(int dmg) {
    } // Bất tử

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void render(Graphics2D g) {
        java.awt.image.BufferedImage img = gameproject.ImageManager.get("wall");
        if (img != null) {
            g.drawImage(img, x, y, width, height, null);
        } else {
            g.setColor(new Color(60, 60, 60)); // Màu xám tối cho tường
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
        }

        if (gameproject.GamePanel.showHitboxes && hitbox != null) {
            g.setColor(Color.BLUE);
            hitbox.draw(g);
        }
    }
}