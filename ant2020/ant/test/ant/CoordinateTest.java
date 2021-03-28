package ant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ant.Coordinate;

/** 
 * Modificare la classe {@link Coordinate} affinche'
 * questi test abbiano successo <B>(Vedi DOMANDA 1)</B>
 */
public class CoordinateTest {

	@Test
	public void testEquals() {
		assertEquals(new Coordinate(0,0), new Coordinate(0,0));
	}
	
	@Test
	public void testSetDiCoordinate() {
		final Set<Coordinate> insieme = new HashSet<>();
		final Coordinate fratello = new Coordinate(0,0);
		final Coordinate gemello = new Coordinate(0,0);
		assertNotSame(fratello, gemello);
		insieme.add(fratello);
		insieme.add(gemello);
		assertEquals("due oggetti distinti ma con le stesse coordinate dovrebbero contare 1!",
					 1, insieme.size());
	}

	
}
