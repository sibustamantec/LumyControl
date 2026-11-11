public class Jaula {
  private final int fila, columna;
  private String estado; // "libre","ocupada" o "sin adecuar"
  private Mascota paciente;

  public Jaula(int fila, int columna){
    this.fila = fila;
    this.columna = columna;
    this.estado = "libre";
    this.paciente = null;
  }

  public int getFila(){return fila;}
  public int getColumna(){return columna;}
  public String getEstado(){return estado;}
  public char getEstadoChar(){
    switch(estado.toLowerCase()){
      case "libre": return 'L';
      case "ocupada": return 'O';
      case "sin adecuar": return 'S';
      default: return '?';
    }
  }
  public Mascota getPaciente(){return paciente;}

  public void ocuparJaula(Mascota paciente){
    if(paciente != null){

        if(estado.equalsIgnoreCase("libre")){
        this.paciente = paciente;
        this.estado = "ocupada";
      } else if(estado.equalsIgnoreCase("sin adecuar")){
        System.out.println("La jaula no está adecuada para recibir pacientes");
      }
      else {
        System.out.println("La jaula está ocupada");
      }
    }
    else{
      System.out.println("Paciente inválido");
    }
  }

  public void liberarJaula(){
    if(estado.equalsIgnoreCase("ocupada")){
      this.paciente = null;
      this.estado = "sin adecuar";
    }
    else{
      System.out.println("La jaula no está ocupada");
    }
  }

  public void adecuarJaula(){
    if(estado.equalsIgnoreCase("sin adecuar")){
      this.estado = "libre";
    }
    else if (estado.equalsIgnoreCase("libre")){
      System.out.println("La jaula ya está adecuada");
    }
    else{
      System.out.println("La jaula está ocupada");
    }
  }
    
  @Override 
  public String toString(){
    return "Jaula [" + (fila + 1) + "," + (columna + 1) + "] - Estado: " + estado + (paciente != null ? " - Paciente: " + paciente.getNombre() : "");
  }

}
