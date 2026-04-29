package gameproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import gameproject.skill.Upgrade;
import gameproject.meta.CharacterClass;

public class Player {
    private float x, y;
    private final int SIZE = 25;

    private float speed = 5.0f;
    private long dashCooldown = 2000;

    private int hearts = 3;
    private final int MAX_HEARTS = 15;
    private long invulnerableUntil = 0;

    // GOM CHUNG TOÀN BỘ NÂNG CẤP VÀO ĐÂY ĐỂ ĐẾM LEVEL
    private Map<Upgrade, Integer> upgradeLevels = new HashMap<>();

    private boolean up, down, left, right;
    private float lastDirX = 1, lastDirY = 0;
    private boolean isDashing = false;
    private long lastDashTime = 0;
    private long dashStartTime = 0;
    private float dashDirX = 0, dashDirY = 0;
    private final long DASH_DURATION = 150;
    private final float DASH_SPEED = 18.0f;

    public Player(float startX, float startY, CharacterClass charClass) {
        this.x = startX;
        this.y = startY;
        this.dashCooldown = (long)(2000 * (1.0f - gameproject.meta.PlayerData.statDashLevel * 0.02f));
        this.lastDashTime = -dashCooldown;
        this.hearts = charClass.baseHp + (gameproject.meta.PlayerData.statHealthLevel / 10);
        this.speed = (5.0f * charClass.speedMulti) * (1.0f + gameproject.meta.PlayerData.statSpeedLevel * 0.02f);
    }

    public void update(int screenWidth, int screenHeight) {
        if (isDashing) {
            if (System.currentTimeMillis() - dashStartTime >= DASH_DURATION) {
                isDashing = false;
            } else {
                x += dashDirX * DASH_SPEED;
                y += dashDirY * DASH_SPEED;
                x = Math.max(0, Math.min(x, GamePanel.WORLD_WIDTH - SIZE));
                y = Math.max(0, Math.min(y, GamePanel.WORLD_HEIGHT - SIZE));
            }
        } else {
            boolean isMoving = false;
            float currentDirX = 0, currentDirY = 0;
            if (up && y > 0) {
                y -= speed;
                currentDirY = -1;
                isMoving = true;
            }
            if (down && y < GamePanel.WORLD_HEIGHT - SIZE) {
                y += speed;
                currentDirY = 1;
                isMoving = true;
            }
            if (left && x > 0) {
                x -= speed;
                currentDirX = -1;
                isMoving = true;
            }
            if (right && x < GamePanel.WORLD_WIDTH - SIZE) {
                x += speed;
                currentDirX = 1;
                isMoving = true;
            }
            if (isMoving) {
                lastDirX = currentDirX;
                lastDirY = currentDirY;
            }
        }
    }

    public void draw(Graphics g) {
        if (isInvulnerable() && System.currentTimeMillis() % 200 < 100)
            return;

        java.awt.image.BufferedImage img = ImageManager.get("player");
        if (img != null) {
            int drawX = (int) x - 10;
            int drawY = (int) y - 20;
            int drawSize = SIZE + 20;
            if (isDashing) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.5f));
                g2d.drawImage(img, drawX, drawY, drawSize, drawSize, null);
                g2d.dispose();
            } else {
                g.drawImage(img, drawX, drawY, drawSize, drawSize, null);
            }
        } else {
            if (isDashing)
                g.setColor(Color.CYAN);
            else
                g.setColor(Color.RED);
            g.fillRect((int) x, (int) y, SIZE, SIZE);
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = true;
            case KeyEvent.VK_S -> down = true;
            case KeyEvent.VK_A -> left = true;
            case KeyEvent.VK_D -> right = true;
            case KeyEvent.VK_SHIFT -> {
                if (!isDashing && System.currentTimeMillis() - lastDashTime >= dashCooldown) {
                    isDashing = true;
                    dashStartTime = System.currentTimeMillis();
                    lastDashTime = dashStartTime;
                    float length = (float) Math.sqrt(lastDirX * lastDirX + lastDirY * lastDirY);
                    if (length == 0) {
                        dashDirX = 1;
                        dashDirY = 0;
                    } else {
                        dashDirX = lastDirX / length;
                        dashDirY = lastDirY / length;
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> up = false;
            case KeyEvent.VK_S -> down = false;
            case KeyEvent.VK_A -> left = false;
            case KeyEvent.VK_D -> right = false;
        }
    }

    public void resetMovement() {
        up = down = left = right = false;
    }

    public void upgradeSpeed(float amount) {
        this.speed += amount;
    }

    public void upgradeDashCooldown(long reduction) {
        this.dashCooldown = Math.max(500, this.dashCooldown - reduction);
    }

    public void addHeart() {
        if (hearts < MAX_HEARTS)
            hearts++;
    }

    public int getHearts() {
        return hearts;
    }

    public boolean takeHit() {
        if (isInvulnerable())
            return false;
        hearts--;
        invulnerableUntil = System.currentTimeMillis() + 1000;
        return hearts <= 0;
    }

    // LÕI XỬ LÝ LEVEL CỦA TẤT CẢ NÂNG CẤP (MAX LEVEL = 10)
    public void levelUpUpgrade(Upgrade u) {
        int current = upgradeLevels.getOrDefault(u, 0);
        if (current < 10)
            upgradeLevels.put(u, current + 1);
    }

    public int getUpgradeLevel(Upgrade u) {
        return upgradeLevels.getOrDefault(u, 0);
    }

    // Giữ nguyên hàm này để tránh báo lỗi các Kỹ năng cũ
    public void levelUpBreakthrough(Upgrade u) {
        levelUpUpgrade(u);
    }

    public int getBreakthroughLevel(Upgrade u) {
        return getUpgradeLevel(u);
    }

    public List<Upgrade> getOwnedBreakthroughs() {
        List<Upgrade> list = new ArrayList<>();
        for (Upgrade u : upgradeLevels.keySet()) {
            if (u.isBreakthrough)
                list.add(u);
        }
        return list;
    }

    public boolean isInvulnerable() {
        return System.currentTimeMillis() < invulnerableUntil;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, SIZE, SIZE);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isDashing() {
        return isDashing;
    }

    public long getLastDashTime() {
        return lastDashTime;
    }

    public long getDashCooldown() {
        return dashCooldown;
    }
}