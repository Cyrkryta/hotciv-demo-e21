package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaCivWinningStrategy;
import hotciv.variants.alphaStrategies.AlphaUnitActionStrategy;
import hotciv.variants.alphaStrategies.AlphaValidMoveStrategy;
import hotciv.variants.fractalMap.WorldLayoutMapAdapter;

public class FractalMapFactory implements GameFactory {
    @Override
    public AgeStrategy createAgeStrategy() {return new LinearAgeStrategy();}

    @Override
    public WinningStrategy createWinningStrategy() {return new AlphaCivWinningStrategy();}

    @Override
    public UnitActionStrategy createUnitActionStrategy() {return new AlphaUnitActionStrategy();}

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {return new WorldLayoutMapAdapter();}

    @Override
    public AttackingStrategy createAttackingStrategy() {return new AlphaAttackingStrategy();}

    @Override
    public ValidMoveStrategy createValidMoveStrategy() {return new AlphaValidMoveStrategy();}
}
