package gameproject.skill;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import gameproject.*;
import gameproject.entity.Enemy;

public class TrailOfFireSkill implements PassiveSkill {
    @Override
    public void update(Player player, ArrayList<Enemy> enemies, VFXManager vfxManager, long currentTime) {
        int fireLevel = player.getBreakthroughLevel(Upgrade.TRAIL_OF_FIRE);
        if (fireLevel <= 0)
            return;

        if (player.isDashing() && currentTime % 30 < 15) {
            vfxManager.addFireTrail(player.getX() + 5, player.getY() + 5, currentTime);
        }

        // BẮT BUỘC SỬA TẠI ĐÂY: GỌI ĐÍCH DANH VFXManager.FireZone
        for (VFXManager.FireZone fz : vfxManager.fireZones) {
            if (!fz.isExplosion) {
                float soulMulti = 1.0f + (gameproject.meta.PlayerData.skillSoulLevels.getOrDefault(Upgrade.TRAIL_OF_FIRE, 0) * 0.05f);
                Rectangle fzHitbox = new Rectangle((int) fz.x, (int) fz.y, 20, 20);
                for (Enemy e : enemies) {
                    if (e.getBounds().intersects(fzHitbox)) {
                        e.applyBurn((int)(2000 * soulMulti), vfxManager);
                    }
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, Player player) {
    }

    @Override
    public void onEnemyDeath(Enemy deadEnemy, Player player, ArrayList<Enemy> enemies, VFXManager vfxManager,
            long currentTime) {
    }
}