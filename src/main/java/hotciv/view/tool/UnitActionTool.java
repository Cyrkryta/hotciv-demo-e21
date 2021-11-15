package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import hotciv.view.figure.HotCivFigure;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class UnitActionTool extends NullTool {
    private final DrawingEditor editor;
    private final Game game;
    private boolean shiftKeyDown = false;

    public UnitActionTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);
        HotCivFigure figureBelowClickPoint = (HotCivFigure) editor.drawing().findFigure(x, y);
        Position actionPosition = GfxConstants.getPositionFromXY(x, y);
        if (figureBelowClickPoint == null || !figureBelowClickPoint.getTypeString().equals(GfxConstants.UNIT_TYPE_STRING)) {
            System.out.println("Click a unit figure to perform its action");
        }else if (e.isShiftDown() && figureBelowClickPoint.getTypeString().equals(GfxConstants.UNIT_TYPE_STRING)){
            if (!game.getUnitAt(actionPosition).getOwner().equals(game.getPlayerInTurn())){
                System.out.println("Not your unit!");
            } else if (game.getUnitAt(actionPosition).getTypeString().equals(GameConstants.LEGION)){
                System.out.println("Legion cannot perform a action");
            } else {
                System.out.println("Performing unit action");
                game.performUnitActionAt(actionPosition);
            }

        }
    }
}
