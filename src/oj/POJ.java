package oj;

import java.io.IOException;
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

public class POJ extends OnlineJudge {
	
	private final String loginUrl = "http://poj.org/login"; 
	private final String submitUrl = "http://poj.org/submit";
	private final String statusUrl = "http://poj.org/status";
	private final String problemUrl = "http://poj.org/problem";
	
	public POJ(){
		super("conf/POJ.properties");
	}

	@Override
	protected Map<String, String> login(Account account) {
		String params[] = { 
				"user_id1", account.getAccount(), 
				"password1", account.getPassword(),
				"B1", "login" ,
				"url", "/"
			};	
			
		Response res=Http.post(loginUrl, params, null);
			
		if(res!=null)
			return res.cookies();
		
		return null;
	}

	@Override
	public boolean submit(SubmitReq sr) {
		String[] params={
				"submit","Submit",
				"problem_id",sr.getSubmit_params(),
				"language",hashLanguage(sr.getLanguage()),
				"source",sr.getCode()
			};
		Map<String,String> cookies = login(sr.getAccount());
		
		if(cookies==null){
			System.out.println("login POJ failed");
			return false;
		}
		
		if(Http.post(submitUrl,params,cookies)==null){
			System.out.println("submit POJ failed");
			return false;			
		}
		
		return true;
	}

	@Override
	public JudgeRsp takeResult(Account account) {
		
		String[] params={
			"problem_id","",
			"user_id",account.getAccount(),
			"result","",
			"language",""
		};
		
		Response res=Http.get(statusUrl,params,null);
		Document doc = null;
		JudgeRsp rsp = new JudgeRsp();
		
		if(res==null){
			System.out.println("take POJ result failed");
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
		
		Element tr = doc.select("body > table").get(1).select("tr").get(1);
				
		rsp.setId(tr.child(0).text());
		rsp.setReuslt(tr.child(3).text());
		rsp.setRunTime(tr.child(5).text());
		rsp.setRunMemory(tr.child(4).text()+"B");
		rsp.setDetail("");
		
		if(rsp.getReuslt().equals("Compile Error")){
			
			String[] com_params = {"solution_id",rsp.getId()};
			res = Http.get("http://poj.org/showcompileinfo", com_params, null);
			
			if(res==null){
				System.out.println("take POJ result failed");
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
			
			rsp.setDetail(doc.select("pre").first().text());
		}
		
		return rsp;
	}

	@Override
	public ProblemRsp linkProblem(String problemId) {
		String[] params={
				"id",problemId
			};
		
			Response res=Http.get(problemUrl, params, null);
			
			if(res==null){
				System.out.println("POJ link problem failed");
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
			Element e = doc.select("body > table").get(1).select("td").first();

			rsp.setTitle(e.child(1).text());
			
			Elements condition = e.child(2).select("tr").first().select("td");	
			rsp.setTimeLimit(Regular.matchFirst(condition.get(0).text(),"\\d+MS"));
			rsp.setMemoryLimit(Regular.matchFirst(condition.get(2).text(),"\\d+K")+"B");
			
			rsp.setProblemDetail(parseImageUrl(e.child(4).html()));
			rsp.setInputDetail(parseImageUrl(e.child(6).html()));
			rsp.setOutputDetail(parseImageUrl(e.child(8).html()));
			rsp.setSampleInput("<pre>"+e.child(10).text()+"</pre>");	
			rsp.setSampleOutput("<pre>"+e.child(12).text()+"</pre>");
			
			if(e.child(13).text().equals("Hint")){
				rsp.setHintDetail(parseImageUrl(e.child(14).html()));
			}else{
				rsp.setHintDetail("");
			}
			
			rsp.setSubmit_params(problemId);
				
			return rsp;	
	}

	@Override
	public boolean isJudging(JudgeRsp rsp) {
		String res = rsp.getReuslt();
		
		return res.equals("Running & Judging") 
			|| res.equals("Compiling");
	}

	@Override
	public String resConvert(String res) {
		if(res.equals("Accepted"))  return OnlineJudge.AC;
		if(res.equals("Wrong Answer")) return OnlineJudge.WA;
		if(res.equals("Presentation Error")) return OnlineJudge.PE;
		if(res.equals("Compile Error")) return OnlineJudge.CE;
		if(res.equals("Time Limit Exceeded")) return OnlineJudge.TLE;
		if(res.equals("Memory Limit Exceeded")) return OnlineJudge.MLE;
		if(res.equals("Output Limit Exceeded")) return OnlineJudge.OLE;
		if(res.equals("Runtime Error")) return OnlineJudge.RE;
		return OnlineJudge.JE;
	}
	
	private String hashLanguage(String language){
		if(language.equals("c++")) return "0";
		if(language.equals("java")) return "2";
		return "0";
	}

	private String parseImageUrl(String text){
		return text.replaceAll("src=\\\"images/", "src=\"http://poj.org/images/");
	}

}
