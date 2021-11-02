package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaValidMoveStrategy;
import hotciv.variants.alphaStrategies.AlphaUnitActionStrategy;
import hotciv.variants.alphaStrategies.AlphaWorldLayoutStrategy;
import hotciv.variants.betaStreategies.BetaCivWinningStrategy;
import hotciv.variants.epsilonStrategies.EpsilonCivWinningStrategy;
import hotciv.variants.zetaStrategies.ZetaWinningStrategy;

public class ZetaCivFactory implements GameFactory {
    @Override
    public AgeStrategy createAgeStrategy() {
        return new LinearAgeStrategy();
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        return new ZetaWinningStrategy(new BetaCivWinningStrategy(), new EpsilonCivWinningStrategy());
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
