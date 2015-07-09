package view;

import java.io.File;

public class Utils {
	
	public static File createDirectory(String path){
		File pathDirector = new File(path);
		if (!pathDirector.isDirectory() && !pathDirector.exists()) {
			pathDirector.mkdirs();
		}
		return pathDirector;
	}

}
