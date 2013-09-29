package net.oredson.loki.kubb.model;

public enum GameStatus {
	BYE ( "BYE"),
	DNF_DRAW ("DNF-D"),
	DNF_ADVANTAGE("DNF-A"),
	FORFEIT("FORFEIT"),
	WIN("WIN");
	
	private String statusCode;
	
	GameStatus(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public String getStatutCode() {
		return statusCode;
	}
}