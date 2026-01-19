package types;

import java.util.ArrayList;

//containes the players deck
public class Player {

    // spare array lists for the players deck
    public ArrayList<String> hand = new ArrayList<String>();
    public ArrayList<String> holder = new ArrayList<String>();
    public ArrayList<String> temp = new ArrayList<String>();

    // transfers the duplicates back to the deck after conting them
    public void TransferDuplicates() {
        holder.clear();

        for (String num : hand) {
            if (!holder.contains(num)) {
                holder.add(num);
            }
        }

        temp = new ArrayList<>(hand);   
        hand = new ArrayList<>(holder); 
    }

    // resets the players hand
    public void ResetHand() {
        hand = new ArrayList<>(temp);
    }

    public void RemoveDuplicates() {
        holder.clear();                 // IMPORTANT

        for (String num : hand) {
            if (!holder.contains(num)) {
                holder.add(num);
            }
        }

        hand = new ArrayList<>(holder); // deduped
    }

}
