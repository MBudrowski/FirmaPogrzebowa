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
@WebServlet("/panel/addNewClient.html")
public class AddNewClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewClientServlet() {
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
		
		if (data == null || !data.isLoggedIn() || !data.hasWorkerPrivs()) {
			response.sendRedirect("../index.html");
			return;
		}
		
		request.getRequestDispatcher("register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		UserData data = (UserData) request.getSession().getAttribute("userData");
		
		if (data == null || !data.isLoggedIn() || !data.hasWorkerPrivs()) {
			response.sendRedirect("../index.html");
			return;
		}
		
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String phone = request.getParameter("phone");
		phone = phone.replaceAll("\\s", "");
		
		if (name == null || name.isEmpty() ||
				surname == null || surname.isEmpty() || phone == null || phone.isEmpty()) {
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("oldPhone", phone);
			request.setAttribute("registerError", "Wymagane jest podanie wszystkich danych formularza!");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		if (!Validator.validatePhone(phone)) {
			request.setAttribute("oldName", name);
			request.setAttribute("oldSurname", surname);
			request.setAttribute("registerError", "Niepoprawny numer telefonu!");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		KlientDao kDao = new KlientDao();
		Klient klient = new Klient(name, surname, phone);
		kDao.save(klient);
		
		request.getRequestDispatcher("register_msg.jsp").forward(request, response);
	}

}
