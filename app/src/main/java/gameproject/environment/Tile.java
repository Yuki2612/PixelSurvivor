package gameproject.environment;

public class Tile {
    public Obstacle obstacle = null;
    public int cost = Integer.MAX_VALUE; // Khoảng cách tới Player
    public float dirX = 0, dirY = 0;    // Hướng dòng chảy
    public boolean isEntrance = false;
    public boolean isBuildingZone = false;
}
