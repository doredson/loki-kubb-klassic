package net.oredson.loki.kubb.strategy;

import net.oredson.loki.kubb.model.Tournament;

/**
 * IBracketStrategy is intended to be a generic way to implement ways to run a bracket.
 * @author danaoredson
 *
 */
public interface IBracketStrategy {
	public void setTournament(Tournament tournament);
	public void generateNextRound();
}
