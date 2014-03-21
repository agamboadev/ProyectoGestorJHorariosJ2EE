<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Acceso Gestión Horarios</title>
	<link rel="stylesheet" type="text/css" href='${pageContext.request.contextPath }/css/Estilos.css' />	
</head>
<body>
	<div id="pagina">
        <jsp:include page="/plantilla/cabecera.jsp"></jsp:include>
        <jsp:include page="/plantilla/menu.jsp"></jsp:include>
        <div id="bloqueColumnaIzq">
        	<div id="bloqueIzq">
            		<p>Teclee su DNI y contraseña para acceder.</p>
        	</div>
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">        		
				<form method="post" action='${pageContext.request.contextPath }/ServUsuarioAcceso'>
					<table>
						<tr>
							<td>DNI:</td>
							<td><input type="text" name="dni" value="${param.dni }" /> ${errores['dni'] }</td>
						</tr>
						<tr>
							<td>Contraseña:</td>
							<td><input type="password" name="pass" /> ${errores['pass'] }</td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" name="enviar" value="Enviar" /></td>
						</tr>			   				
					</table>
				</form>
			</div>        	
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>