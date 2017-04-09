package mbud.servlets;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;

import mbud.data.LockManager;
import mbud.data.UserData;
import mbud.hibernate.dao.CmentarzDao;
import mbud.hibernate.dao.KlientDao;
import mbud.hibernate.dao.KosciolDao;
import mbud.hibernate.dao.PogrzebDao;
import mbud.hibernate.dao.PracownikDao;
import mbud.hibernate.dao.ProduktDao;
import mbud.hibernate.mapping.Cmentarz;
import mbud.hibernate.mapping.Klient;
import mbud.hibernate.mapping.Kosciol;
import mbud.hibernate.mapping.Pogrzeb;
import mbud.hibernate.mapping.Pracownik;
import mbud.hibernate.mapping.Produkt;
import mbud.util.Validator;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet({ "/panel/addFuneral.html", "/panel/editFuneral.html" })
public class PanelAddFuneralServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PanelAddFuneralServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		UserData data = (UserData) request.getSession().getAttribute("userData");
		if (data == null) {
			data = new UserData();
			request.getSession().setAttribute("userData", data);
		}

		if (!data.isLoggedIn() || !data.hasWorkerPrivs()) {
			response.sendRedirect("index.html");
			return;
		}

		getDBData(request, data);

		request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserData data = (UserData) session.getAttribute("userData");

		if (data == null) {
			data = new UserData();
			session.setAttribute("userData", data);
		}

		if (!data.isLoggedIn() || !data.hasWorkerPrivs()) {
			response.sendRedirect("index.html");
			return;
		}

		getDBData(request, data);

		int id = -1;
		Pogrzeb pogrzeb = null;
		try {
			String idString = request.getParameter("id");
			if (idString != null && !idString.isEmpty()) {
				id = Integer.parseInt(idString);

				PogrzebDao dao = new PogrzebDao();
				dao.openSession();
				pogrzeb = dao.findById(id);
				Hibernate.initialize(pogrzeb.getKlient());
				dao.closeSession();

				request.setAttribute("id", id);
			}
		} catch (NumberFormatException | NullPointerException e) {
		}
		
		if (id != -1) {
			if (LockManager.isFuneralLocked(id, session)) {
				response.sendRedirect("locked.html");
				return;
			}
			LockManager.addLock(id, session);
		}

		String accept = request.getParameter("accept");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatterDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (accept != null && accept.equals("1")) {
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String address = request.getParameter("address");
			String birth = request.getParameter("birthDate");
			String death = request.getParameter("deathDate");
			String kosciol = request.getParameter("kosciol");
			String cmentarz = request.getParameter("cmentarz");
			String trumna = request.getParameter("trumna");
			String klient = request.getParameter("klient");
			String cost = request.getParameter("cost");
			String dateExposure = request.getParameter("dateExposure");
			String dateFuneral = request.getParameter("dateFuneral");

			if (name == null || name.isEmpty() || surname == null || surname.isEmpty() || address == null
					|| address.isEmpty() || birth == null || birth.isEmpty() || death == null || death.isEmpty()
					|| kosciol == null || kosciol.isEmpty()
					|| cmentarz == null || cmentarz.isEmpty() || trumna == null || trumna.isEmpty() || klient == null
					|| klient.isEmpty()) {
				request.setAttribute("oldName",
						((name == null || name.isEmpty()) && pogrzeb != null) ? pogrzeb.getImieDenata() : name);
				request.setAttribute("oldSurname", ((surname == null || surname.isEmpty()) && pogrzeb != null)
						? pogrzeb.getNazwiskoDenata() : surname);
				request.setAttribute("oldAddress", ((address == null || address.isEmpty()) && pogrzeb != null)
						? pogrzeb.getAdresDenata() : address);
				request.setAttribute("oldBirth",
						((birth == null || birth.isEmpty()) && pogrzeb != null) ? pogrzeb.getDataUrodzenia() : birth);
				request.setAttribute("oldDeath",
						((death == null || death.isEmpty()) && pogrzeb != null) ? pogrzeb.getDataSmierci() : death);
				request.setAttribute("oldKos", ((kosciol == null || kosciol.isEmpty()) && pogrzeb != null)
						? pogrzeb.getKosciol().getId() : kosciol);
				request.setAttribute("oldCm", ((cmentarz == null || cmentarz.isEmpty()) && pogrzeb != null)
						? pogrzeb.getCmentarz().getId() : cmentarz);
				request.setAttribute("oldTr", ((trumna == null || trumna.isEmpty()) && pogrzeb != null)
						? ((pogrzeb.getTrumna() != null) ? pogrzeb.getTrumna().getId() : 0) : trumna);
				request.setAttribute("oldKl", ((klient == null || klient.isEmpty()) && pogrzeb != null)
						? pogrzeb.getKlient().getId() : klient);
				request.setAttribute("oldCost", cost);
				request.setAttribute("oldDateExp", dateExposure);
				request.setAttribute("oldDateFun", dateFuneral);
				request.setAttribute("funeralError", "Wymagane jest podanie wszystkich danych formularza!");
				request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
				return;
			}
			if (!Validator.validateDate(birth)) {
				request.setAttribute("oldName", name);
				request.setAttribute("oldSurname", surname);
				request.setAttribute("oldAddress", address);
				if (pogrzeb != null) {
					request.setAttribute("oldBirth", formatter.format(pogrzeb.getDataUrodzenia()));
				}
				request.setAttribute("oldDeath", death);
				request.setAttribute("oldKos", kosciol);
				request.setAttribute("oldCm", cmentarz);
				request.setAttribute("oldTr", trumna);
				request.setAttribute("oldKl", klient);
				request.setAttribute("oldCost", cost);
				request.setAttribute("oldDateExp", dateExposure);
				request.setAttribute("oldDateFun", dateFuneral);
				request.setAttribute("funeralError", "Nieprawid³owy format daty urodzenia!");
				request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
				return;
			}
			if (!Validator.validateDate(death)) {
				request.setAttribute("oldName", name);
				request.setAttribute("oldSurname", surname);
				request.setAttribute("oldAddress", address);
				request.setAttribute("oldBirth", birth);
				if (pogrzeb != null) {
					request.setAttribute("oldDeath", formatter.format(pogrzeb.getDataSmierci()));
				}
				request.setAttribute("oldKos", kosciol);
				request.setAttribute("oldCm", cmentarz);
				request.setAttribute("oldTr", trumna);
				request.setAttribute("oldKl", klient);
				request.setAttribute("oldCost", cost);
				request.setAttribute("oldDateExp", dateExposure);
				request.setAttribute("oldDateFun", dateFuneral);
				request.setAttribute("funeralError", "Nieprawid³owy format daty œmierci!");
				request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
				return;
			}
			int cmId, ksId, trId, klId;
			try {
				cmId = Integer.parseInt(cmentarz);
				ksId = Integer.parseInt(kosciol);
				trId = Integer.parseInt(trumna);
				klId = Integer.parseInt(klient);
			} catch (NumberFormatException e) {
				request.setAttribute("oldName", name);
				request.setAttribute("oldSurname", surname);
				request.setAttribute("oldAddress", address);
				request.setAttribute("oldBirth", birth);
				request.setAttribute("oldDeath", death);
				if (pogrzeb != null) {
					request.setAttribute("oldKos", pogrzeb.getKosciol().getId());
					request.setAttribute("oldCm", pogrzeb.getCmentarz().getId());
					request.setAttribute("oldTr", pogrzeb.getTrumna().getId());
					request.setAttribute("oldKl", pogrzeb.getKlient().getId());
				}
				request.setAttribute("oldCost", cost);
				request.setAttribute("oldDateExp", dateExposure);
				request.setAttribute("oldDateFun", dateFuneral);
				request.setAttribute("funeralError", "Wybrano nieprawid³owe dane pogrzebu!");
				request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
				return;
			}

			double costValue = -1;
			try {
				if (cost != null && !cost.isEmpty()) {
					costValue = Double.parseDouble(cost);
				}
			} catch (NumberFormatException e) {
				try {
					NumberFormat f = NumberFormat.getInstance(Locale.FRANCE);
					costValue = f.parse(cost).doubleValue();
				} catch (ParseException ex) {
					request.setAttribute("oldName", name);
					request.setAttribute("oldSurname", surname);
					request.setAttribute("oldAddress", address);
					request.setAttribute("oldBirth", birth);
					request.setAttribute("oldDeath", death);
					request.setAttribute("oldKos", kosciol);
					request.setAttribute("oldCm", cmentarz);
					request.setAttribute("oldTr", trumna);
					request.setAttribute("oldKl", klient);
					if (pogrzeb != null && pogrzeb.getKoszt() != null && pogrzeb.getKoszt() > 0) {
						request.setAttribute("oldCost", pogrzeb.getKoszt().toString());
					}
					request.setAttribute("oldDateExp", dateExposure);
					request.setAttribute("oldDateFun", dateFuneral);
					request.setAttribute("funeralError", "Nieprawid³owa kwota pogrzebu!");
					request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
					return;
				}
			}

			if (dateExposure != null && !dateExposure.isEmpty() && dateFuneral != null && !dateFuneral.isEmpty()) {
				if (!Validator.validateDate(dateExposure)) {
					request.setAttribute("oldName", name);
					request.setAttribute("oldSurname", surname);
					request.setAttribute("oldAddress", address);
					request.setAttribute("oldBirth", birth);
					request.setAttribute("oldDeath", death);
					request.setAttribute("oldKos", kosciol);
					request.setAttribute("oldCm", cmentarz);
					request.setAttribute("oldTr", trumna);
					request.setAttribute("oldKl", klient);
					request.setAttribute("oldCost", cost);
					if (pogrzeb != null && pogrzeb.getDataWystawieniaCiala() != null) {
						request.setAttribute("oldDateExp", formatter.format(pogrzeb.getDataWystawieniaCiala()));
					}
					request.setAttribute("oldDateFun", dateFuneral);
					request.setAttribute("funeralError", "Nieprawid³owy format daty wystawienia cia³a!");
					request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
					return;
				}
				if (!Validator.validateDateTime(dateFuneral)) {
					request.setAttribute("oldName", name);
					request.setAttribute("oldSurname", surname);
					request.setAttribute("oldAddress", address);
					request.setAttribute("oldBirth", birth);
					request.setAttribute("oldDeath", death);
					request.setAttribute("oldKos", kosciol);
					request.setAttribute("oldCm", cmentarz);
					request.setAttribute("oldTr", trumna);
					request.setAttribute("oldKl", klient);
					request.setAttribute("oldCost", cost);
					if (pogrzeb != null && pogrzeb.getDataPogrzebu() != null) {
						request.setAttribute("oldDateFun", formatterDateTime.format(pogrzeb.getDataPogrzebu()));
					}
					request.setAttribute("oldDateExp", dateExposure);
					request.setAttribute("funeralError", "Nieprawid³owy format daty wystawienia cia³a!");
					request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
					return;
				}
				if (costValue == -1) {
					request.setAttribute("oldName", name);
					request.setAttribute("oldSurname", surname);
					request.setAttribute("oldAddress", address);
					request.setAttribute("oldBirth", birth);
					request.setAttribute("oldDeath", death);
					request.setAttribute("oldKos", kosciol);
					request.setAttribute("oldCm", cmentarz);
					request.setAttribute("oldTr", trumna);
					request.setAttribute("oldKl", klient);
					request.setAttribute("oldCost", cost);
					request.setAttribute("oldDateExp", dateExposure);
					request.setAttribute("oldDateFun", dateFuneral);
					request.setAttribute("funeralError",
							"Jeœli zosta³ podany termin, to wymagane jest podanie kosztu pogrzebu!");
					request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
					return;
				}
			} else if ((dateExposure == null || dateExposure.isEmpty()) != (dateFuneral == null
					|| dateFuneral.isEmpty())) {
				request.setAttribute("oldName", name);
				request.setAttribute("oldSurname", surname);
				request.setAttribute("oldAddress", address);
				request.setAttribute("oldBirth", birth);
				request.setAttribute("oldDeath", death);
				request.setAttribute("oldKos", kosciol);
				request.setAttribute("oldCm", cmentarz);
				request.setAttribute("oldTr", trumna);
				request.setAttribute("oldKl", klient);
				request.setAttribute("oldCost", cost);
				request.setAttribute("oldDateExp", dateExposure);
				request.setAttribute("oldDateFun", dateFuneral);
				request.setAttribute("funeralError", "Wymagane jest podanie obu terminów!");
				request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
				return;
			}

			KlientDao kDao = new KlientDao();
			kDao.openSession();
			Klient k = kDao.findById(klId);
			kDao.closeSession();

			CmentarzDao cDao = new CmentarzDao();
			cDao.openSession();
			Cmentarz cm = cDao.findById(cmId);
			cDao.closeSession();

			KosciolDao ksDao = new KosciolDao();
			ksDao.openSession();
			Kosciol ks = ksDao.findById(ksId);
			ksDao.closeSession();

			PracownikDao prDao = new PracownikDao();
			prDao.openSession();
			Pracownik pr = prDao.findById(data.getId());
			Hibernate.initialize(pr.getDomPogrzebowy());
			prDao.closeSession();

			ProduktDao pDao = new ProduktDao();
			pDao.openSession();
			Produkt tr = pDao.findById(trId);
			pDao.closeSession();

			Pogrzeb p = null;
			try {
				if (pogrzeb == null) {
					p = new Pogrzeb(cm, pr.getDomPogrzebowy(), k, ks, name, surname, address, new Date(), formatter.parse(birth),
							formatter.parse(death));
					if (trId != 0) {
						p.setTrumna(tr.getTrumna());
					}
					if (costValue > 0) {
						p.setKoszt(costValue);
					}
					if (dateExposure != null && !dateExposure.isEmpty() && dateFuneral != null
							&& !dateFuneral.isEmpty()) {
						p.setDataWystawieniaCiala(formatter.parse(dateExposure));
						p.setDataPogrzebu(formatterDateTime.parse(dateFuneral));
					}

					PogrzebDao pogrzebDao = new PogrzebDao();
					pogrzebDao.save(p);
				} else {
					if (LockManager.isFuneralLocked(id, session)) {
						response.sendRedirect("locked.html");
						return;
					}
					
					pogrzeb.setImieDenata(name);
					pogrzeb.setNazwiskoDenata(surname);
					pogrzeb.setAdresDenata(address);
					pogrzeb.setDataUrodzenia(formatter.parse(birth));
					pogrzeb.setDataSmierci(formatter.parse(death));
					pogrzeb.setCmentarz(cm);
					pogrzeb.setDomPogrzebowy(pr.getDomPogrzebowy());
					pogrzeb.setKlient(k);
					pogrzeb.setKosciol(ks);
					if (trId != 0) {
						pogrzeb.setTrumna(tr.getTrumna());
					}
					if (costValue > 0) {
						pogrzeb.setKoszt(costValue);
					}
					if (dateExposure != null && !dateExposure.isEmpty() && dateFuneral != null
							&& !dateFuneral.isEmpty()) {
						pogrzeb.setDataWystawieniaCiala(formatter.parse(dateExposure));
						pogrzeb.setDataPogrzebu(formatterDateTime.parse(dateFuneral));
					}

					PogrzebDao pogrzebDao = new PogrzebDao();
					pogrzebDao.update(pogrzeb);
					
					LockManager.removeLock(id);
				}
			} catch (ParseException e) {
				request.setAttribute("oldName", name);
				request.setAttribute("oldSurname", surname);
				request.setAttribute("oldAddress", address);
				request.setAttribute("oldKos", kosciol);
				request.setAttribute("oldCm", cmentarz);
				request.setAttribute("oldTr", trumna);
				request.setAttribute("oldKl", klient);
				if (pogrzeb != null) {
					request.setAttribute("oldBirth", formatter.format(pogrzeb.getDataUrodzenia()));
					request.setAttribute("oldDeath", formatter.format(pogrzeb.getDataSmierci()));
					if (pogrzeb.getDataWystawieniaCiala() != null && pogrzeb.getDataPogrzebu() != null) {
						request.setAttribute("oldDateExp", formatter.format(pogrzeb.getDataWystawieniaCiala()));
						request.setAttribute("oldDateFun", formatterDateTime.format(pogrzeb.getDataPogrzebu()));
					}
				}
				request.setAttribute("oldCost", cost);
				request.setAttribute("funeralError", "Nieprawid³owy format daty!");
				request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
				return;
			}

			response.sendRedirect("funerals.html");
			return;
		} else if (pogrzeb != null) {
			request.setAttribute("oldName", pogrzeb.getImieDenata());
			request.setAttribute("oldSurname", pogrzeb.getNazwiskoDenata());
			request.setAttribute("oldAddress", pogrzeb.getAdresDenata());
			request.setAttribute("oldBirth", pogrzeb.getDataUrodzenia());
			request.setAttribute("oldDeath", pogrzeb.getDataSmierci());
			request.setAttribute("oldDom", pogrzeb.getDomPogrzebowy().getId());
			request.setAttribute("oldKos", pogrzeb.getKosciol().getId());
			request.setAttribute("oldCm", pogrzeb.getCmentarz().getId());
			request.setAttribute("oldTr", ((pogrzeb.getTrumna() != null) ? pogrzeb.getTrumna().getId() : 0));
			request.setAttribute("oldKl", pogrzeb.getKlient().getId());
			if (pogrzeb.getDataWystawieniaCiala() != null && pogrzeb.getDataPogrzebu() != null) {
				request.setAttribute("oldDateExp", formatter.format(pogrzeb.getDataWystawieniaCiala()));
				request.setAttribute("oldDateFun", formatterDateTime.format(pogrzeb.getDataPogrzebu()));
			}
			request.setAttribute("oldCost", pogrzeb.getKoszt());
		} else {
			PracownikDao pDao = new PracownikDao();
			pDao.openSession();
			Pracownik p = pDao.findById(data.getId());
			Hibernate.initialize(p.getDomPogrzebowy());
			pDao.closeSession();

			request.setAttribute("oldDom", p.getDomPogrzebowy().getId());
		}

		request.getRequestDispatcher("addFuneral.jsp").forward(request, response);
	}

	private void getDBData(HttpServletRequest request, UserData data) {
		PracownikDao dao1 = new PracownikDao();
		dao1.openSession();
		Pracownik p = dao1.findById(data.getId());
		Hibernate.initialize(p.getDomPogrzebowy());
		request.setAttribute("domPogrzebowy", p.getDomPogrzebowy());
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

		KlientDao dao5 = new KlientDao();
		dao5.openSession();
		request.setAttribute("klienci", dao5.findAll());
		dao5.closeSession();
	}

}
