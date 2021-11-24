package hotciv.brokerClient;

import com.google.gson.Gson;
import frds.broker.Invoker;
import hotciv.framework.Game;
import hotciv.framework.Unit;

public class HotCivUnitInvoker implements Invoker {

    private final Unit unit;
    private final Gson gson;

    public HotCivUnitInvoker(Unit servant) {
        unit = servant;
        gson = new Gson();
    }

    @Override
    public String handleRequest(String request) {
        return null;
    }
}
