import java.time.LocalDate;

public class ServicioPreventivo extends Servicio {
   
  private String tipoPreventivo, nombreMedicamento, dosisMedicamento;
  private LocalDate fechaProximaAplicacion;

  public ServicioPreventivo(String tipoProcedimiento, String descripcion, String veterinario, String resultado, double costoBase, String tipoPreventivo, String nombreMedicamento, String dosisMedicamento) {
    super(tipoProcedimiento, descripcion, veterinario, resultado, costoBase);
    this.tipoPreventivo = tipoPreventivo;
    this.nombreMedicamento = nombreMedicamento;
    this.dosisMedicamento = dosisMedicamento;
    actualizarFechaProximaAplicacion();
  }

  private void actualizarFechaProximaAplicacion() {
    if (tipoPreventivo.equalsIgnoreCase("Vacunación")) {
      fechaProximaAplicacion = getFecha().plusYears(1);
    } else if (tipoPreventivo.equalsIgnoreCase("Desparasitación")) {
      fechaProximaAplicacion = getFecha().plusMonths(3);
    } else {
      fechaProximaAplicacion = null; // No se puede determinar la fecha próxima
    }
  }

  public LocalDate getFechaProximaAplicacion() {
    return fechaProximaAplicacion;
  }

  @Override 
  public boolean requiereConsentimiento() {
    return false; // Los servicios preventivos no requieren consentimiento
  }

  @Override public Servicio copiar(String veterinario) {
    return new ServicioPreventivo(getTipoProcedimiento(), getDescripcion(), veterinario, "", getCostoBase(), tipoPreventivo, nombreMedicamento, dosisMedicamento);
  }
  
  @Override 
  public String toString() {
    return super.toString() +
            "\nTipo Preventivo: " + tipoPreventivo +
            "\nNombre del Medicamento: " + nombreMedicamento +
            "\nDosis del Medicamento: " + dosisMedicamento +
            "\nFecha Próxima Aplicación: " + (fechaProximaAplicacion != null ? fechaProximaAplicacion : "No aplicable");
  }
}
