package common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringTool {
	static char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	
	
	static public String hash_MD5(String s){
		MessageDigest mdInst=null;
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mdInst.update(s.getBytes());
		byte[] bytes=mdInst.digest();
		
		int n=bytes.length * 2;
		char str[] = new char[n];
        for (int i=0, j=0;i<bytes.length;i++) {
            byte b = bytes[i];
            str[j++] = hexDigits[(b>>>4)&0xf];
            str[j++] = hexDigits[b&0xf];
        }
        return new String(str);
	}
}
