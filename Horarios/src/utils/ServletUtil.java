package utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import modelo.Asignatura;
import modelo.GestorBD;
import modelo.Horario;
import modelo.Horario_Asig;
import modelo.Persona;
import modelo.Rango_horas;

public class ServletUtil {
	
	public static final int DIA_LUNES = 2;
	public static final int DIA_MARTES = 3;
	public static final int DIA_MIERCOLES = 4;
	public static final int DIA_JUEVES = 5;
	public static final int DIA_VIERNES = 6;
	public static final String[] ARRAY_MESES = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
												"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	
	public static String obtenerDescripcionUsuActivo (HttpServletRequest request) {
		HttpSession sesion = request.getSession();
		Persona usuarioActivo = (Persona)sesion.getAttribute("persona");
		String descripcionUsuario = "";
		if (usuarioActivo != null) {
			descripcionUsuario = usuarioActivo.descripcionUsuario();
		}
		return descripcionUsuario;
	}
	
	public static boolean esAnioBisiesto (int anio) {
		GregorianCalendar calendar = new GregorianCalendar();
		if (calendar.isLeapYear(anio)) {
			return true;
		} else {
			return false;
		}
	}	
	
	public static int numDiasMes (int mes, int anio) {
		int diasMes = 0;
		switch (mes) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				diasMes = 31;
				break;
			case 2:
				if (ServletUtil.esAnioBisiesto(anio)) {
					diasMes = 29;
				} else {
					diasMes = 28;
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				diasMes = 30;
				break;
		}
		return diasMes;
	}
	
