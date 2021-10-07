package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaCivWinningStrategy;
import hotciv.variants.alphaStrategies.AlphaUnitActionStrategy;
import hotciv.variants.alphaStrategies.AlphaWorldLayoutStrategy;

public class AlphaCivFactory implements GameFactory {
    @Override
    public AgeStrategy createAgeStrategy() {
        AgeStrategy alphaAgingStrategy = new LinearAgeStrategy();
        return alphaAgingStrategy;
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        WinningStrategy alphaWinningStrategy = new AlphaCivWinningStrategy();
        return alphaWinningStrategy;
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        UnitActionStrategy alphaUnitActionStrategy = new AlphaUnitActionStrategy();
        return alphaUnitActionStrategy;
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        WorldLayoutStrategy alphaWorldLayoutStrategy = new AlphaWorldLayoutStrategy();
        return alphaWorldLayoutStrategy;
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        AttackingStrategy alphaAttackingStrategy = new AlphaAttackingStrategy();
        return alphaAttackingStrategy;
    }
}
