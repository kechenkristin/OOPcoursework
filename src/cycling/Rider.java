package cycling;

import java.util.ArrayList;
import java.util.HashMap;

public class Rider {
	/**
	 * All the rider in the system.
	 *
	 * 
	 * @author Kechen Liu
	 * @version 1.0
	 */
	private String name;
	private int yearOfBirth;
	public static int riderIDCounter = 1;
	private int riderID;
	private int teamId;
	private ArrayList<Integer> stageIds = new ArrayList<Integer>();

	private int riderPoints;
	private int riderMountainPoints;

	private HashMap<Integer, Result> results;

	// constructor
	public Rider(int teamId, String name, int yearOfBirth) {
		this.teamId = teamId;
		this.setRiderName(name);
		this.setYearOfBirth(yearOfBirth);
		this.riderID = riderIDCounter;
		riderIDCounter++;
		// to do
		this.results = new HashMap<>();
	}

	// method: getter& setter
	public String getRiderName() {
		return this.name;
	}

	public void setRiderName(String name) {
		this.name = name;
	}

	public int getYearOfBirth() {
		return this.yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public int getRiderId() {
		return this.riderID;
	}

	public void setRiderId(String name) {
		this.name = name;
	}

	public int getTeamId() {
		return this.teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public ArrayList<Integer> getStageIds() {
		return this.stageIds;
	}

	public void setStageId(ArrayList<Integer> stageIds) {
		this.stageIds = stageIds;
	}

	public int getRiderPoints() {
		return this.riderPoints;
	}

	public void setRiderPoints(int riderPoints) {
		this.riderPoints = riderPoints;
	}

	public int getRiderMountainPoints() {
		return this.riderMountainPoints;
	}

	public void setRiderMountainPoint(int riderMountainPoints) {
		this.riderMountainPoints = riderMountainPoints;
	}

	public HashMap<Integer, Result> getResults() {
		return this.results;
	}

	public void setResult(HashMap<Integer, Result> results) {
		this.results = results;
	}

}
