package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * BadMiniCyclingPortal is a minimally compiling, but non-functioning
 * implementor
 * of the MiniCyclingPortalInterface interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class CyclingPortal implements MiniCyclingPortalInterface {

	// attributes
	public HashMap<Integer, Race> races = new HashMap<>();
	public HashMap<Integer, Stage> stages = new HashMap<>();
	private HashMap<Integer, Segment> segments = new HashMap<>();
	private HashMap<Integer, Team> teams = new HashMap<>();
	private HashMap<Integer, Rider> riders = new HashMap<>();

	// helper functions
	/**
	 * get the ids of the input set
	 * @return a int[] list of ideas
	 */
	private int[] getIds(Set<Integer> inteSet) {
		int[] Ids = new int[inteSet.size()];
		int index = 0;
		for (Integer i : inteSet) {
			Ids[index++] = i;
		}
		return Ids;
	}

	private Race getRace(int raceId) {
		return races.get(raceId);
	}

	private Stage getStage(int stageId) {
		return stages.get(stageId);
	}


	private Result getRiderAllResultInStage(int stageId, int riderId) {
		return riders.get(riderId).getResults().get(stageId);
	}

	@Override
	public int[] getRaceIds() {
		if (races.size() == 0) {
			return new int[] {};
		}
		Set<Integer> integerSet = races.keySet();
		return getIds(integerSet);

	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		if (raceNameCheck(name)) {
			throw new IllegalNameException("Name already taken.");
		}
		if (name == null) {
			throw new InvalidNameException("Name cannot be null.");
		}
		if (name.isEmpty()) {
			throw new InvalidNameException("Name cannot be empty.");
		}
		if (name.length() > 30) {
			throw new InvalidNameException("Name cannot contain more than 30.");
		}
		if (name.contains(" ")) {
			throw new InvalidNameException("Name cannot contain whitespaces.");
		}
		Race race;
		if (description != null) {
			race = new Race(name, description);
		} else {
			race = new Race(name);
		}
		races.put(race.getRaceID(), race);
		return race.getRaceID();
	}

	/* method helps check whether race name exits in the portal */
	private boolean raceNameCheck(String name) {
		HashSet<String> names = new HashSet<>();
		for (Integer i : races.keySet()) {
			names.add(races.get(i).getRaceName());
		}
		return names.contains(name);
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException("Race ID does not exist in system.");
		}
		Race race = getRace(raceId);
		assert (race != null);
		return "race ID: " + race.getRaceID() + " ,race name: " + race.getRaceName() + " ,race description: "
				+ race.getRaceDesc() + " ,the number of stages: " + race.getRaceStages().size()
				+ " ,race's total length: "
				+ race.showRaceLength();
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException("ID does not exist in system.");
		}
		races.remove(raceId);
		assert (races.get(raceId) == null);
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException("ID does not exist in system.");
		}
		return getRace(raceId).getRaceStages().size();
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException("Race ID does not exist in system.");
		}
		if (races.get(raceId).stageNameCheck(stageName)) {
			throw new IllegalNameException("This stage name is already in use.");
		}
		if (stageName == null) {
			throw new InvalidNameException("Name cannot be null.");
		}
		if (stageName.isEmpty()) {
			throw new InvalidNameException("Name cannot be empty.");
		}
		if (stageName.length() > 30) {
			throw new InvalidNameException("Name cannot contain more than 30.");
		}
		if (stageName.contains(" ")) {
			throw new InvalidNameException("Name cannot contain whitespaces.");
		}
		if (length < 5.0) {
			throw new InvalidLengthException("Stage length cannot be less than 5 (kilometres).");
		}
		Stage stage = new Stage(raceId, stageName, description, length, startTime, type);
		Integer staid = stage.getStageID();
		stages.put(staid, stage);
		races.get(raceId).getRaceStages().put(staid, stage);
		assert (stages.get(staid) != null);
		assert (races.get(raceId).getRaceStages().get(staid) != null);
		return staid;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		if (!races.containsKey(raceId)) {
			throw new IDNotRecognisedException("ID does not exist in system.");
		}
		Set<Integer> integerSet = getRace(raceId).getRaceStages().keySet();
		return getIds(integerSet);
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("ID does not exist in system.");
		}
		double length = getStage(stageId).getStageLength();
		assert (length != 0.0);
		return length;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("ID does not exist in system.");
		}
		Integer rid = getStage(stageId).getraceID();
		stages.remove(stageId);
		races.get(rid).getRaceStages().remove(stageId);
		assert (races.get(rid).getRaceStages().get(stageId) == null || stages.get(stageId) == null);
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (location < 0 || location > stages.get(stageId).getStageLength()) {
			throw new InvalidLocationException("Stage length out of bounds.");
		}
		if (getStage(stageId).getStageState() != null) {
			throw new InvalidStageStateException("stage is waiting for results");
		}
		if (getStage(stageId).getStageType() == StageType.TT) {
			throw new InvalidStageTypeException("Cannot add segments to a time-trial stage.");
		}
		Mountains segment = new Mountains(stageId, location, type, averageGradient, length);
		Integer segid = segment.getSegmentId();

		stages.get(stageId).getStageSegments().put(segid, segment);

		int raceId = getStage(stageId).getraceID();
		races.get(raceId).getRaceStages().get(stageId).getStageSegments().put(segid, segment);

		segments.put(segid, segment);

		assert (stages.get(stageId).getStageSegments() != null);
		return segid;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (location < 0 || location > stages.get(stageId).getStageLength()) {
			throw new InvalidLocationException("Stage length out of bounds.");
		}
		if (getStage(stageId).getStageState() != null) {
			throw new InvalidStageStateException("stage is waiting for results");
		}
		if (getStage(stageId).getStageType() == StageType.TT) {
			throw new InvalidStageTypeException("Cannot add segments to a time-trial stage.");
		}
		Sprint segment = new Sprint(stageId, location);
		Integer segid = segment.getSegmentId();

		stages.get(stageId).getStageSegments().put(segid, segment);

		int rid = getStage(stageId).getraceID();
		races.get(rid).getRaceStages().get(stageId).getStageSegments().put(segid, segment);

		segments.put(segid, segment);

		assert (getStage(stageId).getStageSegments() != null);
		return segid;
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		if (!segments.containsKey(segmentId)) {
			throw new IDNotRecognisedException("Segment ID not recognised.");
		}
		int staid = segments.get(segmentId).getStageId();
		if (getStage(staid).getStageState() != null) {
			throw new InvalidStageStateException("Cannot add to stage. Stage already concluded.");
		}
		stages.get(staid).getStageSegments().remove(segmentId);

		int rid = getStage(staid).getraceID();
		races.get(rid).getRaceStages().get(staid).getStageSegments().remove(segmentId);

		segments.remove(segmentId);

		assert (getStage(staid).getStageSegments().get(segmentId) == null);
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}

		if (Objects.equals(getStage(stageId).getStageState(), "waiting for results")) {
			throw new InvalidStageStateException("Stage already concluded.");
		}

		stages.get(stageId).setStageState("waiting for results");
		assert (getStage(stageId).getStageState() != null);

	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		Set<Integer> integerSet = getStage(stageId).getStageSegments().keySet();
		return getIds(integerSet);
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		if (checkTeamName(name)) {
			throw new IllegalNameException("Name already taken.");
		}
		if (name == null) {
			throw new InvalidNameException("Name cannot be null.");
		}
		if (name.isEmpty()) {
			throw new InvalidNameException("Name cannot be empty.");
		}
		if (name.length() > 30) {
			throw new InvalidNameException("Name cannot contain more than 30 characters.");
		}
		Team team = new Team(name, description);
		int tid = team.getTeamID();
		teams.put(tid, team);
		return tid;
	}

	private boolean checkTeamName(String name) {
		HashSet<String> names = new HashSet<>();
		for (Integer i : teams.keySet()) {
			names.add(teams.get(i).getTeamName());
		}
		return (names.contains(name));
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		if (!teams.containsKey(teamId)) {
			throw new IDNotRecognisedException("Team ID not recognised.");
		}
		teams.remove(teamId);
		assert (teams.get(teamId) == null);
	}

	@Override
	public int[] getTeams() {
		Set<Integer> integerSet = teams.keySet();
		return getIds(integerSet);
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		Integer tid = teamId;
		if (!teams.containsKey(tid)) {
			throw new IDNotRecognisedException("Team ID not recognised.");
		}
		Set<Integer> integerSet = teams.get(tid).getTeamRiders().keySet();
		return getIds(integerSet);
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		if (!teams.containsKey(teamID)) {
			throw new IDNotRecognisedException("Team ID not recognised.");
		}
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null.");
		}

		if (yearOfBirth < 1900) {
			throw new IllegalArgumentException("Year of birth cannot be less than 1900.");
		}
		Rider rider = new Rider(teamID, name, yearOfBirth);
		Integer rid = rider.getRiderId();

		teams.get(teamID).getTeamRiders().put(rid, rider);
		riders.put(rid, rider);

		return rid;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		if (!riders.containsKey(riderId)) {
			throw new IDNotRecognisedException("ID not recognised.");
		}
		int teamId = riders.get(riderId).getTeamId();
		riders.remove(riderId);
		teams.get(teamId).getTeamRiders().remove(riderId);
		assert (riders.get(riderId) == null || teams.get(teamId).getTeamRiders().get(riderId) == null);
	}

	/** method help check the length of the checkpoints */
	private boolean guranteeCheckpoints(int stageId, LocalTime... checkpoints) {
		int n = getStage(stageId).getStageSegments().size();
		return (checkpoints.length != (n + 2));
	}

	/** method help assign the at of a rider in that stage */
	private void calcAT(int stageId, int riderId) {
		LocalTime small = Result.findSmallTime(getStage(stageId));
		riders.get(riderId).getResults().get(stageId).calculateAdjustedElapsedTime(small);
		stages.get(stageId).getStageRiders().get(riderId).getResults().get(stageId).calculateAdjustedElapsedTime(small);
		int raceId = getStage(stageId).getraceID();
		races.get(raceId).getRaceStages().get(stageId).getStageRiders().get(riderId).getResults().get(stageId)
				.calculateAdjustedElapsedTime(small);
	}

	/**
	 * check whether a result exits in the stage
	 * return true if there is a result for that rider in the stage
 	 */
	private boolean checkResult(int stageId, int riderId) {
		return getRiderAllResultInStage(stageId, riderId) != null;
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {


		if (checkResult(stageId, riderId)) {
			throw new DuplicatedResultException(
					"Cannot have more than one record of a rider's result for a single stage.");
		}

		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (!riders.containsKey(riderId)) {
			throw new IDNotRecognisedException("Rider ID not recognised.");
		}
		if (guranteeCheckpoints(stageId, checkpoints)) {
			throw new InvalidCheckpointsException("The length of checkpoints is not correct");
		}
		if (getStage(stageId).getStageState() == null) {
			throw new InvalidStageStateException(
					"Results can only be added to a stage while it is waiting for results.");
		}
		// get stage
		int raceId = getStage(stageId).getraceID();

		// get rider
		Rider r = riders.get(riderId);
		r.getResults().put(stageId, new Result(checkpoints));

		riders.get(riderId).getResults().put(stageId, new Result(checkpoints));
		races.get(raceId).getRaceStages().get(stageId).getStageRiders().put(riderId, r);
		stages.get(stageId).getStageRiders().put(riderId, r);
		calcAT(stageId, riderId);
	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (!riders.containsKey(riderId)) {
			throw new IDNotRecognisedException("Rider ID not recognised.");
		}
		if (!checkResult(stageId, riderId)) {
			return new LocalTime[]{};
		}
		return getRiderAllResultInStage(stageId, riderId).getCheckPoints();
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (!riders.containsKey(riderId)) {
			throw new IDNotRecognisedException("Rider ID not recognised.");
		}
		if (getStage(stageId).getStageType() == StageType.TT) {
			return null;
		}
		return getRiderAllResultInStage(stageId, riderId).getAdjustedElapsedTime();
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (!riders.containsKey(riderId)) {
			throw new IDNotRecognisedException("Rider ID not recognised.");
		}
		stages.get(stageId).getStageRiders().get(riderId).getResults().remove(stageId);
		riders.get(riderId).getResults().remove(stageId);
		int raceId = stages.get(stageId).getraceID();
		races.get(raceId).getRaceStages().get(stageId).getStageRiders().get(riderId).getResults().remove(stageId);
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		Stage st = getStage(stageId);
		HashMap<Integer, Result> riderResult = Stage.getRiderResultInStage(st);
		HashMap<Integer, LocalTime> riderET = Result.getET(riderResult);
		HashMap<Integer, LocalTime> sortedRiderET = Result.sortRiderByTime(riderET);
		return Stage.getRankedIds(sortedRiderET);
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		Stage st = getStage(stageId);
		HashMap<Integer, Result> riderResult = Stage.getRiderResultInStage(st);
		HashMap<Integer, LocalTime> riderAT = Result.getAT(riderResult);
		HashMap<Integer, LocalTime> sortedRiderAT = Result.sortRiderByTime(riderAT);
		return Stage.getRankedTimes(sortedRiderAT);
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		StageType type = getStage(stageId).getStageType();
		return Segment.assignRiderPointsInStage(this.getRidersRankInStage(stageId), type);
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		if (!stages.containsKey(stageId)) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		this.races = null;
		this.riders = null;
		this.teams = null;
		this.segments = null;
		this.stages = null;
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(this);
		} catch (java.io.IOException ex) {
			System.out.println(ex.getMessage());
			throw new IOException("there is a problem experienced when trying to save the store contents to the file");
		}
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		CyclingPortal portal = null;
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			Object obj = in.readObject();
			if (obj instanceof CyclingPortal) {
				portal = (CyclingPortal) obj;
			}
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			throw new IOException(
					"There is a problem experienced when trying to load the store contents from the file");
		} catch (ClassNotFoundException e2) {
			throw new ClassNotFoundException("required class files cannot be found when loading");
		}
	}

}
