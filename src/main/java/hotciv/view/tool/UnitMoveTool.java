package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import hotciv.view.figure.HotCivFigure;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.*;
import java.awt.event.MouseEvent;

public class UnitMoveTool extends NullTool{
    private final DrawingEditor editor;
    private final Game game;
    Position fromPosition;
    Position toPosition;
    Point clickValue;
    HotCivFigure figureBelowClickPoint;

    public UnitMoveTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    public void mouseDown(MouseEvent event, int x, int y) {
        super.mouseDown(event, x, y);
        figureBelowClickPoint = (HotCivFigure) editor.drawing().findFigure(x, y);
        fromPosition = GfxConstants.getPositionFromXY(x, y);
        clickValue = event.getPoint();
        if (figureBelowClickPoint == null) {
            System.out.println("No figure below click point");
            return;
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
            int figureY = (int) figureBelowClickPoint.displayBox().getCenterX();
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
        if (figureBelowClickPoint == null) {
            System.out.println("Pick a figure to place it");
            return;
        }
        if (figureBelowClickPoint.getTypeString().equals(GfxConstants.UNIT_TYPE_STRING) &&
                game.getUnitAt(fromPosition).getOwner() == game.getPlayerInTurn()) {
            int movingDistance = rowColumnDistance(fromPosition, toPosition);
            if (game.getUnitAt(fromPosition).getMoveCount() >= 1) {
                if (movingDistance >= 0 && movingDistance <=1) {
                    game.moveUnit(fromPosition, toPosition);
                } else {
                    System.out.println("The move is illegal. Try again");
                    game.moveUnit(fromPosition, fromPosition);
                }
            } else {
                System.out.println("The unit doesn't have any moves left. Try another.");
            }
            /*if (Math.abs(toPosition.getColumn()-fromPosition.getColumn()) >= 0
                    && Math.abs(toPosition.getColumn()-fromPosition.getColumn()) <
                    game.getUnitAt(fromPosition).getMoveCount()) {
                if (Math.abs(toPosition.getRow() - fromPosition.getRow()) >= 0
                        && Math.abs(toPosition.getRow() - fromPosition.getRow()) <
                        game.getUnitAt(fromPosition).getMoveCount()) {
                    game.moveUnit(fromPosition, toPosition);
                }
            }*/
        } else {
            System.out.println("You doesn't own this unit");
        }
    }

    public int rowColumnDistance(Position fromPosition, Position toPosition) {
        int rowDistance = 0;
        int columnDifference = toPosition.getColumn()-fromPosition.getColumn();
        int rowDifference = toPosition.getRow()-fromPosition.getRow();
        rowDistance = Math.abs(columnDifference+rowDifference);
        return rowDistance;
    }
}
