package hotciv.variants.Epsilon;

import hotciv.Utility.Utility;
import hotciv.framework.AttackingStrategy;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.GameImpl;

public class EpsilonAttackingStrategy implements AttackingStrategy {
    private final DieRollStrategy dieRollStrategy;

    public EpsilonAttackingStrategy(DieRollStrategy dieRollStrategy) {
        this.dieRollStrategy = dieRollStrategy;
    }

    @Override
    public boolean calculateAttack(Position from, Position to, Game game) {
        int finalAttackingStrength = getTotalAttackingStrength(from, game) * dieRollStrategy.rollDie();
        int finalDefensiveStrength = getTotalDefensiveStrength(to, game) * dieRollStrategy.rollDie();

        return finalDefensiveStrength < finalAttackingStrength;
    }

    private int getTotalAttackingStrength(Position from, Game game){
        Player attackingPlayer = game.getUnitAt(from).getOwner();
        int attackingStrength = game.getUnitAt(from).getAttackingStrength();
        int attackingSupport = Utility.getFriendlySupport(game, from, attackingPlayer);
        int attackingTerrainMultiplier = Utility.getTerrainFactor(game, from);

        return (attackingStrength+attackingSupport)*attackingTerrainMultiplier;
    }

    private int getTotalDefensiveStrength(Position to, Game game){
        Player defendingPlayer = game.getUnitAt(to).getOwner();
        int defendingStrength = game.getUnitAt(to).getDefensiveStrength();
        int defendingSupport = Utility.getFriendlySupport(game, to, defendingPlayer);
        int defendingTerrainMultiplier = Utility.getTerrainFactor(game, to);

        return (defendingStrength+defendingSupport)*defendingTerrainMultiplier;
    }
}
