package ECB18S1;

import java.util.*;
import java.io.*;

/**
 * 
 * This class will generate a phone book list to store all personal information.
 * 
 * @author chaizhizhi
 *
 */
public class PhoneBook {

	/**
	 * The static fields record is used to store information from phone book file.
	 */
	private ArrayList<PersonalDetail> record;

	/**
	 * Constructor
	 * 
	 */
	public PhoneBook() {
		record = new ArrayList<PersonalDetail>();
	} 

	/**
	 * Read the phone book list
	 * 
	 * @return record The Contact list
	 */
	public ArrayList<PersonalDetail> getRecord() {
		return this.record;
	}

	/**
	 * This method can convert each person's information into the list
	 * 
	 * @param path
	 *            The path of phone book file
	 *
	 */
	public void parse(String path) {
		PersonalDetail person = new PersonalDetail();
		File file = new File(path);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bufr = new BufferedReader(fr);
			// Use string variable currentLine to store the string of current line.
			String currentLine = null;
			String previousLine = null;
			// Use the string variable to store first word of current line and previous line
			String currentLineFirstWord = null;
			String previousLineFirstWord = "";
			// Read personal information.
			while (true) {
				// End of the iteration
				if ((currentLine = bufr.readLine()) == null) {
					if (FormatPerson.CheckPersonalDetail(person))
						record.add(person);
					person = new PersonalDetail();
					break;
				} else {
					if (currentLine.isEmpty()) {
						// when find a empty line, check this person's format and add it to the list
						if (FormatPerson.CheckPersonalDetail(person))
							record.add(person);
						person = new PersonalDetail();
					} else {
						currentLine = currentLine.trim() + " ";
						// Read the first word of every line
						int currentLineFlag = 0;
						int previousLineFlag = 7;
						currentLineFlag = currentLine.indexOf(' ');
						currentLineFirstWord = currentLine.substring(0, currentLineFlag);
						// If the first word of current line is those words : name, birthday, address,
						// phone, email. Then save corresponding information in the person object.
						if (currentLineFirstWord.equalsIgnoreCase("Name")
								|| currentLineFirstWord.equalsIgnoreCase("Birthday")
								|| currentLineFirstWord.equalsIgnoreCase("Address")
								|| currentLineFirstWord.equalsIgnoreCase("Email")
								|| currentLineFirstWord.equalsIgnoreCase("Phone")) {
							if (currentLineFirstWord.equalsIgnoreCase("Name"))
								person.setName(currentLine.substring(currentLineFlag).trim());
							if (currentLineFirstWord.equalsIgnoreCase("Birthday"))
								person.setBirthday(currentLine.substring(currentLineFlag).trim());
							if (currentLineFirstWord.equalsIgnoreCase("Email"))
								person.setEmail(currentLine.substring(currentLineFlag).trim());
							if (currentLineFirstWord.equalsIgnoreCase("Address"))
								person.setAddress(currentLine.substring(currentLineFlag).trim());
							if (currentLineFirstWord.equalsIgnoreCase("Phone"))
								person.setPhone(currentLine.substring(currentLineFlag).trim());

							previousLineFirstWord = currentLineFirstWord;
						}

						// If the first word of current line is not these words: name, birthday...bring
						// current line information to the end of the previous line of instructions and
						// save them
						else {
							if (previousLineFirstWord.equalsIgnoreCase("Name"))
								person.setName(
										previousLine.substring(previousLineFlag).trim() + " " + currentLine.trim());
							else if (previousLineFirstWord.equalsIgnoreCase("Birthday"))
								person.setBirthday(
										previousLine.substring(previousLineFlag).trim() + " " + currentLine.trim());
							else if (previousLineFirstWord.equalsIgnoreCase("Email"))
								person.setEmail(
										previousLine.substring(previousLineFlag).trim() + " " + currentLine.trim());
							else if (previousLineFirstWord.equalsIgnoreCase("Address"))
								person.setAddress(person.getaddress() + " " + currentLine.trim());
							else if (previousLineFirstWord.equalsIgnoreCase("Phone"))
								person.setPhone(previousLine.substring(previousLineFlag).trim());
						}
						previousLine = currentLine;
						previousLineFlag = currentLineFlag;

					}
				}
			}

			fr.close();
			bufr.close();
		} catch (Exception e) {
			System.out.println("Cannot find contact file");
		}
	}

}
