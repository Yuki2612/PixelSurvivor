package gameproject.state;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import gameproject.FontManager;
import gameproject.GamePanel;
import gameproject.Player;
import gameproject.skill.PassiveSkill;
import gameproject.skill.Upgrade;

public class GameOverState implements State {
    private final int score;
    private final int wave;
    private final String weaponName;
    private final List<String> upgradeLines;

    public GameOverState(int score, int wave, String weaponName, Player player, List<PassiveSkill> activeSkills) {
        this.score = score;
        this.wave = wave;
        this.weaponName = weaponName;
        this.upgradeLines = new ArrayList<>();

        // Thu thập tất cả upgrade có level > 0
        for (Upgrade u : Upgrade.values()) {
            int level = player.getUpgradeLevel(u);
            if (level > 0) {
                // Lấy tên ngắn trước dấu "("
                String[] parts = u.description.split("\\(");
                upgradeLines.add("• " + parts[0].trim() + "  [Lv." + level + "]");
            }
        }
    }

    @Override
    public void update(GamePanel game) {
        if (game.input.rPressed) {
            game.input.clearClickAndKey();
            game.startNewGame();
            return;
        }

        // Tọa độ nút Quay lại (Góc trên trái)
        int btnX = 25;
        int btnY = 25;
        int btnW = 110;
        int btnH = 45;

        if (game.input.mouseClicked) {
            if (game.input.mouseX >= btnX && game.input.mouseX <= btnX + btnW &&
                game.input.mouseY >= btnY && game.input.mouseY <= btnY + btnH) {
                game.input.clearClickAndKey();
                game.changeState(new MenuState());
            }
        }
    }

    @Override
    public void render(GamePanel game, Graphics g) {
        int cx = game.screenWidth / 2;
        int cy = game.screenHeight / 2;

        // Nền tối mờ
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(0, 0, game.screenWidth, game.screenHeight);

        // --- NÚT QUAY LẠI (Góc trên trái) ---
        int btnX = 25;
        int btnY = 25;
        int btnW = 110;
        int btnH = 45;

        boolean isHover = game.input.mouseX >= btnX && game.input.mouseX <= btnX + btnW &&
                          game.input.mouseY >= btnY && game.input.mouseY <= btnY + btnH;
        
        g.setColor(isHover ? new Color(120, 120, 120, 200) : new Color(60, 60, 60, 160));
        g.fillRoundRect(btnX, btnY, btnW, btnH, 12, 12);
        g.setColor(isHover ? Color.WHITE : Color.LIGHT_GRAY);
        g.drawRoundRect(btnX, btnY, btnW, btnH, 12, 12);

        // Vẽ hình tam giác
        int[] tx = { btnX + 12, btnX + 28, btnX + 28 };
        int[] ty = { btnY + 22, btnY + 12, btnY + 32 };
        g.fillPolygon(tx, ty, 3);

        g.setFont(FontManager.getFont(18f));
        g.drawString("MENU", btnX + 38, btnY + 28);

        // --- GAME OVER ---
        g.setColor(Color.RED);
        g.setFont(FontManager.getFont(75f));
        String title = "GAME OVER";
        int titleW = g.getFontMetrics().stringWidth(title);
        g.drawString(title, cx - titleW / 2, cy - 230);

        // --- Score & Wave ---
        g.setFont(FontManager.getFont(32f));
        g.setColor(Color.WHITE);
        String scoreLine = "Score: " + score + "        Wave: " + wave;
        int sw = g.getFontMetrics().stringWidth(scoreLine);
        g.drawString(scoreLine, cx - sw / 2, cy - 160);

        // --- Vũ khí ---
        g.setFont(FontManager.getFont(26f));
        g.setColor(Color.CYAN);
        String weapLine = "Weapon: " + weaponName;
        int ww = g.getFontMetrics().stringWidth(weapLine);
        g.drawString(weapLine, cx - ww / 2, cy - 110);

        // --- Đường kẻ phân cách ---
        g.setColor(new Color(255, 255, 255, 60));
        g.fillRect(cx - 350, cy - 88, 700, 2);

        // --- Tiêu đề Upgrades ---
        g.setFont(FontManager.getFont(24f));
        g.setColor(Color.YELLOW);
        String upHeader = upgradeLines.isEmpty() ? "No upgrades selected" : "── Upgrades ──";
        int uhw = g.getFontMetrics().stringWidth(upHeader);
        g.drawString(upHeader, cx - uhw / 2, cy - 60);

        // --- Danh sách upgrade: 2 cột ---
        g.setFont(FontManager.getFont(17f));
        g.setColor(new Color(220, 220, 220));
        int lineH = 24;
        int half = (upgradeLines.size() + 1) / 2;
        for (int i = 0; i < upgradeLines.size(); i++) {
            int col = i < half ? 0 : 1;
            int row = i < half ? i : i - half;
            int x = cx - 330 + col * 340;
            int y = cy - 28 + row * lineH;
            g.drawString(upgradeLines.get(i), x, y);
        }

        // --- Đường kẻ phân cách dưới ---
        int bottomSepY = cy - 28 + half * lineH + 10;
        g.setColor(new Color(255, 255, 255, 60));
        g.fillRect(cx - 350, bottomSepY, 700, 2);

        // --- Nút Restart (Text Hint) ---
        g.setFont(FontManager.getFont(28f));
        g.setColor(Color.LIGHT_GRAY);
        String hint = "Press  'R'  to Restart";
        int hw = g.getFontMetrics().stringWidth(hint);
        g.drawString(hint, cx - hw / 2, bottomSepY + 60);
    }
}
