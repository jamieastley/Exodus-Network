package com.jastley.warmindfordestiny2.Utils;

import static com.jastley.warmindfordestiny2.Definitions.*;


public class InventoryBucketDefinition {

    public static int sortBuckets(String bucketHash){

        int value = 50;

        switch(bucketHash){

            case kineticWeapons:
                value = 1;
                break;
            case energyWeapons:
                value = 2;
                break;
            case powerWeapons:
                value = 3;
                break;
            case helmet:
                value = 4;
                break;
            case gauntlets:
                value = 5;
                break;
            case chestArmor:
                value = 6;
                break;
            case legArmor:
                value = 7;
                break;
            case ghost:
                value = 8;
                break;
            case vault:
                value = 9;
                break;
            case engrams:
                value = 10;
                break;
            case consumables:
                value = 11;
                break;
            case mods:
                value = 12;
                break;
            case shaders:
                value = 13;
                break;
            case shaders2:
                value = 14;
                break;
            case emblems:
                value = 15;
                break;
            case ships:
                value = 16;
                break;
            case vehicles:
                value = 17;
                break;

        }
        return value;
    }

    public static String getBucketName(int id) {

        String category = "Other";

        switch(id) {
            case 1:
                category = "Kinetic Weapons";
                break;
            case 2:
                category = "Energy Weapons";
                break;
            case 3:
                category = "Power Weapons";
                break;
            case 4:
                category = "Helmet";
                break;
            case 5:
                category = "Gauntlets";
                break;
            case 6:
                category = "Chest Armor";
                break;
            case 7:
                category = "Leg Armor";
                break;
            case 8:
                category = "Ghost";
                break;
            case 9:
                category = "Vault";
                break;
            case 10:
                category = "Engrams";
                break;
            case 11:
                category = "Consumables";
                break;
            case 12:
                category = "Modifications";
                break;
            case 13:
                category = "Shaders";
                break;
            case 14:
                category = "Shaders";
                break;
            case 15:
                category = "Emblems";
                break;
            case 16:
                category = "Ships";
                break;
            case 17:
                category = "Vehicles";
                break;
        }
        return category;
    }
}
