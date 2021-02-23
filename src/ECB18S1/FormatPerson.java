package ECB18S1;

/**
 * 
 * This class is a tool class to check the format of personal information.
 * 
 * @author chaizhizhi
 *
 */
public class FormatPerson {
	/**
	 * This method is the interface of this class. It will check whether the given
	 * person's information is valid.
	 * 
	 * @param person
	 *            The personal information list to check.
	 * @return Whether this person's information is valid.
	 */
	public static boolean CheckPersonalDetail(PersonalDetail person) {
		// Format this person's information at first
		FormatBirthday(person);
		FormatPhone(person);
		FormatEmail(person);
		FormatAddress(person);
		if (hasValidName(person.getName()) && hasValidBirthday(person)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the given name is valid.
	 * 
	 * @param name
	 *            Person's name to check
	 * @return Whether name is valid.
	 */
	public static boolean hasValidName(String name) {
		// Use regex to check whether person's name only contain English words
		if (name != null && name.matches("^[a-zA-Z ]+")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Check whether the given person has valid birthday information.
	 * 
	 * @param person
	 *            To check the birthday of this given person
	 * @return Whether birthday is valid
	 */
	public static boolean hasValidBirthday(PersonalDetail person) {
		if (person.getBirthday() == null) {
			return false;
		} else {
			// date[0]——day，date[1]——month,date[2]——years.
			String[] date = person.getBirthday().split("-");
			// Check whether all of birthday are digits.
			if (!date[0].matches("^[0-9]+") || !date[1].matches("^[0-9]+") || !date[2].matches("^[0-9]+")) {
				return false;
			}
			// Check whether there only have 30 days in April, June, September.
			if (date[1].equals("04") || date[1].equals("06") || date[1].equals("09") || date[1].equals("11")) {
				if (Integer.parseInt(date[0]) == 31) {
					return false;
				}
			}
			// Check whether year exceed 2018.
			if (Integer.parseInt(date[2]) > 2018) {
				return false;
			}
			// Check whether day and month are valid.
			if (Integer.parseInt(date[0]) > 31 || Integer.parseInt(date[1]) > 12) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * Check the format of given email.
	 * 
	 * @param email
	 *            check person's Email address.
	 * @return Whether the given email is valid.
	 */
	public static boolean hasValidEmail(String email) {
		if (!email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*$")) {
			return false;
		}
		return true;

	}

	/**
	 * 
	 * Check whether all letters of the given phone are digits.
	 * 
	 * @param phone
	 *            The String of phone to check.
	 * @return Whether the given phone is valid.
	 */
	public static boolean hasValidPhone(String phone) {
		if (!phone.matches("^[0-9]+")) {
			return false;
		}
		return true;
	}

	/**
	 * Check whether Email is invalid and if it is invalid, delete this person's
	 * email.
	 * 
	 * @param person
	 *            To check the Format of person's email
	 * 
	 */
	public static void FormatEmail(PersonalDetail person) {
		if (person.getEmail() != null) {
			if (hasValidEmail(person.getEmail())) {
			} else {
				person.setEmail(null);
			}
		}

	}

	/**
	 * 
	 * Format the given person's address. if the post code is wrong, delete this
	 * person's address.
	 * 
	 * @param person
	 *            Check the address of this person
	 */
	public static void FormatAddress(PersonalDetail person) {
		String[] address = person.getaddress().split(",");
		String Post = address[address.length - 1];
		Post = Post.substring(Post.length() - 4);
		if (!Post.matches("^[0-9]+")) {
			person.setAddress(null);
		}
	}

	/**
	 * 
	 * Format the given person's birthday as dd-mm-yyyy.
	 * 
	 * @param person
	 *            Format the person birthday information
	 * 
	 */
	public static void FormatBirthday(PersonalDetail person) {
		if (person.getBirthday() != null) {
			// Date[0]--day，Date[1]--month,Date[2]--year
			String[] date = person.getBirthday().split("-");
			if (date[0].length() < 2) {
				date[0] = "0" + date[0];
			}
			if (date[1].length() < 2) {
				date[1] = "0" + date[1];
			}
			person.setBirthday(date[0] + "-" + date[1] + "-" + date[2]);
		}
	}

	/**
	 * 
	 * Format the given person's phone number.
	 * 
	 * @param person
	 *            Personb's phone number
	 */
	public static void FormatPhone(PersonalDetail person) {
		String phone = person.getPhone();

		if (phone != null) {
			// If there is a meaningless zero in front of the phone number, delete it
			if (phone.charAt(0) == '0')
				phone = phone.substring(1);
			person.setPhone(phone);
			// Check whether all letters of the given string are digits, else delete phone
			if (!phone.matches("^[0-9]+")) {
				person.setPhone(null);
			}
		}
	}

}
