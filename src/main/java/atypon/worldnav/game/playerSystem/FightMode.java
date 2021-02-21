package atypon.worldnav.game.playerSystem;

import atypon.worldnav.game.items.Item;
import atypon.worldnav.mvc.controller.GameController;
import atypon.worldnav.mvc.controller.GameManager;

public class FightMode {

    String number;

    Player player1;
    Player player2;

    String attackMove1;
    String attackMove2;

    public int id = 0;

    FightMode(Player player1, Player player2, String number) {
        id++;
        this.player1 = player1;
        this.player2 = player2;
        this.number = number;
    }

    void startFight() {

        GameController controller = GameController.getInstance();
        GameManager manager = GameManager.getInstance();
        PlayersManager managerPlayers = manager.getGame(number).getPlayersManager();

        controller.fightModeBroadCast(number, player1, player2, "FIGHTMODE ANNOUNCEMENT : " + player1.getName() + " vs " + player2.getName() + ", ID: " + id);

        if (player1.gold.intValue() > player2.gold.intValue()) {
            loot(player1, player2);
            managerPlayers.goldDistribution(player2);
            managerPlayers.terminate(player2);
            controller.fightModeBroadCast(number, player1, player2, "'" + player1.getName() + "' won the fight against " + player2.getName());
        } else if (player1.gold.intValue() < player2.gold.intValue()) {

            loot(player2, player1);
            managerPlayers.goldDistribution(player1);
            managerPlayers.terminate(player1);
            controller.fightModeBroadCast(number, player1, player2, "'" + player2.getName() + "' won the fight against " + player1.getName());
        } else {
            controller.fightModeBroadCast(number, player1, player2, "ROCK PAPER SCISSORS ON");
        }

    }

    private void loot(Player playerWinner, Player playerLosser) {
        for (Item item : playerLosser.inventory.dropItems()) {
            playerWinner.inventory.addItem(item);
        }
    }

    public void proceed(String name, String attackMove) {
        if (name.equals(player1.getName())) {
            this.attackMove1 = attackMove;
        } else {
            this.attackMove2 = attackMove;
        }
        if (attackMove1 != null && attackMove2 != null) {
            rps(attackMove1, attackMove2);
            attackMove1 = null;
            attackMove2 = null;
        }
    }

    private void rps(String attackMove1, String attackMove2) {
        GameController controller = GameController.getInstance();
        GameManager manager = GameManager.getInstance();
        PlayersManager managerPlayers = manager.getGame(number).getPlayersManager();

        if (attackMove1.equals("rock") && attackMove2.equals("rock") ||
                attackMove1.equals("paper") && attackMove2.equals("paper") ||
                attackMove1.equals("scissors") && attackMove2.equals("scissors")) {
            controller.fightModeBroadCast(number, player1, player2, "FIGHTMODE " + player1.getName() + ": " + attackMove1 + " vs " + player2.getName() + ": " + attackMove2 + "" + ". OUTCOME: DRAW " + id);
        } else if ((attackMove1.equals("rock") && attackMove2.equals("paper")) ||
                (attackMove1.equals("paper") && attackMove2.equals("scissors")) ||
                (attackMove1.equals("scissors") && attackMove2.equals("rock"))) {
            loot(player2, player1);
            managerPlayers.goldDistribution(player1);
            managerPlayers.terminate(player1);
            controller.fightModeBroadCast(number, player1, player2, "FIGHTMODE " + player1.getName() + ": " + attackMove1 + " vs " + player2.getName() + ": " + attackMove2 + "" + ". OUTCOME: " + player2.getName() + " WINS");
        } else if ((attackMove1.equals("rock") && attackMove2.equals("scissors")) ||
                (attackMove1.equals("paper") && attackMove2.equals("rock")) ||
                (attackMove1.equals("scissors") && attackMove2.equals("paper"))) {
            loot(player1, player2);
            managerPlayers.goldDistribution(player2);
            managerPlayers.terminate(player2);
            controller.fightModeBroadCast(number, player1, player2, "FIGHTMODE " + player1.getName() + ": " + attackMove1 + " vs " + player2.getName() + ": " + attackMove2 + "" + ". OUTCOME: " + player1.getName() + " WINS");
        }
    }
}
