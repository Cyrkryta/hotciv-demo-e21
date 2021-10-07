package hotciv.variants.factories;

import hotciv.framework.*;
import hotciv.variants.agingStrategies.LinearAgeStrategy;
import hotciv.variants.alphaStrategies.AlphaAttackingStrategy;
import hotciv.variants.alphaStrategies.AlphaCivWinningStrategy;
import hotciv.variants.alphaStrategies.AlphaUnitActionStrategy;
import hotciv.variants.deltaStrategies.DeltaWorldLayoutStrategy;

public class DeltaCivFactory implements GameFactory {
    @Override
    public AgeStrategy createAgeStrategy() {
        AgeStrategy deltaAgingStrategy = new LinearAgeStrategy();
        return deltaAgingStrategy;
    }

    @Override
    public WinningStrategy createWinningStrategy() {
        WinningStrategy deltaWinningStrategy = new AlphaCivWinningStrategy();
        return deltaWinningStrategy;
    }

    @Override
    public UnitActionStrategy createUnitActionStrategy() {
        UnitActionStrategy deltaUnitActionStrategy = new AlphaUnitActionStrategy();
        return deltaUnitActionStrategy;
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        WorldLayoutStrategy deltaWorldLayoutStrategy = new DeltaWorldLayoutStrategy();
        return deltaWorldLayoutStrategy;
    }

    @Override
    public AttackingStrategy createAttackingStrategy() {
        AttackingStrategy deltaAttackingStrategy = new AlphaAttackingStrategy();
        return deltaAttackingStrategy;
    }
}
