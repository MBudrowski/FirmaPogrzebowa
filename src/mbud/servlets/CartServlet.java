package mbud.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.UserData;
import mbud.hibernate.dao.DomPogrzebowyDao;
import mbud.hibernate.mapping.DomPogrzebowy;
import mbud.util.StringUtil;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/cart.html")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
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
		if (data == null || !data.isLoggedIn()) {
			response.sendRedirect("index.html");
			return;
		}
		DomPogrzebowyDao dao = new DomPogrzebowyDao();
		dao.openSession();
        List<DomPogrzebowy> domy = dao.findAll(); // Obtain all products.
        dao.closeSession();
        for (DomPogrzebowy d : domy) {
        	d.setTelefon(StringUtil.formatNormalNumber(d.getTelefon()));
        }
        request.setAttribute("domyPogrzebowe", domy);
        
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}
}
