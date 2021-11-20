package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.view.GfxConstants;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class SetFocusTool extends NullTool {
    private final DrawingEditor editor;
    private final Game game;
    Position clickedPosition;

    public SetFocusTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = game;
    }

    public void mouseDown(MouseEvent event, int x, int y) {
        super.mouseDown(event, x, y);
        clickedPosition = GfxConstants.getPositionFromXY(x, y);
        if(!figureIsOutsideMap(clickedPosition)){
            game.setTileFocus(clickedPosition);
        }
    }

    private boolean figureIsOutsideMap (Position position) {
        return position.getRow() > 15 || position.getColumn() > 15;
    }
}
