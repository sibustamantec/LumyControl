public class HistoriaClinica {
    private Mascota mascota;
    private ConsultaGeneral consulta; //por el momento una, en un futuro un array
    private String ID;
    private static int numeroHistorias;

    public String getMascota(){return mascota.ficha();}
    public String getID(){return ID;}
    public int getNumeroHistorias(){return numeroHistorias;}

    public HistoriaClinica(Mascota mascota){
        this.mascota = mascota;
        numeroHistorias++;
    }
    public void agregarConsulta(ConsultaGeneral consulta){
        this.consulta = consulta; //en un futuro este metodo agrega cada consulta al array de consultas
    }
    public String historiaSencilla(){
        return "Datos del paciente: \n" + mascota.toString() +
                "\nConsulta: \n" + consulta.resumen() ;
    }
    public String historiaCompleta(){
        return "Datos del paciente: \n" + mascota.toString() +
                "\nConsulta: \n" + consulta.toString();
    }
    //Se planea agregar obtenerUltimaConsulta() cuando se manejen arrays
}
