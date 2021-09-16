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
        switch (unitName){
            case GameConstants.ARCHER:
                this.attackingStrength = 2;
                this.defensiveStrength = 3;
        }
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
            case GameConstants.SETTLER:
                return 3;
            case GameConstants.LEGION:
                return 2;
        }
        return 0;
    }

    @Override
    public int getAttackingStrength() {
        switch (unitName) {
            case GameConstants.ARCHER:
                return 2;
            case GameConstants.LEGION:
                return 4;
            case GameConstants.SETTLER:
                return 0;
        }
        return 0;
    }

    public void reduceMoveCount() {
        moveCount -= 1;
    }

    public void resetMoveCount() {
        moveCount = 1;
    };
}