	public static String diaSemana (int intDiaSemana) {
		String[] diasSemana = new String[] {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
		if ((0 <= intDiaSemana) && (intDiaSemana < 6)) {
			return diasSemana[intDiaSemana].toString();
		} else {
			return "";
		}
	}
	
	public static boolean esMultiploDe6(int n){	
	//  Se comprueba si el numero sea multiplo de 6
		if( ( n % 6 ) == 0 )
			return true;
		else
			return false;
	}
	
	public static boolean esMultiploDe(int multiploDe, int num){	
		//  Se comprueba si el numero sea multiplo de 6
			if( ( num % multiploDe ) == 0 )
				return true;
			else
				return false;
	}
	
	public static StringBuilder contenidoTablaHTML (int idCurso, int mes, int anio, int numPorciones, String descripcionUsuario, int idPerfilUsuario) {
		
		int diasMes = ServletUtil.numDiasMes(mes, anio);
		
		Vector<String> vTitulo = new Vector<String>();
		
		System.out.println("mes: " + mes);
		System.out.println("anio: " + anio);
		System.out.println("numPorciones: " + numPorciones);
		
		int cont = 1;
		Calendar calFecha = new GregorianCalendar();
		calFecha.set(anio, mes-1, cont);
		System.out.println("Date: " + calFecha.get(Calendar.DATE));
		System.out.println("Fecha: " + calFecha.toString());
		int primerDiaDeSemana = calFecha.get(Calendar.DAY_OF_WEEK);
		
		System.out.println("primerDiaDeSemana: " + primerDiaDeSemana);
		
		for (int i=0 ; i<6 ; i++) {
			if (i == 0 ) {
				vTitulo.add("Horario");
			} else {
				if (primerDiaDeSemana <= (i +1)) {
					vTitulo.add(ServletUtil.diaSemana(i) + " " + cont);
					cont++;
				} else {
					vTitulo.add("NADA");
				}
			}
		}
		
		// Se utiliza el contador contHor para saber donde hay que colocar el título 'horario'
		int contHor = 7;
		while (cont < diasMes) {
			cont++;
			System.out.println("** Inicio while.");
			System.out.println("cont: " + cont);
			System.out.println("diasMes: " + diasMes);
			if (contHor == 7) {
				vTitulo.add("Horario");
				contHor = 1;
			} else {
				if (contHor != 6) {
					vTitulo.add(ServletUtil.diaSemana(contHor) + " " + cont);					
				}
				contHor++;
			}			
		}
		
		int i = 1;
		for(String titulo : vTitulo) {			
			System.out.println(i + "- " + titulo);
			i++;
		}
		
		// Una vez que tenemos el esqueleto de la tabla creado, se accede a la BBDDD
		// para recoger los datos de la tabla en caso de que existan.
		// De esta manera podremos introducirlos mientras creamos el código HTML para el jsp
		GestorBD gestorBD = new GestorBD(descripcionUsuario);
		// Comprobamos si existe el horario
		Horario horario = gestorBD.buscarHorario(mes, anio, idCurso);
		
		if (horario != null) {
			numPorciones = horario.getNumPorciones();
		}
		
		//Se crea la tabla en HTML desde el servlet para luego pasarlo al .jsp
		
		StringBuilder conTabla = new StringBuilder();
		boolean primeraVez = true;
		conTabla.append("<div>" + ARRAY_MESES[mes-1] + " de " + anio + "</div>");
		conTabla.append("<table id='tblHorario' name='tblHorario' class='tblHorario' >");		
		int fila = -1;
		int columna = 0;
		// para saber cuantas veces recorre el for
		int contador = 0;
		
		// contador para saber en que dia de la semana se termina el mes
		int contUltimoDiaMesEnSemana = 1;
		
		Rango_horas rango_hora = null;
		Horario_Asig horario_asig = null;
		Asignatura asignatura = null;
		for(String titulo : vTitulo) {	
			if (contador != vTitulo.size()) {
				if (titulo.equals("Horario")) {						
					contUltimoDiaMesEnSemana = 1;
					if (primeraVez) {
						primeraVez = false;
					} else {
						for(int j=0 ; j<numPorciones*6 ; j++) {
							if ((j==0) || (ServletUtil.esMultiploDe6(j))) {
								// Ini Prueba
								if (j!=0) {
									if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
										conTabla.append("<td><input type='checkbox' id='chkFila" + fila + "' value='" + fila + "' onclick='checkFila(" + fila + ")' /></td>");
									}									
								}
								// Fin Prueba
								conTabla.append("</tr>");								
								conTabla.append("<tr>");								
								columna = 0;
								fila++;
							}
							// Comprobamos si esta celda está en la BBDD
						
							if (horario != null) {
								if (columna == 0) {
									rango_hora = gestorBD.buscarRango_horasFila(horario.getIdHorario(), fila);
									if (rango_hora != null){
										if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
											conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > " + rango_hora.getTexto() + " </td>");
										} else {
											conTabla.append("<td style='background-color:white' > " + rango_hora.getTexto() + " </td>");
										}
									} else {
										if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
//											conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > celda " + fila + ", " + columna + " </td>");
											conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > </td>");
										} else {
											conTabla.append("<td style='background-color:white' > </td>");
										}
									}
								} else {
									horario_asig = gestorBD.buscarHorario_Asig(columna, fila, horario.getIdHorario());
									if (horario_asig != null) {
										asignatura = gestorBD.buscarAsignatura(horario_asig.getIdAsignatura());
										if (asignatura != null) {
											if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
												conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > " + asignatura.getNombre() + " </td>");
											} else {
												conTabla.append("<td style='background-color:white' > " + asignatura.getNombre() + " </td>");
											}
										} else {
											if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
//												conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > celda " + fila + ", " + columna + " </td>");
												conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > </td>");
											} else {
												conTabla.append("<td style='background-color:white' > </td>");
											}

										}
									} else {
										if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
//											conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > celda " + fila + ", " + columna + " </td>");
											conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' >  </td>");
										} else {
											conTabla.append("<td style='background-color:white' >  </td>");
										}

									}
								}
							} else {
								if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
//									conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white'> celda " + fila + ", " + columna + " </td>");
									conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white'> </td>");
								} else {
									conTabla.append("<td style='background-color:white'> </td>");
								}
							}						
//							conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > celda " + fila + ", " + columna + " </td>");
							columna++;							
						}					
						// Ini Prueba
						if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
							conTabla.append("<td><input type='checkbox' id='chkFila" + fila + "' value='" + fila + "' onclick='checkFila(" + fila + ")' /></td>");
						}
						
						// Fin Prueba
						conTabla.append("</tr>");						
					}					
					conTabla.append("<tr>");
					fila++;
					columna = 0;
