package hotciv.view.tool;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.stub.FakeObjectGame;
import hotciv.view.GfxConstants;
import hotciv.view.figure.HotCivFigure;
import minidraw.framework.DrawingEditor;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

public class SetFocusTool extends NullTool {
    private final DrawingEditor editor;
    private final FakeObjectGame game;
    HotCivFigure figureBelowClickPoint;
    Position clickedPosition;

    public SetFocusTool(DrawingEditor editor, Game game) {
        this.editor = editor;
        this.game = (FakeObjectGame) game;
    }

    public void mouseDown(MouseEvent event, int x, int y) {
        super.mouseDown(event, x, y);
        clickedPosition = GfxConstants.getPositionFromXY(x, y);
        game.setTileFocus(clickedPosition);
    }
}
