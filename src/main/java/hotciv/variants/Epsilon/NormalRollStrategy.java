package hotciv.variants.Epsilon;


import java.util.Random;

public class NormalRollStrategy implements DieRollStrategy {

    @Override
    public int rollDie() {
            Random rand = new Random();
            int roll = rand.nextInt(6) + 1; // [1-6]
            return roll;
    }
}