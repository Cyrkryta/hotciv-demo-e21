package hotciv.stub.StubServants;

import hotciv.framework.Tile;

import java.util.UUID;

//Test stub of tile for testing Iteration 10 implementation
public class StubTileServant implements Tile {
    private String id;

    private String type;

    public StubTileServant(String type) {
        this.type = type;
        id = UUID.randomUUID().toString();
    }
    public String getTypeString() { return type; }
    public String getId() {
        return id;
    }
}
