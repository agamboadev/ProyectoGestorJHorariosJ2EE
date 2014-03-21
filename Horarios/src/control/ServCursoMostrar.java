package control;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.ServletUtil;

import modelo.Asignatura;
import modelo.Curso;
import modelo.GestorBD;
import modelo.Persona;

public class ServCursoMostrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServCursoMostrar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el servlet ServCursoMostrar POST");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		int idCurso = 0;
		if (request.getParameter("idCurso") != null) {
			idCurso = Integer.parseInt(request.getParameter("idCurso"));
		}
		
		GestorBD gestorBD = new GestorBD(descripcionUsuario);
		Curso curso = gestorBD.buscarCurso(idCurso);
		if (curso != null) {
			// se cargan en un vector las asignaturas del curso
			Vector<Asignatura> vAsignaturas = new Vector<Asignatura>();
			Vector<Persona> vProfesores = new Vector<Persona>();
			vProfesores = gestorBD.listadoPersonas(GestorBD.PROFESOR);
			vAsignaturas = gestorBD.listadoAsignaturasCurso(idCurso);
			request.setAttribute("curso",curso);
			request.setAttribute("vAsignaturas",vAsignaturas);
			request.setAttribute("vProfesores",vProfesores);
			request.getRequestDispatcher("zonaPrivada/cursoMostrar.jsp").forward(request, response);
		} else {
			System.out.println("-- No se ha ncontrado el curso");
			// SE PUEDE CREAR UNA PÁGINA DE ERROR DICIENDO QUE NO SE HA ENCONTRADO EL CURSO
		}
		gestorBD.desconectar();
	}

}
