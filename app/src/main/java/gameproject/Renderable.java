package gameproject;

import java.awt.Graphics2D;

public interface Renderable {
    void render(Graphics2D g);
    float getBottomY();
}
