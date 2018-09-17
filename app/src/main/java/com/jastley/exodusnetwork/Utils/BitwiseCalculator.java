package com.jastley.exodusnetwork.Utils;

import java.util.ArrayList;
import java.util.List;

public class BitwiseCalculator {

    public static List<Integer> getCollectibleStates(int state) {

        List<Integer> stateList = new ArrayList<>();

        //Not acquired = 1
        stateList.add(state & 1);

        //Obscured = 2
        stateList.add(state & 2);

        //Invisible = 4
        stateList.add(state & 4);

        //CannotAffordMaterialRequirements = 8
        stateList.add(state & 8);

        //InventorySpaceUnavailable = 16
        stateList.add(state & 16);

        //UniquenessViolation = 32
        stateList.add(state & 32);

        //PurchaseDisabled = 64
        stateList.add(state & 64);


        return stateList;
    }
}
