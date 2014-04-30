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

public class ServCursoNuevo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServCursoNuevo() {
        super();
            }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		System.out.println("Entra en el Servlet ServCursoNuevo");
		String nombre = request.getParameter("nombre");
		int durHoras = Integer.parseInt(request.getParameter("durHoras"));		
		
		Curso curso = new Curso(nombre, durHoras);		
		
		GestorBD gestorBD = new GestorBD(descripcionUsuario);
		boolean insertado= gestorBD.insertarCurso(curso);
		if (insertado) {			
			System.out.println("Curso con nombre " + curso.getNombre() + "insertado correctamente.");
			response.sendRedirect(getServletContext().getContextPath() + "/ServCursosLista");
		} else {
			System.out.println("Error al insertar el curso");
			String address = "zonaPrivada/cursoNuevo.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}
		gestorBD.desconectar();
	}

}
