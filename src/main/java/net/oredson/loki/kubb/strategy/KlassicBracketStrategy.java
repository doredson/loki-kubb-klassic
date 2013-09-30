package net.oredson.loki.kubb.strategy;

import net.oredson.loki.kubb.model.Score;
import net.oredson.loki.kubb.model.Tournament;
import net.oredson.loki.kubb.model.TournamentTeam;

/**
 * In the Klassic strategy, the next Round will have the same number of matches
 * as the previous Round. Order of the next Round will be based on team score,
 * which is computed like so: # of Games won. # of DNF - advantage as a tie
 * breaker Opponent's scores as a further tie breaker If there are odd #s of
 * teams, a BYE match is added to the round
 * 
 * FIXME: Determine how to select the team that gets the BYE
 * 
 * @author danaoredson
 * 
 */
public class KlassicBracketStrategy implements IBracketStrategy {

	private Tournament tournament;

	@Override
	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	@Override
	public void generateNextRound() {
		assert (tournament != null);

		int currentRound = tournament.getRounds().size() - 1;
		
		// For each registered team
		for (TournamentTeam tournamentTeam : tournament.getTournamentTeams()) {
			Score score = null;
			if(currentRound < 0) {
				// if this is the first round, everyone starts with a score of 0
				score = new Score();
			}
			else {
				score = calculateScore(tournamentTeam, currentRound);
			}
			
			// append score/team pairs to a list; to be sorted below
		}
		// Determine wins, score and opponent scores for this tournament,
		// starting with the current Round, going backward
		// Sort the teams by wins, tie breaker is score, further tie breaker is
		// opponent's score

		// TODO: If there are an odd # of teams, determine which team will have
		// a BYE

		// Create the next Round
		// Create Matches for the new Round, based on sort order and BYE
		// determination
	}

	public Score calculateScore(TournamentTeam tournamentTeam, int roundNumber) {
		Score score = new Score();
		// Find all Matches that tournamentTeam was in, up to round roundNumber
		// Calculate score based on wins
		return score;
	}
}
