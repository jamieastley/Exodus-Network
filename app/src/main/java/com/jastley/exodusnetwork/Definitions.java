package com.jastley.exodusnetwork;

public class Definitions {

//    Bucket Definitions
    public static final String chestArmor = "14239492";
    public static final String shaders = "18606351";
    public static final String legArmor = "20886954";
    public static final String vault = "138197802";
    public static final String lostItems = "215593132";
    public static final String ships = "284967655";
    public static final String engrams = "375726501";
    public static final String powerWeapons = "953998645";
    public static final String auras = "1269569095";
    public static final String specialOrders = "1367666825";
    public static final String consumables = "1469714392";
    public static final String kineticWeapons = "1498876634";
    public static final String classArmor = "1585787867";
    public static final String vehicles = "2025709351";
    public static final String energyWeapons = "2465295065";
    public static final String upgradePoint = "2689798304";
    public static final String strangeCoin = "2689798305";
    public static final String glimmer = "2689798308";
    public static final String legendaryShards = "2689798309";
    public static final String silver = "2689798310";
    public static final String brightDust = "2689798311";
    public static final String shaders2 = "2973005342";
    public static final String emotes = "3054419239";
    public static final String messages = "3161908920";
    public static final String subclass = "3284755031";
    public static final String mods = "3313201758";
    public static final String helmet = "3448274439";
    public static final String gauntlets = "3551918588";
    public static final String materials = "3865314626";
    public static final String ghost = "4023194814";
    public static final String emblems = "4274335291";
    public static final String clanBanners = "4292445962";


//    Damage type definitions
    public static final String dmgTypeNone = "0";
    public static final String dmgTypeKinetic = "1";
    public static final String dmgTypeArc = "2";
    public static final String dmgTypeThermal = "3";
    public static final String dmgTypeVoid = "4";
    public static final String dmgTypeRaid = "5";

    //Stat definitions
    public final String attack = "1480404414";

    //Sockets
    public static final String weaponPerksSockets = "4241085061";
    public static final String weaponModsSockets = "2685412949";

    //Checklists
    public static final String latentMemories = "2955980198";
    public static final String ghostLore = "2360931290";
    public static final String caydeJournals = "2448912219";
    public static final String sleeperNodes = "365218222";
    public static final String raidLairs = "110198094";
    public static final String forsakenCollection = "3393554306";

    //Faction
    public static final String theNine = "1357903713";

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
            case classArmor:
                value = 8;
                break;
            case ghost:
                value = 9;
                break;
            case subclass:
                value = 10;
                break;
            case vault:
                value = 11;
                break;
            case engrams:
                value = 12;
                break;
            case consumables:
                value = 13;
                break;
            case mods:
                value = 14;
                break;
            case shaders:
                value = 15;
                break;
            case shaders2:
                value = 15;
                break;
            case materials:
                value = 16;
                break;
            case emblems:
                value = 17;
                break;
            case ships:
                value = 18;
                break;
            case vehicles:
                value = 19;
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
                category = "Class Armor";
                break;
            case 9:
                category = "Ghost";
                break;
            case 10:
                category = "Sub-Class";
                break;
            case 11:
                category = "Vault";
                break;
            case 12:
                category = "Engrams";
                break;
            case 13:
                category = "Consumables";
                break;
            case 14:
                category = "Modifications";
                break;
            case 15:
                category = "Shaders";
                break;
            case 16:
                category = "Materials";
                break;
            case 17:
                category = "Emblems";
                break;
            case 18:
                category = "Ships";
                break;
            case 19:
                category = "Vehicles";
                break;
        }
        return category;
    }

}
