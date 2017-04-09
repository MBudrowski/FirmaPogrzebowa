package mbud.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.UserData;
import mbud.hibernate.dao.KlientDao;
import mbud.hibernate.mapping.Klient;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/panel/cart.html")
public class PanelCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanelCartServlet() {
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
		/*DomPogrzebowyDao dao = new DomPogrzebowyDao();
		dao.openSession();
        List<DomPogrzebowy> domy = dao.findAll(); // Obtain all products.
        dao.closeSession();
        for (DomPogrzebowy d : domy) {
        	d.setTelefon(StringUtil.formatNormalNumber(d.getTelefon()));
        }
        request.setAttribute("domyPogrzebowe", domy);*/
		
		KlientDao dao = new KlientDao();
		dao.openSession();
		List<Klient> klienci = dao.findAll();
		dao.closeSession();
        request.setAttribute("klienci", klienci);
        
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}
}
