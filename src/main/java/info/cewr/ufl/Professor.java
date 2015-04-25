package info.cewr.ufl;

import java.util.HashMap;

public class Professor {

	private Float salary = 0.0f;
	private Integer yearsAtUF = 0;
	private Integer undergraduateClasses = 0;
	private Integer graduateClasses = 0;
	private Integer effortValue = 0;
	private Integer yearsAtUFValue = 1;
	private Integer graduateCoursesTaughtValue = 2;
	private String gender = "U";
	private String department = "UNKNOWN";
	private String name = "UNDEFINED";
	private Integer salaryRatio = 0;

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

	private void calcSalaryRatio() {
		if (this.effortValue != 0 && this.salary != 0) {
			this.salaryRatio = Math.round(Math.round(this.salary)
					/ this.effortValue);
		} else {
		}
	}

	private void calcEffortValue() {
		this.effortValue = ((this.yearsAtUF * this.yearsAtUFValue)
				+ (this.undergraduateClasses * this.graduateCoursesTaughtValue) + (this.graduateClasses * this.graduateCoursesTaughtValue));
	}

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


	public HashMap<String, String> getData() {
		HashMap<String, String> data = new HashMap<String,String>();
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
