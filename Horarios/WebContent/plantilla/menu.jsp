<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    session="true"
    import="modelo.Persona"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="bloqueMenu">
	<% String ruta = getServletContext().getContextPath(); %>
        	<div id="menu">
        	    <div id='cssmenu'>
					<ul>
					<c:choose>
						<c:when test="${!empty persona }">
							<c:choose>
								<c:when test="${(persona.idPerfil == 1) || (persona.idPerfil == 2) }">
									<!-- <li class='active'><a href='<%=ruta + "/zonaPrivada/inicioPrivado.jsp"%>'><span>PRUEBA INICIO</span></a></li>  -->
									<li class='active'><a href='<%=ruta + "/ServUsuarioFormAlta"%>'><span>Nuevo Usuario</span></a>
									</li>
									<li class='has-sub'><a href='#'><span>Lista Usuarios</span></a>
										<ul>
											<c:if test="${persona.idPerfil == 1}">
												<li><a href='<%=ruta + "/ServUsuariosLista?op=1"%>'><span>Administrador</span></a></li>
									        	<li><a href='<%=ruta + "/ServUsuariosLista?op=2"%>'><span>Profesor</span></a></li>		
									        </c:if>																		        
									        <li class='last'><a href='<%=ruta + "/ServUsuariosLista?op=3"%>'><span>Alumno</span></a></li>
									   	</ul>
									</li>
									<c:if test="${persona.idPerfil == 1}">
										<li class='has-sub'><a href='<%=ruta + "/ServCursosLista"%>'><span>Cursos</span></a>
											<ul>
												<li><a href='<%=ruta + "/ServCursosLista"%>'><span>Listado</span></a></li>
									        	<li class='last'><a href='<%=ruta + "/zonaPrivada/cursoNuevo.jsp"%>'><span>Nuevo</span></a></li>																			        									        	
									   		</ul>
									   	</li>
									</c:if>
									<c:if test="${persona.idPerfil == 2}">
										<li class='active'><a href='<%=ruta + "/ServAsignaturaProfesor"%>'><span>Asignaturas</span></a></li>
									</c:if>								
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${persona.idPerfil == 1}">
								<li class='has-sub'><a href='<%=ruta + "/ServHorariosLista"%>'><span>Horario</span></a>
										<ul>
											<li><a href='<%=ruta + "/ServHorariosLista"%>'><span>Listado</span></a></li>
											<li class='last'><a href='<%=ruta + "/ServHorarioInicio"%>'><span>Nuevo</span></a></li>																			        									        	
										</ul>
									</li>									
								</c:when>
								<c:otherwise>
									<li class='active'><a href='<%=ruta + "/ServHorariosLista"%>'><span>Horario</span></a></li>
								</c:otherwise>
							</c:choose>							
					   		<li class='last'><a href='<%=ruta + "/ServCerrarSesion"%>'><span>Cerrar Sesión</span></a></li>
						</c:when>
						<c:otherwise>
							<!-- <li class='last'><a href='<%=ruta + "/login.jsp"%>'><span>Inicio</span></a></li>  -->
						</c:otherwise>						
					</c:choose>															   					   
					</ul>
				</div>
        	</div>
        </div>