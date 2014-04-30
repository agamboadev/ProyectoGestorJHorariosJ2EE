package filtros;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Validador;

/**
 * Servlet Filter implementation class FiltroCursoModif
 */
public class FiltroCursoModif implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroCursoModif() {
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
		System.out.println("Entra en el filtro de modif curso");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		Map<String, String> errores = new HashMap<String, String>();
		String nombre = req.getParameter("cursoNombre");
		String durHoras = req.getParameter("cursoDurHoras");
		String idCurso = req.getParameter("idCurso");

			
		Validador.validarCampoObligatorio(errores, "cursoNombre", nombre);
		Validador.validarDurHoras(errores, "cursoDurHoras", durHoras, true);

		if (!errores.isEmpty()) {		
			req.setAttribute("errores", errores);
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/ServCursoMostrar?idCurso=" + idCurso);
			requestDispatcher.forward(req, resp);
		}
		else
		{
			req.setAttribute("cursoNombre", nombre);		
			req.setAttribute("cursoDurHoras", durHoras);
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
