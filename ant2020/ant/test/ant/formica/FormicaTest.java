package ant.formica;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ant.Ambiente;

/** 
 * Controllare che questi test abbiano successo sia
 * prima che dopo aver operato le modifiche suggerite<BR/>
 * <B>(Vedi DOMANDA 2)</B>
 */
public class FormicaTest {

	private Ambiente ambiente;
	
	@Before
	public void setUp() throws Exception {
		this.ambiente = new Ambiente(1);
	}

	@Test
	public void testIdProgressiviPerEsploratrici() {
		assertEquals("Gli id sono progressivi base 0 per ciascun tipo dinamico!", 0, new Esploratrice(this.ambiente).getId());
		assertEquals("Gli id sono progressivi base 0 per ciascun tipo dinamico!", 1, new Esploratrice(this.ambiente).getId());
	}

	@Test
	public void testIdProgressiviPerInseguitrici() {
		assertEquals("Gli id sono progressivi base 0 per ciascun tipo dinamico!", 0, new Inseguitrice(this.ambiente).getId());
		assertEquals("Gli id sono progressivi base 0 per ciascun tipo dinamico!", 1, new Inseguitrice(this.ambiente).getId());
	}

}
