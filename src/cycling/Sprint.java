package cycling;

public class Sprint extends Segment{

    public Sprint(int stageId, double location) {
        this.setStageId(stageId);
        this.setLocation(location);
        this.setType(SegmentType.SPRINT);
        this.setSegmentId(segmentCounter);
        segmentCounter++;
    }
}
