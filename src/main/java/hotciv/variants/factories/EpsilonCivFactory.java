package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaUnitActionStrategy;
import hotciv.variants.alphaStrategies.AlphaWorldLayoutStrategy;
import hotciv.variants.epsilonStrategies.DieRollStrategy;
import hotciv.variants.epsilonStrategies.EpsilonAttackingStrategy;
import hotciv.variants.epsilonStrategies.EpsilonCivWinningStrategy;
import hotciv.variants.epsilonStrategies.NormalRollStrategy;

public class EpsilonCivFactory implements GameFactory {
    private DieRollStrategy normalRollStrategy = new NormalRollStrategy();


    @Override
    public AgeStrategy createAgeStrategy() {
        AgeStrategy epsilonAgingStrategy = new LinearAgeStrategy();
        return epsilonAgingStrategy;
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        WinningStrategy epsilonWinningStrategy = new EpsilonCivWinningStrategy();
        return epsilonWinningStrategy;
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
        AttackingStrategy epsilonAttackingStrategy = new EpsilonAttackingStrategy(normalRollStrategy);
        return epsilonAttackingStrategy;
    }
}
