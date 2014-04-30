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
 * Servlet Filter implementation class FiltroNuevaPersona
 */
public class FiltroUsuarioNueva implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroUsuarioNueva() {
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
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		Persona personaActiva = (Persona) session.getAttribute("persona");
		
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
			HttpSession sesion = req.getSession();
			Persona usuarioActivo = (Persona)sesion.getAttribute("persona");
			String descripcionUsuario = "";
			
			GestorBD gestorBD = new GestorBD(descripcionUsuario);	
			Vector<Perfil> perfiles = gestorBD.listadoPerfiles();
			Vector<Curso> vCursos;
			if (personaActiva.getIdPerfil() == GestorBD.PROFESOR) {
				vCursos = gestorBD.listadoCursos(personaActiva.getIdPersona());
			} else {
				vCursos = gestorBD.listadoCursos();
			}
			gestorBD.desconectar();
			
			if (usuarioActivo != null) {
				descripcionUsuario = usuarioActivo.descripcionUsuario();
			}
			
			req.setAttribute("errores", errores);
			req.setAttribute("perfiles", perfiles);
			req.setAttribute("vCursos", vCursos);
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/zonaPrivada/usuarioNuevo.jsp");

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
