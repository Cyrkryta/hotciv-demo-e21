package hotciv.framework;

public class OperationNames {
    // Defining seperator
    public static final String SEPERATOR = "_";

    // Defining prefixes
    public static final String GAME_PREFIX = "game";
    public static final String CITY_PREFIX = "city";
    public static final String UNIT_PREFIX = "unit";
    public static final String TILE_PREFIX = "tile";

    // Game method names
    public static final String GAME_GETWINNER_METHOD = GAME_PREFIX + SEPERATOR + "getWinner-method";
    public static final String GAME_GETAGE_METHOD = GAME_PREFIX + SEPERATOR + "getAge-method";
    public static final String GAME_MOVEUNIT_METHOD = GAME_PREFIX + SEPERATOR + "moveUnit-method";
    public static final String GAME_GETINTURN_METHOD = GAME_PREFIX + SEPERATOR + "playerInTurn-method";
    public static final String GAME_ENDOFTURN_METHOD = GAME_PREFIX + SEPERATOR + "endOfTurn-method";
    public static final String GAME_CHANGEWORKFORCE_METHOD = GAME_PREFIX + SEPERATOR + "changeWorkForce-method";
    public static final String GAME_CHANGEPRODUCTION_METHOD = GAME_PREFIX + SEPERATOR + "changeProduction-method";
    public static final String GAME_PERFORMUNITACTION_METHOD = GAME_PREFIX + SEPERATOR + "performUnitActionAt-method";
    public static final String GAME_SETTILEFOCUS_METHOD = GAME_PREFIX + SEPERATOR + "setTileFocus-method";
    public static final String GAME_GETCITYAT_METHOD = GAME_PREFIX + SEPERATOR + "getCityAt-method";
    public static final String GAME_GETUNITAT_METHOD = GAME_PREFIX + SEPERATOR + "getUnitAt-method";
    public static final String GAME_GETTILEAT_METHOD = GAME_PREFIX + SEPERATOR + "getTileAt-method";

    //City method names
    public static final String CITY_GETOWNER_METHOD = CITY_PREFIX + SEPERATOR + "getOwner-Method";
    public static final String CITY_GETSIZE_METHOD = CITY_PREFIX + SEPERATOR + "getSize-Method";
    public static final String CITY_GETTREASURY_METHOD = CITY_PREFIX + SEPERATOR + "getTreasury-Method";
    public static final String CITY_GETPRODUCTION_METHOD = CITY_PREFIX + SEPERATOR + "getProduction-Method";
    public static final String CITY_GETWORKFORCEFOCUS_METHOD = CITY_PREFIX + SEPERATOR + "getWorkForceFocus-Method";

    //Tile method names
    public static final String TILE_GETTYPESTRING_METHOD = TILE_PREFIX + SEPERATOR + "getTypeString-Method";

    // Unit method names
    public static final String UNIT_GETTYPESTRING_METHOD = UNIT_PREFIX + SEPERATOR + "getTypeString-method";
    public static final String UNIT_GETOWNER_METHOD = UNIT_PREFIX + SEPERATOR + "getOwner-method";
    public static final String UNIT_GETMOVECOUNT_METHOD = UNIT_PREFIX + SEPERATOR + "getMoveCount-method";
    public static final String UNIT_GETDEFENSIVESTRENGTH_METHOD = UNIT_PREFIX + SEPERATOR + "getDefensivestrength-method";
    public static final String UNIT_GETATTACKINGSTRENGTH_METHOD = UNIT_PREFIX + SEPERATOR + "getAttackingStrength-method";



}
