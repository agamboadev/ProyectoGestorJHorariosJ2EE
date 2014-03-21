<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Listado Cursos</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />
</head>
<body>
	<div id="pagina">
        <jsp:include page="/plantilla/cabecera.jsp"></jsp:include>
        <jsp:include page="/plantilla/menu.jsp"></jsp:include>
        <div id="bloqueColumnaIzq">
        	<div id="bloqueIzq">
        		<p>Listado de cursos disponibles.</p>
        	</div> 
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">        		
				<c:choose>
					<c:when test="${!empty vCursos }">
						<table class="tablaInfo">
							<tr>
								<td>Nombre</td>
								<td>Horas</td>
								<td>Eliminar</td>									
							</tr>
							<c:forEach var="curso" items="${vCursos }">
								<tr>
									<td><a href='${pageContext.request.contextPath }/ServCursoMostrar?idCurso=${curso.idCurso }'>${curso.nombre }</a></td>
									<td>${curso.durHoras }</td>	
									<td>									
										<form method="post" action="${pageContext.request.contextPath }/ServCursoEliminar">
											<input type="hidden" name="eliminarIdCurso" value="${curso.idCurso }" />
											<input type="image" name="~imgEliminar" src="./imagenes/eliminar.png" />
										</form>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						No hay nigún curso.
					</c:otherwise>
				</c:choose>				
			</div>        	
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>