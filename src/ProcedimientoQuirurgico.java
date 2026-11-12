public class ProcedimientoQuirurgico extends Servicio{

  private static double recargoEmergencia = 0.30;
  private static double CostoHoraHospitalizacion = 25000; 

  private String nivelRiesgo, tipoAnestesia;
  private boolean urgencia;
  private int tiempoEstimadoMin, horasHospitalizacion, diasRecuperacion;

  public ProcedimientoQuirurgico(String tipoProcedimiento, String descripcion,String veterinario, String resultado, double costoBase, String nivelRiesgo, String tipoAnestesia, boolean urgencia, int tiempoEstimadoMin, int horasHospitalizacion, int diasRecuperacion) {
    super(tipoProcedimiento, descripcion, veterinario, resultado, costoBase);
    this.nivelRiesgo = nivelRiesgo;
    this.tipoAnestesia = tipoAnestesia;
    this.urgencia = urgencia;
    this.tiempoEstimadoMin = tiempoEstimadoMin;
    this.horasHospitalizacion = horasHospitalizacion;
    this.diasRecuperacion = diasRecuperacion;
  }

  public double getRecargoEmergencia() {
    return getCostoBase() * recargoEmergencia;
  }

  public double getCostoHoraHospitalizacion() {
    return CostoHoraHospitalizacion * horasHospitalizacion;
  }

  @Override
  public double calcularCostoServiciosTotal() {
    double costoTotal = getCostoBase();
    if (urgencia) {
      costoTotal += getRecargoEmergencia();
    }
    costoTotal += getCostoHoraHospitalizacion();
    return costoTotal;
  }

  @Override 
  public boolean requiereConsentimiento() {
    return true;
  }

  @Override 
  public Servicio copiar(String veterinario) {
    return new ProcedimientoQuirurgico(getTipoProcedimiento(), getDescripcion(), veterinario, getResultado(), getCostoBase(), nivelRiesgo, tipoAnestesia, urgencia, tiempoEstimadoMin, horasHospitalizacion, diasRecuperacion);
  }

  @Override 
  public String toString() {
    return super.toString() +
            "\nNivel de Riesgo: " + nivelRiesgo +
            "\nTipo de Anestesia: " + tipoAnestesia +
            "\nUrgencia: " + urgencia +
            "\nTiempo Estimado (minutos): " + tiempoEstimadoMin +
            "\nHoras de Hospitalización: " + horasHospitalizacion +
            "\nDías de Recuperación: " + diasRecuperacion +
            "\nRecargo por Emergencia: " + getRecargoEmergencia() +
            "\nCosto por Hospitalización: " + getCostoHoraHospitalizacion();
  }

}