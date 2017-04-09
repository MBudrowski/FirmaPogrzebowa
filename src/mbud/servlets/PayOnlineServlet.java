package mbud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.UserData;
import mbud.hibernate.dao.PogrzebDao;
import mbud.hibernate.dao.ZamowienieDao;
import mbud.hibernate.mapping.Klient;
import mbud.hibernate.mapping.Pogrzeb;
import mbud.hibernate.mapping.Zamowienie;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/payOnline.html")
public class PayOnlineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayOnlineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendError(404);
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
		if (data == null || !data.isLoggedIn()) {
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
		String type = request.getParameter("type");
		if (type == null) {
			response.sendError(404);
			return;
		}
		String accept = request.getParameter("accept");
		
		if (type.equals("z")) {
			ZamowienieDao dao = new ZamowienieDao();
			dao.openSession();
			Zamowienie z = dao.findById(id);
			Klient k = z.getKlient();
			dao.closeSession();
			
			if (k.getId() != data.getId() || z.getOplacone()) {
				response.sendError(404);
				return;
			}
			
			if (accept != null && accept.equals("1")) {
				z.setOplacone(true);
				dao.update(z);
				
				request.getRequestDispatcher("payOnline_msg.jsp").forward(request, response);
				return;
			}
			else {
				request.setAttribute("id", id);
				request.setAttribute("type", type);
				request.setAttribute("kwota", z.getTotalPrice());
			}
		}
		else if (type.equals("p")) {
			PogrzebDao dao = new PogrzebDao();
			dao.openSession();
			Pogrzeb z = dao.findById(id);
			Klient k = z.getKlient();
			dao.closeSession();
			
			if (k.getId() != data.getId() || (z.getOplacony() != null && z.getOplacony())) {
				response.sendError(404);
				return;
			}
			
			if (accept != null && accept.equals("1")) {
				
				z.setOplacony(true);
				dao.update(z);
				
				request.getRequestDispatcher("payOnline_msg.jsp").forward(request, response);
				return;
			}
			else {
				request.setAttribute("id", id);
				request.setAttribute("type", type);
				request.setAttribute("kwota", z.getCenaString());
			}
		}
		else {
			response.sendError(404);
			return;
		}
		
		request.getRequestDispatcher("payOnline.jsp").forward(request, response);
	}
}
