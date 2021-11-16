package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.stub.FakeObjectGame;
import hotciv.view.GfxConstants;
import hotciv.view.figure.HotCivFigure;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.framework.Tool;
import minidraw.standard.NullTool;
import minidraw.standard.handlers.DragTracker;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UnitMoveTool extends NullTool{
    protected Tool fChild;
    protected Tool cachedNullTool;

    private final DrawingEditor editor;
    private final FakeObjectGame game;

    Position fromPosition;
    Position toPosition;
    Point clickValue;
    HotCivFigure figureBelowClickPoint;
    Point FromPoint;
    Point ToPoint;

    public UnitMoveTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = (FakeObjectGame) game;
        fChild = cachedNullTool = new NullTool();
    }

    public void mouseDown(MouseEvent event, int x, int y) {
        super.mouseDown(event, x, y);

        figureBelowClickPoint = (HotCivFigure) editor.drawing().findFigure(x, y);

        fromPosition = GfxConstants.getPositionFromXY(x, y);
        FromPoint = new Point(x, y);
        clickValue = event.getPoint();

        if (figureBelowClickPoint == null) {
            System.out.println("No figure below click point");
        } else if (figureBelowClickPoint.getTypeString().equals(GfxConstants.UNIT_TYPE_STRING) &&
        game.getUnitAt(fromPosition).getOwner() == game.getPlayerInTurn()) {
            System.out.println("You got the unit, nice job!");
        }
    }

    public void mouseDrag(MouseEvent event, int x, int y) {
            super.mouseDrag(event, x, y);
            if (figureBelowClickPoint == null) {
                System.out.println("No figure below click point");
                return;
            } else if (figureBelowClickPoint.getTypeString().equals(GfxConstants.UNIT_TYPE_STRING) &&
                    game.getUnitAt(fromPosition).getOwner() == game.getPlayerInTurn()) {
                int figureX = (int) figureBelowClickPoint.displayBox().getCenterX();
                int figureY = (int) figureBelowClickPoint.displayBox().getCenterY();
                int xOffset = clickValue.x-figureX;
                int yOffset = clickValue.y-figureY;
                int dx = event.getPoint().x - (figureX + xOffset);
                int dy = event.getPoint().y - (figureY + yOffset);

                figureBelowClickPoint.moveBy(dx,dy);
            }
            clickValue = event.getPoint();
    }

    public void mouseUp(MouseEvent event, int x, int y) {
        super.mouseUp(event, x, y);
        toPosition = GfxConstants.getPositionFromXY(x,y);
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
        }
    }

    protected Tool createDragTracker(Figure f) {
        return new DragTracker(editor, f);
    }
}
