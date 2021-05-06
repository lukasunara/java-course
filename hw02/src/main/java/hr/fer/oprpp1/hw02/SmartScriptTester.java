package hr.fer.oprpp1.hw02;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

/**
 * Main class used only to demonstrate how does the {@link SmartScriptParser} work.
 * 
 * @author lukasunara
 *
 */
public class SmartScriptTester {

	/** Recieves one String argument which represents a path to src\test\java\resources\file.txt **/
	public static void main(String[] args) {
		String filepath = args[0];
		
		SmartScriptParser parser = null;
		try {
			String docBody = new String(
				 Files.readAllBytes(Paths.get(filepath)),
				 StandardCharsets.UTF_8
			);
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		
		// should write something like original content of docBody
		System.out.println(originalDocumentBody);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		// now document and document2 should be structurally identical trees
		boolean same = document.equals(document2); // ==> "same" must be true
		
		System.out.println("\n" + same);
	}

}
