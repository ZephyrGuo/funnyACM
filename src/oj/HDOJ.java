package oj;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.Http;
import common.Regular;
import base.Account;
import base.JudgeRsp;
import base.OnlineJudge;
import base.ProblemRsp;
import base.SubmitReq;

public class HDOJ extends OnlineJudge {
	
	private final String loginUrl = "http://acm.hdu.edu.cn/userloginex.php?action=login"; 
	private final String submitUrl = "http://acm.hdu.edu.cn/submit.php?action=submit";
	private final String statusUrl = "http://acm.hdu.edu.cn/status.php";
	private final String problemUrl = "http://acm.hdu.edu.cn/showproblem.php";
	
	public HDOJ(){
		super("conf/HDOJ.properties");
	}

	@Override
	protected Map<String, String> login(Account account) {
		String params[] = { 
				"username", account.getAccount(), 
				"userpass",account.getPassword(),
				"login", "Sign In" 
			};	
			
		Response res=Http.post(loginUrl, params, null);
			
		if(res!=null)
			return res.cookies();
		
		return null;
	}

	@Override
	public boolean submit(SubmitReq sr) {
		String[] params={
				"check","0",
				"problemid",sr.getSubmit_params(),
				"language",hashLanguage(sr.getLanguage()),
				"usercode",sr.getCode()
			};
		Map<String,String> cookies = login(sr.getAccount());
		
		if(cookies==null){
			System.out.println("login HDOJ failed");
			return false;
		}
		
		if(Http.post(submitUrl,params,cookies)==null){
			System.out.println("submit HDOJ failed");
			return false;			
		}
		
		return true;
	}

	@Override
	public JudgeRsp takeResult(Account account) {
		
		String[] params={
			"first","",
			"pid","",
			"user",account.getAccount(),
			"lang","0",
			"status","0"
		};
		
		Response res=Http.get(statusUrl,params,null);
		Document doc = null;
		JudgeRsp rsp = new JudgeRsp();
		
		if(res==null){
			System.out.println("take HDOJ result failed");
			rsp.setReuslt(OnlineJudge.JE);
			return rsp;
		}
		
		try {
			doc = res.parse();
		} catch (IOException e) {
			e.printStackTrace();
			rsp.setReuslt(OnlineJudge.JE);
			return rsp;
		}
		
		Element tr = doc.select("#fixed_table").first().select("tr").get(1);
				
		rsp.setId(tr.child(0).text());
		rsp.setReuslt(tr.child(2).text());
		rsp.setRunTime(tr.child(4).text());
		rsp.setRunMemory(tr.child(5).text()+"B");
		rsp.setDetail("");
		
		if(rsp.getReuslt().equals("Compilation Error")){
			
			String[] rid = {"rid",rsp.getId()};
			res = Http.get("http://acm.hdu.edu.cn/viewerror.php", rid, null);
			
			if(res==null){
				System.out.println("take HDOJ result failed");
				rsp.setReuslt(OnlineJudge.JE);
				return rsp;
			}
			
			try {
				doc = res.parse();
			} catch (IOException e) {
				e.printStackTrace();
				rsp.setReuslt(OnlineJudge.JE);
				return rsp;
			}
			
			rsp.setDetail(doc.select("[cellSpacing=4]").select("td").first().text());
		}
		
		return rsp;
	}

