package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ServletUtil;
import utils.Validador;

import modelo.GestorBD;
import modelo.Persona;
import modelo.Persona_Curso;

public class ServUsuarioModif extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServUsuarioModif() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el Servlet ServUsuarioModif");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		String perfilLista = "3";
		if (request.getParameter("perfilLista") != null) {
			perfilLista = request.getParameter("perfilLista");
		}
		
		Persona persona = new Persona();
		persona.setIdPersona(Integer.parseInt(request.getParameter("idPersona")));
		persona.setDni(request.getParameter("dni"));
		persona.setNombre(request.getParameter("nombre"));
		// Cuando se vaya a insertar en la base de datos se comprobará si el campo pass
		// está vacío o no. Si lo está, no se modificará.
		persona.setPass(request.getParameter("pass"));
		persona.setApellido1(request.getParameter("apellido1"));
		persona.setApellido2(request.getParameter("apellido2"));
		persona.setTelefono(request.getParameter("telefono"));
		persona.setMovil(request.getParameter("movil"));
		persona.setEmail(request.getParameter("email"));
		persona.setIdPerfil(Integer.parseInt(request.getParameter("selPerfil")));
		
		int idCurso = 0;
		if (request.getParameter("selCurso") != null) {
			if (Validador.isNumeric(request.getParameter("selCurso"))) {
				idCurso = Integer.parseInt(request.getParameter("selCurso"));
			}			
		}		
		
		GestorBD gestorBD = new GestorBD(descripcionUsuario);	
		String ruta = getServletContext().getContextPath();
		boolean modificado = gestorBD.modificarPersona(persona);		
		
		if (modificado) {
			// Se comprueba si el perfil de la persona modificada es de alumno para insertar o modificar
			if (persona.getIdPerfil()==GestorBD.ALUMNO) {
				Persona pers = gestorBD.buscarPersona(persona.getDni());
				Persona_Curso pers_curso = gestorBD.buscarPersona_Curso(pers.getIdPersona());
				// Si ya existe un registro en la base de datos, se modifica. Si no, se inserta
				if (pers_curso != null) {
					System.out.println("6666666666666666 Se va a modificar persona_curso");
					pers_curso.setIdCurso(idCurso);
					gestorBD.modificarPersona_Curso(pers_curso);
				} else {
					Persona_Curso persona_curso = new Persona_Curso();
					persona_curso.setIdPersona(pers.getIdPersona());
					persona_curso.setIdCurso(idCurso);
					gestorBD.insertarPersona_Curso(persona_curso);
				}
			} else {
				// Si no es un alumno se eliminan sus posibles como anterior alumno en la tabla persona_curso
				gestorBD.eliminarPersona_Curso(persona.getIdPersona());
			}
			
			response.sendRedirect(ruta + "/ServUsuariosLista?op=" + perfilLista);
		} else {
			System.out.println("Error al modificar la persona");
			String address = "zonaPrivada/usuarioModif.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}
		gestorBD.desconectar();
	}
}
