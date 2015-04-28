package info.cewr.ufl;

import java.util.ArrayList;

/**
 * 
 * Fairly simple object to provide a simple container for professor objects, as
 * well as a few methods for returning the list of professors.
 * 
 * Mostly just to avoid ArrayList of ArrayList nonsense.
 * 
 * @author <a href="drscise@gmail.com">Dale Scheppler</a> 2015
 *
 */
public class Department {

	@SuppressWarnings("unused")
	private String deptName = "UNDEFINED";
	private ArrayList<Professor> professorList = new ArrayList<Professor>();
	
	/**
	 * Create a new instance of a <code>Department</code> object.
	 * @param name The name of the <code>Department</code>, as a <code>String</code>
	 */
	Department(String name) {
		this.deptName = name;
	}

	/**
	 * Add a <code>professor</code> to the professor list.
	 * 
	 * @param professor
	 *            The
	 *            <code>Professor<code> object you wish to add to the Professor list.
	 */
	public void addProfessor(Professor professor) {

		professorList.add(professor);

	}

	/**
	 * Return an <code>ArrayList</code> object containing the
	 * <code>Professor</code>s
	 * 
	 * @return <code>ArrayList</code> of <code>Professor</code> objects.
	 */
	public ArrayList<Professor> getProfessorList() {
		return this.professorList;
	}

}
