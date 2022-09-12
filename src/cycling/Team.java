package cycling;

import java.util.HashMap;

public class Team {
	/**
	 * All the teams in the system.
	 *
	 * 
	 * @author Kechen Liu
	 * @version 1.0
	 */
	static int teamCounter = 1;
	private int teamID;
	private String teamName;
	private String teamDesc;
	// store all the riders in one team
	private HashMap<Integer, Rider> teamRiders = new HashMap<Integer, Rider>();

	Team(String teamName, String teamDesc) {
		this.setTeamName(teamName);
		this.setTeamDesc(teamDesc);
		this.setTeamID(teamCounter);
		teamCounter++;
	}

	// getter and setter methods
	public int getTeamID() {
		return this.teamID;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamDesc() {
		return this.teamDesc;
	}

	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}

	public HashMap<Integer, Rider> getTeamRiders() {
		return this.teamRiders;
	}

}
