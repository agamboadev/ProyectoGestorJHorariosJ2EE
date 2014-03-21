package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.ServletUtil;
import utils.Validador;
import utils.WebUtil;

import modelo.GestorBD;
import modelo.Perfil;
import modelo.Persona;

/**
 * Servlet implementation class ServAccesoUsuario
 */
public class ServUsuarioAcceso extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String ruta;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServUsuarioAcceso() {
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
		System.out.println("Entra en el post del servlet ServUsuarioAcceso");
				
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		String dni = (String) request.getParameter("dni");
		String pass = (String) request.getParameter("pass");
		
		ruta = getServletContext().getContextPath();
		System.out.println("Ruta ContextPath: " + getServletContext().getContextPath());
		System.out.println("Ruta: " + ruta);
		
		if (dni != null) {
			System.out.println("DNI: " + dni);
			GestorBD gestorBD = new GestorBD(descripcionUsuario);			
			Persona persona = gestorBD.buscarPersona(dni, pass);
			if (persona != null) {
				System.out.println("Datos del cliente:");
				System.out.println("-- DNI: " + persona.getDni());
				System.out.println("-- Nombre: " + persona.getNombre());
				System.out.println("-- Apellido1: " + persona.getApellido1());
				System.out.println("-- Apellido2: " + persona.getApellido2());
				System.out.println("-- Email: " + persona.getEmail());
				System.out.println("-- Telefono: " + persona.getTelefono());
				System.out.println("-- Movil: " + persona.getMovil());
				HttpSession session = ((HttpServletRequest)request).getSession();
			    session.setAttribute("persona",persona);
			    
			    response.sendRedirect(ruta + "/zonaPrivada/inicioPrivado.jsp");
			} else {
				Map<String, String> errores = new HashMap<String, String>();
				errores.put("dni", Validador.errorCabecera + GestorBD.PERSONA_NO_ENCONTRADA + Validador.errorPie);
				request.setAttribute("errores", errores);
				WebUtil.forwardTo(request, response, "/login.jsp");
			}
			gestorBD.desconectar();
		} else {
			System.out.println("DNI obtenido vacío");
		}
	}

}
