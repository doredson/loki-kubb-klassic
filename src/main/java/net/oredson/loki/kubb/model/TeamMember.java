package net.oredson.loki.kubb.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TeamMember {
	public static final String PLAYER_ID_FIELD_NAME = "player_id";

	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private int order;

	@DatabaseField
	private boolean captain;

	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = PLAYER_ID_FIELD_NAME, canBeNull = true)
	private Player player;
	
	public TeamMember() {
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

	public boolean isCaptain() {
		return captain;
	}

	public void setCaptain(boolean captain) {
		this.captain = captain;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
