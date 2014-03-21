package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Asignatura;
import modelo.GestorBD;
import utils.ServletUtil;

public class ServAsignaturaModif extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServAsignaturaModif() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el Servlet ServAsignaturaModif");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		Asignatura asignatura = new Asignatura();		
		if (request.getParameter("idAsignatura") != null) {			
			asignatura.setIdAsignatura(Integer.parseInt(request.getParameter("idAsignatura")));			
		} else {
			asignatura.setIdAsignatura(0);
		}

		if (request.getParameter("idCurso") != null) {
			asignatura.setIdCurso(Integer.parseInt(request.getParameter("idCurso")));
		}
		
		if (request.getParameter("selProfesor") != null) {
			asignatura.setIdProfesor(Integer.parseInt(request.getParameter("selProfesor")));
		}
		
		asignatura.setNombre(request.getParameter("asignaturaNombre"));
		
		if (request.getParameter("asignaturaDurHoras") != null) {
			asignatura.setDurHoras(Integer.parseInt(request.getParameter("asignaturaDurHoras")));
		} else {
			asignatura.setDurHoras(0);
		}
		
		GestorBD gestorBD = new GestorBD(descripcionUsuario);	
		String ruta = getServletContext().getContextPath();
		boolean modificado = gestorBD.modificarAsignatura(asignatura);
		gestorBD.desconectar();
		
		if (modificado) {
			System.out.println("Asignatura modificada con exito");
		} else {
			System.out.println("Error al modificar la asignatura");
			String address = "zonaPrivada/inicioPrivado.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}
		response.sendRedirect(ruta + "/ServCursoMostrar?idCurso=" + asignatura.getIdCurso());
	}

}
