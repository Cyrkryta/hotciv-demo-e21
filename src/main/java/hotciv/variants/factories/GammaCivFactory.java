package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaCivWinningStrategy;
import hotciv.variants.alphaStrategies.AlphaWorldLayoutStrategy;
import hotciv.variants.gammaStrategies.GammaUnitActionStrategy;

public class GammaCivFactory implements GameFactory {
    @Override
    public AgeStrategy createAgeStrategy() {
        AgeStrategy gammaAgingStrategy = new LinearAgeStrategy();
        return gammaAgingStrategy;
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        WinningStrategy gammaWinningStrategy = new AlphaCivWinningStrategy();
        return gammaWinningStrategy;
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        UnitActionStrategy gammaUnitActionStrategy = new GammaUnitActionStrategy();
        return gammaUnitActionStrategy;
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        WorldLayoutStrategy gammaWorldLayoutStrategy = new AlphaWorldLayoutStrategy();
        return gammaWorldLayoutStrategy;
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        AttackingStrategy gammaAttackingStrategy = new AlphaAttackingStrategy();
        return gammaAttackingStrategy;
    }
}
