package view;

import java.io.File;

public class WriteDao {
	
	private File path;
	
	public WriteDao(){		
		 path = new File("data/config.xml");
	}
	
	private String xml = "<config><filepath url='' /><item></item></config>";
	
	public void cateXmlFile(){
		
	}
	

}
