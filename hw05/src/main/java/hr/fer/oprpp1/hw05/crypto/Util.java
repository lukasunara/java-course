package hr.fer.oprpp1.hw05.crypto;

/**
 * Class Util contains static methods <code>hextobyte(String hexString)<code> and
 * <code>bytetohex(byte[] byteString)<code> which are used for converting hexadecimal strings to
 * byte arrays or the opposite.
 * 
 * @author lukasunara
 *
 */
public class Util {

	/** String represents hex-digits => used by <code>bytetohex(byte[] byteString)<code> **/
	private static final String HEX_DIGITS = "0123456789abcdef";
	
	/**
	 * Converts a hexadecimal string to a byte array.
	 * 
	 * @param hexString String to convert
	 * @return the created byte array
	 * @throws IllegalArgumentException if given string is odd-sized or contains invalid characters
	 */
	public static byte[] hextobyte(String hexString) {
		int len = hexString.length();	// one hex-digit is 4 bits => two hex-digits are 1 byte (8 bits)
		
		if(len == 0) return new byte[0];
		if(len%2 != 0) throw new IllegalArgumentException("Hexadecimal string cannot be odd-sized!");

		byte[] bytes = new byte[len/2];
		for(int i = 0; i < len; i+=2) {
			int part1 = convertToDigit(hexString.charAt(i)) << 4;
			int part2 = convertToDigit(hexString.charAt(i+1));
			bytes[i/2] = (byte) (part1 + part2);
//			This does not work, I don't know why:
//			bytes[i/2] = (byte) (
//					convertToDigit(hexString.charAt(i)) << 4
//					+ convertToDigit(hexString.charAt(i+1))
//				);
		}
		return bytes;
	}
	
	/**
	 * Converts byte array to a hexadecimal String.
	 * 
	 * @param byteString byte array to convert
	 * @return the created hexadecimal String
	 */
	public static String bytetohex(byte[] byteString) {
		String hexString = "";
		for(byte b : byteString) {
			/* First digit has a value 16 times bigger than normally in a hexadecimal String
			 * so it needs to be shifted to right by 4 spaces (divided by 2^4=16)
			 */
			hexString += HEX_DIGITS.charAt((b & 0xF0) >> 4);
			hexString += HEX_DIGITS.charAt((b & 0x0F));
		}
		return hexString;
	}

	/**
	 * Converts a hex-digit to it's decimal representation.
	 * 
	 * @param c char to convert
	 * @return decimal value of hex-digit
	 */
	private static int convertToDigit(char c) {
		if(c >= '0' && c <= '9') return c - '0';
		if(c >= 'A' && c <= 'F') return c - 'A' + 10;
		if(c >= 'a' && c <= 'f') return c - 'a' + 10;
		
		throw new IllegalArgumentException("Character is not a hex-digit!");
	}
}
