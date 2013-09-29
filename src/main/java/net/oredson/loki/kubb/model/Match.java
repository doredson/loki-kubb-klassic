package net.oredson.loki.kubb.model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Match {
	public static final String TEAM_1_ID_FIELD_NAME = "team_1_id";
	public static final String TEAM_2_ID_FIELD_NAME = "team_2_id";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private int order;

	@DatabaseField
	private int pitch;

	private List<Game> games = new ArrayList<Game>();

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = TEAM_1_ID_FIELD_NAME, canBeNull = true)
	private Team team1;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = TEAM_2_ID_FIELD_NAME, canBeNull = true)
	private Team team2;
	
	public Match() {
		// Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public int getPitch() {
		return pitch;
	}
	
	public void setPitch(int pitch) {
		this.pitch = pitch;
	}
	
	public List<Game> getGames() {
		return games;
	}
	
	public void setGames(List<Game> games) {
		this.games = games;
	}
	
	public Team getTeam1() {
		return team1;
	}
	
	public void setTeam1(Team team1) {
		this.team1 = team1;
	}
	
	public Team getTeam2() {
		return team2;
	}
	
	public void setTeam2(Team team2) {
		this.team2 = team2;
	}
}
