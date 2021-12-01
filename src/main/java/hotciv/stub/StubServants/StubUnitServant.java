package hotciv.stub.StubServants;

import hotciv.framework.Player;
import hotciv.framework.Unit;

import java.util.UUID;

public class StubUnitServant implements Unit {
    private String type;
    private Player owner;
    private int moveCount = 1;
    private String id;

    public StubUnitServant(String type, Player owner) {
        id = UUID.randomUUID().toString();
        this.type = type;
        this.owner = owner;
    }
    public String getTypeString() { return type; }
    public Player getOwner() { return owner; }
    public int getMoveCount() { return moveCount; }
    public int getDefensiveStrength() { return 0; }
    public int getAttackingStrength() { return 0; }
    public void reduceMoveCount() {
        moveCount --;
    }

    public void resetMoveCount() {moveCount = 1;}

    public String getId() {
        return id;
    }
}
