package control;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.ServletUtil;

import modelo.GestorBD;
import modelo.Perfil;
import modelo.Persona;

/**
 * Servlet implementation class ServUsuarioEliminar
 */
public class ServUsuarioEliminar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServUsuarioEliminar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el Servlet ServUsuarioModLista");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		String perfilLista = "3";
		if (request.getParameter("perfilLista") != null) {
			perfilLista = request.getParameter("perfilLista");
		}
		
		String eliminarDni = "";
		if (request.getParameter("eliminarDni") != null) {
			eliminarDni = request.getParameter("eliminarDni");
			
			GestorBD gestorBD = new GestorBD(descripcionUsuario);	
			String ruta = getServletContext().getContextPath();
			Persona persona = gestorBD.buscarPersona(eliminarDni);
			gestorBD.desconectar();
			
			request.setAttribute("eliminarDni", eliminarDni);
			request.setAttribute("usuarioElim", persona);			
		}
		
		if (request.getParameter("eliminar") != null) {
			GestorBD gestorBD = new GestorBD(descripcionUsuario);	
			Persona persona = gestorBD.buscarPersona(eliminarDni);
			if (gestorBD.elimianrPersona(persona)) {
				System.out.println("Persona eliminada correctamente");
			} else {
				System.out.println("Error al eliminar persona");
			}
			gestorBD.desconectar();
			
			String address = "ServUsuariosLista?op=" + perfilLista;
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);	
		} else {
			String address = "zonaPrivada/usuarioEliminar.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
		}
	}

}
