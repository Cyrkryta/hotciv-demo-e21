package hotciv.brokerClient;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.*;

import javax.servlet.http.HttpServletResponse;

public class HotCivUnitInvoker implements Invoker {
    private final Gson gson;
    NameService nameService;

    public HotCivUnitInvoker(NameService nameService, Gson gson) {
        this.gson = new Gson();
        this.nameService = nameService;
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String objectId = requestObject.getObjectId();
        ReplyObject reply = null;

        Unit unit = getUnitOrThrowUnknownException(objectId);

        if (requestObject.getOperationName().equals(OperationNames.UNIT_GETTYPESTRING_METHOD)) {
            String unitType = unit.getTypeString();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(unitType));
        } else if (requestObject.getOperationName().equals(OperationNames.UNIT_GETOWNER_METHOD)) {
            Player owner = unit.getOwner();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(owner));
        } else if (requestObject.getOperationName().equals(OperationNames.UNIT_GETMOVECOUNT_METHOD)) {
            int moveCount = unit.getMoveCount();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(moveCount));
        } else if (requestObject.getOperationName().equals(OperationNames.UNIT_GETDEFENSIVESTRENGTH_METHOD)) {
            int defensiveStrength = unit.getDefensiveStrength();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(defensiveStrength));
        } else if (requestObject.getOperationName().equals(OperationNames.UNIT_GETATTACKINGSTRENGTH_METHOD)) {
            int attackingStrength = unit.getAttackingStrength();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(attackingStrength));
        }

        return gson.toJson(reply);
    }

    private Unit getUnitOrThrowUnknownException(String objectId) {
        Unit unit = nameService.getUnit(objectId);
        if (unit == null) {
            System.out.println("Unit with object id: " + objectId + " does not exist.");
        }
        return unit;
    }
}
