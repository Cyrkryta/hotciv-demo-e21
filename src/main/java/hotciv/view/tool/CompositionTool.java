package hotciv.view.tool;

import hotciv.framework.Game;
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
    // Next determine the state of tool to use

    if (figureBelowClickPoint == null) {
      state = new SetFocusTool(editor, game);
    } else {
      if (figureBelowClickPoint.getTypeString().equals(GfxConstants.TURN_SHIELD_TYPE_STRING)) {
        state = new EndOfTurnTool(editor, game);
      } else if (figureBelowClickPoint.getTypeString().equals(GfxConstants.UNIT_TYPE_STRING)){
        if(e.isShiftDown()){
          state = new UnitActionTool(editor, game);
        } else {
          state = new UnitMoveTool(editor, game);
        }
      }else if (figureBelowClickPoint.getTypeString().equals(GfxConstants.CITY_TYPE_STRING)) {
        state = new SetFocusTool(editor, game);
      }


/*      switch (figureBelowClickPoint.getTypeString()) {
        case GfxConstants.TURN_SHIELD_TYPE_STRING: {
          state = new EndOfTurnTool(editor, game);
        }
        case GfxConstants.UNIT_TYPE_STRING: {
          if (e.isShiftDown()) {
            state = new UnitActionTool(editor, game);
          }else {
            state = new UnitMoveTool(editor, game);
          }
        }
      }*/


/*      if (figureBelowClickPoint.getTypeString().equals(GfxConstants.TURN_SHIELD_TYPE_STRING)) {

      } else {
        // TODO: handle all the cases - action tool, unit move tool, etc
        System.out.println("TODO: PENDING IMPLEMENTATION based upon hitting a figure with type: "
                + figureBelowClickPoint.getTypeString());
        state = new NullTool();
      }*/
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
}