package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.agingStrategies.SlowingAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaUnitActionStrategy;
import hotciv.variants.alphaStrategies.AlphaWorldLayoutStrategy;
import hotciv.variants.betaStreategies.BetaCivWinningStrategy;
import hotciv.variants.deltaStrategies.DeltaWorldLayoutStrategy;
import hotciv.variants.epsilonStrategies.EpsilonAttackingStrategy;
import hotciv.variants.epsilonStrategies.EpsilonCivWinningStrategy;
import hotciv.variants.epsilonStrategies.NormalRollStrategy;
import hotciv.variants.gammaStrategies.GammaUnitActionStrategy;
import hotciv.variants.zetaStrategies.ZetaWinningStrategy;

public class SemiCivFactory implements GameFactory {
    @Override
    public AgeStrategy createAgeStrategy() {
        return new SlowingAgeStrategy();
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        return new ZetaWinningStrategy(new BetaCivWinningStrategy(), new EpsilonCivWinningStrategy());
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        return new GammaUnitActionStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new DeltaWorldLayoutStrategy();
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        return new EpsilonAttackingStrategy(new NormalRollStrategy());
    }
}