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
    public static final String GAME_CHANGEWORKFORCE_METHOD = GAME + SEPERATOR + "changeWorkForce-method";
    public static final String GAME_CHANGEPRODUCTION_METHOD = GAME + SEPERATOR + "changeProduction-method";
    public static final String GAME_PERFORMUNITACTION_METHOD = UNIT + SEPERATOR + "performUnitActionAt-method";
    public static final String GAME_SETTILEFOCUS_METHOD = UNIT + SEPERATOR + "setTileFocus-method";

    //City method names
    public static final String CITY_GETOWNER_METHOD = CITY + SEPERATOR + "getOwner-Method";
    public static final String CITY_GETSIZE_METHOD = CITY + SEPERATOR + "getSize-Method";
    public static final String CITY_GETTREASURY_METHOD = CITY + SEPERATOR + "getTreasury-Method";
    public static final String CITY_GETPRODUCTION_METHOD = CITY + SEPERATOR + "getProduction-Method";
    public static final String CITY_GETWORKFORCEFOCUS_METHOD = CITY + SEPERATOR + "getWorkForceFocus-Method";

    //Tile method names
    public static final String TILE_GETTYPESTRING_METHOD = TILE + SEPERATOR + "getTypeString-Method";

    // Unit method names
    public static final String UNIT_GETTYPESTRING_METHOD = UNIT + SEPERATOR + "getTypeString-method";
    public static final String UNIT_GETOWNER_METHOD = UNIT + SEPERATOR + "getOwner-method";
    public static final String UNIT_GETMOVECOUNT_METHOD = UNIT + SEPERATOR + "getMoveCount-method";
    public static final String UNIT_GETDEFENSIVESTRENGTH_METHOD = UNIT + SEPERATOR + "getDefensivestrength-method";
    public static final String UNIT_GETATTACKINGSTRENGTH_METHOD = UNIT + SEPERATOR + "getAttackingStrength-method";
}
