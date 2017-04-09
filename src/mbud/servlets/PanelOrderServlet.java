package mbud.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.Cart;
import mbud.data.UserData;
import mbud.hibernate.dao.KlientDao;
import mbud.hibernate.dao.PracownikDao;
import mbud.hibernate.dao.ZamowienieDao;
import mbud.hibernate.mapping.DomPogrzebowy;
import mbud.hibernate.mapping.Klient;
import mbud.hibernate.mapping.Pracownik;
import mbud.hibernate.mapping.Produkt;
import mbud.hibernate.mapping.Zamowienie;
import mbud.hibernate.mapping.ZamowienieProdukt;
import mbud.hibernate.mapping.ZamowienieProduktId;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/panel/PanelOrderServlet")
public class PanelOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanelOrderServlet() {
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
		
		int id = Integer.parseInt(request.getParameter("klient"));
		
		UserData userData = (UserData) request.getSession().getAttribute("userData");
		if (userData == null || !userData.isLoggedIn() || !userData.hasWorkerPrivs()) {
			response.sendRedirect("../index.html");
			return;
		}
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		
		Klient klient = null;
		if (id != 0) {
			KlientDao klientDao = new KlientDao();
			klientDao.openSession();
			klient = klientDao.findById(id);
			klientDao.closeSession();
		}
		
		PracownikDao pracownikDao = new PracownikDao();
		pracownikDao.openSession();
		Pracownik p = pracownikDao.findById(userData.getId());
		DomPogrzebowy dom = p.getDomPogrzebowy();
		pracownikDao.closeSession();
		
		Zamowienie zam = new Zamowienie(dom, klient, new Date());
		zam.setOdebrane(true);
		zam.setOplacone(true);
		zam.setPracownik(p);
		ZamowienieProdukt zp;
		ZamowienieProduktId zpid;
		Set<ZamowienieProdukt> produkty = new HashSet<>();
		for (Map.Entry<Produkt, Integer> i : cart.getProducts().entrySet()) {
			zpid = new ZamowienieProduktId(zam, i.getKey());
			zp = new ZamowienieProdukt(zpid, i.getValue());
			produkty.add(zp);
		}
		zam.setProdukty(produkty);
		
		ZamowienieDao zamDao = new ZamowienieDao();
		zamDao.save(zam);
		
		cart.clearCart();
		
		response.sendRedirect("orders.html");
	}
}
