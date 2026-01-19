package types;
    
// initializes player, house, and the deck as instances of the class
public class GameSession {
    public final Player player;
    public final Player house;
    public final Deck deck;

    // make new players
    public GameSession() {
        this.player = new Player();
        this.house = new Player();
        this.deck = new Deck();
    }


    // the start of the game where each player gets four cards
    public int Round1() {

        // checks if the player has a duplicate returning the win
        if (PlayerHasDuplicate() == true && HouseHasDuplicate() == false) {
            return 0;
        } 
        
        // checks if the house has a duplicate returning a house win
        else if (PlayerHasDuplicate() == false && HouseHasDuplicate() == true) {
            return 1;
        } 
        
        // returning the push to go to round2
        else {
            return 2;
        }
    }

    // checks if the player has a duplicate returning the win
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

    // checks if the house has a duplicate returning a house win
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

    // checks house for aces and if it has an ace house wins
    public boolean HouseDrawCard() {
        boolean isWinner = false;
        if (deck.DistributeHouse(house) == "A") {
            isWinner = true;
        }
        return isWinner;
    }

    // checks player for aces and if it has an ace house wins
    public boolean PlayerDrawCard() {
        boolean isWinner = false;
        if (deck.DistributePlayer(player) == "A") {
            isWinner = true;
        }
        return isWinner;
    }
    
    
}