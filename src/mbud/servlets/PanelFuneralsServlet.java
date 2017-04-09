package mbud.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Hibernate;

import mbud.data.UserData;
import mbud.hibernate.dao.PogrzebDao;
import mbud.hibernate.mapping.Pogrzeb;

/**
 * Servlet implementation class ContactServlet
 */
@WebServlet("/panel/funerals.html")
public class PanelFuneralsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanelFuneralsServlet() {
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
		if (userData == null || !userData.isLoggedIn() || !userData.hasWorkerPrivs()) {
			response.sendRedirect("index.html");
			return;
		}
		
		/*ZamowienieDao dao = new ZamowienieDao();
		dao.openSession();
		List<Zamowienie> zamowieniaZr = dao.findWorkerOrdersRealized(userData.getId());
		for (Zamowienie z : zamowieniaZr) {
			Hibernate.initialize(z.getKlient());
			Hibernate.initialize(z.getPracownik());
		}
		dao.closeSession();
		dao.openSession();
		List<Zamowienie> zamowienia = dao.findWorkerOrdersNotRealized(userData.getId());
		for (Zamowienie z : zamowienia) {
			Hibernate.initialize(z.getKlient());
			Hibernate.initialize(z.getPracownik());
		}
		dao.closeSession();
		
        request.setAttribute("zamowienia", zamowienia);
        request.setAttribute("zamowieniaZr", zamowieniaZr);*/
		
		PogrzebDao dao = new PogrzebDao();
		dao.openSession();
		List<Pogrzeb> pogrzeby = dao.findWorkerFunerals(userData.getId());
		for (Pogrzeb p : pogrzeby) {
			Hibernate.initialize(p.getKlient());
		}
		dao.closeSession();
		List<Pogrzeb> realizedFunerals = new LinkedList<>();
		List<Pogrzeb> notRealizedFunerals = new LinkedList<>();
		for (Pogrzeb p : pogrzeby) {
			if (p.getStatus().equals("ZREALIZOWANY")) {
				realizedFunerals.add(p);
			}
			else {
				notRealizedFunerals.add(p);
			}
		}
		realizedFunerals.sort((Pogrzeb a, Pogrzeb b) -> {
			if (a.getDataPogrzebu().before(b.getDataPogrzebu())) {
				return 1;
			}
			return -1;
		});
		
		request.setAttribute("zrealizowanePogrzeby", realizedFunerals);
		request.setAttribute("niezrealizowanePogrzeby", notRealizedFunerals);
		
		request.getRequestDispatcher("funerals.jsp").forward(request, response);
	}
}
