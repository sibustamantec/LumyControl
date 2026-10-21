import java.time.LocalDate;

public class Anamnesis {
  private String motivoConsulta, tipoAlimento, racion, heces, orina;
  private LocalDate ultimaVacunacion, ultimaDesparacitacion;

  public Anamnesis(String motivoConsulta, String tipoAlimento, String racion, String heces, String orina, String ultimaVacunacion, String ultimaDesparacitacion) {
    this.motivoConsulta = motivoConsulta;
    this.tipoAlimento = tipoAlimento;
    this.racion = racion;
    this.heces = heces;
    this.orina = orina;
    setUltimaVacunacion(ultimaVacunacion);
    setUltimaDesparacitacion(ultimaDesparacitacion);
  }

  public String getMotivoConsulta(){return motivoConsulta;}
  public String getTipoAlimento(){return tipoAlimento;}
  public String getRacion(){return racion;}
  public String getHeces(){return heces;}
  public String getOrina(){return orina;}

  public void setUltimaDesparacitacion(String ultimaDesparacitacion) {
    try{
      this.ultimaDesparacitacion = LocalDate.parse(ultimaDesparacitacion);
    }catch(Exception e){
      System.out.println("Fecha inválida");
    }
  }

  public void setUltimaVacunacion(String ultimaVacunacion) {
    try{
      this.ultimaVacunacion = LocalDate.parse(ultimaVacunacion);
    }catch(Exception e){
      System.out.println("Fecha inválida");
    }
  }

  @Override
  public String toString() {
    return "Motivo de Consulta: " + motivoConsulta +
            "\nTipo de Alimento: " + tipoAlimento +
            "\nRación: " + racion +
            "\nHeces: " + heces +
            "\nOrina: " + orina +
            "\nÚltima Vacunación: " + ultimaVacunacion +
            "\nÚltima Desparacitación: " + ultimaDesparacitacion;
  }
  public boolean requiereVacunacion() {
    LocalDate fechaActual = LocalDate.now();
    if (ultimaVacunacion == null || ultimaVacunacion.plusYears(1).isBefore(fechaActual)) {
        return true;
    }
    return false;
  }

    public boolean requiereDesparacitacion() {
    LocalDate fechaActual = LocalDate.now();
    if (ultimaDesparacitacion == null || ultimaDesparacitacion.plusMonths(3).isBefore(fechaActual)) {
        return true;
    }
    return false;
  }
}
