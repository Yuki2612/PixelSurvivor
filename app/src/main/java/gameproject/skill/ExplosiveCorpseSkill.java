package gameproject.skill;

import java.awt.Graphics;
import java.util.ArrayList;

import gameproject.*;
import gameproject.entity.Enemy;

public class ExplosiveCorpseSkill implements PassiveSkill {
    @Override
    public void update(Player player, ArrayList<Enemy> enemies, VFXManager vfxManager, long currentTime) {
    }

    @Override
    public void draw(Graphics g, Player player) {
    }

    @Override
    public void onEnemyDeath(Enemy deadEnemy, Player player, ArrayList<Enemy> enemies, VFXManager vfxManager,
            long currentTime) {
        int corpseLevel = player.getBreakthroughLevel(Upgrade.EXPLOSIVE_CORPSE);
        if (corpseLevel <= 0)
            return;

        vfxManager.triggerScreenShake(5);
        SoundManager.play("explosion");

        float soulMulti = 1.0f + (gameproject.meta.PlayerData.skillSoulLevels.getOrDefault(Upgrade.EXPLOSIVE_CORPSE, 0) * 0.05f);

        // Lv1: 70px  Lv5: 150px
        float explosionRadius = (50 + (corpseLevel * 20)) * soulMulti;
        // 5% maxHp + flat
        int explosionDamage = (int) (((deadEnemy.getMaxHp() * 0.05) + (corpseLevel * 5)) * soulMulti);

        for (Enemy other : enemies) {
            if (other == deadEnemy)
                continue;
            float dist = (float) Math
                    .sqrt(Math.pow(deadEnemy.getX() - other.getX(), 2) + Math.pow(deadEnemy.getY() - other.getY(), 2));
            if (dist <= explosionRadius) {
                other.takeDamage(explosionDamage, vfxManager, currentTime);
            }
        }
        vfxManager.addExplosion(deadEnemy.getX(), deadEnemy.getY(), explosionRadius, currentTime);
    }
}