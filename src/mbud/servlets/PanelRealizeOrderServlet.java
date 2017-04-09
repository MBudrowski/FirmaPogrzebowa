package mbud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;

import mbud.data.UserData;
import mbud.hibernate.dao.ZamowienieDao;
import mbud.hibernate.mapping.Zamowienie;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/panel/realizeOrder.html")
public class PanelRealizeOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanelRealizeOrderServlet() {
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
		
		ZamowienieDao dao = new ZamowienieDao();
		dao.openSession();
		Zamowienie z = dao.findById(id);
		Hibernate.initialize(z.getKlient());
		dao.closeSession();
		
		if (z.getOdebrane()) {
			response.sendError(404);
			return;
		}
		
		if (accept != null && accept.equals("1")) {
			z.setOplacone(true);
			z.setOdebrane(true);
			dao.update(z);
			
			response.sendRedirect("orders.html");
			return;
		}
		else {
			request.setAttribute("id", id);
			request.setAttribute("zamowienie", z);
		}
		
		request.getRequestDispatcher("realizeOrder.jsp").forward(request, response);
	}

}
