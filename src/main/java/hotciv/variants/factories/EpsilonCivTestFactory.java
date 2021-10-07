package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaUnitActionStrategy;
import hotciv.variants.alphaStrategies.AlphaWorldLayoutStrategy;
import hotciv.variants.epsilonStrategies.DieRollStrategy;
import hotciv.variants.epsilonStrategies.EpsilonAttackingStrategy;
import hotciv.variants.epsilonStrategies.EpsilonCivWinningStrategy;
import hotciv.variants.epsilonStrategies.LoadedRollStrategy;

public class EpsilonCivTestFactory implements GameFactory {
    DieRollStrategy rollStrategy;
    WinningStrategy winningStrategy;
    public EpsilonCivTestFactory(DieRollStrategy rollStrategy, WinningStrategy winningStrategy){
        this.rollStrategy = rollStrategy;
        this.winningStrategy = winningStrategy;
    }

    @Override
    public AgeStrategy createAgeStrategy() {
        AgeStrategy epsilonAgingStrategy = new LinearAgeStrategy();
        return epsilonAgingStrategy;
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        return winningStrategy;
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        UnitActionStrategy epsilonUnitActionStrategy = new AlphaUnitActionStrategy();
        return epsilonUnitActionStrategy;
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        WorldLayoutStrategy epsilonWorldLayoutStrategy = new AlphaWorldLayoutStrategy();
        return epsilonWorldLayoutStrategy;
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        AttackingStrategy epsilonAttackingStrategy = new EpsilonAttackingStrategy(rollStrategy);
        return epsilonAttackingStrategy;
    }
}