package mbud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.UserData;
import mbud.hibernate.dao.KlientDao;
import mbud.hibernate.dao.PracownikDao;
import mbud.hibernate.mapping.Klient;
import mbud.hibernate.mapping.Pracownik;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.html")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		
		if (data.isLoggedIn()) {
			response.sendRedirect("index.html");
		}
		
        try {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
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
		
		if (data.isLoggedIn()) {
			response.sendRedirect("index.html");
		}
		
		String login = request.getParameter("login");
		String pwd = request.getParameter("pwd");
		
		PracownikDao pDao = new PracownikDao();
		pDao.openSession();
		Pracownik pracownik = pDao.findByLoginPassword(login, pwd);
		pDao.closeSession();
		if (pracownik != null) {
			data.setId(pracownik.getId());
			data.setImie(pracownik.getImie());
			data.setNazwisko(pracownik.getNazwisko());
			data.setLogin(pracownik.getLogin());
			data.setWorkerPrivs(true);
			request.getSession().setAttribute("userData", data);
			response.sendRedirect("panel/index.html");
			return;
		}
		
		KlientDao kDao = new KlientDao();
		kDao.openSession();
		Klient klient = kDao.findByEmailPassword(login, pwd);
		kDao.closeSession();
		if (klient != null) {
			data.setId(klient.getId());
			data.setImie(klient.getImie());
			data.setNazwisko(klient.getNazwisko());
			data.setLogin(klient.getEmail());
			data.setWorkerPrivs(false);
			request.getSession().setAttribute("userData", data);
			response.sendRedirect("index.html");
			return;
		}
		
		request.setAttribute("wrongLogin", true);
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

}
