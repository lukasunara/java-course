package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class SmartScriptTest {

	@Test
	public void testExample1() {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals(1, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
	}
	
	@Test
	public void testExample2() {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals(1, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
	}
	
	@Test
	public void testExample3() {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals(1, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
	}
	
	@Test
	public void testExample4() {
		String text = readExample(4);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testExample5() {
		String text = readExample(5);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testExample6() {
		String text = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals(2, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
		assertEquals(EchoNode.class, document.getChild(1).getClass());
	}
	
	@Test
	public void testExample7() {
		String text = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals(2, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
		assertEquals(EchoNode.class, document.getChild(1).getClass());
	}
	
	@Test
	public void testExample8() {
		String text = readExample(8);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testExample9() {
		String text = readExample(9);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testExample10() {
		String text = readExample(10);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals(4, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
		assertEquals(ForLoopNode.class, document.getChild(1).getClass());
		assertEquals(TextNode.class, document.getChild(2).getClass());
		assertEquals(ForLoopNode.class, document.getChild(3).getClass());
		
		assertEquals(3, document.getChild(1).numberOfChildren());
		assertEquals(TextNode.class, document.getChild(1).getChild(0).getClass());
		assertEquals(EchoNode.class, document.getChild(1).getChild(1).getClass());
		assertEquals(TextNode.class, document.getChild(1).getChild(2).getClass());
		
		assertEquals(5, document.getChild(3).numberOfChildren());
		assertEquals(TextNode.class, document.getChild(3).getChild(0).getClass());
		assertEquals(EchoNode.class, document.getChild(3).getChild(1).getClass());
		assertEquals(TextNode.class, document.getChild(3).getChild(2).getClass());
		assertEquals(EchoNode.class, document.getChild(3).getChild(3).getClass());
		assertEquals(TextNode.class, document.getChild(3).getChild(4).getClass());
	}
	
	@Test
	public void testExample11() {
		String text = readExample(11);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode document = parser.getDocumentNode();
		
		assertEquals(4, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
		assertEquals(ForLoopNode.class, document.getChild(1).getClass());
		assertEquals(TextNode.class, document.getChild(2).getClass());
		assertEquals(EchoNode.class, document.getChild(3).getClass());
		
		assertEquals(3, document.getChild(1).numberOfChildren());
		assertEquals(TextNode.class, document.getChild(1).getChild(0).getClass());
		assertEquals(EchoNode.class, document.getChild(1).getChild(1).getClass());
		assertEquals(TextNode.class, document.getChild(1).getChild(2).getClass());
	}
	
	@Test
	public void testExample12() {
		String text = readExample(12);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	/** Method used to read the given document. **/
	private String readExample(int n) {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
			if(is==null)
				throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");

			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch(IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}
	
	/** Method used to load filename as a String. **/
	@SuppressWarnings("unused")
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while(true) {
				int read = is.read(buffer);
				if(read<1) break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch(IOException ex) {
			return null;
		}
	}

}
