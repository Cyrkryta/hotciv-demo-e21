package hotciv.brokerClient;

import com.google.gson.Gson;
import frds.broker.Invoker;
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
        Invoker unitInvoker = new HotCivUnitInvoker(servant, nameService, gson);
        invokerMap.put(OperationNames.UNIT_PREFIX, unitInvoker);
        Invoker tileInvoker = new HotCivTileInvoker(servant, nameService, gson);
        invokerMap.put(OperationNames.TILE_PREFIX, gameInvoker);

    }
    @Override
    public String handleRequest(String request) {
        return null;
    }
}
