public class Monstruo extends Carta {
    private int     atk;
    private int     def;
    private int     nivel;
    private boolean puedeAtacar;
    private Posicion posicion;

    public Monstruo(String nombre, int atk, int def, int nivel) {
        super(nombre);
        if (atk < 0)                 throw new IllegalArgumentException("ATK no puede ser negativo.");
        if (def < 0)                 throw new IllegalArgumentException("DEF no puede ser negativa.");
        if (nivel < 1 || nivel > 12) throw new IllegalArgumentException("El nivel debe estar entre 1 y 12.");
        this.atk         = atk;
        this.def         = def;
        this.nivel       = nivel;
        this.puedeAtacar = false;
        this.posicion    = Posicion.ATAQUE;
    }
} 