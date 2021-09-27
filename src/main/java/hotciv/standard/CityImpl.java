package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

public class CityImpl implements City {
    private Player owner;
    private int treasury;
    private String production;
    private String workForceFocus;

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
        return workForceFocus;
    }

    public void addTreasury(int amount) {
        treasury += amount;
    }

    public void changeProd(String unitType){
        production = unitType;
    }

    public void changeOwner(Player newOwner){
        this.owner = newOwner;
    }

    public void changeWorkForceFocus(String balance) {
        this.workForceFocus = balance;
    }
}
