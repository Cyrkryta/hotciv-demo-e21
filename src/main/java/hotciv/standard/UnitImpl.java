package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
    private String unitName;
    private Player playerOwner;
    private int moveCount = 1;
    private boolean isFortified;


    public UnitImpl(String unitName, Player playerOwner) {
        this.unitName = unitName;
        this.playerOwner = playerOwner;
        this.isFortified = false;
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
                if(isFortified){
                    return GameConstants.ARCHER_DEF*2;
                }else{
                    return GameConstants.ARCHER_DEF;
                }
            case GameConstants.SETTLER:
                return GameConstants.SETTLER_DEF;
            case GameConstants.LEGION:
                return GameConstants.LEGION_DEF;
        }
        return 0;
    }

    @Override
    public int getAttackingStrength() {
        return GameConstants.unitConstants.get(unitName)[GameConstants.ATTACK_INDEX];
    }

    public void reduceMoveCount() {
        moveCount -= 1;
    }

    public void resetMoveCount() {
        //Fortified units are not granted movement
        if(!isFortified){
            moveCount = 1;
        }
    }

    public void fortifyUnit(){
        if (isFortified){
            this.isFortified = false;
            resetMoveCount();
        }else{
            reduceMoveCount();
            this.isFortified = true;}
    }
}
