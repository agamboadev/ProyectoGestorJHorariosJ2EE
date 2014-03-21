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

import modelo.Curso;
import modelo.GestorBD;

import utils.ServletUtil;
import utils.Validador;
import utils.WebUtil;

public class FiltroHorario implements Filter {

    public FiltroHorario() {
        
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Entra en el filtro de horario");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String descripcionUsuario = ServletUtil.obtenerDescripcionUsuActivo(req);
		
		// Se comprueba si existe el parametro idHorario.
		// 		- Si existe indica que se quiere mostrar un horario ya existente, con lo que tiene que 
		//   	  'evitar' el filtro, por que no ha existen los campos a validar
		//		- Si no existe significa que viene de la página de creación de horarios y SI hay que validar los campos
		if(req.getParameter("idHorario")==null) {
			Map<String, String> errores = new HashMap<String, String>();
			int anio = 0;
			int numPorciones = 0 ;

			Validador.validarAnio(errores, "anio", req.getParameter("anio"), true);
			Validador.validarNumPorciones(errores, "numPorciones", req.getParameter("numPorciones"), true);			

			if (!errores.isEmpty()) {
				req.setAttribute("errores", errores);
				
				Vector<Curso> vCursos = new Vector<Curso>();
				GestorBD gestorBD = new GestorBD(descripcionUsuario);	
				vCursos = gestorBD.listadoCursos();
				request.setAttribute("vCursos", vCursos);		
				gestorBD.desconectar();

				WebUtil.forwardTo(req, resp, "/zonaPrivada/horarioNuevo.jsp");			
			}
			else
			{
				anio = Integer.parseInt(req.getParameter("anio"));
				numPorciones = Integer.parseInt(req.getParameter("numPorciones"));
				
				if (anio == 0) {
					req.setAttribute("anio", "");	
				} else {
					req.setAttribute("anio", anio);	
				}	
				if (numPorciones == 0) {
					req.setAttribute("numPorciones", "");	
				} else {
					req.setAttribute("numPorciones", numPorciones);	
				}
				chain.doFilter(req, resp);
			}
		} else {			
			// Si existe el campo idHorario
			int idHorario = 0;
			if (Validador.isNumeric(req.getParameter("idHorario"))) {
				idHorario = Integer.parseInt(req.getParameter("idHorario"));
			}
			System.out.println("Existe idHorario: " + idHorario);
			req.setAttribute("idHorario", idHorario);
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
