package base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Task {
	
	public abstract void doRequest(HttpServletRequest request) throws IOException;
	public abstract void doResponse(HttpServletResponse response) throws IOException;
	
}
