package hotciv.stub.StubServants;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.UUID;

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
        return 1;
    }

    @Override
    public int getTreasury() {return treasury;}

    @Override
    public String getProduction() {
        return cityProduction;
    }

    @Override
    public String getWorkforceFocus() {
        return workForceFocus;
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
