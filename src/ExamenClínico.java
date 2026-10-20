public class ExamenClinico {

  private double temperatura, peso;
  private int frecuenciaCardiaca, frecuenciaRespiratoria;
  private String actitud, organosDeLosSentidos, gangliosLinfaticos, sistemaRespiratorio, sistemaCardiovascular, sistemaMusculoesqueletico, sistemaDigestivo, sistemaNeurologico, pielYPelaje, SistemaReproductivo, sistemaUrinario;


public ExamenClinico(double temperatura, double peso, int frecuenciaCardiaca, int frecuenciaRespiratoria, String actitud, String organosDeLosSentidos, String gangliosLinfaticos, String sistemaRespiratorio, String sistemaCardiovascular, String sistemaMusculoesqueletico, String sistemaDigestivo, String sistemaNeurologico, String pielYPelaje, String SistemaReproductivo, String sistemaUrinario) {
    this.temperatura = temperatura;
    this.peso = peso;
    this.frecuenciaCardiaca = frecuenciaCardiaca;
    this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    this.actitud = actitud;
    this.organosDeLosSentidos = organosDeLosSentidos;
    this.gangliosLinfaticos = gangliosLinfaticos;
    this.sistemaRespiratorio = sistemaRespiratorio;
    this.sistemaCardiovascular = sistemaCardiovascular;
    this.sistemaMusculoesqueletico = sistemaMusculoesqueletico;
    this.sistemaDigestivo = sistemaDigestivo;
    this.sistemaNeurologico = sistemaNeurologico;
    this.pielYPelaje = pielYPelaje;
    this.SistemaReproductivo = SistemaReproductivo;
    this.sistemaUrinario = sistemaUrinario;
  }
public void imprimirInformacion() {
    System.out.println("Temperatura: " + temperatura);
    System.out.println("Peso: " + peso);
    System.out.println("Frecuencia Cardiaca: " + frecuenciaCardiaca);
    System.out.println("Frecuencia Respiratoria: " + frecuenciaRespiratoria);
    System.out.println("Actitud: " + actitud);
    System.out.println("Órganos de los Sentidos: " + organosDeLosSentidos);
    System.out.println("Ganglios Linfáticos: " + gangliosLinfaticos);
    System.out.println("Sistema Respiratorio: " + sistemaRespiratorio);
    System.out.println("Sistema Cardiovascular: " + sistemaCardiovascular);
    System.out.println("Sistema Musculoesquelético: " + sistemaMusculoesqueletico);
    System.out.println("Sistema Digestivo: " + sistemaDigestivo);
    System.out.println("Sistema Neurológico: " + sistemaNeurologico);
    System.out.println("Piel y Pelaje: " + pielYPelaje);
    System.out.println("Sistema Reproductivo: " + SistemaReproductivo);
    System.out.println("Sistema Urinario: " + sistemaUrinario);
 
  }

  public boolean detectarFiebre() {
    return temperatura > 39.2;
  }

  public boolean evaluarConstantesVitales(String especie) {

    if(detectarFiebre()){
      return true;
    }

    if (especie.equalsIgnoreCase("canino")){
      return  this.frecuenciaCardiaca < 60 || this.frecuenciaCardiaca > 150 || this.frecuenciaRespiratoria < 10 || this.frecuenciaRespiratoria > 35;
    }
    else if (especie.equalsIgnoreCase("felino")){
      return this.frecuenciaCardiaca < 100 || this.frecuenciaCardiaca > 240 || this.frecuenciaRespiratoria < 12 || this.frecuenciaRespiratoria > 40;
    }
    else{
      return false;
    }
  }

}