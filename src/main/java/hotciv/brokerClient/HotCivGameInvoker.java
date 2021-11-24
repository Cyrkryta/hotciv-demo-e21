package hotciv.brokerClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.Game;
import hotciv.framework.OperationNames;
import hotciv.framework.Player;

import javax.servlet.http.HttpServletResponse;

public class HotCivGameInvoker implements Invoker {

    private final Game game;
    private final Gson gson;

    public HotCivGameInvoker(Game servant) {
        game = servant;
        gson = new Gson();
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
        }

        return gson.toJson(reply);
    }
}
