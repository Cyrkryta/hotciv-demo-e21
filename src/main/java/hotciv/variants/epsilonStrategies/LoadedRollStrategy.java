package hotciv.variants.epsilonStrategies;

/** TEST DOUBLE CLASS FOR EPSILON ATTACK STRATEGY**/
public class LoadedRollStrategy implements DieRollStrategy {
    private int rollingDieNumber = 1;
    private int die1 = 0;
    private int die2 = 0;
    public LoadedRollStrategy() {
    }

    @Override
    public int rollDie() {
        if(rollingDieNumber == 1){
            rollingDieNumber = 2;
            return die1;
        }else if (rollingDieNumber == 2){
            rollingDieNumber = 1;
            return die2;
        }
        return 0;
    }


    public void setDie1And2(int die1, int die2) {
        this.die1 = die1;
        this.die2 = die2;
    }

}
