package info.cewr.ufl;

import info.cewr.util.file.CSVFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
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
		DecimalFormat f = new DecimalFormat("##.00");
		while (deptIterator.hasNext()) {
			String curDeptName = deptIterator.next();
			Department curDept = departments.get(curDeptName);
			ArrayList<Professor> curProfList = new ArrayList<Professor>(
					curDept.getProfessorList());
			Iterator<Professor> profIterator = curProfList.iterator();
			while (profIterator.hasNext()) {

				System.out
						.println("Name,Gender,Department,Effort,Effort/Salary,Effort Difference,Salary Difference");
				Professor curProf = profIterator.next();
				HashMap<String, String> curProfData = curProf.getData();
				Integer curProfEffort = Integer.parseInt(curProfData
						.get("effort"));
				Integer curProfRatio = Integer.parseInt(curProfData
						.get("ratio"));
				Integer floorEffort = curProfEffort - (curProfEffort / 10);
				Integer ceilEffort = curProfEffort + (curProfEffort / 10);
				// System.out.println("Professor " + curProfData.get("name") +
				// " has an effort value of: " + curProfEffort +
				// ". And a range of " + floorEffort + " to " + ceilEffort
				// +".");
				System.out.println(curProfData.get("name") +","
						+ curProfData.get("gender") + ","
						+ curProfData.get("department") + ","
						+ curProfData.get("effort") + ","
						+ curProfData.get("ratio") + ",");
				for (int i = floorEffort; i <= ceilEffort; i++) {
					for (Professor nowProf : curProfList) {
						HashMap<String, String> nowProfData = nowProf.getData();
						Float nowProfEffort = Float.parseFloat(nowProfData
								.get("effort"));
						Float nowProfRatio = Float.parseFloat(nowProfData
								.get("ratio"));
						if (Integer.parseInt(nowProf.getData().get("effort")) == i
								&& nowProfData.get("name") != curProfData
										.get("name") && nowProfEffort != 0) {
							Float effortValueDifference = ((nowProfEffort - curProfEffort) / curProfEffort) * 100;
							Float ratioDifference = ((nowProfRatio - curProfRatio) / curProfRatio) * 100;
							// System.out.println(nowProfData.get("name") +
							// " has a " + f.format(effortValueDifference) +
							// "% difference in effort value.");
							System.out.println(nowProfData.get("name") + ","
									+ nowProfData.get("gender") + ","
									+ nowProfData.get("department") + ","
									+ nowProfData.get("effort") + ","
									+ nowProfData.get("ratio") + ","
									+ f.format(effortValueDifference) + "%,"
									+ f.format(ratioDifference) + "%");
									
									
						}

					}

				}

			}

		}

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
