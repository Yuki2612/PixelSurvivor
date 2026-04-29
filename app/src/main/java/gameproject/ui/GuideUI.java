package gameproject.ui;

import java.awt.Color;
import java.awt.Graphics;
import gameproject.FontManager;

public class GuideUI {
    public static void draw(Graphics g, int screenWidth, int screenHeight, int currentPage) {
        g.setColor(new Color(20, 20, 30));
        g.fillRect(0, 0, screenWidth, screenHeight);

        // Draw X Button
        int closeX = screenWidth - 80;
        int closeY = 30;
        g.setColor(Color.RED);
        g.fillRect(closeX, closeY, 50, 50);
        g.setColor(Color.WHITE);
        g.drawRect(closeX, closeY, 50, 50);
        g.setFont(FontManager.getFont(40f));
        g.drawString("X", closeX + 10, closeY + 40);

        // Draw Arrows
        g.setColor(Color.DARK_GRAY);
        g.fillRect(50, screenHeight / 2 - 50, 100, 100);
        g.fillRect(screenWidth - 150, screenHeight / 2 - 50, 100, 100);
        g.setColor(Color.WHITE);
        g.drawString("<", 85, screenHeight / 2 + 15);
        g.drawString(">", screenWidth - 115, screenHeight / 2 + 15);

        g.setColor(Color.WHITE);
        g.setFont(FontManager.getFont(50f));
        g.drawString("GAME GUIDE (" + (currentPage + 1) + "/3)", screenWidth / 2 - 250, 80);

        g.setFont(FontManager.getFont(22f));
        int startX = screenWidth / 2 - 400;
        int startY = 180;
        int lineH = 40;

        if (currentPage == 0) {
            g.setColor(Color.YELLOW);
            g.drawString("1. BASICS & CONTROLS:", startX, startY);
            g.setColor(Color.WHITE);
            g.drawString("- W, A, S, D: Move your character.", startX, startY + lineH);
            g.drawString("- Left Mouse (Click/Hold): Shoot your weapon.", startX, startY + lineH * 2);
            g.drawString("- Shift: Dash to dodge damage and pass through enemies.", startX, startY + lineH * 3);

            g.setColor(Color.YELLOW);
            g.drawString("2. GAMEPLAY LOOP:", startX, startY + lineH * 5);
            g.setColor(Color.WHITE);
            g.drawString("- Kill enemies to earn EXP. Collect them to Level Up.", startX, startY + lineH * 6);
            g.drawString("- Choose upgrades carefully to survive the increasing horde.", startX, startY + lineH * 7);
            g.drawString("- A Boss appears every 5 Waves. Defeat it for a Weapon Chest.", startX, startY + lineH * 8);
            g.drawString("- Collect Soul Stones to buy permanent upgrades in SKILLS menu.", startX, startY + lineH * 9);
        } else if (currentPage == 1) {
            g.setColor(Color.YELLOW);
            g.drawString("3. ELEMENTAL REACTIONS:", startX, startY);
            g.setColor(Color.WHITE);
            g.drawString("Enemies can be afflicted with elements (Fire, Lightning, Ice, Acid).", startX,
                    startY + lineH);

            g.setColor(new Color(255, 100, 100)); // Fire
            g.drawString("- FIRE: Burns enemies over time (DoT).", startX, startY + lineH * 3);
            g.setColor(new Color(150, 200, 255)); // Ice
            g.drawString("- ICE/CHILL: Slows enemy movement speed by 30%.", startX, startY + lineH * 4);
            g.setColor(new Color(150, 150, 255)); // Lightning
            g.drawString("- SHOCK: Applies electrical charge for reactions.", startX, startY + lineH * 5);
            g.setColor(new Color(100, 255, 100)); // Acid
            g.drawString("- POISON: Applies venom effect for reactions.", startX, startY + lineH * 6);

            g.setColor(Color.ORANGE);
            g.drawString("COMBOS (REACTIONS):", startX, startY + lineH * 8);
            g.setColor(Color.WHITE);
            g.drawString("- THERMAL SHOCK (Fire + Ice): Deals 3x ATK burst damage and FREEZES for 1.5s.", startX,
                    startY + lineH * 9);
            g.drawString("- PLASMA (Poison + Shock): Spreads heavy Plasma DoT (50% ATK) to 5 nearby enemies!", startX, startY + lineH * 10);
        } else if (currentPage == 2) {
            g.setColor(Color.YELLOW);
            g.drawString("4. WEAPON EVOLUTIONS:", startX, startY);
            g.setColor(Color.WHITE);
            g.drawString("Evolve your weapons by reaching required upgrades before opening a Boss Chest:", startX,
                    startY + lineH);

            g.setColor(new Color(255, 150, 150));
            g.drawString("HELLFIRE BOOMSTICK (Evolved Shotgun)", startX, startY + lineH * 3);
            g.setColor(Color.WHITE);
            g.drawString("Req: Shotgun + Damage Upgrade (Lv.3) + Explosive Corpse Skill (Lv.1)", startX,
                    startY + lineH * 4);

            g.setColor(new Color(150, 255, 255));
            g.drawString("RAILGUN (Evolved Assault Rifle)", startX, startY + lineH * 6);
            g.setColor(Color.WHITE);
            g.drawString("Req: Assault Rifle + Optical Scope Upgrade (Lv.3)", startX, startY + lineH * 7);

            g.setColor(new Color(200, 150, 255));
            g.drawString("LIGHTNING GUN (Evolved SMG)", startX, startY + lineH * 9);
            g.setColor(Color.WHITE);
            g.drawString("Req: SMG + Fire Rate Upgrade (Lv.3) + Chain Lightning Skill (Lv.1)", startX,
                    startY + lineH * 10);

            g.setColor(Color.LIGHT_GRAY);
            g.drawString("Note: Breakthrough Skills (Lv.1) and Stat Upgrades (Lv.3) are required.", startX,
                    startY + lineH * 12);
        }
    }
}
