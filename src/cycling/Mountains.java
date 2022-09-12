package cycling;

/* Mountain segment extend segment. */
public class Mountains extends Segment {

    private Double averageGradient;

    private Double length;

    public Double getLength() {
        return this.length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getAverageGradient() {
        return this.averageGradient;
    }

    public void setAverageGradient(Double averageGradient) {
        this.averageGradient = averageGradient;
    }

    public void setAverageGrandient(Double averageGrandient) {
        this.averageGradient = averageGrandient;
    }

    public Mountains(int stageId, Double location, SegmentType type, Double averageGragient, Double length) {
        this.setStageId(stageId);
        this.setLocation(location);
        this.setType(type);
        this.setAverageGradient(averageGragient);
        this.setLength(length);
        this.setSegmentId(segmentCounter);
        segmentCounter++;
    }

    public Mountains(int stageId, Double location) {
    }
}
