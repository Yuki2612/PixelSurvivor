package gameproject.state;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import gameproject.GamePanel;
import gameproject.ImageManager;
import gameproject.SoundManager;
import gameproject.ui.HUD;
import gameproject.skill.Upgrade;
import gameproject.skill.PassiveSkill;
import gameproject.skill.FrostAuraSkill;
import gameproject.skill.PoisonCloudSkill;
import gameproject.skill.OrbitingOrbsSkill;

public class PlayingState implements State {
    @Override
    public void update(GamePanel game) {
        if (game.input.escPressed) {
            game.player.resetMovement();
            game.input.isMouseHolding = false;
            game.input.clearClickAndKey();
            game.changeState(new PauseState());
            return;
        }

        long currentTime = System.currentTimeMillis();
        game.surviveTimeSeconds = (int) ((currentTime - game.startTime) / 500);

        game.player.update(game.screenWidth, game.screenHeight);
        game.vfxManager.update(currentTime);

        game.entityManager.update(game.player, game.vfxManager, game.activeSkills, game.screenWidth, game.screenHeight,
                currentTime, game.surviveTimeSeconds, game);

        if (game.input.isMouseHolding && game.currentWeapon.isAutomatic && game.currentWeapon.canShoot(currentTime)) {
            triggerShoot(game, currentTime);
        } else if (game.input.mouseClicked && !game.currentWeapon.isAutomatic
                && game.currentWeapon.canShoot(currentTime)) {
            triggerShoot(game, currentTime);
        }

        if (game.upgradeManager.processLevelUp(game.player)) {
            game.player.resetMovement();
            game.input.isMouseHolding = false;
            SoundManager.play("levelup");
            game.changeState(new LevelUpState());
        }
    }

    private void triggerShoot(GamePanel game, long currentTime) {
        int critLevel = game.player.getUpgradeLevel(Upgrade.CRIT_CHANCE);
        int finalDamage = game.upgradeManager.playerDamage;
        float baseCrit = gameproject.meta.PlayerData.statCritLevel * 0.01f;
        float totalCrit = baseCrit + (critLevel * 0.07f);
        if (totalCrit > 0 && Math.random() < totalCrit) {
            finalDamage *= 1.5;
        }

        int bouncesAndPierces = game.player.getUpgradeLevel(Upgrade.CHAIN_LIGHTNING);

        game.currentWeapon.shoot(game.player.getX(), game.player.getY(), game.input.mouseX, game.input.mouseY,
                game.upgradeManager.bulletSpeedMulti, finalDamage, bouncesAndPierces, game.entityManager.projectiles,
                currentTime);
    }

    @Override
    public void render(GamePanel game, Graphics g) {
        java.awt.image.BufferedImage bg = ImageManager.get(game.currentBgKey);
        if (bg != null) {
            int bgWidth = bg.getWidth();
            int bgHeight = bg.getHeight();
            for (int x = 0; x < game.screenWidth; x += bgWidth) {
                for (int y = 0; y < game.screenHeight; y += bgHeight)
                    g.drawImage(bg, x, y, null);
            }
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, game.screenWidth, game.screenHeight);
        }

        Graphics2D g2d = (Graphics2D) g;
        game.vfxManager.applyScreenShake(g2d);

        for (PassiveSkill skill : game.activeSkills) {
            if (skill instanceof FrostAuraSkill || skill instanceof PoisonCloudSkill)
                skill.draw(g, game.player);
        }

        game.vfxManager.draw(g, game.player);
        game.entityManager.draw(g);
        game.player.draw(g);

        for (PassiveSkill skill : game.activeSkills) {
            if (skill instanceof OrbitingOrbsSkill)
                skill.draw(g, game.player);
        }

        game.vfxManager.resetScreenShake(g2d);

        HUD.draw(g, game.screenWidth, game.screenHeight, game.score, game.entityManager.waveCount,
                game.upgradeManager.playerDamage, game.currentWeapon.getActualCooldown(), game.player,
                game.upgradeManager.currentExp, game.upgradeManager.expToNextLevel, game.upgradeManager.playerLevel);
    }
}
