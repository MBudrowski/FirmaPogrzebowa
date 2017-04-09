package mbud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.Cart;
import mbud.data.UserData;
import mbud.hibernate.dao.ProduktDao;
import mbud.hibernate.mapping.Produkt;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({"/AddToCartServlet", "/panel/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(404);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		UserData data = (UserData) request.getSession().getAttribute("userData");
		if (data == null || !data.isLoggedIn()) {
			return;
		}
		int id = Integer.parseInt(request.getParameter("id"));
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		ProduktDao dao = new ProduktDao();
		dao.openSession();
		Produkt p = dao.findById(id);
		System.out.println(p);
		dao.closeSession();
		cart.addProduct(p);
		System.out.println(cart.getProducts());
		response.setContentType("text/plain");
		response.getWriter().print(cart.getNumberOfItems());
	}

}
