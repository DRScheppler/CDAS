package info.cewr.util.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for parsing CSV formatted data files.
 * 
 * @author Dale Scheppler
 *
 */
public class CSVFile {

	private List<String[]> entries = new ArrayList<String[]>();

	public CSVFile(String fileLocation) {
		String line = null;
		try {
			BufferedReader inputReader = new BufferedReader(new FileReader(
					fileLocation));
			while ((line = inputReader.readLine()) != null) {
				parseLine(line);
			}
			inputReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void parseLine(String line) {
		String[] entry;
		entry = line.split(",");
		entries.add(entry);
	}

	/**
	 * 
	 * 
	 * Returns an ArrayList of the data in the CSV file, with each entry being
	 * an array of the values.
	 * 
	 * @return <code>ArrayList</code> of the data in the CSV file.
	 */
	public List<String[]> getFileData() {
		return this.entries;
	}

}
