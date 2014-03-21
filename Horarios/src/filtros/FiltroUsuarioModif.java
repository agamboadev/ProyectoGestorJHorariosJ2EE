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
 * Servlet Filter implementation class FiltroUsuarioModif
 */
public class FiltroUsuarioModif implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroUsuarioModif() {
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
		System.out.println("Entra en el filtro de modificar usuario");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		Persona personaActiva = (Persona) session.getAttribute("persona");
		
		Map<String, String> errores = new HashMap<String, String>();
		System.out.println("IDPERSONA: " + request.getParameter("idPersona"));
		int idPersona = Integer.parseInt(request.getParameter("idPersona"));
		System.out.println("DNI: " + req.getParameter("dni"));
		String dni = req.getParameter("dni");		
		String pass = req.getParameter("pass");
		String passRepetido = req.getParameter("passRepetido");
		System.out.println("NOMBRE: " + req.getParameter("nombre"));
		String nombre = req.getParameter("nombre");		
		System.out.println("APELLIDO1: " + req.getParameter("apellido1"));
		String apellido1  = req.getParameter("apellido1");
		System.out.println("APELLIDO2: " + req.getParameter("apellido2"));
		String apellido2  = req.getParameter("apellido2");		
		String telefono  = req.getParameter("telefono");
		String movil  = req.getParameter("movil");
		String email  = req.getParameter("email");
		int idPerfil = Integer.parseInt(req.getParameter("selPerfil"));
		
		Persona usuarioMod = (Persona)req.getAttribute("usuarioMod");
		if (usuarioMod != null) {
			System.out.println("Dni: " + usuarioMod.getDni());
		} else {
			System.out.println("No existe el valor usuarioMod");
		}

		
		Validador.validarDNI(errores, "dni", dni, true);
		// Solo comprobamos el pass si es distinto de espacios. Si el campos pass está a espacion,
		// no se realiza la modificación del pass
		if (!pass.equals("")) {
			Validador.validarPassRepetido(errores, "passRepetido", passRepetido, true, pass);		
			Validador.validarPass(errores, "pass", pass, true);
		}			
		Validador.validarCampoObligatorio(errores, "nombre", nombre);
		Validador.validarCampoObligatorio(errores, "apellido1", apellido1);

		if (!errores.isEmpty()) {
			HttpSession sesion = req.getSession();
			Persona usuarioActivo = (Persona)sesion.getAttribute("persona");
			String descripcionUsuario = "";
			
			GestorBD gestorBD = new GestorBD(descripcionUsuario);	
			Vector<Perfil> perfiles = new Vector<Perfil>();
			Vector<Curso> vCursos;
			if (personaActiva.getIdPerfil() == GestorBD.PROFESOR) {
				vCursos = gestorBD.listadoCursos(personaActiva.getIdPersona());
				perfiles.add(gestorBD.buscarPerfil(GestorBD.ALUMNO));
			} else {
				vCursos = gestorBD.listadoCursos();
				perfiles = gestorBD.listadoPerfiles();
			}	
			gestorBD.desconectar();
			
			if (usuarioActivo != null) {
				descripcionUsuario = usuarioActivo.descripcionUsuario();
			}
			
			req.setAttribute("errores", errores);
			req.setAttribute("usuarioMod", usuarioMod);
			req.setAttribute("perfiles", perfiles);
			req.setAttribute("vCursos", vCursos);
			
			request.setAttribute("idPersona", idPersona);
			request.setAttribute("idPerfil", idPerfil);
			request.setAttribute("dni", dni);
			request.setAttribute("nombre", nombre);
			request.setAttribute("apellido1", apellido1);
			request.setAttribute("apellido2", apellido2);
			request.setAttribute("telefono", telefono);
			request.setAttribute("movil", movil);
			request.setAttribute("email", email);
			request.setAttribute("idPerfil", idPerfil);
			
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/zonaPrivada/usuarioModif.jsp");

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
