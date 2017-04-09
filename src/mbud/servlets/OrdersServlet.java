package mbud.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.UserData;
import mbud.hibernate.dao.PogrzebDao;
import mbud.hibernate.dao.ZamowienieDao;
import mbud.hibernate.mapping.Pogrzeb;
import mbud.hibernate.mapping.Zamowienie;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/orders.html")
public class OrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdersServlet() {
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
		// TODO Auto-generated method stub
		UserData userData = (UserData) request.getSession().getAttribute("userData");
		if (userData == null) {
			userData = new UserData();
			request.getSession().setAttribute("userData", userData);
			response.sendRedirect("index.html");
			return;
		}
		
		ZamowienieDao dao = new ZamowienieDao();
		dao.openSession();
		List<Zamowienie> zamowienia = dao.findUsersOrders(userData.getId());
		dao.closeSession();
		
		PogrzebDao dao2 = new PogrzebDao();
		dao2.openSession();
		List<Pogrzeb> pogrzeby = dao2.findUsersFunerals(userData.getId());
		dao2.closeSession();
		
        request.setAttribute("zamowienia", zamowienia);
        request.setAttribute("pogrzeby", pogrzeby);
		request.getRequestDispatcher("orders.jsp").forward(request, response);
	}
}
