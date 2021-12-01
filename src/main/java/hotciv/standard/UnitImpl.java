package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

import java.util.UUID;

public class UnitImpl implements Unit {
    //Unique unit ID
    private String id;

    private String unitName;
    private Player playerOwner;
    private int maxMoveCount = 1;
    private int moveCount;
    private boolean isFortified;


    public UnitImpl(String unitName, Player playerOwner) {
        id = UUID.randomUUID().toString();

        this.unitName = unitName;
        this.playerOwner = playerOwner;
        this.isFortified = false;

        setMaxMoveCount();
        this.moveCount = maxMoveCount;
        }

    private void setMaxMoveCount() {
        if (this.unitName.equals(GameConstants.SANDWORM)){
            this.maxMoveCount = 2;
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
        int defensiveStrength = GameConstants.unitConstants.get(unitName)[GameConstants.DEFENSE_INDEX];
        if (isFortified){
            return defensiveStrength*2;
        }
        return defensiveStrength;
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
            moveCount = maxMoveCount;
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

    public String getId() {
        return id;
    }
}
