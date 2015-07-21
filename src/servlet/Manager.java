package servlet;

import base.MultiTaskServlet;
import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Manager
 */
@WebServlet("/Manager")
public class Manager extends MultiTaskServlet {
	private static final long serialVersionUID = 1L;

    public Manager() {
        super();
    }

	@Override
	protected void initRegist() {
		this.registTask("addProblem", "AddProblemTask");
		this.registTask("saveProblem", "SaveProblemTask");
		this.registTask("findTaskTpl","FindTaskTemplateTask");
		this.registTask("findUser","FindUserTask");
		this.registTask("assignTask", "AssignTaskTask");
		this.registTask("createTaskTpl", "CreateTaskTplTask");
		this.registTask("findSeason", "FindSeasonTask");
		this.registTask("createContest", "CreateContestTask");
		this.registTask("createSeason", "AddSeasonTask");
		this.registTask("approveSeason", "VerifySeasonTask");
		this.registTask("saveSection", "SaveSectionTask");
		this.registTask("findUserById", "FindUserByIdTask");
		this.registTask("modifyUser", "ModifyUserTask");
		this.registTask("queryUserRecord", "QueryUserRecord");
		this.registTask("editTaskTemplate", "EditTaskTplTask");
		this.registTask("editTask", "EditTaskInfoTask");
		this.registTask("editContest", "EditContestTask");
		this.registTask("editSeason", "EditSeasonTask");
		this.registTask("queryProblem", "QueryProblemTask");
		this.registTask("updProblem", "updateProblemTask");
	}

}
