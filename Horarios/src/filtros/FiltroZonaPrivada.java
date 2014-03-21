package filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.GestorBD;
import modelo.Persona;
import utils.WebUtil;

/**
 * Servlet Filter implementation class FiltroZonaPrivada
 */
public class FiltroZonaPrivada implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroZonaPrivada() {
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
		System.out.println("Entra en el filtro FiltroZonaPrivada");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		System.out.println("RequestURI: " + req.getRequestURI());
		
		HttpSession sesion = req.getSession();
		Persona persona = (Persona) sesion.getAttribute("persona");
		
		if (sesion.getAttribute("persona")!=null) {
			if (persona.getIdPerfil() == GestorBD.ADMINISTRADOR) {
				System.out.println("Existe la persona");
				chain.doFilter(request, response);
			} else {
				System.out.println("Existe la persona pero no es profesor ni administrador");
				WebUtil.forwardTo(req, resp, "/zonaPrivada/noPermisoAcceso.jsp");
			}			
		} else {
			System.out.println("No existe la persona");
			WebUtil.forwardTo(req, resp, "/login.jsp");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
