package hotciv.variants;

import hotciv.framework.AgeStrategy;

import java.util.ArrayList;
import java.util.List;

/** SLOWING AGE STRATEGY
 * Responsible for keeping track of games current age
 * Makes game age by the following rules:
 * +---------------------------+------------------------------------+
 * | Between 4000BC and 100BC  | 100 years pass per round.          |
 * | Around birth of Christ    | the sequence is -100, -1, +1, +50. |
 * | Between 50AD and 1750     | 50 years pass per round.           |
 * | Between 1750 and 1900     | 25 years pass per round.           |
 * | Between 1900 and 1970     | 5 years per round.                 |
 * | After 1970                | 1 year per round.                  |
 * +---------------------------+------------------------------------+
 **/


public class SlowingAgeStrategy implements AgeStrategy {

    private int roundCount = 1;

    @Override
    public int calculateAge(int currentAge) {
        int returnAge = currentAge;
        // 100 Years pass after every round between 4000BC and 100BC.
        // 100 years pass as the first element of sequence with age around 0.
        if (roundCount <= 40) {
            returnAge += 100;
        }
        // Sequence around birth of christ is -100, -1, +1, +50.
        // Check if second age change in sequence around birth is +1 to 1.
        if (roundCount == 41) {
            returnAge += 1;
        }
        // Check if third age change in sequence around birth is -1 to 0
        if (roundCount == 42) {
            returnAge -= 1;
        }
        // Check if fourth age change in sequence around birth is +50 to 50.
        if (roundCount == 43) {
            returnAge += 50;
        }

        roundCount += 1;
        return returnAge;
    }
}

