package control;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.AsignaturaCurso;
import modelo.GestorBD;
import modelo.Persona;
import utils.WebUtil;

/**
 * Servlet implementation class ServAsignaturaProfesor
 */
public class ServAsignaturaProfesor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServAsignaturaProfesor() {
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
		System.out.println("Entra en el servlet ServAsignaturaProfesor");
		
		HttpSession sesion = request.getSession();
		Persona usuarioActivo = (Persona)sesion.getAttribute("persona");
		
		Vector<AsignaturaCurso> vAsignaturaCurso = new Vector<AsignaturaCurso>();
		
		GestorBD gestorBD = new GestorBD(usuarioActivo.descripcionUsuario());				
		vAsignaturaCurso = gestorBD.listadoAsignaturasProfesor(usuarioActivo.getIdPersona());
		System.out.println("Numero de elementos vCursos: " + vAsignaturaCurso.size());
		gestorBD.desconectar();
		
		request.setAttribute("vAsignaturaCurso", vAsignaturaCurso);
		WebUtil.forwardTo(request, response, "zonaPrivada/asignaturasProfesor.jsp");
	}

}
