package gameproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import gameproject.skill.Upgrade;

public class VFXManager {

    // 1. NHÚNG TRỰC TIẾP VÀO ĐÂY THÀNH INNER CLASS
    public static class FireZone {
        public float x, y;
        public long expireTime;
        public boolean isExplosion;
        public boolean isAcid;
        public int radius;

        public FireZone(float x, float y, long expireTime, boolean isExplosion, boolean isAcid, int radius) {
            this.x = x;
            this.y = y;
            this.expireTime = expireTime;
            this.isExplosion = isExplosion;
            this.isAcid = isAcid;
            this.radius = radius;
        }
    }

    public static class DamageText {
        public float x, y;
        public int damage;
        public long expireTime;
        public Color color;

        public DamageText(float x, float y, int damage, long currentTime, Color color) {
            this.x = x;
            this.y = y;
            this.damage = damage;
            this.expireTime = currentTime + 700;
            this.color = color;
        }
    }

    public static class LaserVFX {
        public float x1, y1, x2, y2;
        public long expireTime;
        public LaserVFX(float x1, float y1, float x2, float y2, long currentTime) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.expireTime = currentTime + 400; // Tồn tại 0.4s
        }
    }

    // 2. KHAI BÁO BIẾN BÌNH THƯỜNG
    public ArrayList<FireZone> fireZones = new ArrayList<>();
    public ArrayList<DamageText> damageTexts = new ArrayList<>();
    public ArrayList<LaserVFX> lasers = new ArrayList<>();

    public boolean showDamageText = true;
    private int shakeTimer = 0;
    private int currentDx = 0, currentDy = 0;

    public void triggerScreenShake(int durationFrames) {
        this.shakeTimer = durationFrames;
    }

    public void addExplosion(float x, float y, float radius, long currentTime) {
        fireZones.add(new FireZone(x - radius / 2, y - radius / 2, currentTime + 200, true, false, (int)radius));
    }

    public void addFireTrail(float x, float y, long currentTime) {
        fireZones.add(new FireZone(x, y, currentTime + 3000, false, false, 20));
    }

    public void addAcidZone(float x, float y, float radius, long currentTime) {
        fireZones.add(new FireZone(x - radius / 2, y - radius / 2, currentTime + 5000, false, true, (int)radius));
    }

    public void addLaser(float x1, float y1, float x2, float y2, long currentTime) {
        lasers.add(new LaserVFX(x1, y1, x2, y2, currentTime));
    }

    public void addDamageText(float x, float y, int damage, long currentTime, Color color) {
        if (showDamageText) {
            float offsetX = (float) (Math.random() * 20 - 10);
            float offsetY = (float) (Math.random() * 10 - 5);
            damageTexts.add(new DamageText(x + offsetX, y + offsetY, damage, currentTime, color));
        }
    }
    
    public void addDamageText(float x, float y, int damage, long currentTime) {
        addDamageText(x, y, damage, currentTime, Color.WHITE);
    }

    public void update(long currentTime) {
        Iterator<FireZone> fIt = fireZones.iterator();
        while (fIt.hasNext()) {
            if (currentTime > fIt.next().expireTime)
                fIt.remove();
        }

        Iterator<DamageText> dIt = damageTexts.iterator();
        while (dIt.hasNext()) {
            DamageText dt = dIt.next();
            if (currentTime > dt.expireTime)
                dIt.remove();
            else
                dt.y -= 0.6f;
        }

        Iterator<LaserVFX> lIt = lasers.iterator();
        while (lIt.hasNext()) {
            if (currentTime > lIt.next().expireTime)
                lIt.remove();
        }
    }

    public void applyScreenShake(Graphics2D g2d) {
        if (shakeTimer > 0) {
            currentDx = (int) (Math.random() * 10 - 5);
            currentDy = (int) (Math.random() * 10 - 5);
            g2d.translate(currentDx, currentDy);
            shakeTimer--;
        } else {
            currentDx = 0;
            currentDy = 0;
        }
    }

    public void resetScreenShake(Graphics2D g2d) {
        if (currentDx != 0 || currentDy != 0) {
            g2d.translate(-currentDx, -currentDy);
        }
    }

    public void draw(Graphics g, Player player) {
        Graphics2D g2d = (Graphics2D) g;
        for (LaserVFX l : lasers) {
            g2d.setColor(new Color(0, 255, 255, 220));
            g2d.setStroke(new java.awt.BasicStroke(24));
            g2d.drawLine((int)l.x1, (int)l.y1, (int)l.x2, (int)l.y2);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new java.awt.BasicStroke(10));
            g2d.drawLine((int)l.x1, (int)l.y1, (int)l.x2, (int)l.y2);
            g2d.setStroke(new java.awt.BasicStroke(1));
        }

        for (FireZone fz : fireZones) {
            if (fz.isExplosion) {
                g.setColor(new Color(255, 50, 50, 150));
                g.fillOval((int) fz.x, (int) fz.y, fz.radius, fz.radius);
            } else if (fz.isAcid) {
                g.setColor(new Color(50, 255, 50, 80));
                g.fillOval((int) fz.x, (int) fz.y, fz.radius, fz.radius);
                g.setColor(new Color(0, 255, 0, 150));
                g.drawOval((int) fz.x, (int) fz.y, fz.radius, fz.radius);
            } else {
                g.setColor(new Color(255, 100, 0, 150));
                g.fillRect((int) fz.x, (int) fz.y, fz.radius, fz.radius);
            }
        }

        if (showDamageText) {
            g.setFont(new Font("Arial", Font.BOLD, 14));
            for (DamageText dt : damageTexts) {
                String text = String.valueOf(dt.damage);
                g.setColor(Color.BLACK);
                if (dt.color == Color.WHITE && dt.damage >= 300) {
                    g.setFont(new Font("Arial", Font.BOLD, 22));
                    g.drawString(text, (int) dt.x - 2, (int) dt.y);
                    g.drawString(text, (int) dt.x + 2, (int) dt.y);
                    g.drawString(text, (int) dt.x, (int) dt.y - 2);
                    g.drawString(text, (int) dt.x, (int) dt.y + 2);
                    g.setColor(dt.color);
                    g.drawString(text, (int) dt.x, (int) dt.y);
                    g.setFont(new Font("Arial", Font.BOLD, 14));
                } else {
                    g.drawString(text, (int) dt.x - 1, (int) dt.y - 1);
                    g.drawString(text, (int) dt.x + 1, (int) dt.y - 1);
                    g.drawString(text, (int) dt.x - 1, (int) dt.y + 1);
                    g.drawString(text, (int) dt.x + 1, (int) dt.y + 1);
                    g.setColor(dt.color);
                    g.drawString(text, (int) dt.x, (int) dt.y);
                }
            }
        }
    }
}