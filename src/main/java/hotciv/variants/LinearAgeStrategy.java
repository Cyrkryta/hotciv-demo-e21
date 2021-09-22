package hotciv.variants;

import hotciv.framework.AgeStrategy;
import hotciv.framework.GameConstants;

public class LinearAgeStrategy implements AgeStrategy {

    @Override
    public int calculateAge(int roundsPassed) {
        //Instantiate age at games starting age
        int age = GameConstants.Start_Age;
        //Linearly increment age based on rounds passed
        age += roundsPassed*100;
        return age;
    }
}
