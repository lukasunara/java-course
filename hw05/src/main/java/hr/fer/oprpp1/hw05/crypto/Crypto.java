package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class Crypto is used for interaction with users. The user can create a digest for a binary file
 * or encrypt/decrypt a binary file.
 * 
 * @author lukasunara
 *
 */
public class Crypto {

	/** Represents size of 4kb = 4096 bytes **/
	private static final int SIZE4KB = 4096;
	
	/** Represents size of 64 bytes **/
	private static final int LENGTH_OF_EXPECTED_SHA256 = 64;
	
	/** Represents size of 32 bytes **/
	private static final int LENGTH_OF_IV_AND_PASSWORD = 32;
	
	/**
	 * Creates the digest for the given binary file.
	 * 
	 * @param binaryFile String which represents a path to the binary file
	 * @return byte array which represents the created digest
	 */
	private static byte[] digestFile(String binaryFile) {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(binaryFile)))) {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			
			byte[] b = new byte[SIZE4KB]; // read the file by chunks of 4kb 
			int sizeOfUpdate;
			while((sizeOfUpdate = is.read(b)) != -1) {
				sha.update(b, 0, sizeOfUpdate);
			}
			return sha.digest(); // creates the digest
		} catch (IOException | NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Encrypts or decrypts the given file and creates the encrypted/decrypted file.
	 * 
	 * @param encrypt represents a constant which decides if the method should encrypt or decrypt given file
	 * @param keyText what user provided for password
	 * @param ivText what user provided for initialization vector
	 * @param inputFile file the file for encryption/decryption
	 * @param outputFile the created encrypted/decrypted file
	 */
	private static void encryptOrDecrypt(int encrypt, String keyText, String ivText, String inputFile, String outputFile) {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(inputFile)));
			OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(outputFile)));
		) {
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
			cipher.init(encrypt, keySpec, paramSpec);
			
			byte[] bInput = new byte[SIZE4KB]; // read the file by chunks of 4kb
			int sizeOfUpdate;
			while((sizeOfUpdate = is.read(bInput)) != -1) {
				os.write(cipher.update(bInput, 0, sizeOfUpdate));
			}
			os.write(cipher.doFinal());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method is used to interact with the user. The user has two options: check the expected
	 * sha-256 digest for a binary file; or encrypt/decrypt a binary file.
	 * 
	 * @param args arg[0] defines what should the program do, the others represents paths to files
	 */
	public static void main(String[] args) {
		if(args.length == 0) throw new IllegalArgumentException("Arguments are expected!");
		
		Scanner sc = new Scanner(System.in);
		switch(args[0]) {
			case "checksha": {
				System.out.format("Please provide expected sha-256 digest for %s:\n> ", args[1]);
				String sha256Text = sc.next();
				if(sha256Text.length() != LENGTH_OF_EXPECTED_SHA256) {
					sc.close();
					throw new IllegalArgumentException("Expected sha-256 digest must contain exactly 64 hex-digits!");
				}
				String digest = Util.bytetohex(digestFile(args[1]));
				
				if(digest.equals(sha256Text))
					System.out.format("Digesting completed. Digest of %s matches expected digest.\n",  args[1]);
				else
					System.out.format("Digesting completed. Digest of %s does not match the expected digest. "
						+ "Digest was: %s\n",  args[1], digest);
				break;
			}
			case "encrypt", "decrypt": {
				System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n> ");
				String keyText = sc.next();	// what user provided for password
				if(keyText.length() != LENGTH_OF_IV_AND_PASSWORD) {
					sc.close();
					throw new IllegalArgumentException("Password must contain exactly 32 hex-digits!");
				}
				System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n> ");
				String ivText = sc.next(); 	// what user provided for initialization vector
				if(ivText.length() != LENGTH_OF_IV_AND_PASSWORD) {
					sc.close();
					throw new IllegalArgumentException("Initialization vector must contain exactly 32 hex-digits!");
				}
				int encrypt = args[0].equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
				
				// decides if the method should encrypt or decrypt
				encryptOrDecrypt(encrypt, keyText, ivText, args[1], args[2]);
				
				System.out.format("%s completed. Generated file %s based on file %s.\n"
						, encrypt==Cipher.ENCRYPT_MODE ? "Encryption" : "Decryption", args[1], args[2]);
				break;
			}
			default: {
				sc.close();
				throw new IllegalArgumentException("The given arguments are invalid!");
			}
		}
		sc.close();
	}
	
}
