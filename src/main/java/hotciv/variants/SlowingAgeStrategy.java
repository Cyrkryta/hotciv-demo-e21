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
        if (roundCount <= 40 && currentAge <= 0) {
            returnAge += 100;
        }
        // Sequence around birth of christ is -100, -1, +1, +50.
        // Check if second age change in sequence around birth is +1 to 1.
        else if (roundCount == 41 && currentAge == 0) {
            returnAge += 1;
        }
        // Check if third age change in sequence around birth is -1 to 0
        else if (roundCount == 42 && currentAge == 1) {
            returnAge -= 1;
        }
        // Check if fourth age change in sequence around birth is +50 to 50.
        else if (roundCount == 43 && currentAge == 0) {
            returnAge += 50;
        }
        //Check if fifth age change increments by 50 until year 1750
        //1700/50 = 34 Rounds
        else if ( 44 <= roundCount && roundCount <= 78 && currentAge <= 1750){
          returnAge += 50;
        }
        //Check if sixth age change increments by 25 until year 1900
        //150/25 = 6 rounds
        else if ( 79 <= roundCount && roundCount <= 84 && currentAge < 1900){
            returnAge += 25;
        }

        //Check if seventh age change increments by 5 until year 1970
        //70/5 = 14 rounds
        else if ( 85 <= roundCount && roundCount <= 99 && currentAge <= 1970){
            returnAge += 5;
        }

        roundCount += 1;
        return returnAge;
    }
}

