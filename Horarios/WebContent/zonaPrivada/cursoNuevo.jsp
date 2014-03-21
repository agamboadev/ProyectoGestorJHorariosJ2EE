<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Nuevo Curso</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />
</head>
<body>
	<div id="pagina">
        <jsp:include page="/plantilla/cabecera.jsp"></jsp:include>
        <jsp:include page="/plantilla/menu.jsp"></jsp:include>
        <div id="bloqueColumnaIzq">
        	<div id="bloqueIzq">
            		<p>Crear nuevo curso.</p>
        	</div>
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">        		
				<form method="post" action="${pageContext.request.contextPath }/ServCursoNuevo">
					<table>
						<tr>
							<td>Nombre: </td>
							<td><input type="text" name="nombre" value="${param.nombre }" /> ${errores['nombre'] }</td>
						</tr>
						<tr>
							<td>Duración: </td>
							<td><input type="text" name="durHoras" value="${param.durHoras }" />horas ${errores['durHoras'] }</td>
						</tr>										
						<tr>
							<td colspan="2"><input type="submit" name="enviar" value="Crear" /></td>
						</tr>		   				
					</table>
				</form>
			</div>        	
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>