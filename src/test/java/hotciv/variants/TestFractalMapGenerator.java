package hotciv.variants;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;
import hotciv.variants.factories.FractalMapFactory;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestFractalMapGenerator {

    /************ TESTS FOR RANDOM MAP GENERATION ************/
    @Test
    public void shouldGenerateNewMapEveryTime() {
        String tileToAdd = null;
        HashSet<String> listOfTiles = new HashSet<>();
        for (int i = 0; i < 25; i++) {
            Game game = new GameImpl(new FractalMapFactory());
            tileToAdd = game.getTileAt(new Position(0,0)).getTypeString();
            listOfTiles.add(tileToAdd);
        }
        assertThat(listOfTiles.size(), is(not(1)));
    }
}
