package mbud.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mbud.data.UserData;

/**
 * Servlet Filter implementation class UserPrivsFilter
 */
@WebFilter(
		urlPatterns = { 
				"/cart.jsp", 
				"/cart.html",
				"/orderFuneral.jsp",
				"/orderFuneral.html",
				"/orderFuneral_msg.jsp",
				"/orders.jsp",
				"/orders.html",
				"/payOnline.jsp",
				"/payOnline.html",
				"/payOnline_msg.jsp",
				"/logout.html",
				"/AddToCartServlet",
				"/ChangeCartCount",
				"/DeleteFromCart",
				"/OrderServlet"
		})
public class UserPrivsFilter implements Filter {

    /**
     * Default constructor. 
     */
    public UserPrivsFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(true);
		System.out.println("UserPrivsFilter - filtering access to " + req.getRequestURI());
		UserData data = (UserData) session.getAttribute("userData");
		if (data == null) {
			System.out.println("UserPrivsFilter - no user data found. Creating a new one...");
			data = new UserData();
			session.setAttribute("userData", data);
		}
		if (!data.isLoggedIn()) {
			System.out.println("UserPrivsFilter - user tries to access resource without being logged in!");
			((HttpServletResponse) response).sendRedirect("index.html");
			return;
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
