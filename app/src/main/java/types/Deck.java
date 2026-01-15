package types;

import java.util.ArrayList;
import java.util.Arrays;

public class Deck {
    ArrayList<String> deck = new ArrayList<>(Arrays.asList(
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", // Hearts
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", // Clubs
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", // Diamonds
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" // Spades
    ));

    public void DistributeRound1(Player player, Player house) {
        int randInt = (int) (Math.random() * deck.size());
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
        randInt = (int) (Math.random() * deck.size());
        player.hand.add(deck.get(randInt));
        deck.remove(randInt);
        randInt = (int) (Math.random() * deck.size());
        house.hand.add(deck.get(randInt));
        deck.remove(randInt);
    }

    public void AddAces() {
        for (int i = 0; i < 4; i++) {
            deck.add("A");
        }
    }

    public void RemoveAces() {
        for (int i = 0; i < 4; i++) {
            deck.remove(String.valueOf("A"));
        }
    }

    public String DistributeHouse(Player house) {
        int randInt = (int) (Math.random() * deck.size());
        String card = deck.get(randInt);
        house.hand.add(deck.get(randInt));
        deck.remove(randInt);
        return card;
    }

    public String DistributePlayer(Player player) {
        int randInt = (int) (Math.random() * deck.size());
        String card = deck.get(randInt);
        player.hand.add(deck.get(randInt));
        deck.remove(randInt);
        return card;
    }

    /*
     * void ResetDeck(Player player, Player house) {
     * for (int i = 0; i < player.hand.size(); i++) {
     * deck.add(player.hand.get(0));
     * player.hand.remove(0);
     * }
     * for (int i = 0; i < house.hand.size(); i++) {
     * deck.add(house.hand.get(0));
     * house.hand.remove(0);
     * }
     * }
     */

    public void ResetDeck(Player player, Player house) {
        deck.addAll(player.hand);
        player.hand.clear();

        deck.addAll(house.hand);
        house.hand.clear();
    }

}