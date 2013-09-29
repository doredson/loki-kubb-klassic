package net.oredson.loki.kubb.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Game {
	public static final String STATUS_TEAM_ID_FIELD_NAME = "status_team_id";
	public static final String OPENING_TEAM_ID_FIELD_NAME = "opening_team_id";
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private int order;
	
	// optional length of time in minutes
	@DatabaseField(canBeNull = true)
	private Integer time;
	
	@DatabaseField(dataType=DataType.ENUM_STRING)
	public GameStatus status;
	
	// Should only be null if status is DNF_DRAW
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = STATUS_TEAM_ID_FIELD_NAME, canBeNull = true)
	private Team statusTeam;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = OPENING_TEAM_ID_FIELD_NAME, canBeNull = true)
	private Team openingTeam;
	
	public Game() {
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

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public Team getStatusTeam() {
		return statusTeam;
	}

	public void setStatusTeam(Team statusTeam) {
		this.statusTeam = statusTeam;
	}
}
