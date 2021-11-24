package hotciv.brokerClient;

import frds.broker.Servant;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class StubUnitBrokerClient implements Unit, Servant {
    @Override
    public String getTypeString() {
        String unitType = GameConstants.SETTLER;
        return unitType;
    }

    @Override
    public Player getOwner() {
        Player owner = Player.GREEN;
        return owner;
    }

    @Override
    public int getMoveCount() {
        int moveCount = 43;
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        int defensiveStrength = 112;
        return defensiveStrength;
    }

    @Override
    public int getAttackingStrength() {
        int attackingStrength = 79;
        return attackingStrength;
    }
}
