package control;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Curso;
import modelo.GestorBD;
import modelo.Persona;
import utils.WebUtil;

/**
 * Servlet implementation class ServListaCursos
 */
public class ServCursosLista extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServCursosLista() {
        super();
      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el servlet ServCursosLista");
				
		HttpSession sesion = request.getSession();
		Persona usuarioActivo = (Persona)sesion.getAttribute("persona");
		
		Vector<Curso> vCursos = new Vector<Curso>();
		
		GestorBD gestorBD = new GestorBD(usuarioActivo.descripcionUsuario());				
		vCursos = gestorBD.listadoCursos();
		System.out.println("Numero de elementos vCursos: " + vCursos.size());
		gestorBD.desconectar();
		
		request.setAttribute("vCursos", vCursos);
		WebUtil.forwardTo(request, response, "zonaPrivada/cursosLista.jsp");
	}

}
