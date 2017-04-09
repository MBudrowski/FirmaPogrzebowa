package mbud.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mbud.data.UserData;
import mbud.hibernate.dao.KlientDao;
import mbud.hibernate.mapping.Klient;
import mbud.util.Validator;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/register.html")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
			return;
		}
		
        try {
			request.getRequestDispatcher("register.jsp").forward(request, response);
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
		
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String phone = request.getParameter("phone");
		phone = phone.replaceAll("\\s", "");
		
		if (email == null || email.isEmpty() || pwd == null || pwd.isEmpty() || name == null || name.isEmpty() ||
				surname == null || surname.isEmpty() || phone == null || phone.isEmpty()) {
			request.setAttribute("oldEmail", email);
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("oldPhone", phone);
			request.setAttribute("registerError", "Wymagane jest podanie wszystkich danych formularza!");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		if (!Validator.validateEmail(email)) {
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("oldPhone", phone);
			request.setAttribute("registerError", "Nieprawid³owy adres e-mail!");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		if (!Validator.validatePassword(pwd)) {
			request.setAttribute("oldEmail", email);
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("oldPhone", phone);
			request.setAttribute("registerError", "Has³o musi sk³adaæ siê z przynajmniej 6 znaków!");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		if (!Validator.validatePhone(phone)) {
			request.setAttribute("oldEmail", email);
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("registerError", "Niepoprawny numer telefonu!");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		KlientDao kDao = new KlientDao();
		kDao.openSession();
		Klient klient = kDao.findByEmail(email);
		kDao.closeSession();
		if (klient != null) {
			request.setAttribute("registerError", "Podany adres e-mail jest ju¿ w u¿yciu!");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		klient = new Klient(name, surname, email, pwd, phone);
		kDao.save(klient);
		
		request.getRequestDispatcher("register_msg.jsp").forward(request, response);
	}

}
