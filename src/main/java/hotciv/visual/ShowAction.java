package hotciv.visual;

import hotciv.framework.Game;
import hotciv.stub.FakeObjectGame;
import hotciv.view.tool.UnitActionTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;

/** Template code for exercise.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University
   
   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
 */
public class ShowAction {
  
  public static void main(String[] args) {
    Game game = new FakeObjectGame();

    DrawingEditor editor = 
      new MiniDrawApplication("Shift-Click unit to invoke its action",
                               new HotCivFactory4(game) );
    editor.open();
    editor.showStatus("Shift-Click on unit to see Game's performAction method being called.");

    // TODO: Replace the setting of the tool with your ActionTool implementation.
    editor.setTool(new UnitActionTool(editor,game));
  }
}
