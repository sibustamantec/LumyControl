public class ExamenClinico {

    private double temperatura, peso;
    private int frecuenciaCardiaca, frecuenciaRespiratoria;
    private String actitud, organosDeLosSentidos, gangliosLinfaticos, sistemaRespiratorio, sistemaCardiovascular, sistemaMusculoesqueletico, sistemaDigestivo, sistemaNeurologico, pielYPelaje, sistemaReproductivo, sistemaUrinario;

    public ExamenClinico(double temperatura, double peso, int frecuenciaCardiaca, int frecuenciaRespiratoria, String actitud, String organosDeLosSentidos, String gangliosLinfaticos, String sistemaRespiratorio, String sistemaCardiovascular, String sistemaMusculoesqueletico, String sistemaDigestivo, String sistemaNeurologico, String pielYPelaje, String sistemaReproductivo, String sistemaUrinario) {
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
        this.sistemaReproductivo = sistemaReproductivo;
        this.sistemaUrinario = sistemaUrinario;
    }

    public double getPeso(){return peso;}
    public double getTemperatura(){return temperatura;}
    public int getFrecuenciaCardiaca(){return frecuenciaCardiaca;}
    public int getFrecuenciaRespiratoria(){return frecuenciaRespiratoria;}
    public String getGangliosLinfaticos(){return gangliosLinfaticos;}
    public String getActitud(){return actitud;}
    public String getOrganosDeLosSentidos(){return organosDeLosSentidos;}
    public String getSistemaRespiratorio(){return sistemaRespiratorio;}
    public String getSistemaCardiovascular(){return sistemaCardiovascular;}
    public String getSistemaMusculoesqueletico(){return sistemaMusculoesqueletico;}
    public String getSistemaDigestivo(){return sistemaDigestivo;}
    public String getSistemaNeurologico(){return sistemaNeurologico;}
    public String getPielYPelaje(){return pielYPelaje;}
    public String getSistemaReproductivo(){return sistemaReproductivo;}
    public String getSistemaUrinario(){return sistemaUrinario;}

    public String toString() {
        return "Temperatura: " + temperatura +
        "\nPeso: " + peso +
        "\nFrecuencia Cardiaca: " + frecuenciaCardiaca +
        "\nFrecuencia Respiratoria: " + frecuenciaRespiratoria +
        "\nActitud: " + actitud +
        "\nÓrganos de los Sentidos: " + organosDeLosSentidos +
        "\nGanglios Linfáticos: " + gangliosLinfaticos +
        "\nSistema Respiratorio: " + sistemaRespiratorio +
        "\nSistema Cardiovascular: " + sistemaCardiovascular +
        "\nSistema Musculoesquelético: " + sistemaMusculoesqueletico +
        "\nSistema Digestivo: " + sistemaDigestivo +
        "\nSistema Neurológico: " + sistemaNeurologico +
        "\nPiel y Pelaje: " + pielYPelaje +
        "\nSistema Reproductivo: " + sistemaReproductivo +
        "\nSistema Urinario: " + sistemaUrinario;
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