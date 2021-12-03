package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import hotciv.view.figure.HotCivFigure;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class ChangeWorkForceFocusTool extends NullTool {
    private final DrawingEditor editor;
    private final Game game;
    private final Position cityPosition;

    public ChangeWorkForceFocusTool(DrawingEditor editor, Game game, Position cityPosition) {
        this.editor = editor;
        this.game = game;
        this.cityPosition = cityPosition;
    }

    @Override
    public void mouseDown(MouseEvent e, int x, int y) {
        super.mouseDown(e, x, y);
        HotCivFigure figureBelowClickPoint = (HotCivFigure) editor.drawing().findFigure(x, y);
        if (figureBelowClickPoint == null) {
            System.out.println("No figure below click point");
        } else if ((figureBelowClickPoint.getTypeString().equals(GfxConstants.FOCUS_TYPE_STRING)) && game.getCityAt(cityPosition).getOwner() == game.getPlayerInTurn()) {
            String newFocus = GameConstants.foodFocus;
            if (game.getCityAt(cityPosition).getWorkforceFocus().equals(newFocus)){
                newFocus = GameConstants.productionFocus;
            }
            game.changeWorkForceFocusInCityAt(cityPosition, newFocus);
            } else {
            System.out.println("You do not own this city");
        }
        }
    }