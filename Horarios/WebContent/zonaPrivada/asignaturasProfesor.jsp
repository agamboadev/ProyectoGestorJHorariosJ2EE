<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Asignaturas Profesor</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />
</head>
<body>
	<div id="pagina">
        <jsp:include page="/plantilla/cabecera.jsp"></jsp:include>
        <jsp:include page="/plantilla/menu.jsp"></jsp:include>
        <div id="bloqueColumnaIzq">
        	<div id="bloqueIzq">
        		Asignaturas que imparte el profesor
        	</div> 
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">        		
				<c:choose>
					<c:when test="${!empty vAsignaturaCurso }">
						<table class="tablaInfo">
							<tr>
								<td>Asignatura</td>
								<td>Curso</td>
							</tr>
							<c:forEach var="asignaturaCurso" items="${vAsignaturaCurso }">
								<tr>
									<td>${asignaturaCurso.asignaturaNombre }</td>
									<td>${asignaturaCurso.cursoNombre }</td>	
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						No está incluido en ninguna asignatura.
					</c:otherwise>
				</c:choose>				
			</div>        	
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>