package hr.fer.oprpp1.hw04.db.demo;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.oprpp1.hw04.db.FieldValueGetters;
import hr.fer.oprpp1.hw04.db.IFieldValueGetter;
import hr.fer.oprpp1.hw04.db.StudentRecord;

/**
 * This class is used for formating given records List
 * 
 * @author lukasunara
 *
 */
public class RecordFormatter {

	public static int longestJmbag;
	public static int longestFirstName;
	public static int longestLastName;
	
	/** Formats each record like "| jmbag | firstName | lastName | finalGrade |" **/
	public static LinkedList<String> format(List<StudentRecord> records) {
		longestJmbag = calculateLongest(records, FieldValueGetters.JMBAG); 		
		longestFirstName = calculateLongest(records, FieldValueGetters.FIRST_NAME);
 		longestLastName = calculateLongest(records, FieldValueGetters.LAST_NAME);
 		
		LinkedList<String> formatedStrings = new LinkedList<>();
		records.stream()
			.forEach(sr -> {
				String[] data = {
					sr.getJmbag() + " ".repeat(longestJmbag-sr.getJmbag().length()),
					sr.getLastName() + " ".repeat(longestLastName-sr.getLastName().length()),
					sr.getFirstName() + " ".repeat(longestFirstName-sr.getFirstName().length()),
					String.valueOf(sr.getFinalGrade())
				};
				formatedStrings.add(Arrays.stream(data)
						.collect(Collectors.joining(" | ", "| ", " |"))
					);
			});
		return formatedStrings;
	}
	
	 private static int calculateLongest(List<StudentRecord> records, IFieldValueGetter getter) {
		 if(records.isEmpty()) return 0;
		 
		 return records.stream()
 					.mapToInt(sr -> getter.get(sr).length())
 	 				.max()
 	 				.getAsInt();
 	 }
	 
}
