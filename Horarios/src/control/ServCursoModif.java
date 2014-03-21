package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Curso;
import modelo.GestorBD;
import utils.ServletUtil;

public class ServCursoModif extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServCursoModif() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el Servlet ServCursoModif");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		Curso curso = new Curso();
		if (request.getParameter("idCurso") != null) {			
			curso.setIdCurso(Integer.parseInt(request.getParameter("idCurso")));			
		} else {
			curso.setIdCurso(0);
		}
		
		curso.setNombre(request.getParameter("cursoNombre"));
		
		if (request.getParameter("cursoDurHoras") != null) {
			curso.setDurHoras(Integer.parseInt(request.getParameter("cursoDurHoras")));
		} else {
			curso.setDurHoras(0);
		}
		
		GestorBD gestorBD = new GestorBD(descripcionUsuario);	
		String ruta = getServletContext().getContextPath();
		boolean modificado = gestorBD.modificarCurso(curso);
		gestorBD.desconectar();
		
		if (modificado) {
			System.out.println("Curso modificado con exito");
		} else {
			System.out.println("Error al modificar el curso");
		}
		response.sendRedirect(ruta + "/ServCursoMostrar?idCurso=" + curso.getIdCurso());
	}

}
