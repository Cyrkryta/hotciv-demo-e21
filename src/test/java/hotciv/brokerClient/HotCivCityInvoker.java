package hotciv.brokerClient;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.OperationNames;
import hotciv.framework.Player;

import javax.servlet.http.HttpServletResponse;

public class HotCivCityInvoker implements Invoker {
    private final City city;
    private final Gson gson;

    public HotCivCityInvoker(City servant) {
        city = servant;
        gson = new Gson();
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        //JsonArray array = JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();
        ReplyObject reply = null;

        if (requestObject.getOperationName().equals(OperationNames.CITY_GETOWNER_METHOD)) {
            Player player = city.getOwner();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(player));
        } else if (requestObject.getOperationName().equals(OperationNames.CITY_GETSIZE_METHOD)) {
            int size = city.getSize();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(size));
        }

        return gson.toJson(reply);
    }
}
