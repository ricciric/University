package ant;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/** 
 * Modificare la classe {@link Direzione} affinche'
 * questi test abbiano successo <B>(Vedi DOMANDA 1)</B>
 */
public class DirezioneTest {

	private Direzione sudOvest;

	private Direzione nordEst;

	private Direzione est;

	private Direzione ovest;

	@Before
	public void setUp() throws Exception {
		this.sudOvest = new Direzione(+1, +1);
		this.nordEst = new Direzione(-1, -1);
		this.est   = new Direzione(+1,0);
		this.ovest = new Direzione(-1,0);
	}

	@Test
	public void testDirezioneOpposta() {
		assertEquals(this.sudOvest,this.nordEst.opposta());
		assertEquals(this.nordEst, this.sudOvest.opposta());
		assertEquals(this.est,     this.ovest.opposta());
		assertEquals(this.ovest,   this.est.opposta());
	}

}
