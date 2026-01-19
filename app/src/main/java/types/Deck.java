package types;

import java.util.ArrayList;
import java.util.Arrays;

public class Deck {
    
    // shows the deck and the cards that can be drawn
    ArrayList<String> deck = new ArrayList<>(Arrays.asList(
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", // Hearts
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", // Clubs
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", // Diamonds
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" // Spades
    ));

    // round1, where the house and the player draw their first four cards
    public void DistributeRound1(Player player, Player house) {
        // randint decides what position to pull the cards from
        int randInt = (int) (Math.random() * deck.size());
        // gives the player a card from the deck
        player.hand.add(deck.get(randInt));
        // removes the card drawn by the player out of the deck
        deck.remove(randInt);
        // makes randint a new randome int of the current deck size
        randInt = (int) (Math.random() * deck.size());
        // gives the house a card from the deck
        house.hand.add(deck.get(randInt));
        deck.remove(randInt);
        randInt = (int) (Math.random() * deck.size());
        player.hand.add(deck.get(randInt));
        deck.remove(randInt);
        randInt = (int) (Math.random() * deck.size());
        house.hand.add(deck.get(randInt));
        deck.remove(randInt);
        randInt = (int) (Math.random() * deck.size());
        player.hand.add(deck.get(randInt));
        deck.remove(randInt);
        randInt = (int) (Math.random() * deck.size());
        house.hand.add(deck.get(randInt));
        deck.remove(randInt);
        randInt = (int) (Math.random() * deck.size());
        player.hand.add(deck.get(randInt));
        deck.remove(randInt);
        randInt = (int) (Math.random() * deck.size());
        house.hand.add(deck.get(randInt));
        deck.remove(randInt);
    }

    // when round two begins, four aces are randomly shuffled in
    public void AddAces() {
        for (int i = 0; i < 4; i++) {
            deck.add("A");
        }
    }

    // removes the remaining aces in the deck
    public void RemoveAces() {
        for (int i = 0; i < 4; i++) {
            deck.remove(String.valueOf("A"));
        }
    }

    // distribues a random card within the current deck to the house in round2
    public String DistributeHouse(Player house) {
        int randInt = (int) (Math.random() * deck.size());
        String card = deck.get(randInt);
        house.hand.add(deck.get(randInt));
        deck.remove(randInt);
        return card;
    }

    // distribues a random card within the current deck to the player in round2
    public String DistributePlayer(Player player) {
        int randInt = (int) (Math.random() * deck.size());
        // gets the card string to check for aces later on
        String card = deck.get(randInt);
        player.hand.add(deck.get(randInt));
        deck.remove(randInt);
        return card;
    }

    // new array list for round two
    private final ArrayList<String> base48 = new ArrayList<>(Arrays.asList(
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K",
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K",
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K",
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
    ));

    // resets the deck of the player and house giving the deck back the cards that were drawn
    public void ResetDeck(Player player, Player house) {
        player.hand.clear();
        house.hand.clear();

        deck.clear();
        deck.addAll(base48);
    }
}


