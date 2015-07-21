package common;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;



public class Http {
	
	private static final int timeout = 5000;
	
	
	private Http(){
		
	}
		
	public static Response post(String url,String[] params,Map<String,String> cookies){
		Response res=null;
		try {
			if(cookies!=null && params!=null){
				res = Jsoup.connect(url)
				.data(params)
				.cookies(cookies)
				.method(Method.POST)
				.timeout(timeout)
				.execute();
			}else if(params!=null){
				res = Jsoup.connect(url)
				.data(params)
				.method(Method.POST)
				.timeout(timeout)
				.execute();
			}else if(cookies!=null){
				res = Jsoup.connect(url)
					.cookies(cookies)
					.method(Method.POST)
					.timeout(timeout)
					.execute();
			}else{
				res = Jsoup.connect(url)
					.method(Method.POST)
					.timeout(timeout)
					.execute();
			}
		} catch (IOException e) {
			System.out.println("post["+url+"] error:"+e.toString());
			e.printStackTrace();
			return null;
		}
		return res;
	}
	
	public static Response get(String url,String[] params,Map<String,String> cookies){
		Response res=null;		
		try {
			if(cookies!=null && params!=null){
				res = Jsoup.connect(url)
				.data(params)
				.cookies(cookies)
				.method(Method.GET)
				.timeout(timeout)
				.execute();
			}else if(params!=null){
				res = Jsoup.connect(url)
				.data(params)
				.method(Method.GET)
				.timeout(timeout)
				.execute();
			}else if(cookies!=null){
				res = Jsoup.connect(url)
					.cookies(cookies)
					.method(Method.GET)
					.timeout(timeout)
					.execute();
			}else{
				res = Jsoup.connect(url)
					.method(Method.GET)
					.timeout(timeout)
					.execute();
			}
		} catch (IOException e) {
			System.out.println("get["+url+"] error:"+e.toString());
			e.printStackTrace();
			return null;
		}
		return res;
	}
}
