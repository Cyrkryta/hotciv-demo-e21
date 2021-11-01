package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaCivWinningStrategy;
import hotciv.variants.alphaStrategies.AlphaValidMoveStrategy;
import hotciv.variants.decorators.ThetaSandwormDecorator;
import hotciv.variants.gammaStrategies.GammaUnitActionStrategy;
import hotciv.variants.thetaStrategies.TestThetaWorldLayoutStrategy;
import hotciv.variants.thetaStrategies.ThetaWorldLayoutStrategy;

public class ThetaCivTestFactory implements GameFactory {
    @Override
    public AgeStrategy createAgeStrategy() {
        return new LinearAgeStrategy();
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        return new AlphaCivWinningStrategy();
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        return new GammaUnitActionStrategy();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new TestThetaWorldLayoutStrategy();
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        return new AlphaAttackingStrategy();
    }

    @Override
    public ValidMoveStrategy createValidMoveStrategy() {
        return new ThetaSandwormDecorator(new AlphaValidMoveStrategy());
    }
}