package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Stage {
    /**
     * All the stages in the system.
     *
     * @author Kechen Liu
     * @version 1.0
     */
    public static int stageCounter = 1;
    private int stageID;
    private String stageName;
    private String stageDesc;
    private double stageLength;
    private StageType type;
    private LocalDateTime startTime;
    private String stageState;
    private int raceID;
    private HashMap<Integer, Segment> stageSegments = new HashMap<Integer, Segment>();
    // store all the riders in one stage
    private HashMap<Integer, Rider> stageRiders = new HashMap<Integer, Rider>();

    // constructor
    Stage(int raceId, String stageName, String stageDesc, double stageLength, LocalDateTime startTime, StageType type) {
        this.setRaceID(raceId);
        this.setStageName(stageName);
        this.setStageDesc(stageDesc);
        this.setStageID(stageCounter);
        this.setStageLength(stageLength);
        this.setStageType(type);
        this.setStartTime(startTime);
        this.setStageState(null);
        stageCounter++;
    }

    // getters and setters
    public int getraceID() {
        return this.raceID;
    }

    public void setRaceID(int raceID) {
        this.raceID = raceID;
    }

    public int getStageID() {
        return this.stageID;
    }

    public void setStageID(int stageID) {
        this.stageID = stageID;
    }

    public String getStageName() {
        return this.stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getStageDesc() {
        return this.stageDesc;
    }

    public void setStageDesc(String stageDesc) {
        this.stageDesc = stageDesc;
    }

    public double getStageLength() {
        return this.stageLength;
    }

    public void setStageLength(double stageLength) {
        this.stageLength = stageLength;
    }

    public StageType getStageType() {
        return this.type;
    }

    public void setStageType(StageType type) {
        this.type = type;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getStageState() {
        return this.stageState;
    }

    public HashMap<Integer, Segment> getStageSegments() {
        return this.stageSegments;
    }

    public void setStageState(String stageState) {
        this.stageState = stageState;
    }

    public HashMap<Integer, Rider> getStageRiders() {
        return this.stageRiders;
    }

    /**
     * Get the rider's result in the corresponding stage
     * @param st the stage user wants to find result.
     * @return A HashMap of riderId and corresponding result
     */
    public static HashMap<Integer, Result> getRiderResultInStage(Stage st) {
        int stageId = st.stageID;
        // riderId <stageId, result>
        HashMap<Integer, HashMap<Integer, Result>> rsr = new HashMap<>();
        HashMap<Integer, Rider> riders = st.getStageRiders();

        for (Map.Entry<Integer, Rider> mapElement : riders.entrySet()) {
            rsr.put(mapElement.getKey(), mapElement.getValue().getResults());
        }

        // riderId, result
        HashMap<Integer, Result> rr = new HashMap<>();
        for (Map.Entry<Integer, HashMap<Integer, Result>> m : rsr.entrySet()) {
            if (m.getValue().containsKey(stageId)) {
                rr.put(m.getKey(), m.getValue().get(stageId));
            }
        }
        return rr;
    }

    /**
     *  Get Ranked riderIds
     * @param rt HashMap of riderId and LocalTime(can be eclipsed time or adjusted time
     * @return a ranked int array of riders' ids.
     */
    public static int[] getRankedIds(HashMap<Integer, LocalTime> rt) {
        ArrayList<Integer> listRids = new ArrayList<>();
        // iterate over and get keys and values
        for (Map.Entry<Integer, LocalTime> m : rt.entrySet()) {
            listRids.add(m.getKey());
        }
        int[] ids = new int[listRids.size()];
        for (int i = 0; i < listRids.size(); i++) {
            int e = listRids.get(i);
            ids[i] = e;
        }
        return ids;
    }

    /**
     * Get ranked LocalTimes
     * @param rt HashMap of riderId and LocalTime(can be eclipsed time or adjusted time
     * @return a ranked LocalTime array of riders' time
     */
    public static LocalTime[] getRankedTimes(HashMap<Integer, LocalTime> rt) {
        ArrayList<LocalTime> listTimes = new ArrayList<>();
        // iterate over and get keys and values
        for (Map.Entry<Integer, LocalTime> m : rt.entrySet()) {
            listTimes.add(m.getValue());
        }
        LocalTime[] times = new LocalTime[listTimes.size()];
        for (int i = 0; i < listTimes.size(); i++) {
            LocalTime e = listTimes.get(i);
            times[i] = e;
        }
        return times;
    }

}

