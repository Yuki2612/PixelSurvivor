package gameproject.state;

import java.awt.Graphics;
import gameproject.GamePanel;

public interface State {
    void update(GamePanel game);
    void render(GamePanel game, Graphics g);
}
