package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.view.GfxConstants;
import hotciv.view.figure.HotCivFigure;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Template for the EndOfTurn Tool exercise (FRS 36.42)...
 *
 * @author Henrik Bærbak Christensen, Aarhus University
 */
public class EndOfTurnTool extends NullTool {
  private final DrawingEditor editor;
  private final Game game;

  public EndOfTurnTool(DrawingEditor editor, Game game) {
    this.editor = editor;
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    super.mouseDown(e, x, y);
    HotCivFigure figureBelowClickPoint = (HotCivFigure) editor.drawing().findFigure(x, y);
    if (figureBelowClickPoint == null) {
      System.out.println("No figure below click point");
      return;
    } else if (figureBelowClickPoint.getTypeString().equals(GfxConstants.TURN_SHIELD_TYPE_STRING)) {
      game.endOfTurn();
    }
  }

}
