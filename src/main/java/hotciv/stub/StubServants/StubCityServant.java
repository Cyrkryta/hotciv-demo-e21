package hotciv.stub.StubServants;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.UUID;


//Test stub of city for testing Iteration 10 implementation
public class StubCityServant implements City {
    private String id;

    private Player owner;
    private String cityProduction;
    private String workForceFocus = GameConstants.foodFocus;
    private int treasury;

    public StubCityServant (Player owner){
        id = UUID.randomUUID().toString();
        this.owner = owner;
    }

    @Override
    public Player getOwner() {return owner;}

    @Override
    public int getSize() {
        return 69;
    }

    @Override
    public int getTreasury() {return 420;}

    @Override
    public String getProduction() {
        return "FISK";
    }

    @Override
    public String getWorkforceFocus() {
        return "RØDE_PØLSER";
    }

    public void setProduction(String production) {
        cityProduction = production;
    }

    public void setWorkForceFocus(String focus){
        workForceFocus = focus;
    }

    public void addTreasury(int amount) {
        treasury += amount;
    }

    public String getId() {
        return id;
    }
}
