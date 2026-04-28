package gameproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gameproject.skill.Upgrade;
import gameproject.skill.PassiveSkill;
import gameproject.skill.OrbitingOrbsSkill;
import gameproject.skill.TrailOfFireSkill;
import gameproject.skill.FrostAuraSkill;
import gameproject.skill.ExplosiveCorpseSkill;
import gameproject.skill.VampirismSkill;
import gameproject.skill.PoisonCloudSkill;
import gameproject.weapon.Weapon;

public class UpgradeManager {
    public int playerLevel = 1;
    public int currentExp = 0;
    public int expToNextLevel = 100;

    // Những chỉ số gốc của Player được quản lý tập trung ở đây
    public int playerDamage = 10;
    public float bulletSpeedMulti = 1.0f;

    // Lưu 3 thẻ nâng cấp hiện tại trên màn hình
    public Upgrade[] currentUpgradeOptions;

    public void startNewGame() {
        playerLevel = 1;
        currentExp = 0;
        expToNextLevel = 100;
        playerDamage = 10 + gameproject.meta.PlayerData.statDamageLevel;
        bulletSpeedMulti = 1.0f;
    }

    public void addExp(int amount) {
        currentExp += amount;
    }

    public boolean canLevelUp() {
        return currentExp >= expToNextLevel;
    }

    // Xử lý tăng cấp và sinh ra 3 thẻ nâng cấp ngẫu nhiên
    public boolean processLevelUp(Player player) {
        if (currentExp >= expToNextLevel) {
            currentExp -= expToNextLevel;
            playerLevel++;
            expToNextLevel = (int) (100 * Math.pow(1.25, playerLevel - 1));

            generateOptions(player);
            return true; // Trả về true báo hiệu game nên dừng để bốc thẻ
        }
        return false;
    }

    private void generateOptions(Player player) {
        // Cứ mỗi 3 cấp sẽ cho bốc thẻ Breakthrough
        if (playerLevel % 3 == 0) {
            List<Upgrade> owned = player.getOwnedBreakthroughs();
            List<Upgrade> options = new ArrayList<>();
            for (Upgrade u : owned) {
                if (player.getUpgradeLevel(u) < u.maxLevel)
                    options.add(u);
            }

            if (owned.size() < 3) {
                List<Upgrade> unowned = new ArrayList<>();
                for (Upgrade u : Upgrade.values()) {
                    if (u.isBreakthrough && !owned.contains(u)) {
                        unowned.add(u);
                    }
                }
                Collections.shuffle(unowned);
                for (Upgrade u : unowned) {
                    if (options.size() < 3)
                        options.add(u);
                }
            }
            while (options.size() < 3)
                options.add(Upgrade.SHIELD); // Fallback an toàn
            currentUpgradeOptions = new Upgrade[] { options.get(0), options.get(1), options.get(2) };
        } else {
            // Cấp bình thường sẽ bốc các thẻ tăng chỉ số
            List<Upgrade> normals = new ArrayList<>();
            for (Upgrade u : Upgrade.values()) {
                if (!u.isBreakthrough && player.getUpgradeLevel(u) < u.maxLevel)
                    normals.add(u);
            }
            Collections.shuffle(normals);
            while (normals.size() < 3)
                normals.add(Upgrade.SHIELD); // Fallback an toàn
            currentUpgradeOptions = new Upgrade[] { normals.get(0), normals.get(1), normals.get(2) };
        }
    }

    // Áp dụng thẻ nâng cấp khi người chơi chọn
    public void applyUpgrade(Upgrade upgrade, Player player, List<PassiveSkill> activeSkills, Weapon currentWeapon) {
        player.levelUpUpgrade(upgrade);

        if (upgrade.isBreakthrough) {
            boolean hasSkill = false;
            for (PassiveSkill s : activeSkills) {
                if ((upgrade == Upgrade.ORBITING_ORBS && s instanceof OrbitingOrbsSkill) ||
                        (upgrade == Upgrade.TRAIL_OF_FIRE && s instanceof TrailOfFireSkill) ||
                        (upgrade == Upgrade.FROST_AURA && s instanceof FrostAuraSkill) ||
                        (upgrade == Upgrade.EXPLOSIVE_CORPSE && s instanceof ExplosiveCorpseSkill) ||
                        (upgrade == Upgrade.POISON_CLOUD && s instanceof PoisonCloudSkill)) {
                    hasSkill = true;
                    break;
                }
            }
            if (!hasSkill) {
                if (upgrade == Upgrade.ORBITING_ORBS)
                    activeSkills.add(new OrbitingOrbsSkill());
                else if (upgrade == Upgrade.TRAIL_OF_FIRE)
                    activeSkills.add(new TrailOfFireSkill());
                else if (upgrade == Upgrade.FROST_AURA)
                    activeSkills.add(new FrostAuraSkill());
                else if (upgrade == Upgrade.EXPLOSIVE_CORPSE)
                    activeSkills.add(new ExplosiveCorpseSkill());
                else if (upgrade == Upgrade.POISON_CLOUD)
                    activeSkills.add(new PoisonCloudSkill());
            }
        } else {
            switch (upgrade) {
                case SHIELD -> player.addHeart();
                case DAMAGE -> playerDamage += 5;
                case FIRE_RATE -> currentWeapon.cooldown = (long) (currentWeapon.cooldown * 0.91);
                case MOVE_SPEED -> player.upgradeSpeed(0.3f);
                case DASH_COOLDOWN -> player.upgradeDashCooldown(150);
                case BULLET_SPEED -> bulletSpeedMulti += 0.12f;
                case VAMPIRISM -> {
                    boolean hasVamp = false;
                    for (PassiveSkill s : activeSkills) {
                        if (s instanceof VampirismSkill) {
                            hasVamp = true;
                            break;
                        }
                    }
                    if (!hasVamp) {
                        activeSkills.add(new VampirismSkill());
                    }
                }
                case OPTICAL_SCOPE -> currentWeapon.range *= 1.12f;
                default -> {
                }
            }
        }
    }
}
