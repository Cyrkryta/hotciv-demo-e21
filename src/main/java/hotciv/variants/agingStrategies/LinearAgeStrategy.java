package hotciv.variants.agingStrategies;

import hotciv.framework.AgeStrategy;
import hotciv.framework.GameConstants;

public class LinearAgeStrategy implements AgeStrategy {
    //Instantiate age at games starting age

    @Override
    public int calculateAge(int currentAge) {
        return currentAge+100;
    }
}

