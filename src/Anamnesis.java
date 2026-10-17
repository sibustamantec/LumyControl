Import java.time.LocalDate;

public class Anamnesis {
  private String motivoConsulta, tipoAlimento, racion, heces, orina;
  private LocalDate ultimaVacunacion, ultimaDesparacitacion;

  public Anamnesis(String motivoConsulta, String tipoAlimento, String racion, String heces, String orina, LocalDate ultimaVacunacion, LocalDate ultimaDesparacitacion) {
    this.motivoConsulta = motivoConsulta;
    this.tipoAlimento = tipoAlimento;
    this.racion = racion;
    this.heces = heces;
    this.orina = orina;
    this.ultimaVacunacion = ultimaVacunacion;
    this.ultimaDesparacitacion = ultimaDesparacitacion;
  }

  public void imprimirInformacion() {
    System.out.println("Motivo de Consulta: " + motivoConsulta);
    System.out.println("Tipo de Alimento: " + tipoAlimento);
    System.out.println("Ración: " + racion);
    System.out.println("Heces: " + heces);
    System.out.println("Orina: " + orina);
    System.out.println("Vacunación: " + ultimaVacunacion);
    System.out.println("Desparacitación: " + ultimaDesparacitacion);
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
