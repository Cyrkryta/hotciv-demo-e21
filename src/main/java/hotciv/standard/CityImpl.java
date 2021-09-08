package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

public class CityImpl implements City {
    private final Player owner;
    private int treasury;
    private String production;

    public CityImpl(Player owner){
        this.owner = owner;
    }
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public int getTreasury() {
        return treasury;
    }

    @Override
    public String getProduction() {
        return production;
    }

    @Override
    public String getWorkforceFocus() {
        return null;
    }

    public void addTreasury(int amount) {
        treasury += amount;
    }

    public void changeProd(String unitType){
        production = unitType;
    }

}
