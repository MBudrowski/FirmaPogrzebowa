package mbud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.LockManager;
import mbud.data.UserData;
import mbud.hibernate.dao.PogrzebDao;
import mbud.hibernate.mapping.Pogrzeb;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/panel/deleteFuneral.html")
public class PanelDeleteFuneral extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanelDeleteFuneral() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		UserData data = (UserData) request.getSession().getAttribute("userData");
		if (data == null || !data.isLoggedIn() || !data.hasWorkerPrivs()) {
			response.sendError(404);
			return;
		}
		
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (Exception e) {
			response.sendError(404);
			return;
		}
		
		if (LockManager.isFuneralLocked(id, request.getSession())) {
			response.sendRedirect("locked.html");
			return;
		}
		
		LockManager.addLock(id, request.getSession());

		String accept = request.getParameter("accept");
		
		PogrzebDao dao = new PogrzebDao();
		dao.openSession();
		Pogrzeb p = dao.findById(id);
		dao.closeSession();
		
		if (p == null) {
			response.sendError(404);
			return;
		}
		
		if (accept != null && accept.equals("1")) {
			dao.delete(p);
			
			LockManager.removeLock(id);
			
			response.sendRedirect("funerals.html");
			return;
		}
		else {
			request.setAttribute("id", id);
		}
		
		request.getRequestDispatcher("deleteFuneral.jsp").forward(request, response);
	}

}
