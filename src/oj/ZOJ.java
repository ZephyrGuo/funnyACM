package oj;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import common.Http;
import common.Regular;
import base.*;

public class ZOJ extends OnlineJudge{
	private final String loginUrl="http://acm.zju.edu.cn/onlinejudge/login.do";
	private final String submitUrl="http://acm.zju.edu.cn/onlinejudge/submit.do";
	private final String statusUrl="http://acm.zju.edu.cn/onlinejudge/showRuns.do";
	private final String problemUrl="http://acm.zju.edu.cn/onlinejudge/showProblem.do";
	
	private Map<String, String> cookies;
	
	public ZOJ(){
		super("conf/ZOJ.properties");
	}

	@Override
	protected Map<String, String> login(Account account) {
		String params[] = { 
			"handle", account.getAccount(), 
			"password",account.getPassword(),
			"rememberMe", "on" 
		};	
		
		Response res=Http.post(loginUrl, params, null);
	
		if(res!=null)
			return res.cookies();

		return null;
	}

	@Override
	public boolean submit(SubmitReq sr) {		
		String[] params={
			"source",sr.getCode(),
			"languageId",hashLanguage(sr.getLanguage()),
			"problemId",sr.getSubmit_params()
		};
		
		cookies = login(sr.getAccount()); 
		
		if(cookies == null){
			System.out.println("login ZOJ failed.");
			return false;
		}
		
		if(Http.post(submitUrl,params,cookies)==null){
			System.out.println("submit ZOJ failed.");
			return false;
		}
		
		return true;
	}

	@Override
	public JudgeRsp takeResult(Account account) {
		String[] params={
			"contestId","1",
			"search","true",
			"firstId","-1",
			"lastId","-1",
			"problemCode","",
			"handle",account.getAccount(),
			"idStart","",
			"idEnd",""
		};
		
		Response res=Http.get(statusUrl,params,cookies);
		Document doc=null;
		JudgeRsp rsp=new JudgeRsp(); 
		
		if(res==null){
			System.out.println("take ZOJ result failed");
			rsp.setReuslt(OnlineJudge.JE);
			return rsp;
		}
		
		try {
			doc=res.parse();
		} catch (IOException e) {
			e.printStackTrace();
			rsp.setReuslt(OnlineJudge.JE);
			return rsp;
		}
	
		Element tr=doc.select("table.list > tbody").get(0).child(1);
		
		rsp.setId(tr.child(0).text());
		rsp.setReuslt(tr.child(2).text());
		rsp.setRunTime(tr.child(5).text()+"MS");
		rsp.setRunMemory(tr.child(6).text()+"KB");
		rsp.setDetail("");
		
		if(rsp.getReuslt().equals("Compilation Error")){
			
			String url=tr.child(2).select("a[href]").attr("abs:href");

			try {
				Response r = Http.get(url, null, cookies);
				
				if(r==null){
					System.out.println("take ZOJ result failed");
					rsp.setReuslt(OnlineJudge.JE);
					return rsp;
				}
				
				rsp.setDetail(r.parse().text());
			} catch (IOException e) {
				rsp.setReuslt(OnlineJudge.JE);
				e.printStackTrace();
			}
			
		}
		
		return rsp;
	}

	@Override
	public ProblemRsp linkProblem(String problemId) {
		String[] params={
			"problemCode",problemId
		};
		Response res=Http.get(problemUrl, params, null);
		
		if(res==null){
			System.out.println("link ZOJ problem failed");
			return null;
		}
		
		Document doc=null;
		try {
			doc=res.parse();
		} catch (IOException e) {			
			e.printStackTrace();
			return null;
		}
		
		ProblemRsp rsp=new ProblemRsp();
		Element body=doc.select("#content_body").first();
	
		rsp.setTitle(body.child(0).text());	
		rsp.setTimeLimit(Regular.matchFirst(body.child(2).text(),"[\\d]+ Seconds"));
		rsp.setMemoryLimit(Regular.matchFirst(body.child(2).text(),"[\\d]+ KB"));
		rsp.setProblemDetail(getDetail(body.html()));
		rsp.setSampleInput("");
		rsp.setSampleOutput("");
		rsp.setInputDetail("");
		rsp.setOutputDetail("");
		rsp.setHintDetail("");
		
		String s=Regular.matchFirst(doc.html(),"/onlinejudge/submit\\.do\\?problemId=[\\d]+");

		rsp.setSubmit_params(Regular.matchFirst(s,"[\\d]+"));
			
		return rsp;
	}

	@Override
	public boolean isJudging(JudgeRsp rsp) {
		String res=rsp.getReuslt();	
		return res.equals("Running") || res.equals("Compiling") || res.equals("Queuing");
	}
	
	@Override
	public String resConvert(String res){
		if(res.equals("Accepted")) 				return OnlineJudge.AC; 				
		if(res.equals("Presentation Error")) 	return OnlineJudge.PE;
		if(res.equals("Wrong Answer"))			return OnlineJudge.WA;
		if(res.equals("Time Limit Exceeded")) 	return OnlineJudge.TLE;
		if(res.equals("Memory Limit Exceeded")) return OnlineJudge.MLE;
		if(res.equals("Segmentation Fault")) 	return OnlineJudge.RE;	
		if(res.equals("Non-zero Exit Code")) 	return OnlineJudge.RE;
		if(	res.equals("Floating Point Error")) return OnlineJudge.RE;
		if(res.equals("Runtime Error")) 		return OnlineJudge.RE;
		if(res.equals("Compilation Error")) 	return OnlineJudge.CE;
		if(res.equals("Output Limit Exceeded")) return OnlineJudge.OLE;
		
		return OnlineJudge.JE;
	}

	private String getDetail(String s){
		s=s.replaceAll("showImage.do", "http://acm.zju.edu.cn/onlinejudge/showImage.do");
		String reg="<center>[\\s\\S]*</center>[\\n\\s]*<hr />[\\n\\s]*<center>[\\s\\S]*</center>[\\n\\s]*<hr />";
		s=Regular.removeAll(s, reg);
		reg="<hr />[\\s\\S]*";
		return Regular.removeAll(s, reg);
	}
	
	private String hashLanguage(String language) {
		if(language.equals("c++")) return "2";
		if(language.equals("java")) return "4";
		return "2";
	}
}
