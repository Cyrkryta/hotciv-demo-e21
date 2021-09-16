package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    public String unitName;
    public Player playerOwner;
    public int moveCount = 1;
    public int defensiveStrength;
    public int attackingStrength;

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
        return moveCount;
    }

    @Override
    public int getDefensiveStrength() {
        switch (unitName) {
            case GameConstants.ARCHER:
                return GameConstants.ARCHER_DEF;
            case GameConstants.SETTLER:
                return GameConstants.SETTLER_DEF;
            case GameConstants.LEGION:
                return GameConstants.LEGION_DEF;
        }
        return 0;
    }

    @Override
    public int getAttackingStrength() {
        switch (unitName) {
            case GameConstants.ARCHER:
                return GameConstants.ARCHER_STR;
            case GameConstants.LEGION:
                return GameConstants.LEGION_STR;
            case GameConstants.SETTLER:
                return GameConstants.SETTLER_STR;
        }
        return 0;
    }

    public void reduceMoveCount() {
        moveCount -= 1;
    }

    public void resetMoveCount() {
        moveCount = 1;
    }
}
