package hotciv.brokerClient;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.*;

import javax.servlet.http.HttpServletResponse;

public class HotCivTileInvoker implements Invoker {
    private final Gson gson;
    NameService nameService;

    public HotCivTileInvoker(NameService nameService, Gson gson) {
        this.gson = new Gson();
        this.nameService = nameService;
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String objectId = requestObject.getOperationName();
        //JsonArray array = JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();
        ReplyObject reply = null;
        Tile tile = getTileOrThrowUnknownException(objectId);

        if (requestObject.getOperationName().equals(OperationNames.TILE_GETTYPESTRING_METHOD)) {
            String typeString = tile.getTypeString();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(typeString));
        }
        return gson.toJson(reply);
    }

    private Tile getTileOrThrowUnknownException(String objectId) {

        Tile tile = nameService.getTile(objectId);
        if (tile == null) {
            System.out.println("Tile with object id: " + objectId + " does not exist.");
        }
        return tile;
    }
}
