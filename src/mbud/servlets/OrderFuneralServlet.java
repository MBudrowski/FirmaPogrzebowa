package mbud.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.UserData;
import mbud.hibernate.dao.CmentarzDao;
import mbud.hibernate.dao.DomPogrzebowyDao;
import mbud.hibernate.dao.KlientDao;
import mbud.hibernate.dao.KosciolDao;
import mbud.hibernate.dao.PogrzebDao;
import mbud.hibernate.dao.ProduktDao;
import mbud.hibernate.mapping.Cmentarz;
import mbud.hibernate.mapping.DomPogrzebowy;
import mbud.hibernate.mapping.Klient;
import mbud.hibernate.mapping.Kosciol;
import mbud.hibernate.mapping.Pogrzeb;
import mbud.hibernate.mapping.Produkt;
import mbud.util.Validator;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/orderFuneral.html")
public class OrderFuneralServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderFuneralServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		UserData data = (UserData) request.getSession().getAttribute("userData");
		if (data == null) {
			data = new UserData();
			request.getSession().setAttribute("userData", data);
		}
		
		if (!data.isLoggedIn()) {
			response.sendRedirect("index.html");
			return;
		}
		
		getDBData(request);
		request.getRequestDispatcher("orderFuneral.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		UserData data = (UserData) request.getSession().getAttribute("userData");

		if (data == null) {
			data = new UserData();
			request.getSession().setAttribute("userData", data);
		}
		
		if (!data.isLoggedIn()) {
			response.sendRedirect("index.html");
			return;
		}
		
		getDBData(request);
		
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String address = request.getParameter("address");
		String birth = request.getParameter("birthDate");
		String death = request.getParameter("deathDate");
		String domPogrzebowy = request.getParameter("domPogrzebowy");
		String kosciol = request.getParameter("kosciol");
		String cmentarz = request.getParameter("cmentarz");
		String trumna = request.getParameter("trumna");
		
		if (name == null || name.isEmpty() || surname == null || surname.isEmpty() || address == null || address.isEmpty() ||
				birth == null || birth.isEmpty() || death == null || death.isEmpty() || domPogrzebowy == null || domPogrzebowy.isEmpty() ||
				kosciol == null || kosciol.isEmpty() || cmentarz == null || cmentarz.isEmpty() || trumna == null || trumna.isEmpty()) {
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("oldAddress", address);
			request.setAttribute("oldBirth", birth);
			request.setAttribute("oldDeath", death);
			request.setAttribute("oldDom", domPogrzebowy);
			request.setAttribute("oldKos", kosciol);
			request.setAttribute("oldCm", cmentarz);
			request.setAttribute("oldTr", trumna);
			request.setAttribute("funeralError", "Wymagane jest podanie wszystkich danych formularza!");
			request.getRequestDispatcher("orderFuneral.jsp").forward(request, response);
			return;
		}
		if (!Validator.validateDate(birth)) {
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("oldAddress", address);
			request.setAttribute("oldDeath", death);
			request.setAttribute("oldDom", domPogrzebowy);
			request.setAttribute("oldKos", kosciol);
			request.setAttribute("oldCm", cmentarz);
			request.setAttribute("oldTr", trumna);
			request.setAttribute("funeralError", "Nieprawid³owy format daty urodzenia!");
			request.getRequestDispatcher("orderFuneral.jsp").forward(request, response);
			return;
		}
		if (!Validator.validateDate(death)) {
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("oldAddress", address);
			request.setAttribute("oldBirth", birth);
			request.setAttribute("oldDom", domPogrzebowy);
			request.setAttribute("oldKos", kosciol);
			request.setAttribute("oldCm", cmentarz);
			request.setAttribute("oldTr", trumna);
			request.setAttribute("funeralError", "Nieprawid³owy format daty œmierci!");
			request.getRequestDispatcher("orderFuneral.jsp").forward(request, response);
			return;
		}
		int cmId, ksId, dpId, trId;
		try {
			cmId = Integer.parseInt(cmentarz);
			ksId = Integer.parseInt(kosciol);
			dpId = Integer.parseInt(domPogrzebowy);
			trId = Integer.parseInt(trumna);
		}
		catch (NumberFormatException e) {
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("oldAddress", address);
			request.setAttribute("oldBirth", birth);
			request.setAttribute("oldDeath", death);
			request.setAttribute("funeralError", "Wybrano nieprawid³owe dane pogrzebu!");
			request.getRequestDispatcher("orderFuneral.jsp").forward(request, response);
			return;
		}
		
		KlientDao kDao = new KlientDao();
		kDao.openSession();
		Klient k = kDao.findById(data.getId());
		kDao.closeSession();
		
		CmentarzDao cDao = new CmentarzDao();
		cDao.openSession();
		Cmentarz cm = cDao.findById(cmId);
		cDao.closeSession();
		
		KosciolDao ksDao = new KosciolDao();
		ksDao.openSession();
		Kosciol ks = ksDao.findById(ksId);
		ksDao.closeSession();
		
		DomPogrzebowyDao dpDao = new DomPogrzebowyDao();
		dpDao.openSession();
		DomPogrzebowy dp = dpDao.findById(dpId);
		dpDao.closeSession();
		
		ProduktDao pDao = new ProduktDao();
		pDao.openSession();
		Produkt tr = pDao.findById(trId);
		pDao.closeSession();
		
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Pogrzeb p = null;
		try {
			p = new Pogrzeb(cm, dp, k, ks, name, surname, address, new Date(), formatter.parse(birth), formatter.parse(death));
			if (trId != 0) {
				p.setTrumna(tr.getTrumna());
			}
		} catch (ParseException e) {
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("oldAddress", address);
			request.setAttribute("oldDom", domPogrzebowy);
			request.setAttribute("oldKos", kosciol);
			request.setAttribute("oldCm", cmentarz);
			request.setAttribute("oldTr", trumna);
			request.setAttribute("funeralError", "Nieprawid³owy format daty!");
			request.getRequestDispatcher("orderFuneral.jsp").forward(request, response);
			return;
		}
		
		PogrzebDao pogrzebDao = new PogrzebDao();
		pogrzebDao.save(p);
		
		request.getRequestDispatcher("orderFuneral_msg.jsp").forward(request, response);
	}
	
	private void getDBData(HttpServletRequest request) {
		DomPogrzebowyDao dao1 = new DomPogrzebowyDao();
		dao1.openSession();
		request.setAttribute("domyPogrzebowe", dao1.findAll());
		dao1.closeSession();
		
		KosciolDao dao2 = new KosciolDao();
		dao2.openSession();
		request.setAttribute("koscioly", dao2.findAll());
		dao2.closeSession();
		
		CmentarzDao dao3 = new CmentarzDao();
		dao3.openSession();
		request.setAttribute("cmentarze", dao3.findAll());
		dao3.closeSession();
		
		ProduktDao dao4 = new ProduktDao();
		dao4.openSession();
		request.setAttribute("trumny", dao4.findCoffins());
		dao4.closeSession();
	}

}
