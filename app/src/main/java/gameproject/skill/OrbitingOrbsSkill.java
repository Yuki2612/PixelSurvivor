package gameproject.skill;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import gameproject.*;
import gameproject.entity.Enemy;

public class OrbitingOrbsSkill implements PassiveSkill {
    private float orbitAngle = 0;

    @Override
    public void update(Player player, ArrayList<Enemy> enemies, VFXManager vfxManager, long currentTime) {
        int orbLevel = player.getBreakthroughLevel(Upgrade.ORBITING_ORBS);
        if (orbLevel <= 0)
            return;

        orbitAngle += 0.08f;
        int numOrbs = Math.min(orbLevel, 5); // tối đa 5 orb; level 6-10 tăng dmg thay vì thêm orb
        for (int i = 0; i < numOrbs; i++) {
            float angle = orbitAngle + (float) (i * 2 * Math.PI / numOrbs);
            int ox = (int) (player.getX() + 20 + Math.cos(angle) * 60);
            int oy = (int) (player.getY() + 20 + Math.sin(angle) * 60);
            Rectangle orbHitbox = new Rectangle(ox - 10, oy - 10, 20, 20);

            float soulMulti = 1.0f + (gameproject.meta.PlayerData.skillSoulLevels.getOrDefault(Upgrade.ORBITING_ORBS, 0) * 0.05f);
            int dmg = (int) ((16 + ((orbLevel - 1) * 8)) * soulMulti);
            for (Enemy e : enemies) {
                if (e.getBounds().intersects(orbHitbox)) {
                    e.takeDamage(dmg, vfxManager, currentTime);
                    e.applyKnockback(ox, oy, 10f);
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, Player player) {
        int orbLevel = player.getBreakthroughLevel(Upgrade.ORBITING_ORBS);
        if (orbLevel <= 0)
            return;
        g.setColor(Color.MAGENTA);
        for (int i = 0; i < orbLevel; i++) {
            float angle = orbitAngle + (float) (i * 2 * Math.PI / orbLevel);
            int ox = (int) (player.getX() + 20 + Math.cos(angle) * 60);
            int oy = (int) (player.getY() + 20 + Math.sin(angle) * 60);
            g.fillOval(ox - 10, oy - 10, 20, 20);
        }
    }

    @Override
    public void onEnemyDeath(Enemy deadEnemy, Player player, ArrayList<Enemy> enemies, VFXManager vfxManager,
            long currentTime) {
    }
}