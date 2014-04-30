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
 * Servlet implementation class ServFormAltaPersona
 */
public class ServUsuarioFormAlta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServUsuarioFormAlta() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		System.out.println("Entra en el servlet ServUsuarioFormAlta");
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		Persona personaActiva = (Persona) session.getAttribute("persona");
	    
		GestorBD gestorBD = new GestorBD(descripcionUsuario);	
		Vector<Perfil> perfiles = gestorBD.listadoPerfiles();
		Vector<Curso> vCursos;
		if (personaActiva.getIdPerfil() == GestorBD.PROFESOR) {
			vCursos = gestorBD.listadoCursos(personaActiva.getIdPersona());
		} else {
			vCursos = gestorBD.listadoCursos();
		}
		gestorBD.desconectar();
		request.setAttribute("perfiles", perfiles);
		request.setAttribute("vCursos", vCursos);
		String address = "zonaPrivada/usuarioNuevo.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

}
