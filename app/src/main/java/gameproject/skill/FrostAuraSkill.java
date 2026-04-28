package gameproject.skill;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import gameproject.*;
import gameproject.entity.Enemy;

public class FrostAuraSkill implements PassiveSkill {
    @Override
    public void update(Player player, ArrayList<Enemy> enemies, VFXManager vfxManager, long currentTime) {
        int frostLevel = player.getBreakthroughLevel(Upgrade.FROST_AURA);
        if (frostLevel <= 0)
            return;

        float soulMulti = 1.0f + (gameproject.meta.PlayerData.skillSoulLevels.getOrDefault(Upgrade.FROST_AURA, 0) * 0.05f);

        // Lv1: 100px  Lv5: 260px
        float frostRadius = (60 + (frostLevel * 40)) * soulMulti;
        for (Enemy e : enemies) {
            float dist = (float) Math.sqrt(Math.pow(player.getX() - e.getX(), 2) + Math.pow(player.getY() - e.getY(), 2));
            if (dist <= frostRadius) {
                e.applyChill(1000, vfxManager);
            }
        }
    }

    @Override
    public void draw(Graphics g, Player player) {
        int frostLevel = player.getBreakthroughLevel(Upgrade.FROST_AURA);
        if (frostLevel <= 0)
            return;

        float frostRadius = 60 + (frostLevel * 40);
        g.setColor(new Color(0, 255, 255, 40));
        g.fillOval((int) (player.getX() + 20 - frostRadius), (int) (player.getY() + 20 - frostRadius),
                (int) (frostRadius * 2), (int) (frostRadius * 2));
        g.setColor(new Color(0, 255, 255, 100));
        g.drawOval((int) (player.getX() + 20 - frostRadius), (int) (player.getY() + 20 - frostRadius),
                (int) (frostRadius * 2), (int) (frostRadius * 2));
    }

    public static float getSlowMultiplier(Player player, Enemy enemy) {
        return 1.0f;
    }

    @Override
    public void onEnemyDeath(Enemy deadEnemy, Player player, ArrayList<Enemy> enemies, VFXManager vfxManager,
            long currentTime) {
    }
}