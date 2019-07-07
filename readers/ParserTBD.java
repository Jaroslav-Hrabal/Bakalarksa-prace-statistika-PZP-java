package readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Contains methods for parsing TBD head files. Methods read data files, parse
 * these data and return them as objects.
 * 
 * @author Jaroslav Hrabal
 *
 */

public class ParserTBD {

	public ParserTBD() {

	}

	/**
	 * This method reads the group file returning a Hashmap of String values
	 * describing to which group every well belongs.
	 * 
	 * @param Address
	 *            Address of the file.
	 * @return wells HashMap(String - well, String - group name)
	 * @throws IOException
	 */
	public HashMap<String, String> parseGroups(String Address)
			throws IOException {
		HashMap<String, String> wells = new HashMap<String, String>();

		BufferedReader br = new BufferedReader(new FileReader(Address));
		String groupName = null;
		String line = br.readLine();
		while (line != null) {
			String[] check = line.split("\\s+");
			if (isInteger(check[0])) {
				groupName = check[1];

			} else {
				wells.put(line, groupName);
			}
			line = br.readLine();
		}
		br.close();

		return wells;
	}

	/**
	 * Group names start with a number, this method checks, if the string starts
	 * with a number or not.
	 * 
	 * @param s
	 *            - string to be checked.
	 * @return true - first symbol is a number / false - first symbol is a
	 *         letter.
	 */
	private boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
