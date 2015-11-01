package NotCrowdingOutRuoshi;

import java.io.File;
import java.net.URISyntaxException;

import NotCrowdingOutRuoshi.Views.MainWindow;

public class App {
	public static String ROOT_PATH;
	public static String RESOURCE_ROOT_PATH;

	public static void main(String[] args) {
		try {
			ROOT_PATH = App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().substring(1);
			RESOURCE_ROOT_PATH = (new File(ROOT_PATH)).getParent() + "/resources";
			//RESOURCE_ROOT_PATH = ROOT_PATH + "/resources";
		} catch (URISyntaxException e) {
			System.exit(1);
		}
		(new MainWindow()).setVisible(true);
	}
}
