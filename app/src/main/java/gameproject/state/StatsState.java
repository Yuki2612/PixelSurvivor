package gameproject.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import gameproject.GamePanel;
import gameproject.FontManager;
import gameproject.meta.PlayerData;

public class StatsState implements State {
    private String[] statNames = {
        "Max HP", 
        "Might", 
        "Move Speed", 
        "Dash Cool",
        "Crit Chance",
        "Fire Rate"
    };
    private String[] statDescs = {
        "(+1/10lv)", "(+1 Dmg)", "(+2%)", "(-2% CD)", "(+1%)", "(+2%)"
    };
    private int[] statLevels = new int[6];
    private int[] maxLevels = {30, 20, 10, 10, 20, 10};
    private int[] baseCosts = {100, 200, 150, 100, 150, 200};

    class StatNode {
        int statIndex;
        int cx, cy;
        int[] required;
        public StatNode(int statIndex, int cx, int cy, int[] required) {
            this.statIndex = statIndex;
            this.cx = cx; this.cy = cy; this.required = required;
        }
    }

    private StatNode[] nodes;

    @Override
    public void update(GamePanel game) {
        if (nodes == null) {
            int cx = game.screenWidth / 2;
            nodes = new StatNode[] {
                new StatNode(1, cx, 200, new int[]{}),              // Might
                new StatNode(0, cx - 220, 320, new int[]{1}),       // Health
                new StatNode(2, cx + 220, 320, new int[]{1}),       // Speed
                new StatNode(3, cx - 220, 460, new int[]{0}),       // Dash
                new StatNode(5, cx + 220, 460, new int[]{2}),       // Cooldown
                new StatNode(4, cx, 580, new int[]{3, 5})           // Crit
            };
        }

        // Đọc lại từ PlayerData
        statLevels[0] = PlayerData.statHealthLevel;
        statLevels[1] = PlayerData.statDamageLevel;
        statLevels[2] = PlayerData.statSpeedLevel;
        statLevels[3] = PlayerData.statDashLevel;
        statLevels[4] = PlayerData.statCritLevel;
        statLevels[5] = PlayerData.statCooldownLevel;

        int totalUpgrades = 0;
        for (int l : statLevels) totalUpgrades += l;

        if (game.input.escPressed) {
            PlayerData.save();
            game.changeState(new MenuState());
            game.input.clearClickAndKey();
            return;
        }

        if (game.input.mouseClicked) {
            int mx = game.input.mouseX;
            int my = game.input.mouseY;

            for (StatNode node : nodes) {
                int boxW = 160, boxH = 80;
                int bx = node.cx - boxW / 2;
                int by = node.cy - boxH / 2;

                if (mx >= bx && mx <= bx + boxW && my >= by && my <= by + boxH) {
                    boolean canUnlock = true;
                    for (int req : node.required) {
                        if (statLevels[req] == 0) canUnlock = false;
                    }
                    if (canUnlock) {
                        int cost = (int)(baseCosts[node.statIndex] * Math.pow(1.1, totalUpgrades));
                        if (statLevels[node.statIndex] < maxLevels[node.statIndex] && PlayerData.gold >= cost) {
                            PlayerData.gold -= cost;
                            if (node.statIndex == 0) PlayerData.statHealthLevel++;
                            if (node.statIndex == 1) PlayerData.statDamageLevel++;
                            if (node.statIndex == 2) PlayerData.statSpeedLevel++;
                            if (node.statIndex == 3) PlayerData.statDashLevel++;
                            if (node.statIndex == 4) PlayerData.statCritLevel++;
                            if (node.statIndex == 5) PlayerData.statCooldownLevel++;
                            statLevels[node.statIndex]++;
                        }
                    }
                }
            }

            if (mx >= 50 && mx <= 150 && my >= 50 && my <= 90) {
                PlayerData.save();
                game.changeState(new MenuState());
            }
            
            game.input.clearClickAndKey();
        }
    }

    private StatNode getNodeByIndex(int index) {
        for (StatNode n : nodes) if (n.statIndex == index) return n;
        return null;
    }

    @Override
    public void render(GamePanel game, Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, game.screenWidth, game.screenHeight);

        g.setColor(Color.YELLOW);
        g.setFont(FontManager.getFont(40f));
        g.drawString("STAT TREE", game.screenWidth / 2 - 120, 80);

        g.setColor(Color.ORANGE);
        g.setFont(FontManager.getFont(20f));
        g.drawString("Gold: " + PlayerData.gold, game.screenWidth - 200, 50);

        if (nodes == null) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(4));

        // Vẽ dây nối trước
        for (StatNode node : nodes) {
            boolean nodeUnlocked = statLevels[node.statIndex] > 0;
            for (int req : node.required) {
                StatNode parent = getNodeByIndex(req);
                boolean reqUnlocked = statLevels[req] > 0;
                if (nodeUnlocked) g2.setColor(Color.YELLOW);
                else if (reqUnlocked) g2.setColor(Color.GRAY);
                else g2.setColor(new Color(40, 40, 40));
                
                g2.drawLine(node.cx, node.cy, parent.cx, parent.cy);
            }
        }

        int totalUpgrades = 0;
        for (int l : statLevels) totalUpgrades += l;

        // Vẽ Node Box
        for (StatNode node : nodes) {
            int idx = node.statIndex;
            boolean canUnlock = true;
            for (int req : node.required) {
                if (statLevels[req] == 0) canUnlock = false;
            }

            int boxW = 160, boxH = 80;
            int bx = node.cx - boxW / 2;
            int by = node.cy - boxH / 2;

            g.setColor(new Color(20, 20, 20));
            g.fillRect(bx, by, boxW, boxH);

            g.setFont(FontManager.getFont(14f));
            if (!canUnlock) {
                g.setColor(Color.RED);
                g.drawRect(bx, by, boxW, boxH);
                g.setColor(Color.GRAY);
                g.drawString("LOCKED", bx + 45, by + 45);
            } else {
                boolean isMax = statLevels[idx] >= maxLevels[idx];
                g.setColor(statLevels[idx] > 0 ? Color.YELLOW : Color.WHITE);
                g.drawRect(bx, by, boxW, boxH);
                
                g.setColor(Color.WHITE);
                g.drawString(statNames[idx] + " " + statDescs[idx], bx + 10, by + 25);
                g.setColor(Color.CYAN);
                g.drawString("Lv: " + statLevels[idx] + "/" + maxLevels[idx], bx + 45, by + 45);

                if (isMax) {
                    g.setColor(Color.GRAY);
                    g.drawString("MAXED", bx + 55, by + 65);
                } else {
                    int cost = (int)(baseCosts[idx] * Math.pow(1.1, totalUpgrades));
                    g.setColor(PlayerData.gold >= cost ? Color.GREEN : Color.RED);
                    g.drawString("Cost: " + cost, bx + 35, by + 65);
                }
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFont(20f));
        g.drawRect(50, 50, 100, 40);
        g.drawString("BACK", 70, 75);
    }
}
