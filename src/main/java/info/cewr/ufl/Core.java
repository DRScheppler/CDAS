package info.cewr.ufl;

import info.cewr.util.file.CSVFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Core {

	private String confFileLocation = "conf.txt";
	private List<String> deptList;

	public static void main(String[] args) {
		ArrayList<Professor> professorList = new ArrayList<Professor>();
		ArrayList<String> deptList = new ArrayList<String>();
		HashMap<String, Department> departments = new HashMap<String, Department>();
		CSVFile dataFile = new CSVFile(
				"src/main/resources/SalaryDataByGenderDepartmentTenureCourses.txt");
		Iterator<String[]> dataIterator = dataFile.getFileData().iterator();

		while (dataIterator.hasNext()) {
			Professor professor = new Professor(dataIterator.next());
			professorList.add(professor);

		}
		// try {
		// processProfessors(professorList);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		Iterator<Professor> professorIterator = professorList.iterator();

		while (professorIterator.hasNext()) {
			Professor current = professorIterator.next();
			String department = current.getData().get("department");
			if (deptList.contains(department) == false) {
				deptList.add(department);
				Department dept = new Department(department);
				departments.put(department, dept);
				departments.get(department).addProfessor(current);
			} else {
				departments.get(department).addProfessor(current);
			}
		}
		
		Iterator<String> deptIterator = deptList.iterator();

	}

	private static void processProfessors(ArrayList<Professor> professorList)
			throws Exception {
		Iterator<Professor> professorIterator = professorList.iterator();
		File outFile = new File(
				"output/professorListWithEffortValueAndEffortSalaryRatio.csv");
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		FileOutputStream outStream = new FileOutputStream(outFile);
		OutputStreamWriter writer = new OutputStreamWriter(outStream);
		writer.write("Name,Gender,Department,Salary,Years,Undergrad,Grad,Effort,Effort/Salary"
				+ System.getProperty("line.separator"));
		while (professorIterator.hasNext()) {
			Professor current = professorIterator.next();
			HashMap<String, String> currentData = new HashMap<String, String>();
			currentData = current.getData();
			String output = (currentData.get("name") + ","
					+ currentData.get("gender") + ","
					+ currentData.get("department") + ","
					+ currentData.get("salary") + ","
					+ currentData.get("years") + ","
					+ currentData.get("ugcourses") + ","
					+ currentData.get("gcourses") + ","
					+ currentData.get("effort") + ","
					+ currentData.get("ratio") + System
					.getProperty("line.separator"));
			writer.write(output);
		}
		writer.close();
	}

}
