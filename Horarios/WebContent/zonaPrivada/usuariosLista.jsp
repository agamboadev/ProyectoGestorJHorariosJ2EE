<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Listado Usuarios</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />
	<script type="text/javascript">
		function recargarPagina(direccion) {
			var selCurso = document.getElementById('selCurso');
			location.href=direccion + '&curso=' + selCurso.options[selCurso.selectedIndex].value;
		}
	</script>	
</head>
<body>
	<div id="pagina">
        <jsp:include page="/plantilla/cabecera.jsp"></jsp:include>
        <jsp:include page="/plantilla/menu.jsp"></jsp:include>
        <div id="bloqueColumnaIzq">
        	<div id="bloqueIzq">
        		<c:choose>
        			<c:when test="${param.op == 1 }">Administradores</c:when>
        			<c:when test="${param.op == 2 }">Profesores</c:when>
        			<c:when test="${param.op == 3 }">Alumnos</c:when>
        			<c:otherwise>NADA</c:otherwise>
        		</c:choose>        		
        	</div> 
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">        				
				<c:choose>
					<c:when test="${!empty vPersonas }">
						<c:choose>
							<c:when test="${!empty vCursos }">
								<span id="lblCurso">Curso: </span></td>
								<select name="selCurso" id="selCurso" onChange="recargarPagina('${pageContext.request.contextPath }/ServUsuariosLista?op=3');">
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
							</c:when>
						</c:choose>
						<table class="tablaInfo">
							<tr>
								<td>Dni</td>
								<td>Nombre</td>
								<td>Apellidos</td>									
								<td>Modificar</td> 
								<td>Eliminar</td>
							</tr>
							<c:forEach var="pers" items="${vPersonas }">
								<tr>
									<td>${pers.dni }</td>
									<td>${pers.nombre }</td>
									<td>${pers.apellido1 } ${pers.apellido2 }</td>
									<td>
										<form method="post" action="${pageContext.request.contextPath }/ServUsuarioModLista">
											<input type="hidden" name="modificarDni" value="${pers.dni }" />
											<input type="hidden" name="perfilLista" value="${pers.idPerfil }" />
											<input type="image" name="imgModificar" src="./imagenes/modificar.png" />											
										</form>
									</td>  
									<td>
										<form method="post" action="${pageContext.request.contextPath }/ServUsuarioEliminar">
											<input type="hidden" name="eliminarDni" value="${pers.dni }" />
											<input type="hidden" name="perfilLista" value="${pers.idPerfil }" />
											<input type="image" name="~imgEliminar" src="./imagenes/eliminar.png" />
										</form>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						No hay nigún usuario.
					</c:otherwise>
				</c:choose>				
			</div>        	
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>