package mbud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.UserData;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/panel/index.html")
public class PanelIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanelIndexServlet() {
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
		/*KlientDao dao = new KlientDao();
		dao.openSession();
        List<Klient> klienci = dao.findAll(); // Obtain all products.
        dao.closeSession();
        request.setAttribute("klienci", klienci); // Store products in request scope.
        ZamowienieDao dao2 = new ZamowienieDao();
        dao2.openSession();
        Zamowienie zam = dao2.findById(5);
        request.setAttribute("zamowienie", zam); // Store products in request scope.
        dao2.closeSession();*/
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
