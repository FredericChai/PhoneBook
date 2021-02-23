package ECB18S1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * 
 * This class achieves all manipulation functions: add, delete, save and query.
 * 
 * @author chaizhizhi
 *
 */
public class ContactProcessor {
	// The variable record is used to store information from phone book file.
	private ArrayList<PersonalDetail> record;
	// QueryRecord is used to store results from query methods.
	private ArrayList<PersonalDetail> queryRecord;

	public ContactProcessor(ArrayList<PersonalDetail> record) {
		this.record = record;
		queryRecord = new ArrayList<PersonalDetail>();
	}

	/**
	 * This methods was used to read instructions from instructions file. Then
	 * select which instructions should be taken ,such as add, delete, query and
	 * save.
	 * 
	 * @param instructionPath
	 *            The path of instruction file
	 * @param outputPath
	 *            The path of output file
	 * @param reportPath
	 *            The path of report file
	 */
	public void readInstruction(String instructionPath, String outputPath, String reportPath) {
		File file = new File(instructionPath);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bufr = new BufferedReader(fr);
			String line = null;
			String instructionWord = null;

			while (true) {
				if ((line = bufr.readLine()) == null) {
					break;
				} else {
					// read every line from instruction file
					line = line.trim();
					if (!line.isEmpty()) {
						// Use the instructionWord variables to store first three letter
						instructionWord = line.substring(0, 3);
						if (instructionWord.equalsIgnoreCase("add"))
							add(line.substring(line.indexOf(' ')));
						if (instructionWord.equalsIgnoreCase("del"))
							delete(line.substring(line.indexOf(' ')));
						if (instructionWord.equalsIgnoreCase("que"))
							query(line.substring(line.indexOf(' ')));
						if (instructionWord.equalsIgnoreCase("sav"))
							save(outputPath);
					}
				}

			}

			fr.close();
			bufr.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot find instruction file or something error");
		}
	}

	/**
	 * 
	 * Add person's record into the list.
	 * 
	 * @param information
	 *            Person's information such as name, birthday from instruction file
	 */
	private void add(String information) {
		// Add a '; *' to avoid the string index out of bound
		information = information.trim() + "; *";
		int flag = 0;
		// Use string array personInfo[] contain person's information
		// :[0]name,[1]birthday,[2]phone,[3]email,[4]address
		String personInfo[] = new String[5];
		// Use string[] personInfo to store information from add instruction
		while (true) {
			// Put the flag at the first space of the new string
			flag = information.indexOf(' ');
			// Save the substring into string array variable: personInfo.
			if (information.substring(0, flag).equalsIgnoreCase("name"))
				personInfo[0] = information.substring(flag, information.indexOf(';')).trim();
			if (information.substring(0, flag).equalsIgnoreCase("birthday"))
				personInfo[1] = information.substring(flag, information.indexOf(';')).trim();
			if (information.substring(0, flag).equalsIgnoreCase("phone"))
				personInfo[2] = information.substring(flag, information.indexOf(';')).trim();
			if (information.substring(0, flag).equalsIgnoreCase("email"))
				personInfo[3] = information.substring(flag, information.indexOf(';')).trim();
			if (information.substring(0, flag).equalsIgnoreCase("address"))
				personInfo[4] = information.substring(flag, information.indexOf(';')).trim();
			// Get rid of the head of instruction : the word "add".
			information = information.substring(information.indexOf(';') + 1).trim();
			// Exit of the iteration
			if (information.equals("*"))
				break;
		}
		PersonalDetail person = new PersonalDetail();
		if (personInfo[0] != null && personInfo[1] != null) {
			boolean b = true;
			for (int i = 0; i < record.size(); i++) {
				// If list already have the person with same name and birthday, update the
				// existing information
				if (personInfo[0].equalsIgnoreCase(record.get(i).getName())
						&& personInfo[1].equalsIgnoreCase(record.get(i).getBirthday())) {// if they are the same
					if (personInfo[2] != null && FormatPerson.hasValidPhone(personInfo[2]))
						record.get(i).setPhone(personInfo[2]);
					if (personInfo[3] != null && FormatPerson.hasValidEmail(personInfo[3])) {
						record.get(i).setEmail(personInfo[3]);
					}
					if (personInfo[4] != null)
						record.get(i).setAddress(personInfo[4]);
					// Use variable b to tell whether the person are the same one
					b = false;
				}
			}
			// if cannot find such person, add a new person into the list
			if (b) {
				person.setName(personInfo[0]);
				person.setBirthday(personInfo[1]);
				person.setPhone(personInfo[2]);
				person.setEmail(personInfo[3]);
				person.setAddress(personInfo[4]);
				if (FormatPerson.CheckPersonalDetail(person)) {
					record.add(person);
				}
			}
		}
		person = new PersonalDetail();
	}

	/**
	 * 
	 * Delete person's record according to instructions from instructions file. It
	 * also consider some other Exceptional case: 1 detele person according to its
	 * name or birthday 2 some times there may have many persons with same name but
	 * different birthday.
	 * 
	 * @param information
	 *            Personal information like phone, name from instruction file
	 * 
	 */
	private void delete(String information) {
		information = information.trim();
		// Use the flag to store the index of list
		int flag = 0;
		// When there has both birthday and name
		if (information.matches("^[a-zA-Z]+[ a-zA-Z]+\\;[ 0-9]+\\-[0-9]+\\-[0-9]{4}")) {
			String name = information.substring(0, information.indexOf(';')).trim();
			String birth = information.substring(information.indexOf(';') + 1).trim();
			for (flag = 0; flag < record.size(); flag++) {
				// If the person's name matches the instructions
				if (name.equalsIgnoreCase(record.get(flag).getName())) {
					String[] dateA = birth.split("-");
					String[] dateB = record.get(flag).getBirthday().split("-");
					boolean b = false;
					// check the date of birthday and format the birthday before check
					for (int i = 0; i < dateB.length; i++) {
						if (Integer.parseInt(dateA[i]) != Integer.parseInt(dateB[i])) {
							b = false;
							break;
						}
						b = true;
					}
					if (b) { // if name was the same ,remove it
						record.remove(flag);
						break;
					}

				}
			}

		}
		// When there only has name information
		if (information.matches("^[a-zA-Z]+[ a-zA-Z]+")) {
			String name = information;
			for (flag = 0; flag < record.size(); flag++) {
				if (record.get(flag).getName().equalsIgnoreCase(name)) {
					record.remove(flag);
					flag--;
				}
			}
		}

	}

	/**
	 * 
	 * Query person information according to the name, birthday or phone. Output
	 * them by using querySave.() method. This method also consider: if there are
	 * many results, sort all results by name or birthday
	 * 
	 * @param information
	 *            Person's information like person's name, birthday from instruction file
	 * 
	 */
	private void query(String information) {
		information = information.trim();
		// Every time before querying the list, clean it
		queryRecord = new ArrayList<PersonalDetail>();
		// Body of the check instruction
		String queryContent = information.substring(information.indexOf(' ')).trim();
		String queryHead = information.substring(0, information.indexOf(' ')).trim();

		// Query the list according to name
		if (queryHead.equalsIgnoreCase("name")) {
			for (int i = 0; i < record.size(); i++) {
				if (queryContent.equalsIgnoreCase(record.get(i).getName())) {
					queryRecord.add(record.get(i));
				}
			}
			QuerySort.insertSort(queryRecord);
		}
		// query the list according to phone number
		if (queryHead.equalsIgnoreCase("phone")) {
			for (int i = 0; i < record.size(); i++) {
				if (queryContent.equalsIgnoreCase(record.get(i).getPhone())) {
					queryRecord.add(record.get(i));
				}
			}
			QuerySort.MSDsort(queryRecord);
		}
		if (queryHead.equalsIgnoreCase("birthday")) {
			for (int i = 0; i < record.size(); i++) {
				if (queryContent.equalsIgnoreCase(record.get(i).getBirthday())) {
					queryRecord.add(record.get(i));
				}
			}
			QuerySort.MSDsort(queryRecord);
		}

		querySave("report file.txt", queryHead, queryContent);
	}

	/**
	 * 
	 * Output all information from record list into output file
	 * 
	 * @param output
	 *            The path of output file
	 */
	private void save(String output) {

		File fileO = new File(output);
		try {
			FileWriter fwO = new FileWriter(fileO);
			BufferedWriter bufwO = new BufferedWriter(fwO);

			// Every iteration will output a person's information
			for (int i = 0; i < record.size(); i++) {
				bufwO.write("name: " + record.get(i).getName());
				bufwO.newLine();
				bufwO.write("birthday: " + record.get(i).getBirthday());
				bufwO.newLine();
				if (record.get(i).getaddress() != null) {
					bufwO.write("address: " + record.get(i).getaddress());
					bufwO.newLine();
				}
				if (record.get(i).getPhone() != null) {
					bufwO.write("phone: " + record.get(i).getPhone());
					bufwO.newLine();
				}
				if (record.get(i).getEmail() != null) {
					bufwO.write("email: " + record.get(i).getEmail());
					bufwO.newLine();
				}

				bufwO.newLine();
			}
			bufwO.close();
			fwO.close();
		} catch (Exception e) {
			System.out.println(" Invalid outport file path");
		}

	}

	/**
	 * 
	 * This method will output query result to the report file
	 * 
	 * @param report
	 *            The path of report file
	 * @param queryHead
	 *            The head of query instruction
	 * @param queryContent
	 *            The body of query instruction
	 * 
	 */
	private void querySave(String report, String queryHead, String queryContent) {
		File fileR = new File(report);
		try {
			FileWriter fwR = new FileWriter(fileR, true);
			BufferedWriter bufwR = new BufferedWriter(fwR);
			bufwR.write("====== query " + queryHead + " " + queryContent + " ======");
			bufwR.newLine();
			// Every iteration will output all of a person's information in the variable
			// queryRecord
			for (int i = 0; i < queryRecord.size(); i++) {

				bufwR.write("name: " + queryRecord.get(i).getName());
				bufwR.newLine();
				bufwR.write("birthday: " + queryRecord.get(i).getBirthday());
				bufwR.newLine();
				if (queryRecord.get(i).getaddress() != null) {
					bufwR.write("address: " + queryRecord.get(i).getaddress());
					bufwR.newLine();
				}
				if (queryRecord.get(i).getPhone() != null) {
					bufwR.write("phone: " + queryRecord.get(i).getPhone());
					bufwR.newLine();
				}
				if (queryRecord.get(i).getEmail() != null) {
					bufwR.write("email: " + queryRecord.get(i).getEmail());
					bufwR.newLine();
				}
				bufwR.newLine();
			}
			bufwR.write("====== end of query " + queryHead + " " + queryContent + " ======");
			bufwR.newLine();
			bufwR.newLine();
			bufwR.close();
			fwR.close();

		} catch (Exception e) {
			System.out.println(" Invalid report file path");
		}

	}

}
