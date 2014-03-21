package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Curso;
import modelo.GestorBD;
import modelo.Horario;
import utils.ServletUtil;

public class ServHorarioEliminar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServHorarioEliminar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el Servlet ServHorarioEliminar");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		int eliminarIdHorario = 0;
		if (request.getParameter("eliminarHorario") != null) {
			eliminarIdHorario = Integer.parseInt(request.getParameter("eliminarHorario"));
			
			GestorBD gestorBD = new GestorBD(descripcionUsuario);	
			
			Horario horario = gestorBD.buscarHorario(eliminarIdHorario);
			Curso curso = gestorBD.buscarCurso(horario.getIdCurso());
			gestorBD.desconectar();
			
			request.setAttribute("eliminarIdHorario", eliminarIdHorario);
			request.setAttribute("horarioElim", horario);	
			request.setAttribute("curso", curso);
			request.setAttribute("mes", ServletUtil.ARRAY_MESES[horario.getMes()]);
			request.setAttribute("anio", horario.getAnio());
		}
		
		if (request.getParameter("eliminar") != null) {
			GestorBD gestorBD = new GestorBD(descripcionUsuario);	
			Horario horario = gestorBD.buscarHorario(eliminarIdHorario);
			if (gestorBD.eliminarHorario(horario)) {
				System.out.println("Horario eliminado correctamente");
			} else {
				System.out.println("Error al eliminar horario");
			}
			gestorBD.desconectar();
			
			String address = "ServHorariosLista";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);	
		} else {
			String address = "zonaPrivada/horarioEliminar.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}	
	}
}
