package net.oredson.loki.kubb.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TournamentTeam {
	public static final String TEAM_ID_FIELD_NAME = "team_id";

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private int order;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = TEAM_ID_FIELD_NAME, canBeNull = true)
	private Team team;
	
	@ForeignCollectionField(orderColumnName="order")
	private ForeignCollection<TeamMember> teamMembers;

	public TournamentTeam() {
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

	public Team getTeam() {
		return team;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
}
