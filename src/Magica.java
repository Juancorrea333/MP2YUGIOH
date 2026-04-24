public class Magica extends Carta implements AccionMagica {
    private String       descripcion;
    private AccionMagica efecto;

    public Magica(String nombre, String descripcion, AccionMagica efecto) {
        super(nombre);
        this.descripcion = descripcion;
        this.efecto      = efecto;
    }
} 