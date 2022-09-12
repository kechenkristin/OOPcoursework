package cycling;

public class Segment {
	/**
	 * All the segments in the system.
	 *
	 * 
	 * @author Kechen Liu
	 * @version 1.0
	 */
	private Double location;

	private int segmentId;

	private int stageId;

	private SegmentType type;

	public static int segmentCounter = 1;

	// constructor
	public Segment() {
	}

	// get&set
	public Double getLocation() {
		return this.location;
	}

	public void setLocation(Double location) {
		this.location = location;
	}

	public SegmentType gettype() {
		return this.type;
	}

	public void setType(SegmentType type) {
		this.type = type;
	}

	public int getStageId() {
		return this.stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public int getSegmentId() {
		return this.segmentId;
	}

	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}


	/**
	 * assign rider's point in stage based on their rank in stage and stage type
	 * @param riderRank rider's rank in stage, call portal's getRiderRank method to get
	 * @param type stage's type
	 * @return int array of rider's points, corresponding to rider's id
	 */
	public static int[] assignRiderPointsInStage(int[] riderRank, StageType type) {
		int[] points = new int[riderRank.length];
		if (type == StageType.FLAT) {
			for (int i = 0; i < riderRank.length; i++) {
				int n = riderRank[i];
				switch (n) {
					case 1 -> points[i] = 50;
					case 2 -> points[i] = 30;
					case 3 -> points[i] = 20;
					case 4 -> points[i] = 18;
					case 5 -> points[i] = 16;
					case 6 -> points[i] = 14;
					case 7 -> points[i] = 12;
					case 8 -> points[i] = 10;
					case 9 -> points[i] = 8;
					case 10 -> points[i] = 7;
					case 11 -> points[i] = 6;
					case 12 -> points[i] = 5;
					case 13 -> points[i] = 4;
					case 14 -> points[i] = 3;
					case 15 -> points[i] = 2;
				}
			}
		}
		if (type == StageType.TT
				|| type == StageType.HIGH_MOUNTAIN) {
			for (int i = 0; i < riderRank.length; i++) {
				int n = riderRank[i];
				switch (n) {
					case 1 -> points[i] = 20;
					case 2 -> points[i] = 17;
					case 3 -> points[i] = 15;
					case 4 -> points[i] = 13;
					case 5 -> points[i] = 11;
					case 6 -> points[i] = 10;
					case 7 -> points[i] = 9;
					case 8 -> points[i] = 8;
					case 9 -> points[i] = 7;
					case 10 -> points[i] = 6;
					case 11 -> points[i] = 5;
					case 12 -> points[i] = 4;
					case 13 -> points[i] = 3;
					case 14 -> points[i] = 2;
					case 15 -> points[i] = 1;
				}
			}

		}

		if (type == StageType.MEDIUM_MOUNTAIN) {
			for (int i = 0; i < riderRank.length; i++) {
				int n = riderRank[i];
				switch (n) {
					case 1 -> points[i] = 30;
					case 2 -> points[i] = 25;
					case 3 -> points[i] = 22;
					case 4 -> points[i] = 19;
					case 5 -> points[i] = 17;
					case 6 -> points[i] = 15;
					case 7 -> points[i] = 13;
					case 8 -> points[i] = 11;
					case 9 -> points[i] = 9;
					case 10 -> points[i] = 7;
					case 11 -> points[i] = 6;
					case 12 -> points[i] = 5;
					case 13 -> points[i] = 4;
					case 14 -> points[i] = 3;
					case 15 -> points[i] = 2;
				}
			}
		}
		return points;
	}

}
