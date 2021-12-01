package hotciv.brokerClient;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HotCivCityInvoker implements Invoker {
    private final Gson gson;
    private final NameService nameService;

    public HotCivCityInvoker(NameService nameService, Gson gson) {
        this.gson = gson;
        this.nameService = nameService;
    }

    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String objectId = requestObject.getObjectId();
        String operationName = requestObject.getOperationName();
        //JsonArray array = JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();
        City city = getCityOrThrowUnknownException(objectId);

        ReplyObject reply = null;


        if (requestObject.getOperationName().equals(OperationNames.CITY_GETOWNER_METHOD)) {
            Player player = city.getOwner();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(player));
        } else if (requestObject.getOperationName().equals(OperationNames.CITY_GETSIZE_METHOD)) {
            int size = city.getSize();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(size));
        } else if (requestObject.getOperationName().equals(OperationNames.CITY_GETTREASURY_METHOD)) {
            int treasury = city.getTreasury();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(treasury));
        } else if (requestObject.getOperationName().equals(OperationNames.CITY_GETPRODUCTION_METHOD)) {
            String production = city.getProduction();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(production));
        } else if (requestObject.getOperationName().equals(OperationNames.CITY_GETWORKFORCEFOCUS_METHOD)) {
            String production = city.getWorkforceFocus();
            reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(production));
        }

        return gson.toJson(reply);
    }

    private City getCityOrThrowUnknownException(String objectId) {
        City city = nameService.getCity(objectId);
        if (city == null) {
            System.out.println("City with object id: " + objectId + " does not exist.");
        }
        return city;
    }
}
