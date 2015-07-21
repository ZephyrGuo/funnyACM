package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	private static String root_path;

	private static void init(){
		if(root_path!=null) return;
		File f = new File(Configuration.class.getResource("").getPath());
		root_path=Regular.matchFirst(f.getPath(), ".*WEB-INF")+"/";
		root_path=root_path.replaceAll("%20", " ");
		//root_path="D:\\java web\\funnyACM\\WebContent\\WEB-INF\\";
	}
	
	private static Properties load(FileInputStream file){
		Properties f = new Properties();
		try {
			f.load(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return f;
	}
	
	public static Properties load(String path){
		init();
		path=root_path+path;
		try {
			return Configuration.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
