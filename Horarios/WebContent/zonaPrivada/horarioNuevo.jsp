<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Nuevo Horario</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />
</head>
<body>
	<div id="pagina">
        <jsp:include page="/plantilla/cabecera.jsp"></jsp:include>
        <jsp:include page="/plantilla/menu.jsp"></jsp:include>
        <div id="bloqueColumnaIzq">
        	<div id="bloqueIzq">
            		<p>Nuevo horario.</p>
            		<p>- Nº de porciones: es el número de asignaturas (bloques) que se van a añadir cada día, incluiendo también el/los descanso/s.</p>
        	</div>
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">
        		<c:choose>
        			<c:when test="${!empty vCursos }">
        				<form method="post" action="${pageContext.request.contextPath }/ServHorarioCrear">        		
			        		<table>
			        			<tr>
									<td>Curso: </td>
									<td>
										<select name="selCurso">
											<c:forEach var="curso" items="${vCursos }">														
												<option value="${curso.idCurso }">${curso.nombre }</option>
											</c:forEach>																		
										</select>
									</td>
								</tr>
			        			<tr>
			        				<td>Mes a mostrar </td>
			        				<td>
			        					<select name="selMes">
										  <option value="1">Enero</option>
										  <option value="2">Febrero</option>
										  <option value="3">Marzo</option>
										  <option value="4">Abril</option>
										  <option value="5">Mayo</option>
										  <option value="6">Junio</option>
										  <option value="7">Julio</option>
										  <option value="8">Agosto</option>
										  <option value="9">Septiembre</option>
										  <option value="10">Octubre</option>
										  <option value="11">Noviembre</option>
										  <option value="12">Diciembre</option>
										</select>
			        				</td>
			        			</tr>
			        			<tr>
			        				<td>Año </td>
			        				<td><input type="text" name="anio" value="${param.anio }"/> ${errores['anio'] }</td>
			        			</tr>
			        			<tr>
			        				<td>Nº de porciones por dia (incluidos descansos) </td>
			        				<td><input type="text" name="numPorciones" value="${param.numPorciones }" /> ${errores['numPorciones'] }</td>
			        			</tr>
			        			<tr>
			        				<td colspan="2"><input type="submit" name="crear" value="Crear" ></input></td>
			        			</tr>
			        		</table>     		
						</form>
        			</c:when>
        			<c:otherwise>
        				<div>No se pueden crear horarios porque no hay ningún curso disponible.</div>
        			</c:otherwise>
        		</c:choose>        		
			</div>        	
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>