package hotciv.framework;

/** Collection of constants used in HotCiv Game. Note that strings are
 * used instead of enumeration types to keep the set of valid
 * constants open to extensions by future HotCiv variants.  Enums can
 * only be changed by compile time modification.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
public class GameConstants {
  // The size of the world is set permanently to a 16x16 grid 
  public static final int WORLDSIZE = 16;
  // Valid unit types
  public static final String ARCHER    = "archer";
  public static final String LEGION    = "legion";
  public static final String SETTLER   = "settler";
  //Unit cost
  public static final int ARCHER_COST  = 10;
  public static final int LEGION_COST  = 15;
  public static final int SETTLER_COST = 30;
  //Unit Attack strength
  public static final int ARCHER_STR = 2;
  public static final int LEGION_STR = 4;
  public static final int SETTLER_STR= 0;
  //Unit cost
  public static final int ARCHER_DEF  = 3;
  public static final int LEGION_DEF = 2;
  public static final int SETTLER_DEF = 3;


  // Valid terrain types
  public static final String PLAINS    = "plains";
  public static final String OCEANS    = "ocean";
  public static final String FOREST    = "forest";
  public static final String HILLS     = "hills";
  public static final String MOUNTAINS = "mountain";
  // Valid production balance types
  public static final String productionFocus = "hammer";
  public static final String foodFocus = "apple";

  //ALPHA CIV ONLY!!! Start positions of red and blue City
  public static final Position Blue_City_Pos = new Position(4,1);
  public static final Position Red_City_Pos = new Position(1,1);

  //Map Tile position constants
  public static final Position Ocean_Tile_Position = new Position(1,0);
  public static final Position Mountain_Tile_Position = new Position(2,2);
  public static final Position Hill_Tile_Position = new Position(0,1);

  //Start Unit position constants
  public static final Position RedSettler_Start_Position = new Position(4,3);
  public static final Position RedArcher_Start_Position = new Position(2,0);
  public static final Position BlueLegion_Start_Position = new Position(3,2);
}
