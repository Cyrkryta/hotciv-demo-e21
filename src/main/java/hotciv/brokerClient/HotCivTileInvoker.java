package hotciv.brokerClient;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.City;
import hotciv.framework.OperationNames;
import hotciv.framework.Player;
import hotciv.framework.Tile;

import javax.servlet.http.HttpServletResponse;

public class HotCivTileInvoker implements Invoker {
    private final Tile tile;
    private final Gson gson;

    public HotCivTileInvoker(Tile servant) {
        tile = servant;
        gson = new Gson();
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        //JsonArray array = JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();
        ReplyObject reply = null;

        if (requestObject.getOperationName().equals(OperationNames.TILE_GETTYPESTRING_METHOD)) {
            String typeString = tile.getTypeString();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(typeString));
        }
        return gson.toJson(reply);
    }
}
