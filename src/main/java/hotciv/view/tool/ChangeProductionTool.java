package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import hotciv.view.figure.HotCivFigure;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class ChangeProductionTool extends NullTool {
    private final DrawingEditor editor;
    private final Game game;
    private final Position cityPosition;

    public ChangeProductionTool(DrawingEditor editor, Game game, Position cityPosition) {
        this.editor = editor;
        this.game = game;
        this.cityPosition = cityPosition;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);
        Position clickedPosition = GfxConstants.getPositionFromXY(x, y);
        HotCivFigure figureBelowClickPoint = (HotCivFigure) editor.drawing().findFigure(x, y);
        if (figureBelowClickPoint == null) {
            System.out.println("No figure below click point");
        } else if (figureBelowClickPoint.getTypeString().equals(GfxConstants.UNIT_TYPE_STRING)
                    && figureIsOutsideMap(clickedPosition)) {
            game.changeProductionInCityAt(cityPosition, nextUnitType(cityPosition));
        }
    }

    private boolean figureIsOutsideMap (Position position) {
        return position.getRow() > 15 || position.getColumn() > 15;
    }

    private String nextUnitType(Position position) {
        String cityProduction = game.getCityAt(position).getProduction();
        switch (cityProduction) {
            case (GameConstants.ARCHER): {
                return GameConstants.LEGION;
        }
            case (GameConstants.LEGION):{
                return GameConstants.SETTLER;
            }
            case (GameConstants.SETTLER):{
                return GameConstants.ARCHER;
            }
        }
        return GameConstants.ARCHER;
    }
}