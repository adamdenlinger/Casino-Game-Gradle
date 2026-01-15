package types;

import java.util.ArrayList;

public class Player {
    public ArrayList<Integer> hand = new ArrayList<Integer>();
    public ArrayList<Integer> holder = new ArrayList<Integer>();
    public ArrayList<Integer> temp = new ArrayList<Integer>();

    public void TransferDuplicates() {
        for (Integer num : hand) {
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