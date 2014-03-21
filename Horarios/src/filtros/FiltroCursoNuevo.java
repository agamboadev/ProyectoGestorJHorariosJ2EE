package filtros;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Curso;
import modelo.GestorBD;
import modelo.Perfil;
import modelo.Persona;
import utils.Validador;

/**
 * Servlet Filter implementation class FiltroCursoNuevo
 */
public class FiltroCursoNuevo implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroCursoNuevo() {
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
		System.out.println("Entra en el filtro de nuevo curso");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		Persona personaActiva = (Persona) session.getAttribute("persona");
		
		Map<String, String> errores = new HashMap<String, String>();
		String nombre = req.getParameter("nombre");
		String durHoras = req.getParameter("durHoras");

			
		Validador.validarCampoObligatorio(errores, "nombre", nombre);
		Validador.validarDurHoras(errores, "durHoras", durHoras, true);

		if (!errores.isEmpty()) {		
			req.setAttribute("errores", errores);
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/zonaPrivada/cursoNuevo.jsp");
			requestDispatcher.forward(req, resp);
		}
		else
		{
			req.setAttribute("nombre", nombre);		
			req.setAttribute("durhoras", durHoras);
			chain.doFilter(req, resp);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
