package info.cewr.util.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PDFData {

	private static final String fileLocation = "C:\\Users\\Leonassan\\Desktop\\MariaProject\\Raw Data Sources\\v-14_salaries2.txt";
	private static Integer cycle = 0;
	private static String department = "";
	private static String subDepartment = "";

	public static void main(String[] args) {

		try {
			read(fileLocation);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void read(String fileLocation) throws IOException,
			FileNotFoundException {

		BufferedReader input = new BufferedReader(new FileReader(fileLocation));
		String line;
		while ((line = input.readLine()) != null) {
			line = stripJunk(line);
			line = sortDataFromDepartmentInfo(line);
			line = findProfessors(line);
			if (line.length() > 1) {
				System.out.println(line);
			}
		}
		input.close();

	}

	public static String stripJunk(String input) {
		if (input.contains("UNIVERSITY OF FLORIDA")
				|| input.contains("FALL 2014 UNIVERSITY EMPLOYEE FILE")
				|| input.contains("DETAIL OF SALARIES AS OF OCTOBER 23, 2014")
				|| input.contains("ALL FUND SOURCES")
				|| input.contains("------------------------------------------------------------------------------------------------------------------------------------")
				|| input.contains("           NAME                       JOB                BGT       CURRENT")
				|| input.contains("                                     TITLE               FTE        RATE")
				|| input.contains("DEPT     TOTAL")
				|| input.contains("COLLEGE  TOTAL")
				|| input.contains("GRAND    TOTAL")) {
			input = "";
		}
		int firstPass = 0;
		while (input.contains("  ") || input.contains(";;")) {
			if (firstPass == 0) {
				firstPass = 1;
				input = input.replace("  ", ";");
			}
			input = input.replace(";;", ";");
		}

		if (input.contains("; ")) {
			input = input.replace("; ", ";");
		}

		if (input.contains(",") && input.length() < 40
				&& !input.contains("PROF")) {
			input.replace(",", "");
		}


		return input.trim();
	}

	public static String sortDataFromDepartmentInfo(String input) {
		if (input.length() < 2) {
			return "".trim();
		}
		if (!input.contains(".")) {
			sortDepartmentsFromSubdepartments(input);
			input = "";
		} else {
			input = department + ";" + subDepartment + ";" + input;
		}
		return stripJunk(input.trim());

	}

	public static void sortDepartmentsFromSubdepartments(String input) {
		if (cycle == 0) {
			department = input;
			cycle = 1;
		} else {
			subDepartment = input;
			cycle = 0;
		}

	}

	public static String findProfessors(String input) {
		String[] tokens = null;
		if (input.length() > 1) {
			tokens = input.split(";");
			if (!tokens[4].contains("PROF") || !tokens[5].contains("1.00")) {
				input = "";
			}
		}


		return input.trim().replaceFirst(";", "");
	}

}
