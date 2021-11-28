package hotciv.brokerClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.*;

import javax.servlet.http.HttpServletResponse;

public class HotCivGameInvoker implements Invoker {

    private final Game game;
    private final Gson gson;
    private final Position from;
    private final Position to;
    private final Position fakeCityPosition;

    public HotCivGameInvoker(Game servant) {
        game = servant;
        gson = new Gson();
        from = new Position(3,3);
        to = new Position(3,4);
        fakeCityPosition = new Position(3,5);
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        //JsonArray array = JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();
        ReplyObject reply = null;

        if (requestObject.getOperationName().equals(OperationNames.GAME_GETWINNER_METHOD)) {
            Player player = game.getWinner();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(player));
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GETAGE_METHOD)) {
            int age = game.getAge();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(age));
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_MOVEUNIT_METHOD)) {
            boolean isPossible = game.moveUnit(from, to);
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(isPossible));
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GETINTURN_METHOD)) {
            Player player = game.getPlayerInTurn();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(player));
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_ENDOFTURN_METHOD)) {
            game.endOfTurn();
            reply = new ReplyObject(HttpServletResponse.SC_OK, null);
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_CHANGEPRODUCTION_METHOD)) {
            game.changeProductionInCityAt(fakeCityPosition, GameConstants.SETTLER);
            reply = new ReplyObject(HttpServletResponse.SC_OK, null);
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_CHANGEWORKFORCE_METHOD)) {
            game.changeWorkForceFocusInCityAt(fakeCityPosition, GameConstants.ARCHER);
            reply = new ReplyObject(HttpServletResponse.SC_OK, null);
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_PERFORMUNITACTION_METHOD)) {
            game.performUnitActionAt(fakeCityPosition);
            reply = new ReplyObject(HttpServletResponse.SC_OK, null);
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_SETTILEFOCUS_METHOD)) {
            game.performUnitActionAt(fakeCityPosition);
            reply = new ReplyObject(HttpServletResponse.SC_OK, null);
        }
        return gson.toJson(reply);
    }
}
