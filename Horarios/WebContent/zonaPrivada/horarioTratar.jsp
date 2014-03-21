<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Horario</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/Estilos.css" />
	<link rel="stylesheet" type="text/css" href="../css/Estilos.css" />
	<style>
		#tblHorario {
			border: solid 1px;
		}
		#tblHorario tr td {
			border: solid 1px;
			width:6em;
			heidht:4em;
			background-color: white;
		}
	</style>
	<script type="text/javascript">
		//var numDias = 5;
		
		var colorNoSelec = "white";
		var colorSelec = "red";
		
		var separadorCelda = "&";
		var separadorFilaColumna = "$";
		var separadorTexto = "@";
		
		// Elimina la selección del conjunto de celdas y se 'restaura' esl estado de los checkBox y div visibles
		function eliminarSeleccion() {
			var tabla = document.getElementById('tblHorario');
			document.getElementById("fila").value = "";
			document.getElementById("columna").value = "";
			var filas = tabla.rows.length;
			var numPorciones = document.getElementById('numPorciones').value;
			var cont = 0;
			for (var f=1;f<filas;f++) {
				var columnas = tabla.rows[f].cells.length;				
				if (cont < numPorciones) {
					// Se asigna Checked=false a todos los checkbox
					document.getElementById("chkFila" + f).checked = false;
					for (var c=0;c<columnas;c++) {
						tabla.rows[f].cells[c].style.background = colorNoSelec;
					}
					cont++;
				} else {
					cont = 0;
				}				
			}
			// Se ocultan los apartados con textBox de hora y asignatura
			var divHora = document.getElementById('divHora');
			var divAsignatura = document.getElementById('divAsignatura');
			divHora.style.display = 'none';
			divAsignatura.style.display = 'none';
		}
		
		// Elimina la selección del conjunto de celdas correspondientes a las clases-asignaturas
		// y se 'restaura' esl estado de los checkBox
		function eliminarSeleccionAsignaturas() {
			var tabla = document.getElementById('tblHorario');
			var filas = tabla.rows.length;
			var numPorciones = document.getElementById('numPorciones').value;

			var cont = 0;
			for (var f=1;f<filas;f++) {
				var columnas = tabla.rows[f].cells.length;
				if (cont < numPorciones) {
					// Se asigna Checked=false a todos los checkbox
					document.getElementById("chkFila" + f).checked = false;
					for (var c=1;c<columnas;c++) {
						tabla.rows[f].cells[c].style.backgroundColor = colorNoSelec;
					}		
					cont++;
				} else {
					cont = 0;
				}	
			}
		}
		
		// Elimina la selección del conjunto de celdas correspondientes a las horas
		function eliminarSeleccionHoras() {
			var tabla = document.getElementById('tblHorario');
			var filas = tabla.rows.length;
			var numPorciones = document.getElementById('numPorciones').value;

			var cont = 0;
			for (var f=1;f<filas;f++) {
				if (cont < numPorciones) {
					tabla.rows[f].cells[0].style.backgroundColor = colorNoSelec;		
					cont++;
				} else {
					cont = 0;
				}	
			}
		}
		
		function actualizarDatosStrTabla (strTablaNuevo) {
			// El string de la tabla que ya existe
			var strTabla = document.getElementById("strTabla").value;
			// Se crean dos arrays (split del string con el separador ';''): (formato > fila,columna:dato)
			//		- Uno con los elementos viejos
			//		- Otro con los elementos nuevos
			var elemStrTabla = strTabla.split(separadorCelda);
			var elemNuevo = strTablaNuevo.split(separadorCelda);
			// Recorremos el array de elementos viejos y los comparamos con los nuevos para 
			// ver si hay elementos correspondientes a la misma celda. En ese caso se actualizará
			var longitudElemStrTabla = elemStrTabla.length;
			var longitudElemNuevo = elemNuevo.length;
			
			var strTablaFinal = "";
			var distinto = true;
			if (strTabla != "") {
				for (var i=0 ; i<longitudElemStrTabla ; i++) {
					distinto = true;
					var celdaViejo = elemStrTabla[i].substring(0,elemStrTabla[i].indexOf(separadorTexto));
					for (var j=0 ; j<longitudElemNuevo ; j++) {
						// Se comparan los elementos					
						var celdaNuevo = elemNuevo[j].substring(0,elemNuevo[j].indexOf(separadorTexto));
						if (celdaViejo == celdaNuevo) {
							distinto = false;
						} else {
						}
					}
					if (distinto) {
						// Si la celda del elemento viejo es distinto al de los nuevos, se añada al nuevo string
						//strTablaFinal += elemStrTabla[j] + ";";
					} else {
						elemStrTabla.splice(i,1);
						// Se actualiza la longitud del array de elementos 'viejos'
						longitudElemStrTabla = elemStrTabla.length;
					}					
				}
			} else {
			}
			// Se conactenan los elementos del array 'original' con el separador ';''
			var strFinal = "";
			for (var x=0 ; x<elemStrTabla.length ; x++) {
				strFinal += elemStrTabla[x] + separadorCelda;
			}
			document.getElementById("strTabla").value = strFinal;
			document.getElementById("strTabla").value += strTablaNuevo;
		}
		
		function ocultarDivsSiEsNecesario() {
			var tabla = document.getElementById('tblHorario');			
			
			var filas = tabla.rows.length;
			var numPorciones = document.getElementById('numPorciones').value;
			var cont = 0;
			var ocultar = true;
			for (var f=1;f<filas;f++) {				
				var columnas = tabla.rows[f].cells.length;
				if (cont < numPorciones) {
					for (var c=0;c<columnas;c++) {
						if (tabla.rows[f].cells[c].style.backgroundColor == colorSelec) {
							ocultar = false;
						}
					}
					cont++;
				} else {
					cont = 0;
				}				
			}
			var divHora = document.getElementById('divHora');
			var divAsignatura = document.getElementById('divAsignatura');
			if (ocultar) {
				divHora.style.display = 'none';
				divAsignatura.style.display = 'none';
			}
		}
		
		function accionCeldaSeleccionada (filaCelda, columnaCelda) {			
			
			var tabla = document.getElementById('tblHorario');
			var divHora = document.getElementById('divHora');
			var divAsignatura = document.getElementById('divAsignatura');
			var hiddenFila = document.getElementById("fila");
			var hiddenColumna = document.getElementById("columna");
			if (columnaCelda=="0") {
				divHora.style.display = 'inline';
				divAsignatura.style.display = 'none';
				eliminarSeleccionAsignaturas();
			} else {
				divHora.style.display = 'none';
				divAsignatura.style.display = 'inline';
				eliminarSeleccionHoras();
			}
			hiddenFila.value=filaCelda;
			hiddenColumna.value=columnaCelda;
			
			ilumina(filaCelda, columnaCelda);
		}
		function modifHorario() {
			var tabla = document.getElementById('tblHorario');
			var divHora = document.getElementById('divHora');
			var hiddenFila = document.getElementById("fila").value;
			var hiddenColumna = document.getElementById("columna").value;
			var horaInicio = document.getElementById("horaInicio").value;
			var horaFin = document.getElementById("horaFin").value;
			
			var numPorciones = document.getElementById('numPorciones').value;
			// se hace un bucle recorriendo toda la tabla, creando un string con todos los datos
			var strTabla = "";
			var filas = tabla.rows.length;
			var cont = 0;
			// Se salta la primera fila por ser la del título 
			for (var f=1;f<filas;f++) {
				if (cont < numPorciones) {
					if (tabla.rows[f].cells[0].style.backgroundColor == colorSelec) {							
						// Se carga el contenido de los text en la celda correspondiente
						tabla.rows[f].cells[0].innerHTML=horaInicio + " - " + horaFin;
						strTabla += f + separadorFilaColumna + "0" + separadorTexto + horaInicio + " - " + horaFin + separadorCelda;
					}
					cont++;
				} else {
					cont = 0;
				}				
			}
			actualizarDatosStrTabla(strTabla);
			
			
			// Se ocultan los text y se inicializan los valores de los campos ocultos
			divHora.style.display = 'none';
			hiddenFila="";
			hiddenColumna="";
			horaInicio="";
			horaFin="";
			eliminarSeleccion();
		}
		function modifAsignatura() {
			var tabla = document.getElementById('tblHorario');
			var divAsignatura = document.getElementById('divAsignatura');
			var hiddenFila = document.getElementById("fila").value;
			var hiddenColumna = document.getElementById("columna").value;
			var idAsignatura = document.getElementById("selAsignatura").options[document.getElementById("selAsignatura").selectedIndex].value;
			var txtAsignatura = document.getElementById("selAsignatura").options[document.getElementById("selAsignatura").selectedIndex].text;
			
			// Se carga el contenido de los text en la celda correspondiente
			var numPorciones = document.getElementById('numPorciones').value;
			// se hace un bucle recorriendo toda la tabla, creando un string con todos los datos
			var strTabla = "";
			var filas = tabla.rows.length;
			var cont = 0;
			// Se salta la primera fila por ser la del título 
			for (var f=1;f<filas;f++) {
				var columnas = tabla.rows[f].cells.length;
				if (cont < numPorciones) {
					for (var c=0;c<columnas;c++) {
						if (tabla.rows[f].cells[c].style.backgroundColor == colorSelec) {							
							tabla.rows[f].cells[c].innerHTML=txtAsignatura;
							strTabla += f + separadorFilaColumna + c + separadorTexto + idAsignatura + separadorCelda;
						}
					}
					cont++;
				} else {
					cont = 0;
				}				
			}
			actualizarDatosStrTabla(strTabla);
			
			// Se ocultan los text y se inicializan los valores de los campos ocultos
			divAsignatura.style.display = 'none';
			hiddenFila="";
			hiddenColumna="";
			eliminarSeleccion();
		}

		function crearStringDatosTabla (fila, columna , idAsignatura) {
			alert("Entra en crearStringDatosTabla");
			var strTabla = document.getElementById("strTabla").value;
			if (columna != "") {
				strTabla += fila + separadorFilaColumna + columna + separadorTexto + idAsignatura + separadorCelda;	
			} else {
				for(var c=1 ; c<numDias ; c++) {
					strTabla += fila + separadorFilaColumna + c + separadorTexto + idAsignatura + separadorCelda;	
				}	
			}
							
			document.getElementById('strTabla').value = strTabla;
		}
		function ilumina(fila, columna) {
			var tabla = document.getElementById('tblHorario');

			if (tabla.rows[fila].cells[columna].style.backgroundColor == colorSelec) {
				tabla.rows[fila].cells[columna].style.backgroundColor = colorNoSelec;
				ocultarDivsSiEsNecesario();
			} else {
				tabla.rows[fila].cells[columna].style.backgroundColor = colorSelec;
			}
			
			if (columna != 0) {
				comprobarFilaSelec(fila);					
			}				
		}
		function checkFila (fila) {
			var chkFila = document.getElementById("chkFila" + fila);
			var tabla = document.getElementById('tblHorario');
			var columnas = tabla.rows[fila].cells.length;
			var divAsignatura = document.getElementById('divAsignatura');
			if (chkFila.checked == true) {
				for (var c=1;c<columnas-1;c++) {
					tabla.rows[fila].cells[c].style.background = colorSelec;
				}				
				divAsignatura.style.display = 'inline';
			} else {
				for (var c=1;c<columnas-1;c++) {
					tabla.rows[fila].cells[c].style.background = colorNoSelec;					
				}
			}
			hiddenFila = fila;
			hiddenFila = "";
			
			ocultarDivsSiEsNecesario();
		}
		
		function comprobarFilaSelec (fila){
			var chkFila = document.getElementById("chkFila" + fila);
			var tabla = document.getElementById('tblHorario');
			var columnas = tabla.rows[fila].cells.length;
			var filaCompleta = true;
			for (var c=1;c<columnas-1;c++) {
				if (tabla.rows[fila].cells[c].style.backgroundColor == colorNoSelec) {
					filaCompleta = false;
				}										
			}			
			chkFila.checked = filaCompleta;			
		}
		function datosCelda (i, j) {
			alert(i + "," + j);
		}		
		function redireccionar() {
			self.location = "${pageContext.request.contextPath}/ServCerrarSesion";
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
        			<c:when test="${persona.idPerfil == 1 }">
        				<p>Crear horario.</p>
	            		<p>- Seleccione la/s celda/s en la/s que quiera añadir la asignatura o el rango de hora (las seleccionadas se iluminarán de rojo).</p>
	            		<p>- Se puede seleccionar una fila entera para añadir la misma asignatura seleccionando caja que está a la derecha de la fila.</p>
	            		<p>- Los descansos serán las casillas que se queden en blanco.</p>
        			</c:when>
        			<c:otherwise>
        				<p>Horario</p>
        			</c:otherwise>
        		</c:choose>            		
        	</div>
        </div>
        <div id="bloqueContenido">
        	<div id="contenido">
				${conTabla }
				<c:if test="${persona.idPerfil == 1 }">
					<div id="divHora" name="divHora" style="display:none">
						<!-- <form method="post" action='${pageContext.request.contextPath }/ServUsuarioAcceso'> -->
							<table>
								<tr>
									<td colspan="2"><h3>Hora</h3></td>
								</tr>
								<tr>
									<td>Inicio:</td>
									<td><input type="text" id="horaInicio" name="horaInicio" value="${param.horaInicio }" /> </td>
								</tr>
								<tr>
									<td>Fin:</td>
									<td><input type="text" id="horaFin" name="horaFin" value="${param.horaFin }" /> </td>
								</tr>
								<tr>
									<td colspan="2"><input type="button" name="aniadirHora" value="Añadir" onClick="modifHorario()" /></td>
								</tr>			   				
							</table>
					</div>
					<div id="divAsignatura" name="divAsignatura" style="display:none">
						<table>
							<tr>
								<td colspan="2"><h3>Asignatura</h3></td>
							</tr>
							<tr>
								<td>Asignatura: </td>
								<td>
									<select id="selAsignatura" name="selAsignatura">
										<c:forEach var="asignatura" items="${vAsignaturas }">														
											<option value="${asignatura.idAsignatura }">${asignatura.nombre }</option>
										</c:forEach>																	
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="2"><input type="button" name="aniadirAsig" value="Añadir" onClick="modifAsignatura()" /></td>
							</tr>			   				
						</table>
					</div>
						<input type="hidden" id="fila" name="fila" value="" />
						<input type="hidden" id="columna" name="columna" value="" />					
						
					<form method="post" action='${pageContext.request.contextPath }/ServHorarioTratar'>	
						<input type="hidden" id="idCurso" name="idCurso" value="${idCurso }" />					
						<input type="hidden" id="anio" name="anio" value="${anio }" />
						<input type="hidden" id="mes" name="mes" value="${mes }" />
						<input type="hidden" id="numPorciones" name="numPorciones" value="${numPorciones }" />
						<input type="hidden" id="strTabla" name="strTabla" value="${strTabla }" />
						<div id="divEliminarSeleccion">
							<input type="button" onClick="eliminarSeleccion()" id="elimSeleccion" name="elimSeleccion" value="Eliminar Selección" />
						</div>
						<input type="submit" name="guardar" value="Guardar" />
					</form>
				</c:if>				
			</div>			      
   	 	</div>
   	 	<jsp:include page="/plantilla/pie.jsp"></jsp:include>
   	 </div>
</body>
</html>