package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import hotciv.view.figure.HotCivFigure;
import minidraw.framework.*;
import minidraw.standard.NullTool;
import minidraw.standard.handlers.DragTracker;

import java.awt.event.MouseEvent;

public class UnitMoveTool extends NullTool{
    // Tools
    protected Tool fChild;
    protected Tool cachedNullTool;
    // Figures
    protected HotCivFigure draggedFigure;
    // Defining integers
    private int fFirstX, fFirstY; // Previous mouse positions;

    private final DrawingEditor editor;
    private final Game game;

    public UnitMoveTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
        fChild = cachedNullTool = new NullTool();
        draggedFigure = null;
    }

    public void mouseDown(MouseEvent event, int x, int y) {
        fFirstX = x;
        fFirstY = y;

        Drawing model = editor.drawing();
        model.lock();

        draggedFigure = (HotCivFigure) model.findFigure(event.getX(), event.getY());

        if (draggedFigure.getTypeString().equals(GfxConstants.UNIT_TYPE_STRING)) {
            fChild = createDragTracker(draggedFigure);
            System.out.println("You clicked a unit figure!");
        } else {
            if (!event.isShiftDown()) {
                model.clearSelection();
            }
        }
        fChild.mouseDown(event, x, y);
    }

    public void mouseDrag(MouseEvent event, int x, int y) {
        fChild.mouseDrag(event, x, y);
    }

    public void mouseUp(MouseEvent event, int x, int y) {
        editor.drawing().unlock();
        Position fromPosition = GfxConstants.getPositionFromXY(fFirstX, fFirstY);
        Position toPosition = GfxConstants.getPositionFromXY(x, y);
        if (fromPosition.getColumn() == toPosition.getColumn() && fromPosition.getRow() == toPosition.getRow()) {
            fChild = new SetFocusTool(editor, game);
            fChild.mouseDown(event, x, y);
        } else {
            if (game.moveUnit(fromPosition, toPosition)) {
                System.out.println("Move successful!");
                fChild.mouseUp(event, x, y);
                fChild = cachedNullTool;
                draggedFigure = null;
            } else {
                System.out.println("The move is illegal... Try again.");
                draggedFigure.moveBy(fFirstX-x, fFirstY-y);
            }
        }


        /*toPosition = GfxConstants.getPositionFromXY(x,y);
        ToPoint = new Point(x, y);
        if (figureBelowClickPoint == null) {
            System.out.println("Pick a figure to place it");
            return;
        }
        if (figureBelowClickPoint.getTypeString().equals(GfxConstants.UNIT_TYPE_STRING) &&
                game.getUnitAt(fromPosition).getOwner() == game.getPlayerInTurn()) {
                if (game.moveUnit(fromPosition, toPosition)) {
                    System.out.println("Move successful!");;
                } else {
                    System.out.println("The move is illegal. Try again");
                    int fromToRowDifference = FromPoint.x- ToPoint.x;
                    int fromToColumnDifference = FromPoint.y- ToPoint.y;
                    figureBelowClickPoint.moveBy(fromToRowDifference, fromToColumnDifference);
                    }
        } else {
            System.out.println("You don't own this unit");
        }*/
    }

    protected Tool createDragTracker(Figure f) {
        return new DragTracker(editor, f);
    }
}
