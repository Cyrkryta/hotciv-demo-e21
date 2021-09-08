package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    public String unitName;
    public Player playerOwner;
    public UnitImpl(String unitName, Player playerOwner) {
        this.unitName = unitName;
        this.playerOwner = playerOwner;
    }
    @Override
    public String getTypeString() {
        return unitName;
    }

    @Override
    public Player getOwner() {

        return playerOwner;
    }

    @Override
    public int getMoveCount() {
        return 0;
    }

    @Override
    public int getDefensiveStrength() {
        return 0;
    }

    @Override
    public int getAttackingStrength() {
        return 0;
    }
}
