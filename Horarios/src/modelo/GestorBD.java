package modelo;

import java.util.Vector;

import base_Datos.AsignaturaDAO;
import base_Datos.CursoDAO;
import base_Datos.HorarioDAO;
import base_Datos.Horario_AsigDAO;
import base_Datos.PerfilDAO;
import base_Datos.PersonaDAO;
import base_Datos.Persona_CursoDAO;
import base_Datos.Rango_horasDAO;

public class GestorBD {
	public static final String PERSONA_NO_ENCONTRADA = "Usuario y/o contraseña incorrectos";
	public static final int ADMINISTRADOR = 1;
	public static final int PROFESOR = 2;
	public static final int ALUMNO = 3;
	
	public static final String NOMBRE_FICHERO_LOG = "HorarioLog.txt";
	public static final String NOMBRE_CARPETA_LOG = "Horarios/Log";
	
	private PersonaDAO personadao;
	private PerfilDAO perfildao;
	private CursoDAO cursodao;
	private AsignaturaDAO asignaturadao;
	private Rango_horasDAO rango_horasdao;
	private HorarioDAO horariodao;
	private Horario_AsigDAO horario_Asigdao;
	private Persona_CursoDAO persona_cursodao;
	
	public GestorBD() {
		personadao = new PersonaDAO();
		perfildao = new PerfilDAO();
		cursodao = new CursoDAO();
		asignaturadao = new AsignaturaDAO();
		rango_horasdao = new Rango_horasDAO();
		horariodao = new HorarioDAO();
		horario_Asigdao = new Horario_AsigDAO();
		persona_cursodao = new Persona_CursoDAO();
	}
	
	public GestorBD(String usuario) {
		personadao = new PersonaDAO(usuario);
		perfildao = new PerfilDAO(usuario);
		cursodao = new CursoDAO(usuario);
		asignaturadao = new AsignaturaDAO(usuario);
		rango_horasdao = new Rango_horasDAO(usuario);
		horariodao = new HorarioDAO(usuario);
		horario_Asigdao = new Horario_AsigDAO(usuario);
		persona_cursodao = new Persona_CursoDAO(usuario);
	}
	
	// PERSONA
	
	public Persona buscarPersona (String dni, String pass) {
		return personadao.buscarPersona(dni, pass);
	}
	
	public Persona buscarPersona (String dni) {
		return personadao.buscarPersona(dni);
	}
	
	public Vector<Persona> listadoPersonas (int idPerfil) {
		return personadao.listadoPersonas(idPerfil);
	}
	
	public Vector<Persona> listadoPersonasCurso (int idCurso) {
		return personadao.listadoPersonasCurso(idCurso);
	}
	
	public boolean insertarPersona (Persona persona) {
		return personadao.insertarPersona (persona);
	}
	
	public boolean modificarPersona (Persona persona) {
		return personadao.modificarPersona (persona);
	}
	
	public boolean elimianrPersona (Persona persona) {
		return personadao.eliminarPersona (persona);
	}
	
	public Persona profesorAsignatura(int idAsignatura) {
		return personadao.profesorAsignatura(idAsignatura);
	}
	
	// PERFIL
	
	public Perfil buscarPerfil (int idPerfil) {
		return perfildao.buscarPerfil(idPerfil);
	}
	
	public String nombrePerfil (int idPerfil) {
		return perfildao.nombrePerfil(idPerfil);
	}
	
	public Vector<Perfil> listadoPerfiles () {
		
		return perfildao.listadoPerfiles();
	}
	
	// CURSO
	
	public Curso buscarCurso (int idCurso) {
		return cursodao.buscarCurso(idCurso);
	}
	
	public Vector<Curso> listadoCursos () {
		return cursodao.listadoCursos();
	}
	
	public Vector<Curso> listadoCursos (int idProfesor) {
		return cursodao.listadoCursos(idProfesor);
	}
	
	public Curso obtenerCursoAlumno (int idAlumno) {
		return cursodao.obtenerCursoAlumno(idAlumno);
	}
	
	public boolean insertarCurso (Curso curso) {
		return cursodao.insertarCurso (curso);
	}
	
	public boolean elimianrCurso (Curso curso) {
		return cursodao.eliminarCurso(curso);
	}
	
	public boolean modificarCurso (Curso curso) {
		return cursodao.modificarCurso (curso);
	}
	
	// ASIGNATURA
	
	public Asignatura buscarAsignatura (int idAsignatura) {
		return asignaturadao.buscarAsignatura(idAsignatura);
	}
	
