package control;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Curso;
import modelo.GestorBD;
import modelo.Persona;
import utils.WebUtil;

/**
 * Servlet implementation class ServListaUsuarios
 */
public class ServUsuariosLista extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServUsuariosLista() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el servlet ServUsuariosLista GET");
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el servlet ServUsuariosLista POST");
		HttpSession sesion = request.getSession();
		Persona usuarioActivo = (Persona)sesion.getAttribute("persona");
		int opPerfil = 1;		
		if (usuarioActivo != null) {
			// Alumno. Se le redirecciona a inicioPrivada. No tiene persimo para esto
			if (usuarioActivo.getIdPerfil() == 3) {				
				response.sendRedirect("zonaPrivada/inicioPrivado.jsp");
			} else {
				if (usuarioActivo.getIdPerfil() == 2) {
					opPerfil = 3;
				} else if (request.getParameter("op") != null) {
					opPerfil = Integer.parseInt(request.getParameter("op"));
				}
				int idPerfil = usuarioActivo.getIdPerfil();
				Vector<Persona> vPersonas = new Vector<Persona>();
				if ((idPerfil == GestorBD.ADMINISTRADOR) || (idPerfil == GestorBD.PROFESOR)) {				
					GestorBD gestorBD = new GestorBD(usuarioActivo.descripcionUsuario());									
					if (opPerfil == GestorBD.ALUMNO) {						
						System.out.println("********************** Listad de alumnos");
						// si el perfil de alumno, se crea el vector con los cursos
						Vector<Curso> vCursos = new Vector<Curso>();
						// El curso seleccionado puede venir como parametro 'curso'
						int idCurso = 0;
						// Si el usuario activo es el profesor, solo se muestran los cursos en los que está asignado
						if (idPerfil == GestorBD.PROFESOR) {
							vCursos = gestorBD.listadoCursos(usuarioActivo.getIdPersona());
						} else {
							vCursos = gestorBD.listadoCursos();
						}						
						if (request.getParameter("curso") != null) {
							idCurso = Integer.parseInt(request.getParameter("curso"));
						} else if (vCursos.size()>0) {
							idCurso = vCursos.get(0).getIdCurso();
						}
						vPersonas = gestorBD.listadoPersonasCurso(idCurso);
						System.out.println("vCursos.size(): " + vCursos.size());
						request.setAttribute("vCursos", vCursos);
						request.setAttribute("idCurso", idCurso);
					} else {
						vPersonas = gestorBD.listadoPersonas(opPerfil);
					}
					System.out.println("Numero de elementos vPersonas: " + vPersonas.size());
					gestorBD.desconectar();
				} 					
				request.setAttribute("vPersonas", vPersonas);
				WebUtil.forwardTo(request, response, "zonaPrivada/usuariosLista.jsp");
			}
		}
	}

}
