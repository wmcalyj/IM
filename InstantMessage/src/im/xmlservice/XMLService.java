package im.xmlservice;

import java.util.List;

public interface XMLService {
	/**
	 * return true if successfully create a new entry
	 * 
	 * @param username
	 *            {@link String} username
	 * @param password
	 *            {@link String} password hash (not originally password)
	 * @return {@link Boolean} true if success, otherwise, false
	 */
	public boolean createNewEntry(String username, String password);

	/**
	 * return true if the username and password is correct, otherwise, false
	 * 
	 * @param username
	 *            {@link String} user name
	 * @param password
	 *            {@link String} password
	 * @return {@link Boolean} true if allow the user to login, otherwise, false
	 */
	public boolean allowLogin(String username, String password);

	/**
	 * 
	 * @return List<{@link XMLModel}> pre-load the XML file. Unless the file is
	 *         modified.
	 */
	public List<XMLModel> preLoadXMLFile();
}
