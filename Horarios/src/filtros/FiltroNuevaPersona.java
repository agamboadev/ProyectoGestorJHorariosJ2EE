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
 * Servlet Filter implementation class FiltroNuevaPersona
 */
public class FiltroNuevaPersona implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroNuevaPersona() {
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
		System.out.println("Entra en el filtro de nuevo usuario");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		Map<String, String> errores = new HashMap<String, String>();
		String dni = req.getParameter("dni");
		String pass = req.getParameter("pass");
		String passRepetido = req.getParameter("passRepetido");
		String nombre = req.getParameter("nombre");
		String apellido1  = request.getParameter("apellido1");
		//String apellido2  = request.getParameter("apellido2");
		//String telefono  = request.getParameter("telefono");
		//String movil  = request.getParameter("movil");
		//String email  = request.getParameter("email");

		
		Validador.validarDNI(errores, "dni", dni, true);
		Validador.validarPassRepetido(errores, "passRepetido", passRepetido, true, pass);		
		Validador.validarPass(errores, "pass", pass, true);		
		Validador.validarCampoObligatorio(errores, "nombre", nombre);
		Validador.validarCampoObligatorio(errores, "apellido1", apellido1);

		if (!errores.isEmpty()) {
			req.setAttribute("errores", errores);
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/zonaPrivada/nuevoPersona.jsp");

			requestDispatcher.forward(req, resp);
		}
		else
		{
			req.setAttribute("dni", dni);		
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
