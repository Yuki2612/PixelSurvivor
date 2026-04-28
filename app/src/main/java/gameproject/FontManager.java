package gameproject;

import java.awt.Font;
import java.io.File;

public class FontManager {
    private static Font baseFont;

    public static void load(String path) {
        try {
            // Nạp file TTF và tạo Font gốc
            baseFont = Font.createFont(Font.TRUETYPE_FONT, new File(path));
        } catch (Exception e) {
            System.err.println("LỖI: Không tải được Font. Sẽ dùng Arial làm dự phòng. Lỗi: " + path);
            baseFont = new Font("Arial", Font.BOLD, 20);
        }
    }

    // Hàm lấy font với kích cỡ tùy chỉnh
    public static Font getFont(float size) {
        if (baseFont != null) {
            return baseFont.deriveFont(size); // Scale font không bị vỡ
        }
        return new Font("Arial", Font.BOLD, (int) size);
    }
}