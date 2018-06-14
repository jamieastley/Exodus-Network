package com.jastley.warmindfordestiny2.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jamie1192 on 20/4/18.
 */

public class Response_GetHistoricalStatsAccount {


    @Expose
    @SerializedName("MessageData")
    private MessageData MessageData;
    @Expose
    @SerializedName("Message")
    private String Message;
    @Expose
    @SerializedName("ErrorStatus")
    private String ErrorStatus;
    @Expose
    @SerializedName("ThrottleSeconds")
    private int ThrottleSeconds;
    @Expose
    @SerializedName("ErrorCode")
    private String ErrorCode;
    @Expose
    @SerializedName("Response")
    private Response Response;

    public MessageData getMessageData() {
        return MessageData;
    }

    public String getMessage() {
        return Message;
    }

    public String getErrorStatus() {
        return ErrorStatus;
    }

    public int getThrottleSeconds() {
        return ThrottleSeconds;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public Response getResponse() {
        return Response;
    }

    public static class MessageData {
    }

    public static class Response {
        @Expose
        @SerializedName("mergedAllCharacters")
        private MergedAllCharacters mergedAllCharacters;
        @Expose
        @SerializedName("mergedDeletedCharacters")
        private MergedDeletedCharacters mergedDeletedCharacters;

        public MergedAllCharacters getMergedAllCharacters() {
            return mergedAllCharacters;
        }

        public MergedDeletedCharacters getMergedDeletedCharacters() {
            return mergedDeletedCharacters;
        }
    }

    public static class MergedAllCharacters {
        @Expose
        @SerializedName("merged")
        private Merged merged;
        @Expose
        @SerializedName("results")
        private Results results;

        public Merged getMerged() {
            return merged;
        }

        public Results getResults() {
            return results;
        }
    }

    public static class Merged {
        @Expose
        @SerializedName("allTime")
        private AccountAllTime accountAllTime;

        public AccountAllTime getAccountAllTime() {
            return accountAllTime;
        }
    }

    public static class AllTime {
        @Expose
        @SerializedName("combatRating")
        private CombatRating combatRating;
        @Expose
        @SerializedName("winLossRatio")
        private WinLossRatio winLossRatio;
        @Expose
        @SerializedName("averageScorePerLife")
        private AverageScorePerLife averageScorePerLife;
        @Expose
        @SerializedName("averageScorePerKill")
        private AverageScorePerKill averageScorePerKill;
        @Expose
        @SerializedName("activitiesWon")
        private ActivitiesWon activitiesWon;
        @Expose
        @SerializedName("highestLightLevel")
        private HighestLightLevel highestLightLevel;
        @Expose
        @SerializedName("highestCharacterLevel")
        private HighestCharacterLevel highestCharacterLevel;
        @Expose
        @SerializedName("longestKillDistance")
        private LongestKillDistance longestKillDistance;
        @Expose
        @SerializedName("fastestCompletionMs")
        private FastestCompletionMs fastestCompletionMs;
        @Expose
        @SerializedName("totalActivityDurationSeconds")
        private TotalActivityDurationSeconds totalActivityDurationSeconds;
        @Expose
        @SerializedName("teamScore")
        private TeamScore teamScore;
        @Expose
        @SerializedName("remainingTimeAfterQuitSeconds")
        private RemainingTimeAfterQuitSeconds remainingTimeAfterQuitSeconds;
        @Expose
        @SerializedName("publicEventsCompleted")
        private PublicEventsCompleted publicEventsCompleted;
        @Expose
        @SerializedName("orbsGathered")
        private OrbsGathered orbsGathered;
        @Expose
        @SerializedName("orbsDropped")
        private OrbsDropped orbsDropped;
        @Expose
        @SerializedName("mostPrecisionKills")
        private MostPrecisionKills mostPrecisionKills;
        @Expose
        @SerializedName("longestSingleLife")
        private LongestSingleLife longestSingleLife;
        @Expose
        @SerializedName("longestKillSpree")
        private LongestKillSpree longestKillSpree;
        @Expose
        @SerializedName("allParticipantsTimePlayed")
        private AllParticipantsTimePlayed allParticipantsTimePlayed;
        @Expose
        @SerializedName("allParticipantsScore")
        private AllParticipantsScore allParticipantsScore;
        @Expose
        @SerializedName("allParticipantsCount")
        private AllParticipantsCount allParticipantsCount;
        @Expose
        @SerializedName("weaponBestType")
        private WeaponBestType weaponBestType;
        @Expose
        @SerializedName("weaponKillsMelee")
        private WeaponKillsMelee weaponKillsMelee;
        @Expose
        @SerializedName("weaponKillsSuper")
        private WeaponKillsSuper weaponKillsSuper;
        @Expose
        @SerializedName("weaponKillsGrenadeLauncher")
        private WeaponKillsGrenadeLauncher weaponKillsGrenadeLauncher;
        @Expose
        @SerializedName("weaponKillsGrenade")
        private WeaponKillsGrenade weaponKillsGrenade;
        @Expose
        @SerializedName("weaponKillsAbility")
        private WeaponKillsAbility weaponKillsAbility;
        @Expose
        @SerializedName("weaponKillsSword")
        private WeaponKillsSword weaponKillsSword;
        @Expose
        @SerializedName("weaponKillsSideArm")
        private WeaponKillsSideArm weaponKillsSideArm;
        @Expose
        @SerializedName("weaponKillsRelic")
        private WeaponKillsRelic weaponKillsRelic;
        @Expose
        @SerializedName("weaponKillsSubmachinegun")
        private WeaponKillsSubmachinegun weaponKillsSubmachinegun;
        @Expose
        @SerializedName("weaponKillsSniper")
        private WeaponKillsSniper weaponKillsSniper;
        @Expose
        @SerializedName("weaponKillsShotgun")
        private WeaponKillsShotgun weaponKillsShotgun;
        @Expose
        @SerializedName("weaponKillsScoutRifle")
        private WeaponKillsScoutRifle weaponKillsScoutRifle;
        @Expose
        @SerializedName("weaponKillsRocketLauncher")
        private WeaponKillsRocketLauncher weaponKillsRocketLauncher;
        @Expose
        @SerializedName("weaponKillsPulseRifle")
        private WeaponKillsPulseRifle weaponKillsPulseRifle;
        @Expose
        @SerializedName("weaponKillsTraceRifle")
        private WeaponKillsTraceRifle weaponKillsTraceRifle;
        @Expose
        @SerializedName("weaponKillsHandCannon")
        private WeaponKillsHandCannon weaponKillsHandCannon;
        @Expose
        @SerializedName("weaponKillsFusionRifle")
        private WeaponKillsFusionRifle weaponKillsFusionRifle;
        @Expose
        @SerializedName("weaponKillsAutoRifle")
        private WeaponKillsAutoRifle weaponKillsAutoRifle;
        @Expose
        @SerializedName("suicides")
        private Suicides suicides;
        @Expose
        @SerializedName("adventuresCompleted")
        private AdventuresCompleted adventuresCompleted;
        @Expose
        @SerializedName("heroicPublicEventsCompleted")
        private HeroicPublicEventsCompleted heroicPublicEventsCompleted;
        @Expose
        @SerializedName("score")
        private Score score;
        @Expose
        @SerializedName("resurrectionsReceived")
        private ResurrectionsReceived resurrectionsReceived;
        @Expose
        @SerializedName("resurrectionsPerformed")
        private ResurrectionsPerformed resurrectionsPerformed;
        @Expose
        @SerializedName("precisionKills")
        private PrecisionKills precisionKills;
        @Expose
        @SerializedName("objectivesCompleted")
        private ObjectivesCompleted objectivesCompleted;
        @Expose
        @SerializedName("killsDeathsAssists")
        private KillsDeathsAssists killsDeathsAssists;
        @Expose
        @SerializedName("killsDeathsRatio")
        private KillsDeathsRatio killsDeathsRatio;
        @Expose
        @SerializedName("efficiency")
        private Efficiency efficiency;
        @Expose
        @SerializedName("opponentsDefeated")
        private OpponentsDefeated opponentsDefeated;
        @Expose
        @SerializedName("bestSingleGameScore")
        private BestSingleGameScore bestSingleGameScore;
        @Expose
        @SerializedName("bestSingleGameKills")
        private BestSingleGameKills bestSingleGameKills;
        @Expose
        @SerializedName("averageLifespan")
        private AverageLifespan averageLifespan;
        @Expose
        @SerializedName("deaths")
        private Deaths deaths;
        @Expose
        @SerializedName("secondsPlayed")
        private SecondsPlayed secondsPlayed;
        @Expose
        @SerializedName("averageKillDistance")
        private AverageKillDistance averageKillDistance;
        @Expose
        @SerializedName("kills")
        private Kills kills;
        @Expose
        @SerializedName("totalKillDistance")
        private TotalKillDistance totalKillDistance;
        @Expose
        @SerializedName("averageDeathDistance")
        private AverageDeathDistance averageDeathDistance;
        @Expose
        @SerializedName("totalDeathDistance")
        private TotalDeathDistance totalDeathDistance;
        @Expose
        @SerializedName("assists")
        private Assists assists;
        @Expose
        @SerializedName("activitiesEntered")
        private ActivitiesEntered activitiesEntered;
        @Expose
        @SerializedName("activitiesCleared")
        private ActivitiesCleared activitiesCleared;

        public CombatRating getCombatRating() {
            return combatRating;
        }

        public WinLossRatio getWinLossRatio() {
            return winLossRatio;
        }

        public AverageScorePerLife getAverageScorePerLife() {
            return averageScorePerLife;
        }

        public AverageScorePerKill getAverageScorePerKill() {
            return averageScorePerKill;
        }

        public ActivitiesWon getActivitiesWon() {
            return activitiesWon;
        }

        public HighestLightLevel getHighestLightLevel() {
            return highestLightLevel;
        }

        public HighestCharacterLevel getHighestCharacterLevel() {
            return highestCharacterLevel;
        }

        public LongestKillDistance getLongestKillDistance() {
            return longestKillDistance;
        }

        public FastestCompletionMs getFastestCompletionMs() {
            return fastestCompletionMs;
        }

        public TotalActivityDurationSeconds getTotalActivityDurationSeconds() {
            return totalActivityDurationSeconds;
        }

        public TeamScore getTeamScore() {
            return teamScore;
        }

        public RemainingTimeAfterQuitSeconds getRemainingTimeAfterQuitSeconds() {
            return remainingTimeAfterQuitSeconds;
        }

        public PublicEventsCompleted getPublicEventsCompleted() {
            return publicEventsCompleted;
        }

        public OrbsGathered getOrbsGathered() {
            return orbsGathered;
        }

        public OrbsDropped getOrbsDropped() {
            return orbsDropped;
        }

        public MostPrecisionKills getMostPrecisionKills() {
            return mostPrecisionKills;
        }

        public LongestSingleLife getLongestSingleLife() {
            return longestSingleLife;
        }

        public LongestKillSpree getLongestKillSpree() {
            return longestKillSpree;
        }

        public AllParticipantsTimePlayed getAllParticipantsTimePlayed() {
            return allParticipantsTimePlayed;
        }

        public AllParticipantsScore getAllParticipantsScore() {
            return allParticipantsScore;
        }

        public AllParticipantsCount getAllParticipantsCount() {
            return allParticipantsCount;
        }

        public WeaponBestType getWeaponBestType() {
            return weaponBestType;
        }

        public WeaponKillsMelee getWeaponKillsMelee() {
            return weaponKillsMelee;
        }

        public WeaponKillsSuper getWeaponKillsSuper() {
            return weaponKillsSuper;
        }

        public WeaponKillsGrenadeLauncher getWeaponKillsGrenadeLauncher() {
            return weaponKillsGrenadeLauncher;
        }

        public WeaponKillsGrenade getWeaponKillsGrenade() {
            return weaponKillsGrenade;
        }

        public WeaponKillsAbility getWeaponKillsAbility() {
            return weaponKillsAbility;
        }

        public WeaponKillsSword getWeaponKillsSword() {
            return weaponKillsSword;
        }

        public WeaponKillsSideArm getWeaponKillsSideArm() {
            return weaponKillsSideArm;
        }

        public WeaponKillsRelic getWeaponKillsRelic() {
            return weaponKillsRelic;
        }

        public WeaponKillsSubmachinegun getWeaponKillsSubmachinegun() {
            return weaponKillsSubmachinegun;
        }

        public WeaponKillsSniper getWeaponKillsSniper() {
            return weaponKillsSniper;
        }

        public WeaponKillsShotgun getWeaponKillsShotgun() {
            return weaponKillsShotgun;
        }

        public WeaponKillsScoutRifle getWeaponKillsScoutRifle() {
            return weaponKillsScoutRifle;
        }

        public WeaponKillsRocketLauncher getWeaponKillsRocketLauncher() {
            return weaponKillsRocketLauncher;
        }

        public WeaponKillsPulseRifle getWeaponKillsPulseRifle() {
            return weaponKillsPulseRifle;
        }

        public WeaponKillsTraceRifle getWeaponKillsTraceRifle() {
            return weaponKillsTraceRifle;
        }

        public WeaponKillsHandCannon getWeaponKillsHandCannon() {
            return weaponKillsHandCannon;
        }

        public WeaponKillsFusionRifle getWeaponKillsFusionRifle() {
            return weaponKillsFusionRifle;
        }

        public WeaponKillsAutoRifle getWeaponKillsAutoRifle() {
            return weaponKillsAutoRifle;
        }

        public Suicides getSuicides() {
            return suicides;
        }

        public AdventuresCompleted getAdventuresCompleted() {
            return adventuresCompleted;
        }

        public HeroicPublicEventsCompleted getHeroicPublicEventsCompleted() {
            return heroicPublicEventsCompleted;
        }

        public Score getScore() {
            return score;
        }

        public ResurrectionsReceived getResurrectionsReceived() {
            return resurrectionsReceived;
        }

        public ResurrectionsPerformed getResurrectionsPerformed() {
            return resurrectionsPerformed;
        }

        public PrecisionKills getPrecisionKills() {
            return precisionKills;
        }

        public ObjectivesCompleted getObjectivesCompleted() {
            return objectivesCompleted;
        }

        public KillsDeathsAssists getKillsDeathsAssists() {
            return killsDeathsAssists;
        }

        public KillsDeathsRatio getKillsDeathsRatio() {
            return killsDeathsRatio;
        }

        public Efficiency getEfficiency() {
            return efficiency;
        }

        public OpponentsDefeated getOpponentsDefeated() {
            return opponentsDefeated;
        }

        public BestSingleGameScore getBestSingleGameScore() {
            return bestSingleGameScore;
        }

        public BestSingleGameKills getBestSingleGameKills() {
            return bestSingleGameKills;
        }

        public AverageLifespan getAverageLifespan() {
            return averageLifespan;
        }

        public Deaths getDeaths() {
            return deaths;
        }

        public SecondsPlayed getSecondsPlayed() {
            return secondsPlayed;
        }

        public AverageKillDistance getAverageKillDistance() {
            return averageKillDistance;
        }

        public Kills getKills() {
            return kills;
        }

        public TotalKillDistance getTotalKillDistance() {
            return totalKillDistance;
        }

        public AverageDeathDistance getAverageDeathDistance() {
            return averageDeathDistance;
        }

        public TotalDeathDistance getTotalDeathDistance() {
            return totalDeathDistance;
        }

        public Assists getAssists() {
            return assists;
        }

        public ActivitiesEntered getActivitiesEntered() {
            return activitiesEntered;
        }

        public ActivitiesCleared getActivitiesCleared() {
            return activitiesCleared;
        }
    }

    public static class CombatRating {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WinLossRatio {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class AverageScorePerLife {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class AverageScorePerKill {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class ActivitiesWon {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class HighestLightLevel {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class HighestCharacterLevel {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class LongestKillDistance {
        @Expose
        @SerializedName("activityId")
        private String activityId;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public String getActivityId() {
            return activityId;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class FastestCompletionMs {
        @Expose
        @SerializedName("activityId")
        private String activityId;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public String getActivityId() {
            return activityId;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class TotalActivityDurationSeconds {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class Pga {
        @Expose
        @SerializedName("displayValue")
        private String displayValue;
        @Expose
        @SerializedName("value")
        private double value;

        public String getDisplayValue() {
            return displayValue;
        }

        public double getValue() {
            return value;
        }
    }

    public static class TeamScore {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class RemainingTimeAfterQuitSeconds {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class PublicEventsCompleted {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class OrbsGathered {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class OrbsDropped {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class MostPrecisionKills {
        @Expose
        @SerializedName("activityId")
        private String activityId;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public String getActivityId() {
            return activityId;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class LongestSingleLife {
        @Expose
        @SerializedName("activityId")
        private String activityId;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public String getActivityId() {
            return activityId;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class LongestKillSpree {
        @Expose
        @SerializedName("activityId")
        private String activityId;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public String getActivityId() {
            return activityId;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class AllParticipantsTimePlayed {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class AllParticipantsScore {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class AllParticipantsCount {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponBestType {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsMelee {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsSuper {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsGrenadeLauncher {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsGrenade {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsAbility {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsSword {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsSideArm {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsRelic {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsSubmachinegun {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsSniper {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsShotgun {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsScoutRifle {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsRocketLauncher {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsPulseRifle {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsTraceRifle {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsHandCannon {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsFusionRifle {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class WeaponKillsAutoRifle {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class Suicides {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class AdventuresCompleted {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class HeroicPublicEventsCompleted {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class Score {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class ResurrectionsReceived {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class ResurrectionsPerformed {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class PrecisionKills {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class ObjectivesCompleted {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class KillsDeathsAssists {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class KillsDeathsRatio {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class Efficiency {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class OpponentsDefeated {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class BestSingleGameScore {
        @Expose
        @SerializedName("activityId")
        private String activityId;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public String getActivityId() {
            return activityId;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class BestSingleGameKills {
        @Expose
        @SerializedName("activityId")
        private String activityId;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public String getActivityId() {
            return activityId;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class AverageLifespan {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class Deaths {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class SecondsPlayed {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class AverageKillDistance {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class Kills {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class TotalKillDistance {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class AverageDeathDistance {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class TotalDeathDistance {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class Assists {
        @Expose
        @SerializedName("pga")
        private Pga pga;
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Pga getPga() {
            return pga;
        }

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class ActivitiesEntered {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }

    public static class ActivitiesCleared {
        @Expose
        @SerializedName("basic")
        private Basic basic;
        @Expose
        @SerializedName("statId")
        private String statId;

        public Basic getBasic() {
            return basic;
        }

        public String getStatId() {
            return statId;
        }
    }



    public static class Results {
        @Expose
        @SerializedName("allPvP")
        private AllPvP allPvP;

        public AllPvP getAllPvP() {
            return allPvP;
        }

        @Expose
        @SerializedName("allPvE")
        private AllPvE allPvE;

        public AllPvE getAllPvE() {
            return allPvE;
        }
    }

    public static class AllPvP {
        @Expose
        @SerializedName("allTime")
        private AllTime allTime;

        public AllTime getAllTime() {
            return allTime;
        }
    }

    public static class AllPvE {
        @Expose
        @SerializedName("allTime")
        private AllTime allTime;

        public AllTime getAllTime() {
            return allTime;
        }
    }

    public static class AccountAllTime {
        @Expose
        @SerializedName("highestLightLevel")
        private HighestLightLevel highestLightLevel;
        @Expose
        @SerializedName("highestCharacterLevel")
        private HighestCharacterLevel highestCharacterLevel;
        @Expose
        @SerializedName("longestKillDistance")
        private LongestKillDistance longestKillDistance;
        @Expose
        @SerializedName("fastestCompletionMs")
        private FastestCompletionMs fastestCompletionMs;
        @Expose
        @SerializedName("combatRating")
        private CombatRating combatRating;
        @Expose
        @SerializedName("totalActivityDurationSeconds")
        private TotalActivityDurationSeconds totalActivityDurationSeconds;
        @Expose
        @SerializedName("teamScore")
        private TeamScore teamScore;
        @Expose
        @SerializedName("remainingTimeAfterQuitSeconds")
        private RemainingTimeAfterQuitSeconds remainingTimeAfterQuitSeconds;
        @Expose
        @SerializedName("orbsGathered")
        private OrbsGathered orbsGathered;
        @Expose
        @SerializedName("orbsDropped")
        private OrbsDropped orbsDropped;
        @Expose
        @SerializedName("mostPrecisionKills")
        private MostPrecisionKills mostPrecisionKills;
        @Expose
        @SerializedName("longestSingleLife")
        private LongestSingleLife longestSingleLife;
        @Expose
        @SerializedName("longestKillSpree")
        private LongestKillSpree longestKillSpree;
        @Expose
        @SerializedName("allParticipantsTimePlayed")
        private AllParticipantsTimePlayed allParticipantsTimePlayed;
        @Expose
        @SerializedName("allParticipantsScore")
        private AllParticipantsScore allParticipantsScore;
        @Expose
        @SerializedName("allParticipantsCount")
        private AllParticipantsCount allParticipantsCount;
        @Expose
        @SerializedName("winLossRatio")
        private WinLossRatio winLossRatio;
        @Expose
        @SerializedName("weaponBestType")
        private WeaponBestType weaponBestType;
        @Expose
        @SerializedName("weaponKillsMelee")
        private WeaponKillsMelee weaponKillsMelee;
        @Expose
        @SerializedName("weaponKillsSuper")
        private WeaponKillsSuper weaponKillsSuper;
        @Expose
        @SerializedName("weaponKillsGrenadeLauncher")
        private WeaponKillsGrenadeLauncher weaponKillsGrenadeLauncher;
        @Expose
        @SerializedName("weaponKillsGrenade")
        private WeaponKillsGrenade weaponKillsGrenade;
        @Expose
        @SerializedName("weaponKillsAbility")
        private WeaponKillsAbility weaponKillsAbility;
        @Expose
        @SerializedName("weaponKillsSword")
        private WeaponKillsSword weaponKillsSword;
        @Expose
        @SerializedName("weaponKillsSideArm")
        private WeaponKillsSideArm weaponKillsSideArm;
        @Expose
        @SerializedName("weaponKillsRelic")
        private WeaponKillsRelic weaponKillsRelic;
        @Expose
        @SerializedName("weaponKillsSubmachinegun")
        private WeaponKillsSubmachinegun weaponKillsSubmachinegun;
        @Expose
        @SerializedName("weaponKillsSniper")
        private WeaponKillsSniper weaponKillsSniper;
        @Expose
        @SerializedName("weaponKillsShotgun")
        private WeaponKillsShotgun weaponKillsShotgun;
        @Expose
        @SerializedName("weaponKillsScoutRifle")
        private WeaponKillsScoutRifle weaponKillsScoutRifle;
        @Expose
        @SerializedName("weaponKillsRocketLauncher")
        private WeaponKillsRocketLauncher weaponKillsRocketLauncher;
        @Expose
        @SerializedName("weaponKillsPulseRifle")
        private WeaponKillsPulseRifle weaponKillsPulseRifle;
        @Expose
        @SerializedName("weaponKillsTraceRifle")
        private WeaponKillsTraceRifle weaponKillsTraceRifle;
        @Expose
        @SerializedName("weaponKillsHandCannon")
        private WeaponKillsHandCannon weaponKillsHandCannon;
        @Expose
        @SerializedName("weaponKillsFusionRifle")
        private WeaponKillsFusionRifle weaponKillsFusionRifle;
        @Expose
        @SerializedName("weaponKillsAutoRifle")
        private WeaponKillsAutoRifle weaponKillsAutoRifle;
        @Expose
        @SerializedName("suicides")
        private Suicides suicides;
        @Expose
        @SerializedName("resurrectionsReceived")
        private ResurrectionsReceived resurrectionsReceived;
        @Expose
        @SerializedName("resurrectionsPerformed")
        private ResurrectionsPerformed resurrectionsPerformed;
        @Expose
        @SerializedName("precisionKills")
        private PrecisionKills precisionKills;
        @Expose
        @SerializedName("objectivesCompleted")
        private ObjectivesCompleted objectivesCompleted;
        @Expose
        @SerializedName("killsDeathsAssists")
        private KillsDeathsAssists killsDeathsAssists;
        @Expose
        @SerializedName("killsDeathsRatio")
        private KillsDeathsRatio killsDeathsRatio;
        @Expose
        @SerializedName("efficiency")
        private Efficiency efficiency;
        @Expose
        @SerializedName("opponentsDefeated")
        private OpponentsDefeated opponentsDefeated;
        @Expose
        @SerializedName("bestSingleGameScore")
        private BestSingleGameScore bestSingleGameScore;
        @Expose
        @SerializedName("bestSingleGameKills")
        private BestSingleGameKills bestSingleGameKills;
        @Expose
        @SerializedName("averageScorePerLife")
        private AverageScorePerLife averageScorePerLife;
        @Expose
        @SerializedName("averageScorePerKill")
        private AverageScorePerKill averageScorePerKill;
        @Expose
        @SerializedName("score")
        private Score score;
        @Expose
        @SerializedName("averageLifespan")
        private AverageLifespan averageLifespan;
        @Expose
        @SerializedName("deaths")
        private Deaths deaths;
        @Expose
        @SerializedName("secondsPlayed")
        private SecondsPlayed secondsPlayed;
        @Expose
        @SerializedName("averageKillDistance")
        private AverageKillDistance averageKillDistance;
        @Expose
        @SerializedName("kills")
        private Kills kills;
        @Expose
        @SerializedName("totalKillDistance")
        private TotalKillDistance totalKillDistance;
        @Expose
        @SerializedName("averageDeathDistance")
        private AverageDeathDistance averageDeathDistance;
        @Expose
        @SerializedName("totalDeathDistance")
        private TotalDeathDistance totalDeathDistance;
        @Expose
        @SerializedName("assists")
        private Assists assists;
        @Expose
        @SerializedName("activitiesWon")
        private ActivitiesWon activitiesWon;
        @Expose
        @SerializedName("activitiesEntered")
        private ActivitiesEntered activitiesEntered;

        public HighestLightLevel getHighestLightLevel() {
            return highestLightLevel;
        }

        public HighestCharacterLevel getHighestCharacterLevel() {
            return highestCharacterLevel;
        }

        public LongestKillDistance getLongestKillDistance() {
            return longestKillDistance;
        }

        public FastestCompletionMs getFastestCompletionMs() {
            return fastestCompletionMs;
        }

        public CombatRating getCombatRating() {
            return combatRating;
        }

        public TotalActivityDurationSeconds getTotalActivityDurationSeconds() {
            return totalActivityDurationSeconds;
        }

        public TeamScore getTeamScore() {
            return teamScore;
        }

        public RemainingTimeAfterQuitSeconds getRemainingTimeAfterQuitSeconds() {
            return remainingTimeAfterQuitSeconds;
        }

        public OrbsGathered getOrbsGathered() {
            return orbsGathered;
        }

        public OrbsDropped getOrbsDropped() {
            return orbsDropped;
        }

        public MostPrecisionKills getMostPrecisionKills() {
            return mostPrecisionKills;
        }

        public LongestSingleLife getLongestSingleLife() {
            return longestSingleLife;
        }

        public LongestKillSpree getLongestKillSpree() {
            return longestKillSpree;
        }

        public AllParticipantsTimePlayed getAllParticipantsTimePlayed() {
            return allParticipantsTimePlayed;
        }

        public AllParticipantsScore getAllParticipantsScore() {
            return allParticipantsScore;
        }

        public AllParticipantsCount getAllParticipantsCount() {
            return allParticipantsCount;
        }

        public WinLossRatio getWinLossRatio() {
            return winLossRatio;
        }

        public WeaponBestType getWeaponBestType() {
            return weaponBestType;
        }

        public WeaponKillsMelee getWeaponKillsMelee() {
            return weaponKillsMelee;
        }

        public WeaponKillsSuper getWeaponKillsSuper() {
            return weaponKillsSuper;
        }

        public WeaponKillsGrenadeLauncher getWeaponKillsGrenadeLauncher() {
            return weaponKillsGrenadeLauncher;
        }

        public WeaponKillsGrenade getWeaponKillsGrenade() {
            return weaponKillsGrenade;
        }

        public WeaponKillsAbility getWeaponKillsAbility() {
            return weaponKillsAbility;
        }

        public WeaponKillsSword getWeaponKillsSword() {
            return weaponKillsSword;
        }

        public WeaponKillsSideArm getWeaponKillsSideArm() {
            return weaponKillsSideArm;
        }

        public WeaponKillsRelic getWeaponKillsRelic() {
            return weaponKillsRelic;
        }

        public WeaponKillsSubmachinegun getWeaponKillsSubmachinegun() {
            return weaponKillsSubmachinegun;
        }

        public WeaponKillsSniper getWeaponKillsSniper() {
            return weaponKillsSniper;
        }

        public WeaponKillsShotgun getWeaponKillsShotgun() {
            return weaponKillsShotgun;
        }

        public WeaponKillsScoutRifle getWeaponKillsScoutRifle() {
            return weaponKillsScoutRifle;
        }

        public WeaponKillsRocketLauncher getWeaponKillsRocketLauncher() {
            return weaponKillsRocketLauncher;
        }

        public WeaponKillsPulseRifle getWeaponKillsPulseRifle() {
            return weaponKillsPulseRifle;
        }

        public WeaponKillsTraceRifle getWeaponKillsTraceRifle() {
            return weaponKillsTraceRifle;
        }

        public WeaponKillsHandCannon getWeaponKillsHandCannon() {
            return weaponKillsHandCannon;
        }

        public WeaponKillsFusionRifle getWeaponKillsFusionRifle() {
            return weaponKillsFusionRifle;
        }

        public WeaponKillsAutoRifle getWeaponKillsAutoRifle() {
            return weaponKillsAutoRifle;
        }

        public Suicides getSuicides() {
            return suicides;
        }

        public ResurrectionsReceived getResurrectionsReceived() {
            return resurrectionsReceived;
        }

        public ResurrectionsPerformed getResurrectionsPerformed() {
            return resurrectionsPerformed;
        }

        public PrecisionKills getPrecisionKills() {
            return precisionKills;
        }

        public ObjectivesCompleted getObjectivesCompleted() {
            return objectivesCompleted;
        }

        public KillsDeathsAssists getKillsDeathsAssists() {
            return killsDeathsAssists;
        }

        public KillsDeathsRatio getKillsDeathsRatio() {
            return killsDeathsRatio;
        }

        public Efficiency getEfficiency() {
            return efficiency;
        }

        public OpponentsDefeated getOpponentsDefeated() {
            return opponentsDefeated;
        }

        public BestSingleGameScore getBestSingleGameScore() {
            return bestSingleGameScore;
        }

        public BestSingleGameKills getBestSingleGameKills() {
            return bestSingleGameKills;
        }

        public AverageScorePerLife getAverageScorePerLife() {
            return averageScorePerLife;
        }

        public AverageScorePerKill getAverageScorePerKill() {
            return averageScorePerKill;
        }

        public Score getScore() {
            return score;
        }

        public AverageLifespan getAverageLifespan() {
            return averageLifespan;
        }

        public Deaths getDeaths() {
            return deaths;
        }

        public SecondsPlayed getSecondsPlayed() {
            return secondsPlayed;
        }

        public AverageKillDistance getAverageKillDistance() {
            return averageKillDistance;
        }

        public Kills getKills() {
            return kills;
        }

        public TotalKillDistance getTotalKillDistance() {
            return totalKillDistance;
        }

        public AverageDeathDistance getAverageDeathDistance() {
            return averageDeathDistance;
        }

        public TotalDeathDistance getTotalDeathDistance() {
            return totalDeathDistance;
        }

        public Assists getAssists() {
            return assists;
        }

        public ActivitiesWon getActivitiesWon() {
            return activitiesWon;
        }

        public ActivitiesEntered getActivitiesEntered() {
            return activitiesEntered;
        }
    }

    public static class Basic {
        @Expose
        @SerializedName("displayValue")
        private String displayValue;
        @Expose
        @SerializedName("value")
        private double value;

        public String getDisplayValue() {
            return displayValue;
        }

        public double getValue() {
            return value;
        }
    }

    public static class MergedDeletedCharacters {
        @Expose
        @SerializedName("merged")
        private Merged merged;
        @Expose
        @SerializedName("results")
        private Results results;

        public Merged getMerged() {
            return merged;
        }

        public Results getResults() {
            return results;
        }
    }
}
