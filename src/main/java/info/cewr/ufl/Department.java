package info.cewr.ufl;

import java.util.ArrayList;

public class Department {

	private String deptName = "UNDEFINED";
	private ArrayList<Professor> professorList = new ArrayList<Professor>();

	Department(String name) {
		this.deptName = name;
	}

	public void addProfessor(Professor professor) {

		professorList.add(professor);

	}

}
