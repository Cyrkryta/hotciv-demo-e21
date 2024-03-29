package hotciv.visual;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.stub.FakeObjectGame;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Show how GUI changes can be induced by making
    updates in the underlying domain model.

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
public class ShowUpdating {
  
  public static void main(String[] args) {
    Game game = new FakeObjectGame();

    DrawingEditor editor = 
      new MiniDrawApplication( "Click anywhere to see Drawing updates",  
                               new HotCivFactory4(game) );
    editor.open();
    editor.setTool( new UpdateTool(editor, game) );

    editor.showStatus("Click anywhere to state changes reflected on the GUI");
                      
    // Try to set the selection tool instead to see
    // completely free movement of figures, including the icon

    // editor.setTool( new SelectionTool(editor) );
  }
}

/** A tool that simply 'does something' new every time
 * the mouse is clicked anywhere; as a visual testing
 * of the 'from Domain to GUI' data flow is coded correctly*/
class UpdateTool extends NullTool {
  private FakeObjectGame game;
  private DrawingEditor editor;
  public UpdateTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = (FakeObjectGame) game;
  }
  private int count = 0;

  public void mouseDown(MouseEvent e, int x, int y) {
    switch(count) {
    case 0: {
      editor.showStatus( "State change: Moving archer to (1,1)" );
      game.moveUnit( new Position(2,0), new Position(1,1) );
      break;
    }
    case 1: {
      editor.showStatus( "State change: Moving archer to (2,2)" );
      game.moveUnit( new Position(1,1), new Position(2,2) );
      break;
    }
      case 2: {
        editor.showStatus( "State change: Moving theta unit to (6,4)" );
        game.moveUnit( new Position(6,3), new Position(6,4) );
        break;
      }
    case 3: {
      editor.showStatus( "State change: End of Turn (over to blue)" );
      game.endOfTurn();
      break;
    }
    case 4: {
      editor.showStatus( "State change: End of Turn (over to red)" );
      game.endOfTurn();
      break;
    }
    case 5: {
      editor.showStatus( "State change: Inspect Unit at (4,2)" );
      game.setTileFocus(new Position(4,2));
      break;
    }
    case 6: {
      editor.showStatus( "State change: Inspect City/Unit at (3,2)" );
      game.setTileFocus(new Position(3,2));
      break;
    }
    case 7: {
      editor.showStatus( "State change: Inspect City at (8,8)" );
      game.setTileFocus(new Position(8,8));
      break;
    }
    case 8: {
      editor.showStatus( "State change: Production changed in City at (8,8)" );
      Position cityPos = new Position(8,8);
      game.changeProductionInCityAt(cityPos, GameConstants.ARCHER);
      break;
    }
    case 9: {
      editor.showStatus( "State change: Focus changed in City at (8,8)" );
      Position cityPos = new Position(8,8);
      game.changeWorkForceFocusInCityAt(cityPos, GameConstants.productionFocus);
      break;
    }
    case 10: {
      editor.showStatus( "State change: Focus on empty tile at (8,9)" );
      game.setTileFocus(new Position(8,9));
      break;
    }
    case 11: {
      editor.showStatus("Creating new unit");
      game.produceUnits(new Position(8,8));
    }
      // TODO: Add more state changes for other things to test
    default: {
      editor.showStatus("No more changes in my list...");
    }
    }
    count ++;
  }
}

