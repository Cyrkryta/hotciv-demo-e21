package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.SlowingAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaValidMoveStrategy;
import hotciv.variants.alphaStrategies.AlphaUnitActionStrategy;
import hotciv.variants.alphaStrategies.AlphaWorldLayoutStrategy;
import hotciv.variants.betaStreategies.BetaCivWinningStrategy;

public class BetaCivFactory implements GameFactory {
    @Override
    public AgeStrategy createAgeStrategy() {
        return new SlowingAgeStrategy();
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        return new BetaCivWinningStrategy();
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        return new AlphaUnitActionStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new AlphaWorldLayoutStrategy();
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        return new AlphaAttackingStrategy();
    }

    @Override
    public ValidMoveStrategy createValidMoveStrategy() {
        return new AlphaValidMoveStrategy();
    }
}
