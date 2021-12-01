package hotciv.stub.StubServants;

import hotciv.framework.Player;
import hotciv.framework.Unit;

import java.util.UUID;

//Test stub of unit for testing Iteration 10 implementation
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
    public int getMoveCount() { return 43; }
    public int getDefensiveStrength() { return 112; }
    public int getAttackingStrength() { return 79; }
    public void reduceMoveCount() {
        moveCount --;
    }

    public void resetMoveCount() {moveCount = 1;}

    public String getId() {
        return id;
    }
}
