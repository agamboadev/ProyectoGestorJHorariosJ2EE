package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.GestorBD;
import modelo.Persona;
import modelo.Persona_Curso;
import utils.ServletUtil;
import utils.Validador;

public class ServUsuarioNueva extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServUsuarioNueva() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el Servlet ServUsuarioNueva");
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		String dni = request.getParameter("dni");
		String pass = request.getParameter("pass");
		String nombre = request.getParameter("nombre");
		String apellido1 = request.getParameter("apellido1");
		String apellido2 = request.getParameter("apellido2");
		String telefono = request.getParameter("telefono");
		String movil = request.getParameter("movil");
		String email = request.getParameter("email");
		int idPerfil =  Integer.parseInt(request.getParameter("selPerfil"));
		int idCurso = 0;
		if (request.getParameter("selCurso") != null) {
			if (Validador.isNumeric(request.getParameter("selCurso"))) {
				idCurso = Integer.parseInt(request.getParameter("selCurso"));
			}			
		}		
		
		Persona persona = new Persona(dni, pass, nombre, apellido1, apellido2, telefono, movil, email, idPerfil);		
		
		GestorBD gestorBD = new GestorBD(descripcionUsuario);
		boolean insertado= gestorBD.insertarPersona(persona);
		if (insertado) {			
			System.out.println("Persona con dni " + persona.getDni() + "insertado correctamente.");
			
			// una vez que se ha insertado la persona, si el perfil es de alumno,  se obtiene su id a través del dni
			// para añadir su correspondiente registro en persona_curso
			if (persona.getIdPerfil()==GestorBD.ALUMNO) {
				Persona pers = gestorBD.buscarPersona(persona.getDni());
				Persona_Curso pers_curso = gestorBD.buscarPersona_Curso(pers.getIdPersona());
				// Si ya existe un registro en la base de datos, se modifica. Si no, se inserta
				if (pers_curso != null) {
					pers_curso.setIdCurso(idCurso);
					gestorBD.modificarPersona_Curso(pers_curso);
				} else {
					Persona_Curso persona_curso = new Persona_Curso();
					persona_curso.setIdPersona(pers.getIdPersona());
					persona_curso.setIdCurso(idCurso);
					gestorBD.insertarPersona_Curso(persona_curso);
				}
			}			
		
			response.sendRedirect("ServUsuariosLista?op=" + persona.getIdPerfil());			
		} else {
			System.out.println("Error al insertar la persona");
			String address = "zonaPrivada/usuarioNuevo.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}
		gestorBD.desconectar();
	}

}