	public Vector<Asignatura> listadoAsignaturasCurso (int idCurso) {
		return asignaturadao.listadoAsignaturasCurso(idCurso);
	}
	
	public Vector<AsignaturaCurso> listadoAsignaturasProfesor (int idProfesor) {
		return asignaturadao.listadoAsignaturasProfesor(idProfesor);
	}
	
	public boolean insertarAsignatura (Asignatura asignatura) {
		return asignaturadao.insertarAsignatura (asignatura);
	}
	
	public boolean modificarAsignatura (Asignatura asignatura) {
		return asignaturadao.modificarAsignatura (asignatura);
	}
	
	// RANGO_HORAS
	
	public Rango_horas buscarRangoHoras (int idRango_horas) {
		return rango_horasdao.buscarRango_horas(idRango_horas);
	}
	
	public Vector<Rango_horas> listadoRango_horas () {
		return rango_horasdao.listadoRango_horas();
	}
	
	public boolean insertarRango_horas (Rango_horas rango_horas) {
		return rango_horasdao.insertarRango_horas(rango_horas);
	}
	
	public boolean modificarRango_horas (Rango_horas rango_horas) {
		return rango_horasdao.modificarRango_horas(rango_horas);
	}
	
	public Rango_horas buscarRango_horasFila (int idHorario, int fila) {
		return rango_horasdao.buscarRango_horasFila(idHorario,fila);
	}
	
	public boolean eliminarRango_horas (int idHorario) {
		return rango_horasdao.eliminarRango_horas(idHorario);
	}
	
	// HORARIO
	
	public Horario buscarHorario (int idHorario) {
		return horariodao.buscarHorario(idHorario);
	}
	
	public Horario buscarHorario (int mes, int anio, int idCurso) {
		return horariodao.buscarHorario(mes, anio, idCurso);
	}
	
	public Vector<Horario> listadoHorario () {
		return horariodao.listadoHorario();
	}
	
	public Vector<Horario> listadoHorario (int idCurso, int anio) {
		return horariodao.listadoHorario(idCurso, anio);
	}
	
	public Vector<Horario> listadoHorario (int idCurso) {
		return horariodao.listadoHorario(idCurso);
	}
	
	public boolean insertarHorario (Horario horario) {
		return horariodao.insertarHorario(horario);
	}
	
	public boolean modificarHorario (Horario horario) {
		return horariodao.modificarHorario(horario);
	}
	
	public boolean eliminarHorario (Horario horario) {
		return horariodao.eliminarHorario(horario);
	}
	
	// HORARIO - ASIG
	
//	public Horario_Asig buscarHorario_Asig (int idHorario_Asig) {
//		return horario_Asigdao.buscarHorario_Asig(idHorario_Asig);
//	}
	
	public Horario_Asig buscarHorario_Asig (int dia, int fila, int idHorario) {
		return horario_Asigdao.buscarHorario_Asig(dia, fila, idHorario);
	}
	
	public Vector<Horario_Asig> listadoHorario_Asig () {
		return horario_Asigdao.listadoHorario_Asig();
	}
	
	public boolean insertarHorario_Asig (Horario_Asig horario_Asig) {
		return horario_Asigdao.insertarHorario_Asig(horario_Asig);
	}
	
	public boolean modificarHorario_Asig (Horario_Asig horario_Asig) {
		return horario_Asigdao.modificarHorario_Asig(horario_Asig);
	}
	
	public boolean eliminarHorario_asig (int idHorario) {
		return horario_Asigdao.eliminarHorario_asig(idHorario);
	}
	
	// PERSONA_CURSO
	
	public Vector<Persona_Curso> buscarPersonasDeCurso (int idCurso) {
		return persona_cursodao.buscarPersonasDeCurso(idCurso);
	}
	
	public Persona_Curso buscarPersona_Curso (int idPersona) {
		return persona_cursodao.buscarPersona_Curso(idPersona);
	}
	
	public boolean insertarPersona_Curso (Persona_Curso persona_curso) {
		return persona_cursodao.insertarPersona_Curso(persona_curso);
	}
	
	public boolean modificarPersona_Curso (Persona_Curso persona_curso) {
		return persona_cursodao.modificarPersona_Curso(persona_curso);
	}
	
	public boolean eliminarPersona_Curso (int idPersona) {
		return persona_cursodao.eliminarPersona_Curso(idPersona);
	}
		
	// DESCONECTAR
	
	public void desconectar() {
		personadao.desconectar();
	}
}
