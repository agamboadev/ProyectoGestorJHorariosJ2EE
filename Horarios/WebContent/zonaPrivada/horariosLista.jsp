<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Listado Horarios</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />
</head>
<body>
	<div id="pagina">
        <jsp:include page="/plantilla/cabecera.jsp"></jsp:include>
        <jsp:include page="/plantilla/menu.jsp"></jsp:include>
        <div id="bloqueColumnaIzq">
        	<div id="bloqueIzq">
        		Listado de horarios
        	</div> 
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">
        		<c:choose>
					<c:when test="${!empty vCursos }">
						<form method="post" action="${pageContext.request.contextPath }/ServHorariosLista">
			        		<table>
			        			<tr>
									<td>Curso: </td>
									<td>
										<select name="selCurso">
											<c:forEach var="curso" items="${vCursos }">	
												<c:choose>
													<c:when test="${idCurso == curso.idCurso }">
														<option selected="selected" value="${curso.idCurso }">${curso.nombre }</option>											
													</c:when>													
													<c:otherwise>
														<option value="${curso.idCurso }">${curso.nombre }</option>
													</c:otherwise>
												</c:choose>																							
											</c:forEach>																		
										</select>
									</td>
								</tr>
			        			<tr>
			        				<td>Año </td>
			        				<td><input type="text" name="anio" value="${param.anio }"/> ${errores['anio'] }</td>
			        			</tr>
			        			<tr>
			        				<td colspan="2"><input type="submit" name="mostrar" value="Mostrar" ></input></td>
			        			</tr>
			        		</table>     		
						</form>
						<c:choose>
							<c:when test="${!empty vHorarios }">
								<table class="tablaInfo">
									<tr>
										<td>Mes</td>
										<c:if test="${persona.idPerfil == 1 }">
											<td>Eliminar</td>
										</c:if>								
									</tr>
									<c:forEach var="horario" items="${vHorarios }">
										<tr>
											<td>
												<a href='${pageContext.request.contextPath }/ServHorarioCrear?idHorario=${horario.idHorario }'>
													<c:choose>
														<c:when test="${horario.mes == 1 }">
															Enero
														</c:when>
														<c:when test="${horario.mes == 2 }">														
															Febrero
														</c:when>
														<c:when test="${horario.mes == 3 }">
															Marzo
														</c:when>
														<c:when test="${horario.mes == 4 }">
															Abril
														</c:when>
														<c:when test="${horario.mes == 5 }">
															Mayo
														</c:when>
														<c:when test="${horario.mes == 6 }">
															Junio
														</c:when>
														<c:when test="${horario.mes == 7 }">
															Julio
														</c:when>
														<c:when test="${horario.mes == 8 }">
															Agosto
														</c:when>
														<c:when test="${horario.mes == 9 }">
															Septiembre
														</c:when>
														<c:when test="${horario.mes == 10 }">
															Octubre
														</c:when>
														<c:when test="${horario.mes == 11 }">
															Noviembre
														</c:when>
														<c:when test="${horario.mes == 12 }">
															Diciembre
														</c:when>
													</c:choose>
												</a>
											</td>
											<c:if test="${persona.idPerfil == 1 }">
												<td>									
													<form method="post" action="${pageContext.request.contextPath }/ServHorarioEliminar">
														<input type="hidden" name="eliminarHorario" value="${horario.idHorario }" />
														<input type="image" name="~imgEliminar" src="./imagenes/eliminar.png" />
													</form>
												</td>
											</c:if>
										</tr>
									</c:forEach>
								</table>
							</c:when>
							<c:otherwise>
								${mensaje }
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						No hay ningún curso.
					</c:otherwise>
				</c:choose>        		
			</div>        	
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>