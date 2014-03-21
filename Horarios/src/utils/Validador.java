package utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;


public final class Validador {

	private final static String VALOR_DNI_INCORRECTO = "Formato DNI incorrecto";
	private final static String VALOR_NOMBRE_INCORRECTO = "Formato nombre incorrecto";
	private final static String VALOR_APELLIDO1_INCORRECTO = "Formato primer apellido incorrecto";
	private final static String VALOR_PASS_REPETIDA_INCORRECTA = "Las contraseñas no coinciden";
	private final static String VALOR_CLAVE_INCORRECTO = "Clave incorrecto";
	private final static String VALOR_LONG_INCORRECTO = "Valor entero incorrecto";
	private final static String VALOR_DOUBLE_INCORRECTO = "Valor real incorrecto";
	private final static String CAMPO_OBLIGATORIO = "Campo obligatorio";
	public final static String CAMPO_NUMERICO_SI = "Tiene que ser numérico";
	
	public final static String errorCabecera = "<font color=\"red\"><b>";
	public final static String errorPie = "</b></font>";

	private Validador() {
	}

	public final static void validarDNI (Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad, boolean obligatorio) {
		
		System.out.println("Validar DNI.");
		boolean correcto = true;
		valorPropiedad = valorPropiedad == null ? "" : valorPropiedad.trim();
		if (validarObligatorio(errores, nombrePropiedad, valorPropiedad, obligatorio)) {
			
			System.out.println("--- El campo no está vació,");
			
			if (valorPropiedad.length()!=9) {
				correcto = false;
			} else {
				System.out.println("--- DNI con longitud correcta de 9.");
				String strParteNum = valorPropiedad.substring(0, 8);
				String letra = valorPropiedad.substring(8);
				System.out.println("--- strParteNum: " + strParteNum);
				System.out.println("--- letra: " + letra);
				if (!isNumeric(strParteNum)) {
					System.out.println("--- strParteNum no es numérico.");
					correcto = false;
				}
				if (isNumeric(letra)) {
					System.out.println("--- letra es numérico.");
					correcto=false;
				}
			}
		} else {
			System.out.println("--- El campo está vació.");
		}
		if (!correcto) {
			errores.put(nombrePropiedad, errorCabecera + VALOR_DNI_INCORRECTO + errorPie);
		}
	}
	
	public final static void  validarPass(Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad, boolean obligatorio) {
		
		int claveLong = 0;
		valorPropiedad = valorPropiedad == null ? "" : valorPropiedad.trim();
		if (validarObligatorio(errores, nombrePropiedad, valorPropiedad, obligatorio)) {
			
			boolean correcto = true;
			try {
				claveLong = valorPropiedad.length();
				if (claveLong < 4) {
					correcto = false;
				}
			} catch (NumberFormatException e) {
				correcto = false;
			}

			if (!correcto) {
				errores.put(nombrePropiedad, errorCabecera + VALOR_CLAVE_INCORRECTO + errorPie);
			}
		}
	}
	
	public final static void validarPassRepetido (Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad, boolean obligatorio, String clave) {
		System.out.println("Validar ClaveRepetida.");
		boolean correcto = true;
		if (validarObligatorio(errores, nombrePropiedad, valorPropiedad, obligatorio)) {
			
			System.out.println("--- El campo no está vació,");
			System.out.println("--- valorPropiedad: " + valorPropiedad);
			System.out.println("--- clave: " + clave);
			if (!valorPropiedad.equals(clave)) {
				correcto = false;
			}
		}	
		if (!correcto) {
			errores.put(nombrePropiedad, errorCabecera + VALOR_PASS_REPETIDA_INCORRECTA + errorPie);
		}
	}
	
	public final static void validarNombre (Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad, boolean obligatorio) {
		System.out.println("Validar Nombre.");
		boolean correcto = true;
		if (validarObligatorio(errores, nombrePropiedad, valorPropiedad, obligatorio)) {
			
		}	
		if (!correcto) {
			errores.put(nombrePropiedad, errorCabecera + VALOR_PASS_REPETIDA_INCORRECTA + errorPie);
		}
	}
	
	public final static long validarLong(Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad, boolean obligatorio,
			long limiteInferior, long limiteSuperior) {

		long propiedadLong = 0;
		valorPropiedad = valorPropiedad == null ? "" : valorPropiedad.trim();

		if (validarObligatorio(errores, nombrePropiedad, valorPropiedad, obligatorio)) {

			boolean correcto = true;
			try {
				propiedadLong = new Long(valorPropiedad).longValue();
				if ((propiedadLong < limiteInferior)
						|| (propiedadLong > limiteSuperior)) {
					correcto = false;
				}
			} catch (NumberFormatException e) {
				correcto = false;
			}

			if (!correcto) {
				errores.put(nombrePropiedad,errorCabecera + VALOR_LONG_INCORRECTO + errorPie);
			}

		}

		return propiedadLong;

	}

