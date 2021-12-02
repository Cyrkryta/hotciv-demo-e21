package hotciv.brokerClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.*;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;
import hotciv.stub.StubServants.StubCityServant;
import hotciv.stub.StubServants.StubTileServant;
import hotciv.stub.StubServants.StubUnitServant;

import javax.servlet.http.HttpServletResponse;

public class HotCivGameInvoker implements Invoker {

    private final Game game;
    private final Gson gson;
    private final NameService nameService;


    public HotCivGameInvoker(Game servant, NameService nameService, Gson gson) {
        game = servant;
        this.gson = gson;
        this.nameService = nameService;
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        JsonArray array = JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();
        ReplyObject reply = null;

        if (requestObject.getOperationName().equals(OperationNames.GAME_GETWINNER_METHOD)) {
            Player player = game.getWinner();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(player));
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GETAGE_METHOD)) {
            int age = game.getAge();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(age));
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_MOVEUNIT_METHOD)) {
            Position from = gson.fromJson(array.get(0), Position.class);
            Position to = gson.fromJson(array.get(1), Position.class);
            boolean isPossible = game.moveUnit(from, to);
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(isPossible));
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GETINTURN_METHOD)) {
            Player player = game.getPlayerInTurn();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(player));
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_ENDOFTURN_METHOD)) {
            game.endOfTurn();
            reply = new ReplyObject(HttpServletResponse.SC_OK, null);
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_CHANGEPRODUCTION_METHOD)) {
            Position position = gson.fromJson(array.get(0), Position.class);
            String unitType = gson.fromJson(array.get(1), String.class);
            game.changeProductionInCityAt(position, unitType);
            reply = new ReplyObject(HttpServletResponse.SC_OK, null);
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_CHANGEWORKFORCE_METHOD)) {
            Position position = gson.fromJson(array.get(0), Position.class);
            String focus = gson.fromJson(array.get(1), String.class);
            game.changeWorkForceFocusInCityAt(position, focus);
            reply = new ReplyObject(HttpServletResponse.SC_OK, null);
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_PERFORMUNITACTION_METHOD)) {
            Position position = gson.fromJson(array.get(0), Position.class);
            game.performUnitActionAt(position);
            reply = new ReplyObject(HttpServletResponse.SC_OK, null);
        } else if (requestObject.getOperationName().equals(OperationNames.GAME_SETTILEFOCUS_METHOD)) {
            Position position = gson.fromJson(array.get(0), Position.class);
            game.setTileFocus(position);
            reply = new ReplyObject(HttpServletResponse.SC_OK, null);

        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GETCITYAT_METHOD)) {
            Position position = gson.fromJson(array.get(0), Position.class);
            City city = game.getCityAt(position);
            if (city == null){
                reply = new ReplyObject(HttpServletResponse.SC_NO_CONTENT, gson.toJson(null));
            } else {
                String id = city.getId();
                nameService.putCity(id, city);
                reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(id));
            }

        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GETUNITAT_METHOD)) {
            Position position = gson.fromJson(array.get(0), Position.class);
            Unit unit = game.getUnitAt(position);
            if (unit == null) {
                reply = new ReplyObject(HttpServletResponse.SC_NO_CONTENT, gson.toJson(null));
            } else {
                String id = unit.getId();
                nameService.putUnit(id, unit);
                reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(id));
            }

        } else if (requestObject.getOperationName().equals(OperationNames.GAME_GETTILEAT_METHOD)) {
            Position position = gson.fromJson(array.get(0), Position.class);
            Tile tile = game.getTileAt(position);
            if (tile == null) {
                reply = new ReplyObject(HttpServletResponse.SC_NO_CONTENT, gson.toJson(null));
            } else {
                String id = tile.getId();
                nameService.putTile(id, tile);
                reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(id));
            }
        }
        return gson.toJson(reply);
    }
}
