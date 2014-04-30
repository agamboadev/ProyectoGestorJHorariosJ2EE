<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Modificar Usuario</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />
	<script type="text/javascript">
		var perfilAlumno = 3;
		function comprobarSiEsAlumno() {
			var selPerfil = document.getElementById('selPerfil');
			var selCurso = document.getElementById('selCurso');
			var lblCurso = document.getElementById('lblCurso');
			if (perfilAlumno==selPerfil.options[selPerfil.selectedIndex].value) {
				selCurso.style.display = 'inline';
				lblCurso.style.display = 'inline';
			} else {
				selCurso.style.display = 'none';
				lblCurso.style.display = 'none';
			}
		}
	</script>
</head>
<body>
	<div id="pagina">
        <jsp:include page="/plantilla/cabecera.jsp"></jsp:include>
        <jsp:include page="/plantilla/menu.jsp"></jsp:include>
        <div id="bloqueColumnaIzq">
        	<div id="bloqueIzq">
            		<p>- Los campos con asterisco son obligatorios.</p>
            		<p>- Al añadir un nuevo alumno, hay que indicar a que curso pertenece.</p>
            		<p>- Para cambiar la contraseña se intruce la nueva y su confirmación en el campo correpondiente. 
            			 Si no se quiere cambiar, dejar en blanco.</p>
        	</div>
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">        		
				<form method="post" action="${pageContext.request.contextPath }/ServUsuarioModif">
				<input type="hidden" name="idPersona" value="${idPersona }" />
					<table>
						<tr>
							<td>DNI: *</td>
							<td><input type="text" name="dni" value="${dni }" /> ${errores['dni'] }</td>
						</tr>
						<tr>
							<td>Nueva Contraseña:</td>
							<td><input type="password" name="pass" /> ${errores['pass'] }</td>
						</tr>
						<tr>
							<td>Repetir Contraseña:</td>
							<td><input type="password" name="passRepetido" /> ${errores['passRepetido'] }</td>
						</tr> 
						<tr>
							<td>Nombre: *</td>
							<td><input type="text" name="nombre" value="${nombre }" /> ${errores['nombre'] }</td>
						</tr>
						<tr>
							<td>Primer Apellido: *</td>
							<td><input type="text" name="apellido1" value="${apellido1 }" /> ${errores['apellido1'] }</td>
						</tr>
						<tr>
							<td>Segundo Apellido: </td>
							<td><input type="text" name="apellido2" value="${apellido2 }" /> ${errores['apellido2'] }</td>
						</tr>
						<tr>
							<td>Telefono: </td>
							<td><input type="text" name="telefono" value="${telefono }" /> ${errores['telefono'] }</td>
						</tr>
						<tr>
							<td>Movil: </td>
							<td><input type="text" name="movil" value="${movil }" /> ${errores['movil'] }</td>
						</tr>
						<tr>
							<td>Email:</td>
							<td><input type="text" name="email" value="${email }" /> ${errores['email'] }</td>
						</tr>						
						<tr>
							<td>Perfil: *</td>
							<td>
								<select name="selPerfil" id="selPerfil" onchange="comprobarSiEsAlumno()">
									<c:forEach var="perfil" items="${perfiles }">
										<c:choose>
											<c:when test="${idPerfil == perfil.idPerfil }">
												<option selected="selected" value="${perfil.idPerfil }">${perfil.nombre }</option>
											</c:when>													
											<c:otherwise>
												<option value="${perfil.idPerfil }">${perfil.nombre }</option>
											</c:otherwise>
										</c:choose>						  									  	
									</c:forEach>																		
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<c:choose>
										<c:when test="${idPerfil == 3 }">
											<span id="lblCurso" style="display:inline">Curso:* </span>
										</c:when>
										<c:otherwise>
											<span id="lblCurso" style="display:none">Curso:* </span>
										</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${idPerfil == 3 }">
										<select name="selCurso" id="selCurso" style="display:inline">
									</c:when>
									<c:otherwise>
										<select name="selCurso" id="selCurso" style="display:none">
									</c:otherwise>
								</c:choose>
									<c:forEach var="curso" items="${vCursos }">									
										<option value="${curso.idCurso }">${curso.nombre }</option>
									</c:forEach>																		
								</select>
							</td>
						</tr>
						<tr>
							<td><input type="submit" name="modificar" value="Modificar" id="modificar" /></td>
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