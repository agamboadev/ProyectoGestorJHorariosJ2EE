package filtros;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.Curso;
import modelo.GestorBD;
import modelo.Persona;
import utils.ServletUtil;
import utils.Validador;
import utils.WebUtil;

public class FiltroHorariosLista implements Filter {

    public FiltroHorariosLista() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Entra en el filtro de listado de horarios");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		HttpSession sesion = req.getSession();
		Persona usuarioActivo = (Persona)sesion.getAttribute("persona");
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(req);
		
		if (request.getParameter("mostrar")!=null) {
			
			
			Map<String, String> errores = new HashMap<String, String>();
			int anio = 0;

			Validador.validarAnio(errores, "anio", req.getParameter("anio"), true);		

			if (!errores.isEmpty()) {
				req.setAttribute("errores", errores);
				
				Vector<Curso> vCursos = new Vector<Curso>();
				GestorBD gestorBD = new GestorBD(descripcionUsuario);
				switch (usuarioActivo.getIdPerfil()) {
					case GestorBD.ADMINISTRADOR:
						vCursos = gestorBD.listadoCursos();
						break;
					case GestorBD.PROFESOR:
						vCursos = gestorBD.listadoCursos(usuarioActivo.getIdPersona());	
						break;
					case GestorBD.ALUMNO:
						Curso curso = gestorBD.obtenerCursoAlumno(usuarioActivo.getIdPersona());
						if (curso!=null) {
							vCursos.add(curso);
						}			
						break;
				}
				request.setAttribute("vCursos", vCursos);	
				System.out.println("++++++++idCurso: " + req.getParameter("selCurso"));
				request.setAttribute("idCurso", req.getParameter("selCurso"));
				gestorBD.desconectar();

				WebUtil.forwardTo(req, resp, "/zonaPrivada/horariosLista.jsp");			
			}
			else
			{
				anio = Integer.parseInt(req.getParameter("anio"));
				
				if (anio == 0) {
					req.setAttribute("anio", "");	
				} else {
					req.setAttribute("anio", anio);	
				}	
				chain.doFilter(req, resp);
			}
		} else {			
			Vector<Curso> vCursos = new Vector<Curso>();
			GestorBD gestorBD = new GestorBD(descripcionUsuario);
			switch (usuarioActivo.getIdPerfil()) {
				case GestorBD.ADMINISTRADOR:
					vCursos = gestorBD.listadoCursos();
					break;
				case GestorBD.PROFESOR:
					vCursos = gestorBD.listadoCursos(usuarioActivo.getIdPersona());	
					break;
				case GestorBD.ALUMNO:
					Curso curso = gestorBD.obtenerCursoAlumno(usuarioActivo.getIdPersona());
					if (curso!=null) {
						vCursos.add(curso);
					}			
					break;
			}
			request.setAttribute("vCursos", vCursos);			
			gestorBD.desconectar();

			WebUtil.forwardTo(req, resp, "/zonaPrivada/horariosLista.jsp");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
