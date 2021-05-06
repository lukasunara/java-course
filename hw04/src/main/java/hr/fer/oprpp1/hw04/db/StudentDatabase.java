package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Instances of this class represent a student database from a file. 
 * 
 * @author lukasunara
 *
 */
public class StudentDatabase {

	/** List of all student records in "database.txt" **/
	private ArrayList<StudentRecord> studentRecords;
	
	/** Used for fast retrieval of student records when jmbag is known **/
	private HashMap<String, StudentRecord> mapByJmbagIndex;
	
	/**
	 * Constructor creates a new StudentDatabase. Checks if there are any jmbag duplicates
	 * or invalid final grades.
	 * 
	 * @param rows list of rows in the database.txt
	 */
	public StudentDatabase(List<String> rows) {
		studentRecords = new ArrayList<>();
		for(String row : rows) {
			studentRecords.add(createStudentRecord(row));
		}
		mapByJmbagIndex = new HashMap<>();
		for(StudentRecord sr : studentRecords) {
			mapByJmbagIndex.put(sr.getJmbag(), sr);
		}
	}

	/** Checks if given info is in appropriate format and creates a new StudentRecord. **/
	private StudentRecord createStudentRecord(String row) {
		String[] record = row.split("\t");
		
		checkDuplicateJmbag(record[0]);
		
		int grade = Integer.parseInt(record[3]);
		checkGrade(grade);
		
		return new StudentRecord(record[0], record[1], record[2], grade);
	}
	
	/** Checks if given jmbag already exists. **/
	private void checkDuplicateJmbag(String jmbag) {
		studentRecords.forEach(sr -> {
			if(sr.getJmbag().equals(jmbag))
				throw new IllegalArgumentException("Duplicate of jmbag: " + jmbag);
		});
	}
	
	/** Checks if given grade is in interval [1, 5]. **/
	private void checkGrade(int grade) {
		if(grade < 1 || grade > 5)
			throw new IllegalArgumentException("Final grade is out of range: " + grade);
	}
	
	/**
	 * Fast retrieval of student records for the given jmbag.
	 * 
	 * @param jmbag student's jmbag we are searching for
	 * @return StudentRecord with the same jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return mapByJmbagIndex.get(jmbag);
	}
	
	/**
	 * Creates a list of all student records which are accepted by the given filter.
	 * 
	 * @param filter IFilter used for accepting of student records
	 * @return list of all student records which are accepted by the given filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		return studentRecords
					.stream()
					.filter(sr -> filter.accepts(sr))
					.collect(Collectors.toList());
	}
	
}
