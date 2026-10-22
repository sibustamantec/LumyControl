import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


public class ConsultaGeneral {

  private static final LocalTime HORA_INICIO_NOCTURNO = LocalTime.of(20, 0);
  private static final LocalTime HORA_FIN_NOCTURNO = LocalTime.of(6, 0);

  private String numeroConsulta, veterinario, diagnostico, tratamiento;
  private LocalDateTime fechaHora;
  private Anamnesis anamnesis;
  private ExamenClinico examenClinico;
  private ArrayList<Servicio> servicios;


  public ConsultaGeneral(String numeroConsulta, LocalDateTime fechaHora, String veterinario, Anamnesis anamnesis, ExamenClinico examenClinico, String diagnostico, String tratamiento) {
    this.numeroConsulta = numeroConsulta;
    this.fechaHora = fechaHora;
    this.veterinario = veterinario;
    this.anamnesis = anamnesis;
    this.examenClinico = examenClinico;
    this.diagnostico = diagnostico;
    this.tratamiento = tratamiento;
    this.servicios = new ArrayList<>();
  }

  public String getNumeroConsulta() {return numeroConsulta;}
  public LocalDateTime getFechaHora() {return fechaHora;}
  public String getVeterinario() {return veterinario;}
  public Anamnesis getAnamnesis() {return anamnesis;}
  public ExamenClinico getExamenClinico() {return examenClinico;}
  public ArrayList<Servicio> getServicios() {return servicios;}

  public void registrarAnamnesis(Anamnesis a) {this.anamnesis = a;}
  public void registrarExamenClinico(ExamenClinico e) {this.examenClinico = e;}
  public void modificarDiagnostico(String nuevoDiagnostico) {this.diagnostico = nuevoDiagnostico;}
  public void modificarTratamiento(String nuevoTratamiento) { this.tratamiento = nuevoTratamiento;}
  public void agregarServicio(Servicio s) {this.servicios.add(s);}

  public String resumen() {
    return "Consulta #" + numeroConsulta + 
    "\nFecha: " + fechaHora.toLocalDate() + 
    "\nVeterinario: " + veterinario +
    "\nDiagnostico: " + diagnostico +
    "\nTratamiento: " + tratamiento;
  }
  @Override
  public String toString() {
    return resumen() +
    "\nAnamnesis: \n" + anamnesis + 
    "\nExamen Clinico: \n" + examenClinico;
  }


  public boolean esNocturno() {
    LocalTime hora = fechaHora.toLocalTime();
    return !hora.isBefore(HORA_INICIO_NOCTURNO) || hora.isBefore(HORA_FIN_NOCTURNO);
  }

  public boolean hayAlerta(String especie) {
    return (examenClinico != null)? examenClinico.evaluarConstantesVitales(especie) : false;
  }

  public Recibo generarRecibo(String numeroRecibo, Tutor tutor, Mascota mascota) {

    return new Recibo(numeroRecibo, tutor, mascota, this);
  }

}
