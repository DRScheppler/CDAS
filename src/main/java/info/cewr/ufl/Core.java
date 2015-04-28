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


/**
 * A very good friend of mine came to me and asked me to help her process some data for a paper.
 * This code isn't the cleanest ever, and to be frank I don't have a lot of time to polish it.
 * However it's important I get some documentation in here, so there you have it.
 * 
 * @author <a href="drscise@gmail.com">Dale Scheppler</a> 2015
 *
 */
public class Core {
	// TODO: Make the input file location and other application configs come
	// from here.
	@SuppressWarnings("unused")
	private String confFileLocation = "conf.txt";
	@SuppressWarnings("unused")
	private List<String> deptList;

	public static void main(String[] args) {
		ArrayList<Professor> professorList = new ArrayList<Professor>();
		ArrayList<String> deptList = new ArrayList<String>();
		HashMap<String, Department> departments = new HashMap<String, Department>();
		// TODO: Make configurable, also paths are relative to project
		// structure.
		CSVFile dataFile = new CSVFile(
				"src/main/resources/SalaryDataByGenderDepartmentTenureCourses.txt");
		Iterator<String[]> dataIterator = dataFile.getFileData().iterator();

		while (dataIterator.hasNext()) {
			Professor professor = new Professor(dataIterator.next());
			professorList.add(professor);

		}
		// Disabled because I didn't want to keep overwriting my output files.
		// TODO: Make this part not run if the input file isn't defined. Or
		// something.
		// try {
		// processProfessors(professorList);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// A list of a custom object at 2AM? You bet.
		Iterator<Professor> professorIterator = professorList.iterator();

		// Alright, for each professor
		while (professorIterator.hasNext()) {
			Professor current = professorIterator.next();
			// Get the department name
			String department = current.getData().get("department");
			// Have we seen this department before?
			// If not, create it
			if (deptList.contains(department) == false) {
				deptList.add(department);
				Department dept = new Department(department);
				departments.put(department, dept);
				departments.get(department).addProfessor(current);
			} else {
				departments.get(department).addProfessor(current);
			}
		}

		// Now iterate through that department list
		Iterator<String> deptIterator = deptList.iterator();
		DecimalFormat f = new DecimalFormat("##.00");
		while (deptIterator.hasNext()) {
			String curDeptName = deptIterator.next();
			Department curDept = departments.get(curDeptName);
			// Get the professor list from that department
			ArrayList<Professor> curProfList = new ArrayList<Professor>(
					curDept.getProfessorList());
			// And for each of those professors..
			Iterator<Professor> profIterator = curProfList.iterator();
			while (profIterator.hasNext()) {
				// TODO: Dale don't do stuff like this!
				// This chunk is a mess and needs refactored, basically we're
				// finding each professor in the department
				// within 10% of each other for "effort value" and then figuring
				// out the ratio of their effort value
				// to their salary.
				System.out.println("Name,Gender,Department,Effort,Effort/Salary,Effort Difference,Salary Difference");
				Professor curProf = profIterator.next();
				HashMap<String, String> curProfData = curProf.getData();
				Integer curProfEffort = Integer.parseInt(curProfData
						.get("effort"));
				Integer curProfRatio = Integer.parseInt(curProfData
						.get("ratio"));
				Integer floorEffort = curProfEffort - (curProfEffort / 10);
				Integer ceilEffort = curProfEffort + (curProfEffort / 10);
				// Some debug code, need to get the logger working..
				// TODO: Add a logger. Log4j
				// System.out.println("Professor " + curProfData.get("name") +
				// " has an effort value of: " + curProfEffort +
				// ". And a range of " + floorEffort + " to " + ceilEffort
				// +".");
				System.out.println(curProfData.get("name") + ","
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
							// More debug code
							// TODO: Add logger
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

	/**
	 * Processes an ArrayList of Professor objects and dumps to a CSV file.
	 * 
	 * @param professorList
	 *            The <code>ArrayList</code> of <code>Professor</code> objects
	 *            you wish to process.
	 * @throws Exception
	 *             Lazymode error handling!
	 */
	@SuppressWarnings("unused")
	private static void processProfessors(ArrayList<Professor> professorList)
			throws Exception {
		// TODO: Refactor the heck out of this. Perhaps even merge it into the
		// CSVUtils package.
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
