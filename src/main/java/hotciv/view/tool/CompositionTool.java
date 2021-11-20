package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import hotciv.view.figure.HotCivFigure;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Template for the CompositionTool exercise (FRS 36.44).
 * Composition tool is basically a State Pattern, similar
 * to MiniDraw's SelectionTool. That is, upon mouse-down
 * it must determine what the user wants (from analyzing
 * what graphical elements have been clicked - unit?
 * city? tile? turn-shield? etc.) and then set its
 * internal tool state to the appropriate tool - and
 * then delegate the mouse down request to that tool.
 *
 * @author Henrik Bærbak Christensen, Aarhus University
 */
public class CompositionTool extends NullTool {
  private final DrawingEditor editor;
  private final Game game;
  private HotCivFigure figureBelowClickPoint;
  private Position cityFocusPosition;

  private Tool state;

  public CompositionTool(DrawingEditor editor, Game game) {
    state = new NullTool();
    this.editor = editor;
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    // Find the figure (if any) just below the mouse click position
    figureBelowClickPoint = (HotCivFigure) editor.drawing().findFigure(x, y);
    Position clickedPosition = GfxConstants.getPositionFromXY(x, y);
    // Next determine the state of tool to use
    if (figureBelowClickPoint == null) {
      state = new SetFocusTool(editor, game);
    } else {
      if (figureBelowClickPoint.getTypeString().equals(GfxConstants.TURN_SHIELD_TYPE_STRING)) {
        state = new EndOfTurnTool(editor, game);
      } else if (figureBelowClickPoint.getTypeString().equals(GfxConstants.UNIT_TYPE_STRING)){
        if(e.isShiftDown()){
          state = new UnitActionTool(editor, game);
        } else if (figureIsOutsideMap(clickedPosition)){
          state = new ChangeProductionTool(editor, game, cityFocusPosition);
        } else {
          state = new UnitMoveTool(editor, game);
        }
      } else if (figureBelowClickPoint.getTypeString().equals(GfxConstants.CITY_TYPE_STRING)) {
          state = new SetFocusTool(editor, game);
          cityFocusPosition = clickedPosition;
      } else if (figureBelowClickPoint.getTypeString().equals(GfxConstants.FOCUS_TYPE_STRING)) {
          state = new ChangeWorkForceFocusTool(editor, game, cityFocusPosition);
      }
    }

    // Finally, delegate to the selected state
    state.mouseDown(e, x, y);
  }

  public void mouseDrag(MouseEvent e, int x, int y) {
    state.mouseDrag(e,x,y);
  }

  public void mouseUp(MouseEvent e, int x, int y) {
    state.mouseUp(e,x,y);
  }

  private boolean figureIsOutsideMap (Position position) {
    return position.getRow() > 15 || position.getColumn() > 15;
  }
}