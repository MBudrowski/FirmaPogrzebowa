package mbud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;

import mbud.data.UserData;
import mbud.hibernate.dao.PogrzebDao;
import mbud.hibernate.mapping.Pogrzeb;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/panel/receivePayment.html")
public class PanelReceivePayment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanelReceivePayment() {
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

		String accept = request.getParameter("accept");
		
		PogrzebDao dao = new PogrzebDao();
		dao.openSession();
		Pogrzeb p = dao.findById(id);
		Hibernate.initialize(p.getKlient());
		dao.closeSession();
		
		if (p.getOplacony() != null && p.getOplacony()) {
			response.sendError(404);
			return;
		}
		
		if (accept != null && accept.equals("1")) {
			p.setOplacony(true);
			dao.update(p);
			
			response.sendRedirect("funerals.html");
			return;
		}
		else {
			request.setAttribute("id", id);
			request.setAttribute("pogrzeb", p);
		}
		
		request.getRequestDispatcher("receivePayment.jsp").forward(request, response);
	}

}
