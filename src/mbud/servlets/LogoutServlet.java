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
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout.html")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
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
		
		if (data == null) {
			data = new UserData();
			request.getSession().setAttribute("userData", data);
		}
		
		if (data.isLoggedIn()) {
			data.setId(0);
			data.setImie(null);
			data.setNazwisko(null);
			data.setLogin(null);
			data.setWorkerPrivs(false);
		}

		Cart cart = (Cart) request.getSession().getAttribute("cart");
		
		if (cart == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		cart.clearCart();
		
		response.sendRedirect("index.html");
	}

}
