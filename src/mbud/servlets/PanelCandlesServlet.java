package mbud.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.UserData;
import mbud.hibernate.dao.ProduktDao;
import mbud.hibernate.mapping.Produkt;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/panel/candles.html")
public class PanelCandlesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanelCandlesServlet() {
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
			response.sendRedirect("../index.html");
			return;
		}
		ProduktDao dao = new ProduktDao();
		dao.openSession();
		List<Produkt> znicze = dao.findCandles();
		dao.closeSession();

		request.setAttribute("znicze", znicze);
		request.getRequestDispatcher("products_candles.jsp").forward(request, response);
	}
}
