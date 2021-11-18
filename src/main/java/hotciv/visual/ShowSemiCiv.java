package hotciv.visual;

import hotciv.framework.Game;
import hotciv.standard.GameImpl;
import hotciv.variants.factories.SemiCivFactory;
import hotciv.view.tool.CompositionTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class ShowSemiCiv {
    public static void main(String[] args) {
        Game game = new GameImpl(new SemiCivFactory());

        DrawingEditor editor =
                new MiniDrawApplication( "SemiCiv Demonstration",
                        new HotCivFactory4(game) );
        editor.open();
        editor.showStatus("Execute different command to examine the functional SemiCiv game.");

        editor.setTool(new CompositionTool(editor, game));
    }
}
