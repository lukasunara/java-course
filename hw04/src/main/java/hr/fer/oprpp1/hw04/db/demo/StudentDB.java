package hr.fer.oprpp1.hw04.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import hr.fer.oprpp1.hw04.db.QueryFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.QueryParserException;
import hr.fer.oprpp1.hw04.db.StudentDatabase;
import hr.fer.oprpp1.hw04.db.StudentRecord;

/**
 * Main class used for testing of {@link StudentDatabase} and {@link QueryParser}
 * classes and their methods.
 * 
 * @author lukasnara
 */
public class StudentDB {

	/** The program should terminate when user enters the "exit" command. **/
	public static void main(String[] args) throws IOException {
		List<String> lines = new ArrayList<>();
		try {
			lines = loadDatabase();			
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		StudentDatabase db = new StudentDatabase(lines);
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			String keyword = sc.next(); // skip query keyword
			if(keyword.equals("exit")) break;
			else if(!keyword.equals("query")) {
				System.out.println("Given string must start with 'query'!");
				continue;
			}
			String query = sc.nextLine(); // read rest of this line

			QueryParser parser = null;
			try {
				parser = new QueryParser(query); // parsing of given query
			} catch(QueryParserException ex) {
				System.out.println(ex.getMessage());
				continue;
			}
			
			LinkedList<StudentRecord> records = new LinkedList<>();
			if(parser.isDirectQuery()) {
				System.out.println("Using index for record retrieval.");
				StudentRecord sr = db.forJMBAG(parser.getQueriedJMBAG());
				if(sr != null) records.add(sr);
			} else {
				for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
					records.add(r);
				}
			}
			LinkedList<String> output = new LinkedList<>();
			if(records.size() > 0) {
				output = RecordFormatter.format(records);
				print(output);
			}
			System.out.println("Records selected: " + output.size() + "\n");
		}
		System.out.println("Goodbye!\n");
		sc.close();
	}

 	private static List<String> loadDatabase() throws IOException {
		List<String> lines = Files.readAllLines(
				Paths.get("./src/test/resources/database.txt"),
				StandardCharsets.UTF_8
		);
		return lines;
	}
 	
 	private static void print(LinkedList<String> output) {
 		printBorder();
 		output.forEach(System.out::println);
 		printBorder();
 	}
 	
 	private static void printBorder() {
 		System.out.format("+" + "=".repeat(RecordFormatter.longestJmbag+2) + "+" 
 				+ "=".repeat(RecordFormatter.longestLastName+2) + "+"
 				+ "=".repeat(RecordFormatter.longestFirstName+2) + "+"
 				+ "=".repeat(3) + "+\n"
 		);
 	}
 	
}