	public final static double validarDouble(Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad, boolean obligatorio,
			double limiteInferior, double limiteSuperior) {

		double valorDouble = 0;
		valorPropiedad = valorPropiedad == null ? "" : valorPropiedad.trim();

		if (validarObligatorio(errores, nombrePropiedad, valorPropiedad, obligatorio)) {
			boolean correcto = true;
			try {
				valorDouble = Double.parseDouble(valorPropiedad);
				if ((valorDouble < limiteInferior)
						|| (valorDouble > limiteSuperior)) {
					correcto = false;
				}
			} catch (NumberFormatException e) {
				correcto=false;
			}
			if(!correcto)
				errores.put(nombrePropiedad, errorCabecera + VALOR_DOUBLE_INCORRECTO + errorPie);
		}

		return valorDouble;

	}

	public final static boolean validarObligatorio(Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad) {

		if ((valorPropiedad == null) || (valorPropiedad.length() == 0)) {
			errores.put(nombrePropiedad, errorCabecera + CAMPO_OBLIGATORIO + errorPie);
			return false;
		} else {
			return true;
		}
	}

	public final static boolean validarObligatorio(Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad, boolean obligatorio) {

		if (obligatorio) {
			return validarObligatorio(errores, nombrePropiedad, valorPropiedad);
		} else {
			return true;
		}

	}
	
	public final static void validarCampoObligatorio(Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad) {

		System.out.println("Entra en validarCampoObligatorio");
		System.out.println("--- nombrePropiedad: " + nombrePropiedad);
		System.out.println("--- valorPropiedad: " + valorPropiedad);
		if (!validarObligatorio(errores, nombrePropiedad, valorPropiedad)) {
			System.out.println("--- ValidarObligatorio -> false");
			errores.put(nombrePropiedad, errorCabecera + CAMPO_OBLIGATORIO + errorPie);
		}
	}
	
	public final static void validarAnio (Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad, boolean obligatorio) {
		
		System.out.println("Validar Anio.");
		valorPropiedad = valorPropiedad == null ? "" : valorPropiedad.trim();
		if (validarObligatorio(errores, nombrePropiedad, valorPropiedad, obligatorio)) {
			
			System.out.println("--- El campo no está vació,");
			
			if (!isNumeric(valorPropiedad)) {
				errores.put(nombrePropiedad, errorCabecera + CAMPO_NUMERICO_SI + errorPie);
			}
		} else {
			errores.put(nombrePropiedad, errorCabecera + CAMPO_OBLIGATORIO + errorPie);
		}
	}
	
	public final static void validarNumPorciones (Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad, boolean obligatorio) {
		
		System.out.println("Validar NumPorciones.");
		System.out.println("++++numPorciones: " + valorPropiedad);
		valorPropiedad = valorPropiedad == null ? "" : valorPropiedad.trim();
		if (validarObligatorio(errores, nombrePropiedad, valorPropiedad, obligatorio)) {
			if (!valorPropiedad.equals("")) {
				
				System.out.println("--- El campo no está vació,");
				
				if (!isNumeric(valorPropiedad)) {
					errores.put(nombrePropiedad, errorCabecera + CAMPO_NUMERICO_SI + errorPie);
				}
			}
		} else {
			errores.put(nombrePropiedad, errorCabecera + CAMPO_OBLIGATORIO + errorPie);
		}		
	}
	
	public final static void validarDurHoras (Map<String, String> errores,
			String nombrePropiedad, String valorPropiedad, boolean obligatorio) {
		
		System.out.println("Validar DurHoras.");
		System.out.println("++++durhoras: " + valorPropiedad);
		valorPropiedad = valorPropiedad == null ? "" : valorPropiedad.trim();
		if (validarObligatorio(errores, nombrePropiedad, valorPropiedad, obligatorio)) {
			if (!valorPropiedad.equals("")) {
				
				System.out.println("--- El campo no está vació,");
				
				if (!isNumeric(valorPropiedad)) {
					errores.put(nombrePropiedad, errorCabecera + CAMPO_NUMERICO_SI + errorPie);
				}
			}
		} else {
			errores.put(nombrePropiedad, errorCabecera + CAMPO_OBLIGATORIO + errorPie);
		}		
	}
	
	public static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
}
