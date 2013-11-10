package im.mainclass;

import im.encryptionservice.EncryptionService;
import im.front.gui.LoginGui;

public class Run {
	public static void main(String[] args)
	{
		LoginGui login = new LoginGui();
		login.Login();
		EncryptionService es = new EncryptionService();
		es.testEncrypt("测试下中文看看，诶哟卧槽，还真能打中文？！fuck！！！牛逼！！！");
	}
}
