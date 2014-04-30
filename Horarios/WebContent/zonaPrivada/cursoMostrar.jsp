<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Mostrar Curso</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />
</head>
<body>
	<div id="pagina">
        <jsp:include page="/plantilla/cabecera.jsp"></jsp:include>
        <jsp:include page="/plantilla/menu.jsp"></jsp:include>
        <div id="bloqueColumnaIzq">
        	<div id="bloqueIzq">
        		Datos Curso seleccionado
        	</div> 
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">
        		<form method="post" action="${pageContext.request.contextPath }/ServCursoModif">
					<table>
						<tr>
							<td colspan="3">Curso Seleccionado</td>
						</tr>
						<tr>							
							<td>Nombre: </td>
							<td><input type="text" name="cursoNombre" value="${curso.nombre }" /></td>
							<td><input type="submit" name="btnModifAsignatura" value="Modificar" /></td>
						</tr>
						<tr>							
							<td>Duración: </td>
							<td><input type="text" name="cursoDurHoras" value="${curso.durHoras }" /> horas</td>
							<td></td>
						</tr>
					</table>
					<input type="hidden" name="idCurso" value="${curso.idCurso }" />
				</form>
					<br />
					<table>
						<tr>
							<td ><h1>Asignaturas</h1></td>
						</tr>
						<c:forEach var="asignatura" items="${vAsignaturas }">
							<tr>
								<td>
									<form method="post" action="${pageContext.request.contextPath }/ServAsignaturaModif">
										<table>
											<tr>
												<td>Nombre: </td>
												<td><input type="text" name="asignaturaNombre"  value="${asignatura.nombre }"/></td>
												<td><input type="submit" name="btnModifAsignatura" value="Modificar" /></td>
											</tr>
											<tr>
												<td>Duración: </td>
												<td><input type="text" name="asignaturaDurHoras"  value="${asignatura.durHoras }"/> horas</td>
												<td></td>
											</tr>
											<tr>
												<td>Profesor: </td>
												<td>
													<select name="selProfesor">
														<c:forEach var="profesor" items="${vProfesores }">
															<c:choose>
																<c:when test="${profesor.idPersona == asignatura.idProfesor }">
																	<option value="${profesor.idPersona }" selected="selected">${profesor.nombre } ${profesor.apellido1 } ${profesor.apellido2 }</option>
																</c:when>
																<c:otherwise>
																	<option value="${profesor.idPersona }">${profesor.nombre } ${profesor.apellido1 } ${profesor.apellido2 }</option>
																</c:otherwise>
															</c:choose>																												
														</c:forEach>																		
													</select>
												</td>
												<td></td>
											</tr>
										</table>
										<input type="hidden" name="idAsignatura" value="${asignatura.idAsignatura }" />
										<input type="hidden" name="idCurso" value="${curso.idCurso }" />
									</form>
								</td>
							</tr>							
						</c:forEach>						
					</table>					
					<form method="post" action="${pageContext.request.contextPath }/ServAsignaturaNueva"> 
						<table class="tablaInfo">
							<tr>
								<td colspan="2">Nueva Asignatura</td>
							</tr>
								<tr>
								<td>Nombre: </td>
								<td><input type="text" name="nombre" /> ${errores['nombre'] }</td>
							</tr>
							<tr>
								<td>Duración: </td>
								<td><input type="text" name="durHoras" /> horas ${errores['durHoras'] }</td>
							</tr>
							<tr>
								<td>Profesor: </td>
								<td>
									<select name="selProfesor">
										<c:forEach var="profesor" items="${vProfesores }">														
											<option value="${profesor.idPersona }">${profesor.nombre } ${profesor.apellido1 } ${profesor.apellido2 }</option>
										</c:forEach>																		
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="2"><input type="submit" name="enviar" value="Enviar" /></td>
							</tr>
						</table>
						<input type="hidden" name="idCurso" value="${curso.idCurso }" />
					</form>	
			</div>        	
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>