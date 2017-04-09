package mbud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.Cart;
import mbud.data.UserData;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({"/ChangeCartCount", "/panel/ChangeCartCount"})
public class ChangeCartCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeCartCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserData data = (UserData) request.getSession().getAttribute("userData");
		if (data == null || !data.isLoggedIn()) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		int count = Integer.parseInt(request.getParameter("count"));
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.changeProductCount(id, count);
		
		response.sendRedirect("cart.html");
	}
}
