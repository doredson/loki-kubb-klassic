package net.oredson.loki.kubb.model;

import com.j256.ormlite.field.DatabaseField;

public class Team {
	@DatabaseField(generatedId = true)
	private int id;
	
	private String name;
	private String location;
	
	public Team() {
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
}
