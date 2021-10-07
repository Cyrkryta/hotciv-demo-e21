package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.SlowingAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaUnitActionStrategy;
import hotciv.variants.alphaStrategies.AlphaWorldLayoutStrategy;
import hotciv.variants.betaStreategies.BetaCivWinningStrategy;

public class BetaCivFactory implements GameFactory {
    @Override
    public AgeStrategy createAgeStrategy() {
        AgeStrategy betaAgingStrategy = new SlowingAgeStrategy();
        return betaAgingStrategy;
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        WinningStrategy betaWinningStrategy = new BetaCivWinningStrategy();
        return betaWinningStrategy;
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        UnitActionStrategy betaUnitActionStrategy = new AlphaUnitActionStrategy();
        return betaUnitActionStrategy;
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        WorldLayoutStrategy betaWorldLayoutStrategy = new AlphaWorldLayoutStrategy();
        return betaWorldLayoutStrategy;
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        AttackingStrategy betaAttackingStrategy = new AlphaAttackingStrategy();
        return betaAttackingStrategy;
    }
}
