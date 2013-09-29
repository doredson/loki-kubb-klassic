package net.oredson.loki.kubb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
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
	private List<TournamentTeam> teams = new ArrayList<TournamentTeam>();
	private List<Round> rounds = new ArrayList<Round>();
	
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
	
	public List<TournamentTeam> getTeams() {
		return teams;
	}
	
	public void setTeams(List<TournamentTeam> teams) {
		this.teams = teams;
	}
	
	public List<Round> getRounds() {
		return rounds;
	}
	
	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}
}
