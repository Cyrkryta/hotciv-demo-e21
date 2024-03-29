package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaCivWinningStrategy;
import hotciv.variants.alphaStrategies.AlphaValidMoveStrategy;
import hotciv.variants.decorators.ThetaSandwormActionDecorator;
import hotciv.variants.decorators.ThetaSandwormDecorator;
import hotciv.variants.gammaStrategies.GammaUnitActionStrategy;
import hotciv.variants.thetaStrategies.ThetaWorldLayoutStrategy;

public class ThetaCivFactory implements GameFactory {
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
        return new ThetaSandwormActionDecorator(new GammaUnitActionStrategy());
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new ThetaWorldLayoutStrategy();
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