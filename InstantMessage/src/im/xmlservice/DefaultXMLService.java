package im.xmlservice;

import java.util.List;

public class DefaultXMLService implements XMLService {

	@Override
	public boolean createNewEntry(String username, String password) {
		// TODO Auto-generated method stub

		// TODO re-laod the XMLFile
		return false;
	}

	@Override
	public boolean allowLogin(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<XMLModel> preLoadXMLFile() {
		// TODO Auto-generated method stub
		return null;
	}

}
