<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Eliminar Usuario</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />		
</head>
<body>
	<div id="pagina">
        <jsp:include page="/plantilla/cabecera.jsp"></jsp:include>
        <jsp:include page="/plantilla/menu.jsp"></jsp:include>
        <div id="bloqueColumnaIzq">
        	<div id="bloqueIzq">
   				Eliminar usuario
        	</div> 
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">
        		<form method="post" action="${pageContext.request.contextPath }/ServUsuarioEliminar">
					<input type="hidden" name="eliminarDni" value="${eliminarDni }" />
					<table>
						<tr>
							<td colspan="2">¿Eliminar el usuario ${usuarioElim.nombre } ${usuarioElim.apellido1 } ${usuarioElim.apellido2 }?</td>
						</tr>
						<tr>
							<td><input type="submit" name="eliminar" value="Eliminar" /></td>
							<td><input type="button" name="cancelar" value="Cancelar" onclick="location.href='ServUsuariosLista?op=${param.perfilLista }';" /></td>
						</tr>
					</table>
					<input type="hidden" name="perfilLista" value="${param.perfilLista }" />
				</form>        				
			</div>        	
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>