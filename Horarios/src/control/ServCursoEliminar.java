package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ServletUtil;

import modelo.Curso;
import modelo.GestorBD;

/**
 * Servlet implementation class ServCursoEliminar
 */
public class ServCursoEliminar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServCursoEliminar() {
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
		System.out.println("Entra en el Servlet ServCursoEliminar");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		int eliminarIdCurso = 0;
		if (request.getParameter("eliminarIdCurso") != null) {
			eliminarIdCurso = Integer.parseInt(request.getParameter("eliminarIdCurso"));
			
			GestorBD gestorBD = new GestorBD(descripcionUsuario);	
			
			Curso curso = gestorBD.buscarCurso(eliminarIdCurso);
			gestorBD.desconectar();
			
			request.setAttribute("eliminarIdCurso", eliminarIdCurso);
			request.setAttribute("cursoElim", curso);			
		}
		
		if (request.getParameter("eliminar") != null) {
			GestorBD gestorBD = new GestorBD(descripcionUsuario);	
			Curso curso = gestorBD.buscarCurso(eliminarIdCurso);
			if (gestorBD.elimianrCurso(curso)) {
				System.out.println("Curso eliminado correctamente");
			} else {
				System.out.println("Error al eliminar curso");
			}
			gestorBD.desconectar();
			
			String address = "ServCursosLista";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);	
		} else {
			String address = "zonaPrivada/cursoEliminar.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}	}

}
