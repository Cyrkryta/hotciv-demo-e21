package hotciv.framework;

public class OperationNames {
    // Defining seperator
    public static final String SEPERATOR = "_";

    // Defining prefixes
    public static final String GAME = "game";
    public static final String CITY = "city";
    public static final String UNIT = "unit";
    public static final String TILE = "tile";

    // Game method names
    public static final String GAME_GETWINNER_METHOD = GAME + SEPERATOR + "getWinner-method";
    public static final String GAME_GETAGE_METHOD = GAME + SEPERATOR + "getAge-method";
    public static final String GAME_MOVEUNIT_METHOD = GAME + SEPERATOR + "moveUnit-method";
    public static final String GAME_GETINTURN_METHOD = GAME + SEPERATOR + "playerInTurn-method";
    public static final String GAME_ENDOFTURN_METHOD = GAME + SEPERATOR + "endOfTurn-method";
}
