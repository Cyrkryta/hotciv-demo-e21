package hotciv.variants;

import hotciv.framework.AgeStrategy;
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

    @Override
    public int calculateAge(int currentAge) {
        int returnAge = currentAge;
        if (currentAge < -100) {
            returnAge += 100;
        }else if (currentAge < 0){
            returnAge += 100;
        }


        return returnAge;
    }
}

