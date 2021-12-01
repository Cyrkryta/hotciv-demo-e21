package hotciv.brokerClient;

import frds.broker.Requestor;
import hotciv.framework.GameConstants;
import hotciv.framework.OperationNames;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitProxy implements Unit {

    public static  String UNIT_OBJECTID;
    private final Requestor requestor;

    public UnitProxy(String id, Requestor requestor) {
        UNIT_OBJECTID = id;
        this.requestor = requestor;
    }

    @Override
    public String getTypeString() {
        String uid = requestor.sendRequestAndAwaitReply(UNIT_OBJECTID, OperationNames.UNIT_GETTYPESTRING_METHOD,
                String.class);
        return uid;
    }

    @Override
    public Player getOwner() {
        Player uid = requestor.sendRequestAndAwaitReply(UNIT_OBJECTID, OperationNames.UNIT_GETOWNER_METHOD,
                Player.class);
        return uid;
    }

    @Override
    public int getMoveCount() {
        int uid = requestor.sendRequestAndAwaitReply(UNIT_OBJECTID, OperationNames.UNIT_GETMOVECOUNT_METHOD,
                int.class);
        return uid;
    }

    @Override
    public int getDefensiveStrength() {
        int uid = requestor.sendRequestAndAwaitReply(UNIT_OBJECTID, OperationNames.UNIT_GETDEFENSIVESTRENGTH_METHOD,
                int.class);
        return uid;
    }

    @Override
    public int getAttackingStrength() {
        int uid = requestor.sendRequestAndAwaitReply(UNIT_OBJECTID, OperationNames.UNIT_GETATTACKINGSTRENGTH_METHOD,
                int.class);
        return uid;
    }

    public String getId() {
        return UNIT_OBJECTID;
    }
}
