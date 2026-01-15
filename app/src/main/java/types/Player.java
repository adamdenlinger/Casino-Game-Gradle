package types;

import java.util.ArrayList;

public class Player {
    public ArrayList<String> hand = new ArrayList<String>();
    public ArrayList<String> holder = new ArrayList<String>();
    public ArrayList<String> temp = new ArrayList<String>();

    public void TransferDuplicates() {
        for (String num : hand) {
            if (!holder.contains(num)) {
                holder.add(num);
            }
        }

        temp = new ArrayList<>(hand);
        hand = new ArrayList<>(holder);
    }

    public void ResetHand() {
        hand = new ArrayList<>(temp);
    }
}