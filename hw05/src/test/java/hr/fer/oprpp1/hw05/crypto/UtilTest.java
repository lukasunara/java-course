package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	public void hextobyteTest() {
		assertArrayEquals(new byte[0], Util.hextobyte(""));
		assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01aE22"));
	}

	@Test
	public void hextobyteThrows() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("1aE22"));
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("1aE2Z"));
	}

	@Test
	public void bytetohexTest() {
		assertEquals("", Util.bytetohex(new byte[0]));
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
	}

}
