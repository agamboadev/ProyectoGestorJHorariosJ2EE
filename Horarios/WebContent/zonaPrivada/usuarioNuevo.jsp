<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Nuevo Usuario</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />
	<script type="text/javascript">
		var perfilAlumno = 3;
		function comprobarSiEsAlumno() {
			var selPerfil = document.getElementById('selPerfil');
			var selCurso = document.getElementById('selCurso');
			var lblCurso = document.getElementById('lblCurso');
			var lblNoCursos  = document.getElementById('lblNoCursos');
			var enviar = document.getElementById('enviar');
			if (perfilAlumno==selPerfil.options[selPerfil.selectedIndex].value) {
				selCurso.style.display = 'inline';
				lblCurso.style.display = 'inline';
				if (selCurso.length == 0) {
					enviar.style.display = 'none';
					lblNoCursos.style.display = 'inline';
				} else {
					enviar.style.display = 'inline';
					lblNoCursos.style.display = 'none';
				}
			} else {
				selCurso.style.display = 'none';
				lblCurso.style.display = 'none';
				enviar.style.display = 'inline';
				lblNoCursos.style.display = 'none';
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
        	</div>
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">        		
				<form method="post" action="${pageContext.request.contextPath }/ServUsuarioNueva">
					<table>
						<tr>
							<td>DNI:*</td>
							<td><input type="text" name="dni" value="${param.dni }" /> ${errores['dni'] }</td>
						</tr>
						<tr>
							<td>Contraseña:*</td>
							<td><input type="password" name="pass" /> ${errores['pass'] }</td>
						</tr>
						<tr>
							<td>Repetir Contraseña:*</td>
							<td><input type="password" name="passRepetido" /> ${errores['passRepetido'] }</td>
						</tr>
						<tr>
							<td>Nombre:*</td>
							<td><input type="text" name="nombre" value="${param.nombre }" /> ${errores['nombre'] }</td>
						</tr>
						<tr>
							<td>Primer Apellido:*</td>
							<td><input type="text" name="apellido1" value="${param.apellido1 }" /> ${errores['apellido1'] }</td>
						</tr>
						<tr>
							<td>Segundo Apellido:</td>
							<td><input type="text" name="apellido2" value="${param.apellido2 }" /> ${errores['apellido2'] }</td>
						</tr>
						<tr>
							<td>Telefono:</td>
							<td><input type="text" name="telefono" value="${param.telefono }" /> ${errores['telefono'] }</td>
						</tr>
						<tr>
							<td>Movil:</td>
							<td><input type="text" name="movil" value="${param.movil }" /> ${errores['movil'] }</td>
						</tr>
						<tr>
							<td>Email:</td>
							<td><input type="text" name="email" value="${param.email }" /> ${errores['email'] }</td>
						</tr>						
						<tr>
							<td>Perfil:* </td>
							<td>
								<select id="selPerfil" name="selPerfil" onChange="comprobarSiEsAlumno()">
									<c:forEach var="perfil" items="${perfiles }">
										<c:if test="${(persona.idPerfil != 2) || (perfil.idPerfil == 3) }">
											<option value="${perfil.idPerfil }">${perfil.nombre }</option>
										</c:if>									  									  	
									</c:forEach>																		
								</select>
							</td>
						</tr>
						<tr>
							<td>
								<c:choose>
									<c:when test="${persona.idPerfil == 2 }">
										<span id="lblCurso" style="display:inline">Curso:* </span>
									</c:when>
									<c:otherwise>
										<span id="lblCurso" style="display:none">Curso:* </span>
									</c:otherwise>
								</c:choose>							
							</td>
							<td>
								<c:choose>
									<c:when test="${persona.idPerfil == 2 }">
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
							<td colspan="2">								
								<c:choose>
									<c:when test="${persona.idPerfil == 2 }">
										<c:choose>
											<c:when test="${empty vCursos }">
												<span id="lblNoCursos" style="display:inline">No hay cursos disponibles.<br />Sin cursos no se pueden añadir alumno. </span>
											</c:when>
											<c:otherwise>
												<input type="submit" name="enviar" value="Enviar" id="enviar"/>
												<span id="lblNoCursos" style="display:none">No hay cursos disponibles.<br />Sin cursos no se pueden añadir alumno. </span>
											</c:otherwise>
										</c:choose>
									</c:when>									
									<c:otherwise>
										<input type="submit" name="enviar" value="Enviar" id="enviar"/>
										<span id="lblNoCursos" style="display:none">No hay cursos disponibles.<br />Sin cursos no se pueden añadir alumno. </span>
									</c:otherwise>
								</c:choose>								
							</td>
						</tr>		   				
					</table>
				</form>
			</div>        	
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>