package hotciv.framework;

import java.util.HashMap;

public interface WorldLayoutStrategy {
    HashMap<Position, Tile> createWorld();

    HashMap<Position, City> placeCities();
}