//					conTabla.append("<td style='background-color:#454545; color:white'>" + titulo + "</td>");
					conTabla.append("<td class='trTituloHorario' >" + titulo + "</td>");
					columna++;
				} else {
//					conTabla.append("<td style='background-color:#454545; color:white'>" + titulo + "</td>");
					conTabla.append("<td class='trTituloHorario'>" + titulo + "</td>");
					columna++;
					contUltimoDiaMesEnSemana++;
				}
			}
			contador++;
		}
		if (vTitulo.size()>0) {
			System.out.println("vTitulo.size es mayor a 0");
			System.out.println("*** Va a entrar en el for");
			for(int j=0 ; j<numPorciones*contUltimoDiaMesEnSemana ; j++) {		
				System.out.println("******* j = " + j);
				if ((j==0) || (ServletUtil.esMultiploDe(contUltimoDiaMesEnSemana, j))) {
					System.out.println("******* entra en el if");
					// Ini Prueba
					if (j!=0) {
						if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
							conTabla.append("<td><input type='checkbox' id='chkFila" + fila + "' value='" + fila + "' onclick='checkFila(" + fila + ")' /></td>");
						}						
					}
					// Fin Prueba
					conTabla.append("</tr>");
					conTabla.append("<tr>");
					fila++;
					columna = 0;
				}
				if (horario != null) {
					if (columna == 0) {
						rango_hora = gestorBD.buscarRango_horasFila(horario.getIdHorario(), fila);
						if (rango_hora != null){
							if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
								conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > " + rango_hora.getTexto() + " </td>");
							} else {
								conTabla.append("<td style='background-color:white' > " + rango_hora.getTexto() + " </td>");
							}
						} else {
							if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
//								conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > celda " + fila + ", " + columna + " </td>");
								conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > </td>");
							} else {
								conTabla.append("<td style='background-color:white' > </td>");
							}

						}
					} else {
						horario_asig = gestorBD.buscarHorario_Asig(columna, fila, horario.getIdHorario());
						if (horario_asig != null) {
							asignatura = gestorBD.buscarAsignatura(horario_asig.getIdAsignatura());
							if (asignatura != null) {
								if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
									conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > " + asignatura.getNombre() + " </td>");
								} else {
									conTabla.append("<td style='background-color:white' > " + asignatura.getNombre() + " </td>");
								}
							} else {
								if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
//									conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > celda " + fila + ", " + columna + " </td>");
									conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > </td>");
								} else {
									conTabla.append("<td style='background-color:white' > </td>");
								}

							}
						} else {
							if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
//								conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > celda " + fila + ", " + columna + " </td>");
								conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white' > </td>");
							} else {
								conTabla.append("<td style='background-color:white' > </td>");
							}

						}
					}
				} else {
					if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
//						conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white'> celda " + fila + ", " + columna + " </td>");
						conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white'> </td>");
					} else {
						conTabla.append("<td style='background-color:white'> </td>");
					}

				}
//				conTabla.append("<td onclick='accionCeldaSeleccionada(" + fila + "," + columna + ")' style='background-color:white'> celda " + fila + ", " + columna + " </td>");
				columna++;
			}
		} else {
			System.out.println("vTitulo.size NO es mayor a 0");
		}
		// Ini Prueba
		if (idPerfilUsuario == GestorBD.ADMINISTRADOR) {
			conTabla.append("<td><input type='checkbox' id='chkFila" + fila + "' value='" + fila + "' onclick='checkFila(" + fila + ")' /></td>");
		}		
		// Fin Prueba
		conTabla.append("</tr>");
		conTabla.append("</table>");
		
		return conTabla;
	}
}
