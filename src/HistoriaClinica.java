import java.util.ArrayList;
public class HistoriaClinica {
    private Mascota mascota;
    private ArrayList<ConsultaGeneral> consultas; //por el momento una, en un futuro un array
    private String ID;

    public String getMascota(){return mascota.ficha();}
    public String getID(){return ID;}


    public HistoriaClinica(Mascota mascota){
        this.mascota = mascota;
        this.consultas = new ArrayList<>();
    }
    public void agregarConsulta(ConsultaGeneral consulta){
        consultas.add(consulta); //en un futuro este metodo agrega cada consulta al array de consultas
    }
    public ConsultaGeneral obtenerUltimaConsulta(){
        if(consultas.isEmpty()){return null;}
        return consultas.getLast();
    }
    public String historiaSencilla(){
        String texto = "Datos del paciente: \n" + mascota.toString();
        if(consultas.isEmpty()){
            return texto + "\nSin consultas registradas.";
        }
        for(ConsultaGeneral consulta : consultas){
            texto += "\nConsulta: \n" + consulta.resumen();
        }
        return texto;
    }
    public String historiaCompleta(){
        String texto = "Datos del paciente: \n" + mascota.toString();
        if(consultas.isEmpty()){
            return texto + "\nSin consultas registradas.";
        }
        for(ConsultaGeneral consulta : consultas){
            texto += "\nConsulta: \n" + consulta.toString();
        }
        return texto;
    }
}
