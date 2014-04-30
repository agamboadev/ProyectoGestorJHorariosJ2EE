package filtros;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Validador;
import utils.WebUtil;

public class FiltroUsuarioAcceso implements Filter {

    public FiltroUsuarioAcceso() {
    	
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("Entra en el filtro de acceso");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		Map<String, String> errores = new HashMap<String, String>();
		String dni = req.getParameter("dni");
		String pass = req.getParameter("pass");

		
		Validador.validarObligatorio(errores, "dni", dni, true);
		Validador.validarObligatorio(errores, "pass", pass, true);

		if (!errores.isEmpty()) {
			req.setAttribute("errores", errores);
			WebUtil.forwardTo(req, resp, "/login.jsp");			
		}
		else
		{
			req.setAttribute("dni", dni);		
			chain.doFilter(req, resp);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
