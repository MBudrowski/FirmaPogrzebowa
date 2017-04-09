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
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;

import mbud.data.LockManager;
import mbud.data.UserData;
import mbud.hibernate.dao.PogrzebDao;
import mbud.hibernate.mapping.Pogrzeb;
import mbud.util.Validator;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/panel/determineDateFuneral.html")
public class PanelDetermineDate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanelDetermineDate() {
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
		
		HttpSession session = request.getSession();
		UserData data = (UserData) session.getAttribute("userData");
		if (data == null || !data.isLoggedIn() || !data.hasWorkerPrivs()) {
			response.sendError(404);
			return;
		}
		
		int id;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (Exception e) {
			response.sendError(404);
			return;
		}
		
		if (LockManager.isFuneralLocked(id, session)) {
			response.sendRedirect("locked.html");
			return;
		}
		
		LockManager.addLock(id, session);
		
		PogrzebDao dao = new PogrzebDao();
		dao.openSession();
		Pogrzeb p = dao.findById(id);
		Hibernate.initialize(p.getKlient());
		dao.closeSession();
		
		if (p.getDataPogrzebu() != null || p.getDataWystawieniaCiala() != null) {
			response.sendError(404);
			return;
		}

		String dateExposure = request.getParameter("dateExposure");
		String dateFuneral = request.getParameter("dateFuneral");
		if (dateExposure == null || dateExposure.equals("") || dateFuneral == null || dateFuneral.equals("")) {
			request.setAttribute("id", id);
			request.setAttribute("pogrzeb", p);
			request.getRequestDispatcher("determineDateFuneral.jsp").forward(request, response);
			return;
		}
		if (!Validator.validateDate(dateExposure) || !Validator.validateDateTime(dateFuneral)) {
			request.setAttribute("id", id);
			request.setAttribute("pogrzeb", p);
			request.setAttribute("errorMsg", "Nieprawid³owy format daty!");
			request.getRequestDispatcher("determineDateFuneral.jsp").forward(request, response);
			return;
		}
        SimpleDateFormat formatterDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dEx, dFu;
		try {
			dEx = formatterDate.parse(dateExposure);
			dFu = formatterDateTime.parse(dateFuneral);
		} catch (ParseException e) {
			request.setAttribute("id", id);
			request.setAttribute("pogrzeb", p);
			request.setAttribute("errorMsg", "Nieprawid³owy format daty!");
			return;
		}

		if (LockManager.isFuneralLocked(id, session)) {
			response.sendRedirect("locked.html");
			return;
		}
		
		p.setDataWystawieniaCiala(dEx);
		p.setDataPogrzebu(dFu);
		dao.update(p);
		
		LockManager.removeLock(id);
		
		response.sendRedirect("funerals.html");
	}
}
