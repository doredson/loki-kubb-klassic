package net.oredson.loki.kubb.model;

public enum TournamentStrategy {
	DMK_KLASSIC("DMK_KLASSIC");
	
	private String strategyCode;
	
	private TournamentStrategy(String strategyCode) {
		this.strategyCode = strategyCode;
	}
	
	public String getStrategyCode()
	{
		return strategyCode;
	}
}
