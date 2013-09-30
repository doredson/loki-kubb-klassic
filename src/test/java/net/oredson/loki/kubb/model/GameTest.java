package net.oredson.loki.kubb.model;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GameTest {

	@Test
	public void testGameFields() {
		Team statusTeam = new Team();
		Game g = new Game();
		g.setId(40);
		g.setOrder(5);
		g.setStatus(GameStatus.BYE);
		g.setTime(60);
		g.setStatusTeam(statusTeam);
		
		assertThat(g.getId(), is(40));
		assertThat(g.getOrder(), is(5));
		assertThat(g.getStatus(), is(GameStatus.BYE));
		assertThat(g.getTime(), is(60));
		assertThat(g.getStatusTeam(), is(sameInstance(statusTeam)));
	}
}
