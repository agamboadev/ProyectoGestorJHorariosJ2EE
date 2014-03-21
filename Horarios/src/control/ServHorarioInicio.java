package control;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ServletUtil;
import utils.WebUtil;

import modelo.Curso;
import modelo.GestorBD;

/**
 * Servlet implementation class ServHorarioInicio
 */
public class ServHorarioInicio extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServHorarioInicio() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el servlet ServHorarioInicio POST");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(request);
		
		Vector<Curso> vCursos = new Vector<Curso>();
		GestorBD gestorBD = new GestorBD(descripcionUsuario);	
		vCursos = gestorBD.listadoCursos();
		request.setAttribute("vCursos", vCursos);		
		gestorBD.desconectar();
		WebUtil.forwardTo(request, response, "zonaPrivada/horarioNuevo.jsp");
	}
}
