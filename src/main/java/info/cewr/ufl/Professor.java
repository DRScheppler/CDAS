package info.cewr.ufl;

import java.util.HashMap;

/**
 * Handles the setup and processing of data related to professors. Expects an
 * <code>Array</code> of <code>String</code> for initialization.
 * 
 * @author <a href="drscise@gmail.com">Dale Scheppler</a> 2015
 *
 */
public class Professor {

	private Float salary = 0.0f;
	private Integer yearsAtUF = 0;

	private Integer graduateClasses = 0;
	private Integer effortValue = 0;
	// Changing these values changes the weights of years at UF and classes
	// taught.
	// Useful for fine-tuning.
	private Integer yearsAtUFValue = 1;
	private Integer graduateCoursesTaughtValue = 2;
	private Integer underGraduateCoursesTaughtValue = 1;
	private Integer undergraduateClasses = 0;
	private String gender = "U";
	private String department = "UNKNOWN";
	private String name = "UNDEFINED";
	private Integer salaryRatio = 0;

	/**
	 * Creates a new instance of a <code>Professor</code> object.
	 * 
	 * @param data
	 *            <code>String Array</code> of the data parsed from the input
	 *            csv file.
	 */
	Professor(String[] data) {
		this.name = data[0];
		this.gender = data[1];
		this.department = getDepartment(data[2]);
		this.salary = Float.parseFloat(data[3]);
		this.yearsAtUF = Integer.parseInt(data[4]);
		this.undergraduateClasses = Integer.parseInt(data[5]);
		this.graduateClasses = Integer.parseInt(data[6]);
		calcEffortValue();
		calcSalaryRatio();
	}

	/**
	 * Calculates the salary ratio of the professor.
	 */
	private void calcSalaryRatio() {
		if (this.effortValue != 0 && this.salary != 0) {
			this.salaryRatio = Math.round(Math.round(this.salary)
					/ this.effortValue);
		} else {
		}
	}

	/**
	 * Calculates the effort value of the professor.
	 */

	private void calcEffortValue() {
		this.effortValue = ((this.yearsAtUF * this.yearsAtUFValue)
				+ (this.undergraduateClasses * this.underGraduateCoursesTaughtValue) + (this.graduateClasses * this.graduateCoursesTaughtValue));
	}

	/**
	 * Maps the department codes used in the source CSV to the actual department
	 * names.
	 * 
	 * @param num
	 *            The value of the department field in the source CSV, as a
	 *            <code>String</code>
	 * @return <code>String</code> of the department name.
	 */
	private String getDepartment(String num) {
		String deptName = "UNDEFINED";
		switch (num) {
		case "1":
			deptName = "Food Science";
			break;
		case "2":
			deptName = "Forest";
			break;
		case "3":
			deptName = "Electrical";
			break;
		case "4":
			deptName = "Aerospace Engineering";
			break;
		case "5":
			deptName = "Law";
			break;
		case "6":
			deptName = "English";
			break;
		case "7":
			deptName = "Mathematics";
			break;
		case "8":
			deptName = "Physics";
			break;

		}
		return deptName;
	}

	/**
	 * Get the data for this professor as a <code>HashMap</code>
	 * 
	 * @return <code>HashMap</code> representation of the professor attributes.
	 */
	public HashMap<String, String> getData() {
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("name", this.name);
		data.put("gender", this.gender);
		data.put("department", this.department);
		data.put("salary", this.salary.toString());
		data.put("years", this.yearsAtUF.toString());
		data.put("ugcourses", this.undergraduateClasses.toString());
		data.put("gcourses", this.graduateClasses.toString());
		data.put("effort", this.effortValue.toString());
		data.put("ratio", this.salaryRatio.toString());

		return data;
	}
}
