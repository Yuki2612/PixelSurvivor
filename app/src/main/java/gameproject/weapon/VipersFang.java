package gameproject.weapon;

import gameproject.SoundManager;
import java.util.ArrayList;

public class VipersFang extends Weapon {
    public VipersFang() {
        super("Viper's Fang", 0.8f, 100, true, 400f);
    }

    @Override
    public void shoot(float startX, float startY, float targetX, float targetY,
            float bulletSpeedMulti, int playerDamage, int bounces,
            ArrayList<Projectile> projectiles, long currentTime) {
        
        lastShootTime = currentTime;
        SoundManager.play("shoot");

        Projectile p = new Projectile(startX, startY, targetX, targetY, bulletSpeedMulti, range);
        p.damage = (int)(playerDamage * damageMultiplier);
        p.bouncesLeft = bounces + 3;
        p.isPoisonous = true;
        projectiles.add(p);
    }
}
