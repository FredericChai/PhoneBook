package ECB18S1;



/**
 * 
 * This class contains the main method. It read the path of phone book file, instruction file, output file, report file
 * from command line.
 * 
 * @author chaizhizhi
 *
 */
public class ECB {
	public static void main(String[] args) {
		String contact = args[0];
		String instructions = args[1];
		String output = args[2];
		String report = args[3];
		PhoneBook phonebook = new PhoneBook();
		phonebook.parse(contact);
		ContactProcessor processor = new ContactProcessor(phonebook.getRecord());
		processor.readInstruction(instructions, output, report);
	}
}
