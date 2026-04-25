import java.util.*;
public class Jugador {
    private String        nombre;
    private int           lp = 8000;
    private List<Carta>   mazo   = new ArrayList<>();
    private List<Carta>   mano   = new ArrayList<>();
    private List<Monstruo> campo  = new ArrayList<>();
    private List<Trampa>  trampas = new ArrayList<>(); 
    public Jugador(String nombre) { this.nombre = nombre; }
    public String         getNombre()  { return nombre; }
    public int            getLp()      { return lp; }
    public List<Carta>    getMano()    { return mano; }
    public List<Monstruo> getCampo()  { return campo; }
    public List<Carta>    getMazo()   { return mazo; }
    public List<Trampa>   getTrampas(){ return trampas; }
    public boolean estaVivo()       { return lp > 0; }
    public boolean tieneMazo()      { return !mazo.isEmpty(); }
    public boolean tieneMano()      { return !mano.isEmpty(); }
    public boolean tieneMonstruos() { return !campo.isEmpty(); }
    public boolean tieneTrampas()   { return !trampas.isEmpty(); }
    public void agregarAlMazo(Carta c) { mazo.add(c); }
}
