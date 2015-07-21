package common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regular {
	
	private Regular(){
		
	}
	
	public static String matchFirst(String str,String reg){
		Pattern pattern=Pattern.compile(reg);
		Matcher matcher=pattern.matcher(str);
		if(matcher.find()) return matcher.group();
		return null;
	}
	
	public static String[] match(String str,String reg){
		Pattern pattern=Pattern.compile(reg);
		Matcher matcher=pattern.matcher(str);
		List<String> list = new ArrayList<String>();
		
		while(matcher.find()){
			list.add( matcher.group() );
		}
		
		String[] res = new String[list.size()];
		
		return list.toArray(res);
	}
	
	public static String removeAll(String str,String reg){
		return str.replaceAll(reg, "");
	}
	
}
