package hotciv.brokerClient;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

public class NullObserver implements GameObserver {
    @Override
    public void worldChangedAt(Position pos) {

    }

    @Override
    public void turnEnds(Player nextPlayer, int age) {

    }

    @Override
    public void tileFocusChangedAt(Position position) {

    }

    @Override
    public void cityWorkFocusChanges(String focus) {

    }

    @Override
    public void cityProductionChanged(String unitType) {

    }
}
