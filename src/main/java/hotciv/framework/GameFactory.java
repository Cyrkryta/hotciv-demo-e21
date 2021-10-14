package hotciv.framework;

public interface GameFactory {
   // AgeStrategy.
   public AgeStrategy createAgeStrategy();
   // Winning Strategy.
   public WinningStrategy createWinningStrategy();
   // UnitAction Strategy.
   public UnitActionStrategy createUnitActionStrategy();
   // WorldLayoutStrategy.
   public WorldLayoutStrategy createWorldLayoutStrategy();
   // AttackingStrategy
   public AttackingStrategy createAttackingStrategy();
   // MoveUnitStrategy
   ValidMoveStrategy createValidMoveStrategy();
}