	@Override
	public ProblemRsp linkProblem(String problemId) {
		String[] params={
				"pid",problemId
			};
		
			Response res=Http.get(problemUrl, params, null);
			
			if(res==null){
				System.out.println("HDOJ link problem failed");
				return null;
			}
			
			Document doc=null;
			try {
				doc=res.parse();
			} catch (IOException e) {				
				e.printStackTrace();
				return null;
			}
			
			ProblemRsp rsp = new ProblemRsp();
			Element e = doc.select("body > table").first().child(0).child(3);

			rsp.setTitle(e.select("h1").first().text());
			
			String text = e.select("font").first().text();
			rsp.setTimeLimit(parseTimeLimit(text));
			rsp.setMemoryLimit(parseMemoryLimit(text));
			
			Elements panels = e.select("div.panel_content");
			
			rsp.setProblemDetail(parseDetail(panels.get(0).html()));
			rsp.setInputDetail(panels.get(1).html());
			rsp.setOutputDetail(panels.get(2).html());
			rsp.setSampleInput(parseSample(panels.get(3).html()));
			
			if(panels.get(4).select("div").size()>2){
				Map.Entry<String, String> pair = peelHint(panels.get(4).html());
				rsp.setSampleOutput(pair.getKey());
				rsp.setHintDetail(pair.getValue());
			}else{
				rsp.setSampleOutput(parseSample(panels.get(4).html()));
			}
			
			rsp.setSubmit_params(problemId);
				
			return rsp;	
	}

	@Override
	public boolean isJudging(JudgeRsp rsp) {
		String res = rsp.getReuslt();
		
		return res.equals("Queuing") 
			|| res.equals("Running") 
			|| res.equals("Compiling");
	}

	@Override
	public String resConvert(String res) {
		if(res.equals("Accepted"))  return OnlineJudge.AC;
		if(res.equals("Wrong Answer")) return OnlineJudge.WA;
		if(res.equals("Presentation Error")) return OnlineJudge.PE;
		if(res.equals("Compilation Error")) return OnlineJudge.CE;
		if(res.equals("Time Limit Exceeded")) return OnlineJudge.TLE;
		if(res.equals("Memory Limit Exceeded")) return OnlineJudge.MLE;
		if(res.equals("Output Limit Exceeded")) return OnlineJudge.OLE;
		if(res.equals("Runtime Error (ACCESS_VIOLATION)")) return OnlineJudge.RE;
		if(res.equals("Runtime Error (STACK_OVERFLOW)")) return OnlineJudge.RE;
		if(res.equals("Runtime Error (INTEGER_DIVIDE_BY_ZERO)")) return OnlineJudge.RE;
		return OnlineJudge.JE;
	}
	
	private String hashLanguage(String language){
		if(language.equals("c++")) return "0";
		if(language.equals("java")) return "5";
		return "0";
	}
	
	private String parseTimeLimit(String text){
		String s = Regular.matchFirst(text, "Time Limit: \\d+/\\d+");
		s = Regular.removeAll(s,"Time Limit: \\d+/");
		return s + "MS";
	}
	
	private String parseMemoryLimit(String text){
		String s = Regular.matchFirst(text, "Memory Limit: \\d+/\\d+");
		if(s==null) return "";
		
		s = Regular.removeAll(s,"Memory Limit: \\d+/");
		return s + "KB";		
	}
	
	private String parseDetail(String text){
		String[] href = Regular.match(text, "data/images/.*\"?");
		
		for(int i=0;i<href.length;i++){
			href[i] = "<img src=\"http://acm.hdu.edu.cn/" + href[i]; 
			text = text.replaceFirst("<img .*>?", href[i]);
		}
		
		return text;
	}
	
	private String parseSample(String text){
		text = text.replaceAll("(<div[\\s\\S]*\">)|(</div>)", "");
		return text;
	}
	
	private Map.Entry<String, String> peelHint(String text){
		Map.Entry<String, String> pair = null;
		
		text = text.replaceAll("</div>", "");
		StringBuffer sf = new StringBuffer();
		
		for(int i=0;i<text.length();i++){
			if(i+4<=text.length() && text.substring(i, i+4).equals("<div")){
				while(text.charAt(i) != '>') i++;
			}else{
				sf.append(text.charAt(i));
			}
		}
		
		text = sf.toString();		
	
		String[] s = text.split("<i>Hint</i>");
		
		if(s.length==2){
			s[0]+="</pre>";
			s[1] = "<pre>" + s[1];
			s[1] = parseDetail(s[1]);
			pair = new AbstractMap.SimpleEntry<String,String>(s[0],s[1]);
		}else{
			pair = new AbstractMap.SimpleEntry<String,String>(text,"");
		}
		
		return pair;
	}
}
