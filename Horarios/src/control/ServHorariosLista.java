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
import modelo.Horario;
import modelo.Persona;
import utils.WebUtil;

public class ServHorariosLista extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServHorariosLista() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Entra en el servlet ServhorariosLista");
		
		HttpSession sesion = request.getSession();
		Persona usuarioActivo = (Persona)sesion.getAttribute("persona");
		
		GestorBD gestorBD = new GestorBD(usuarioActivo.descripcionUsuario());
		Vector<Curso> vCursos = new Vector<Curso>();
		Vector<Horario> vHorarios = new Vector<Horario>();
		
		int idCurso = 0;
		int anio = 0;
		
		if (request.getParameter("mostrar")!=null) {
			System.out.println("*********** Se ha puldsado el boton MOSTRAR");
			if (request.getParameter("selCurso")!=null) {
				idCurso = Integer.parseInt(request.getParameter("selCurso"));
				anio = Integer.parseInt(request.getParameter("anio"));
				vHorarios = gestorBD.listadoHorario(idCurso, anio);
				if (vHorarios.size()==0) {
					request.setAttribute("mensaje", "No hay nigún horario.");
				}
				System.out.println("Se crea el vector de horarios. Tamaño: " + vHorarios.size());
				request.setAttribute("vHorarios", vHorarios);
			}			
		}
		
		switch (usuarioActivo.getIdPerfil()) {
			case GestorBD.ADMINISTRADOR:
				vCursos = gestorBD.listadoCursos();
				break;
			case GestorBD.PROFESOR:
				vCursos = gestorBD.listadoCursos(usuarioActivo.getIdPersona());	
				break;
			case GestorBD.ALUMNO:
				System.out.println("Entra COMO ALUMNO********************************************************");
				Curso curso = gestorBD.obtenerCursoAlumno(usuarioActivo.getIdPersona());
				if (curso!=null) {
					vCursos.add(curso);
				}			
				break;
		}
		System.out.println("Numero de elementos vCursos: " + vCursos.size());
		gestorBD.desconectar();
		
		request.setAttribute("vCursos", vCursos);
		WebUtil.forwardTo(request, response, "zonaPrivada/horariosLista.jsp");
	}

}
