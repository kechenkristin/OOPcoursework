package cycling;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Result {
    private LocalTime[] checkpoints;
    private LocalTime totalElapsedTime;
    private LocalTime adjustedElapsedTime;

    public LocalTime[] getCheckPoints() {
        return this.checkpoints;
    }

    public void setCheckPoints(LocalTime[] checkpoints) {
        this.checkpoints = checkpoints;
    }

    public LocalTime getAdjustedElapsedTime() {
        return this.adjustedElapsedTime;
    }

    public void setAdjustedElapsedTime(LocalTime adjustedElapsedTime) {
        this.adjustedElapsedTime = adjustedElapsedTime;
    }

    public LocalTime getTotalElapsedTime() {
        return this.totalElapsedTime;
    }

    public void setTotalElapsedTime(LocalTime totalElapsedTime) {
        this.totalElapsedTime = totalElapsedTime;
    }

    public Result(LocalTime[] checkp) {
        checkpoints = checkp;
        totalElapsedTime = calculateTotalElapsedTime();
    }

    /** method to find the smallest finish time in a stage. */
    public static LocalTime findSmallTime(Stage st) {
        int stageId = st.getStageID();
        HashMap<Integer, Rider> allRidersInStage = st.getStageRiders();
        List<LocalTime> ftList = new ArrayList<>();

        for (Map.Entry<Integer, Rider> mapElement : allRidersInStage.entrySet()) {
            LocalTime[] checkpoints = mapElement.getValue().getResults().get(stageId).getCheckPoints();
            LocalTime finishTime = checkpoints[checkpoints.length - 1];
            ftList.add(LocalTime.parse(finishTime.toString()));
        }
        return ftList.get(0);
    }

    public LocalTime calculateTotalElapsedTime() {
        LocalTime et = calcTime(checkpoints[0], checkpoints[checkpoints.length - 1]);
        return et;
    }

    public LocalTime calculateAdjustedElapsedTime(LocalTime smallest) {
        this.adjustedElapsedTime = calcTime(checkpoints[0], smallest);
        return adjustedElapsedTime;
    }

    /* method to calculate the difference between start and end. */
    public static LocalTime calcTime(LocalTime start, LocalTime end) {
        Duration time = Duration.between(start, end);
        long longd = time.toMillis();
        LocalTime newTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(longd), ZoneId.systemDefault())
                .toLocalTime();
        return newTime;
    }

    /**
     * method to get rider's eclipsed finish time
     *
     * @param rr HashMap of RiderId and the corresponding result.
     * @return HashMap of RiderId and corresponding eclipsed finish time.
     */
    public static HashMap<Integer, LocalTime> getET(HashMap<Integer, Result> rr) {
        HashMap<Integer, LocalTime> et = new HashMap<>();
        for (Map.Entry<Integer, Result> m : rr.entrySet()) {
            et.put(m.getKey(), m.getValue().getTotalElapsedTime());
        }
        return et;
    }

    /**
     * method to get rider's adjusted  finish time
     *
     * @param rr HashMap of RiderId and the corresponding result.
     * @return HashMap of RiderId and corresponding adjusted finish time.
     */
    public static HashMap<Integer, LocalTime> getAT(HashMap<Integer, Result> rr) {
        HashMap<Integer, LocalTime> et = new HashMap<>();
        for (Map.Entry<Integer, Result> m : rr.entrySet()) {
            et.put(m.getKey(), m.getValue().getAdjustedElapsedTime());
        }
        return et;
    }

    /**
     * method to get rider's adjusted  finish time
     *
     * @param rr HashMap of RiderId and the corresponding result.
     * @return HashMap of RiderId and corresponding adjusted finish time.
     */
    public static HashMap<Integer, LocalTime> getFT(HashMap<Integer, Result> rr) {
        HashMap<Integer, LocalTime> ft = new HashMap<>();
        for (Map.Entry<Integer, Result> m : rr.entrySet()) {
            LocalTime finishTime = m.getValue().getCheckPoints()[m.getValue().getCheckPoints().length - 1];
            ft.put(m.getKey(), finishTime);
        }
        return ft;
    }

    /**
     *  method for sorting a HashMap of Integer and LocalTime.
     *
     * @param rt HashMap RiderId and LocalTime.
     * @return a sorted HashMap.
     */
    public static HashMap<Integer, LocalTime> sortRiderByTime(HashMap<Integer, LocalTime> rt) {
        List<Map.Entry<Integer, LocalTime>> list = new LinkedList<Map.Entry<Integer, LocalTime>>(rt.entrySet());

        // Sort the list
        list.sort(new Comparator<Map.Entry<Integer, LocalTime>>() {
            public int compare(Map.Entry<Integer, LocalTime> o1, Map.Entry<Integer, LocalTime> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        HashMap<Integer, LocalTime> sorted = new LinkedHashMap<Integer, LocalTime>();
        for (Map.Entry<Integer, LocalTime> m : list) {
            sorted.put(m.getKey(), m.getValue());
        }
        return sorted;
    }

}
