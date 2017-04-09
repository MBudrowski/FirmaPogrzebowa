package mbud.servlets;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mbud.data.LockManager;
import mbud.data.UserData;
import mbud.hibernate.dao.PogrzebDao;
import mbud.hibernate.mapping.Pogrzeb;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/panel/valuateFuneral.html")
public class PanelValuateFuneral extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PanelValuateFuneral() {
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
		dao.closeSession();

		String cost = request.getParameter("cost");
		Double costDouble = null;
		if (cost != null) {
			try {
				costDouble = Double.parseDouble(cost);
			}
			catch (NumberFormatException e) {
				try {
					NumberFormat f = NumberFormat.getInstance(Locale.FRANCE);
					costDouble = f.parse(cost).doubleValue();
				}
				catch (ParseException ex) {
					request.setAttribute("id", id);
					request.setAttribute("pogrzeb", p);
					request.setAttribute("errorMsg", "Nieprawid³owa kwota!");
					request.getRequestDispatcher("valuateFuneral.jsp").forward(request, response);
					return;
				}
			}
		}
		
		if (p.getKoszt() != null && p.getKoszt() > 0.0) {
			response.sendError(404);
			return;
		}
		
		if (costDouble != null && costDouble > 0.0) {

			if (LockManager.isFuneralLocked(id, session)) {
				response.sendRedirect("locked.html");
				return;
			}
			
			LockManager.removeLock(id);
			
			p.setKoszt(costDouble);
			dao.update(p);
			
			response.sendRedirect("funerals.html");
			return;
		}
		else {
			request.setAttribute("id", id);
			request.setAttribute("pogrzeb", p);
		}
		request.getRequestDispatcher("valuateFuneral.jsp").forward(request, response);
	}

}
