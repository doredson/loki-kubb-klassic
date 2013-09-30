package net.oredson.loki.kubb.model;

import java.util.Date;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Tournament {
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField
	private String name;
	
	@DatabaseField
	private String location;
	
	@DatabaseField
	private Date date;

	@ForeignCollectionField(orderColumnName="order")
	private ForeignCollection<TournamentTeam> tournamentTeams;

	@ForeignCollectionField(orderColumnName="order")
	private ForeignCollection<Round> rounds;
	
	@DatabaseField(dataType=DataType.ENUM_STRING)
	private TournamentStrategy tournamentStrategy;
	
	public Tournament() {
		// Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public ForeignCollection<TournamentTeam> getTournamentTeams() {
		return tournamentTeams;
	}

	public void setTournamentTeams(ForeignCollection<TournamentTeam> tournamentTeams) {
		this.tournamentTeams = tournamentTeams;
	}

	public ForeignCollection<Round> getRounds() {
		return rounds;
	}

	public void setRounds(ForeignCollection<Round> rounds) {
		this.rounds = rounds;
	}

	public TournamentStrategy getTournamentStrategy() {
		return tournamentStrategy;
	}

	public void setTournamentStrategy(TournamentStrategy tournamentStrategy) {
		this.tournamentStrategy = tournamentStrategy;
	}
}
