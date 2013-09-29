package net.oredson.loki.kubb.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Round {
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private int order;
	
	@ForeignCollectionField
	private ForeignCollection<Match> matches;
	
	public Round() {
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
	
	public ForeignCollection<Match> getMatches() {
		return matches;
	}
	
	public void setMatches(ForeignCollection<Match> matches) {
		this.matches = matches;
	}
}
