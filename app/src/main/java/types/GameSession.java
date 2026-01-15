package types;

public class GameSession {
    public final Player player;
    public final Player house;
    public final Deck deck;

    public GameSession() {
        this.player = new Player();
        this.house = new Player();
        this.deck = new Deck();
    }



    public int Round1() {
        if (PlayerHasDuplicate() == true && HouseHasDuplicate() == false) {
            return 0;
        } else if (PlayerHasDuplicate() == false && HouseHasDuplicate() == true) {
            return 1;
        } else {
            return 2;
        }
    }

    public boolean PlayerHasDuplicate() {
        boolean hasDuplicate = false;

        for (int i = 0; i < player.hand.size(); i++) {
            for (int j = i + 1; j < player.hand.size(); j++) {
                if (player.hand.get(i).equals(player.hand.get(j))) {
                    hasDuplicate = true;
                    break;
                }
            }
            if (hasDuplicate)
                break;
        }

        return hasDuplicate;
    }

    public boolean HouseHasDuplicate() {
        boolean hasDuplicate = false;

        for (int i = 0; i < house.hand.size(); i++) {
            for (int j = i + 1; j < house.hand.size(); j++) {
                if (house.hand.get(i).equals(house.hand.get(j))) {
                    hasDuplicate = true;
                    break;
                }
            }
            if (hasDuplicate)
                break;
        }

        return hasDuplicate;
    }

    public boolean HouseDrawCard() {
        boolean isWinner = false;
        if (deck.DistributeHouse(house) == 1) {
            isWinner = true;
        }
        return isWinner;
    }

    public boolean PlayerDrawCard() {
        boolean isWinner = false;
        if (deck.DistributePlayer(player) == 1) {
            isWinner = true;
        }
        return isWinner;
    }
    
    
}