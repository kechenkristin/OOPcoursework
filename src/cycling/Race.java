package cycling;

import java.util.HashMap;
import java.util.HashSet;

public class Race {

	/**
	 * All the races in the system.
	 *
	 * 
	 * @author Kechen Liu
	 * @version 1.0
	 */
	public static Integer raceCounter = 1;
	private int raceID;
	private String raceName;
	private String raceDesc;
	// store all the stages in one race
	private HashMap<Integer, Stage> raceStages = new HashMap<Integer, Stage>();

	// constructor
	Race(String raceName) {
		this.setRaceName(raceName);
		this.setRaceDesc(null);
		this.setRaceID(raceCounter);
		raceCounter++;
	}

	Race(String raceName, String raceDesc) {
		this.setRaceName(raceName);
		this.setRaceDesc(raceDesc);
		this.setRaceID(raceCounter);
		raceCounter++;
	}

	// methods:getter& setter
	public int getRaceID() {
		return this.raceID;
	}

	public void setRaceID(int raceID) {
		this.raceID = raceID;
	}

	public String getRaceName() {
		return this.raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public String getRaceDesc() {
		return this.raceDesc;
	}

	public void setRaceDesc(String raceDesc) {
		this.raceDesc = raceDesc;
	}

	public HashMap<Integer, Stage> getRaceStages() {
		return this.raceStages;
	}

	public void setRaceStages(HashMap<Integer, Stage> rs) {this.raceStages = rs;}

	public double showRaceLength() {
		double totallength = 0.0;
		for (Stage stage : this.raceStages.values()) {
			totallength += stage.getStageLength();
		}
		return totallength;
	}

	public boolean stageNameCheck(String name) {
		HashSet<String> names = new HashSet<String>();
		for (Integer i :raceStages.keySet()) {
			names.add(raceStages.get(i).getStageName());
		}
		return names.contains(name) == true;
	}



}
