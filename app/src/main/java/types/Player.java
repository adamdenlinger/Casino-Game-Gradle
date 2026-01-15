package types;

import java.util.ArrayList;

public class Player {

    public ArrayList<String> hand = new ArrayList<String>();
    public ArrayList<String> holder = new ArrayList<String>();
    public ArrayList<String> temp = new ArrayList<String>();

    public void TransferDuplicates() {
        holder.clear();                 // IMPORTANT

        for (String num : hand) {
            if (!holder.contains(num)) {
                holder.add(num);
            }
        }

        temp = new ArrayList<>(hand);   // snapshot original
        hand = new ArrayList<>(holder); // deduped
    }

    public void ResetHand() {
        hand = new ArrayList<>(temp);
    }
}
