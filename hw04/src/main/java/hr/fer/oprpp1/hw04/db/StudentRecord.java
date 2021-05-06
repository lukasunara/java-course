package hr.fer.oprpp1.hw04.db;

/**
 * Instances of this class represent records for each student. There cannot exist multiple
 * records for the same student.
 * 
 * @author lukasunara
 *
 */
public class StudentRecord {

	/** Represents student's unique jmbag **/
	private String jmbag;
	
	/** Represents student's first name **/
	private String firstName;
	
	/** Represents student's last name **/
	private String lastName;
	
	/** Represents student's final grade **/
	private int finalGrade;

	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		return jmbag.hashCode();
//		return Objects.hash(jmbag);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		
		return this.jmbag.equals(other.jmbag);
//		return Objects.equals(jmbag, other.jmbag);
	}

	/**
	 * Public getter method for student's jmbag.
	 * 
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Public getter method for student's first name.
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Public getter method for student's last name.
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Public getter method for student's final grade.
	 * 
	 * @return the finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

}
