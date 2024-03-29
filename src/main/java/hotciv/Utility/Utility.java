/*
 * Copyright (C) 2018 Henrik BÃ¦rbak Christensen, baerbak.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package hotciv.Utility;

import hotciv.framework.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * At 09 May 2018
 *
 * @author Henrik Baerbak Christensen, CS @ AU
 */

public class Utility {

    public static Iterator<Position> get8neighborhoodIterator(Position center) {
        List<Position> list = new ArrayList<>();
        // Define the 'delta' to add to the row for the 8 positions
        int[] rowDelta = new int[] {-1, -1, 0, +1, +1, +1, 0, -1};
        // Define the 'delta' to add to the column for the 8 positions34WN780-B
        int[] columnDelta = new int[] {0, +1, +1, +1, 0, -1, -1, -1};

        for (int index = 0; index < rowDelta.length; index++) {
            int row = center.getRow() + rowDelta[index];
            int col = center.getColumn() + columnDelta[index];
            if (row >= 0 && col >= 0
                    && row < GameConstants.WORLDSIZE
                    && col < GameConstants.WORLDSIZE)
                list.add(new Position(row, col));
        }
        return list.iterator();
    }

    public static Iterable<Position> get8neighborhoodOf(Position center) {
        final Iterator<Position> iterator = get8neighborhoodIterator(center);
        return () -> iterator;
    }

    /**
     * get the terrain factor for the attack and defense strength according to the
     * GammaCiv specification
     *
     * @param game
     *          the game the factor should be given for
     * @param position
     *          the position that the factor should be calculated for
     * @return the terrain factor
     */
    public static int getTerrainFactor(Game game, Position position) {
        // cities overrule underlying terrain
        if ( game.getCityAt(position) != null ) { return 3; }
        Tile t = game.getTileAt(position);
        if (Objects.equals(t.getTypeString(), GameConstants.FOREST) ||
                Objects.equals(t.getTypeString(), GameConstants.HILLS)) {
            return 2;
        }
        return 1;
    }

    /**
     * calculate the additional support a unit at position p owned by a given
     * player gets from friendly units on the given game.
     *
     * @param game
     *          the game to calculate on
     * @param position
     *          the position of the unit whose support is wanted
     * @param player
     *          the player owning the unit at position 'position'
     * @return the support for the unit, +1 for each friendly unit in the 8
     *         neighborhood.
     */
    public static int getFriendlySupport(Game game, Position position,
                                         Player player) {
        Iterator<Position> neighborhood = Utility.get8neighborhoodIterator(position);
        Position p;
        int support = 0;
        while ( neighborhood.hasNext() ) {
            p = neighborhood.next();
            if ( game.getUnitAt(p) != null &&
                    game.getUnitAt(p).getOwner() == player ) {
                support++;
            }
        }
        return support;
    }
}
