public class ConsultaEspecializada extends Servicio {

  private String especialidad, motivoRemision, procedimientosEspeciales;
  private double costoProcedimientosEspeciales;
  private boolean intrusivo;

  public ConsultaEspecializada(String tipoProcedimiento, String descripcion, String veterinario, String resultado, double costoBase, String especialidad, String motivoRemision, String procedimientosEspeciales, double costoProcedimientosEspeciales, boolean intrusivo) {
      super(tipoProcedimiento, descripcion, veterinario, resultado, costoBase);
      this.especialidad = especialidad;
      this.motivoRemision = motivoRemision ;
      this.procedimientosEspeciales = procedimientosEspeciales;
      this.costoProcedimientosEspeciales = costoProcedimientosEspeciales;
      this.intrusivo = intrusivo;
  }

  @Override 
  public double calcularCostoServiciosTotal() {
      return getCostoBase() + costoProcedimientosEspeciales;
  }

  @Override 
  public boolean requiereConsentimiento() {
    return intrusivo;
  }
  
  @Override 
  public Servicio copiar(String veterinario) {
      return new ConsultaEspecializada(getTipoProcedimiento(), getDescripcion(), veterinario, "", getCostoBase(), especialidad, motivoRemision, procedimientosEspeciales, costoProcedimientosEspeciales, intrusivo);
  }

  @Override 
  public String toString() {
      return super.toString() +
              "\nEspecialidad: " + especialidad +
              "\nMotivo de Remisión: " + motivoRemision +
              "\nProcedimientos Especiales: " + procedimientosEspeciales;
  }
}
