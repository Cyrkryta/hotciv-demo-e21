package hotciv.framework;

import java.util.*;

/**
 * Collection of constants used in HotCiv Game. Note that strings are
 * used instead of enumeration types to keep the set of valid
 * constants open to extensions by future HotCiv variants.  Enums can
 * only be changed by compile time modification.
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class GameConstants {
    // The size of the world is set permanently to a 16x16 grid
    public static final int WORLDSIZE = 16;
    // Valid unit types
    public static final String ARCHER = "archer";
    public static final String LEGION = "legion";
    public static final String SETTLER = "settler";
    public static final String SANDWORM = "sandworm";
    //Unit Attack
    public static final int ATTACK_INDEX = 0;
    public static final int ARCHER_ATK = 2;
    public static final int LEGION_ATK = 4;
    public static final int SETTLER_ATK = 0;
    public static final int SANDWORM_ATK = 0;
    //Unit Defenses
    public static final int DEFENSE_INDEX = 1;
    public static final int ARCHER_DEF = 3;
    public static final int LEGION_DEF = 2;
    public static final int SETTLER_DEF = 3;
    public static final int SANDWORM_DEF = 10;
    //Unit cost constants
    public static final int COST_INDEX = 2;
    public static final int ARCHER_COST = 10;
    public static final int LEGION_COST = 15;
    public static final int SETTLER_COST = 30;
    public static final int SANDWORM_COST = 30;

    //Unit Attack-, Defense- and Cost are store in an array
    public static final int[] archerArray = new int[]{ARCHER_ATK, ARCHER_DEF, ARCHER_COST};
    public static final int[] legionArray = new int[]{LEGION_ATK, LEGION_DEF, LEGION_COST};
    public static final int[] settlerArray = new int[]{SETTLER_ATK, SETTLER_DEF, SETTLER_COST};
    public static final int[] sandwormArray = new int[]{SANDWORM_ATK, SANDWORM_DEF, SANDWORM_COST};

    //This map contains a key-value pair of unit types and their corresponding constants from unit arrays
    public static final Map<String, int[]> unitConstants = Collections.unmodifiableMap(
            new HashMap<String, int[]>() {{
                put(ARCHER, archerArray);
                put(LEGION, legionArray);
                put(SETTLER, settlerArray);
                put(SANDWORM, sandwormArray);
            }});

    // Valid terrain types
    public static final String PLAINS = "plains";
    public static final String OCEANS = "ocean";
    public static final String FOREST = "forest";
    public static final String HILLS = "hills";
    public static final String MOUNTAINS = "mountain";
    public static final String DESSERT = "dessert";

    //List of movable terrain for land units
    public static final List<String> regularMovableTerrain = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add(PLAINS);
                add(FOREST);
                add(HILLS);
                add(DESSERT);
            }});

    // Valid production balance types
    public static final String productionFocus = "hammer";
    public static final String foodFocus = "apple";

    /*** ALPHA CIV CONSTANTS ***/
    //region
    //ALPHA CIV ONLY!!! Start positions of red and blue City
    public static final Position Blue_City_Pos = new Position(4, 1);
    public static final Position Red_City_Pos = new Position(1, 1);

    //ALPHA CIV ONLY!!!Map Tile position constants
    public static final Position Ocean_Tile_Position = new Position(1, 0);
    public static final Position Mountain_Tile_Position = new Position(2, 2);
    public static final Position Hill_Tile_Position = new Position(0, 1);

    //ALPHA CIV ONLY!!!Start Unit position constants
    public static final Position RedSettler_Start_Position = new Position(4, 3);
    public static final Position RedArcher_Start_Position = new Position(2, 0);
    public static final Position BlueLegion_Start_Position = new Position(3, 2);

    //Starting age for game
    public static int Start_Age = -4000;
    //endregion
}
