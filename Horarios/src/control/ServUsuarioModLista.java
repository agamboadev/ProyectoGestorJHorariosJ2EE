package control;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Curso;
import modelo.GestorBD;
import modelo.Perfil;
import modelo.Persona;
import utils.ServletUtil;

/**
 * Servlet implementation class ServUsuarioModLista
 */
public class ServUsuarioModLista extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServUsuarioModLista() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el Servlet ServUsuarioModLista");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		Persona personaActiva = (Persona) session.getAttribute("persona");
		
		String modificarDni = "";		
		if (request.getParameter("modificarDni") != null) {
			System.out.println("request.getParameter('modificarDni') es distinto de null");
			modificarDni = request.getParameter("modificarDni");
		}
		System.out.println("*** modificarDni: " + modificarDni);
		
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
		System.out.println("vCursos.size(): " + vCursos.size());
		Persona persona = gestorBD.buscarPersona(modificarDni);
		persona.toString();
		gestorBD.desconectar();
		request.setAttribute("perfiles", perfiles);
		request.setAttribute("vCursos", vCursos);
		request.setAttribute("idPersona", persona.getIdPersona());
		request.setAttribute("dni", persona.getDni());
		request.setAttribute("nombre", persona.getNombre());
		request.setAttribute("apellido1", persona.getApellido1());
		request.setAttribute("apellido2", persona.getApellido2());
		request.setAttribute("telefono", persona.getTelefono());
		request.setAttribute("movil", persona.getMovil());
		request.setAttribute("email", persona.getEmail());
		request.setAttribute("idPerfil", persona.getIdPerfil());
		
		String address = "zonaPrivada/usuarioModif.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);	
	}
}
