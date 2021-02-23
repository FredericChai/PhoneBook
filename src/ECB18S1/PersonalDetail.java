package ECB18S1;

/**
 * 
 * This class about person with setter and getter methods for five fields: name, birthday, address, email, phone.
 * 
 * @author chaizhizhi
 *
 */
public class PersonalDetail {

	private String name;
	private String birthday;
	private String address;
	private String email;
	private String phone;

	/**
	 * 
	 * Constructor
	 * 
	 */
	public PersonalDetail() {
		name = null;
		birthday = null;
		address = null;
		email = null;
		phone = null;
	}

	/**
	 * Save the given name.
	 * 
	 * @param name
	 *            Name
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * Save the given birthday.
	 * 
	 * @param birthday
	 *            Birthday
	 * 
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * Save the given address
	 * 
	 * @param address
	 *            Address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Save the given Email
	 * 
	 * @param email
	 *            Email
	 * 
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Save the given phone
	 * 
	 * @param phone
	 *            Phone number
	 *
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Read the Name
	 * 
	 * @return String person's name
	 *
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Read the birthday
	 * 
	 * @return String person's birthday
	 */
	public String getBirthday() {
		return this.birthday;
	}

	/**
	 * Read the address
	 * 
	 * @return String -person's address
	 */
	public String getaddress() {
		return this.address;
	}

	/**
	 * Read the Email
	 * 
	 * @return String -person's email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Read the phone
	 * 
	 * @return String -person's phone
	 */
	public String getPhone() {
		return this.phone;
	}

}
