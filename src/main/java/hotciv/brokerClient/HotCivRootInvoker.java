package hotciv.brokerClient;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.RequestObject;
import hotciv.framework.Game;
import hotciv.framework.NameService;
import hotciv.framework.OperationNames;

import java.util.*;

public class HotCivRootInvoker implements Invoker {
    private final Map<String, Invoker> invokerMap;
    private Gson gson;

    public  HotCivRootInvoker (Game servant){
        gson = new Gson();

        NameService nameService = new InMemoryNameService();
        invokerMap = new HashMap<>();

        Invoker gameInvoker = new HotCivGameInvoker(servant, nameService, gson);
        invokerMap.put(OperationNames.GAME_PREFIX, gameInvoker);
        Invoker cityInvoker = new HotCivCityInvoker(nameService, gson);
        invokerMap.put(OperationNames.CITY_PREFIX, cityInvoker);
        Invoker unitInvoker = new HotCivUnitInvoker(nameService, gson);
        invokerMap.put(OperationNames.UNIT_PREFIX, unitInvoker);
        Invoker tileInvoker = new HotCivTileInvoker(nameService, gson);
        invokerMap.put(OperationNames.TILE_PREFIX, tileInvoker);

    }
    @Override
    public String handleRequest(String request) {
        RequestObject requestObject = gson.fromJson(request, RequestObject.class);
        String operationName = requestObject.getOperationName();

        String reply;

        // Identify the invoker to use.
        String type = operationName.substring(0, operationName.indexOf(OperationNames.SEPERATOR));
        Invoker subInvoker = invokerMap.get(type);

        // Upcall on subInvoker.
        reply = subInvoker.handleRequest(request);

        return reply;
    }
}
