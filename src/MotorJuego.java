import java.util.*;

public class MotorJuego {

    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador activo;
    private Jugador oponente;
    private int     numeroTurno;
    private boolean juegoTerminado;
    private Jugador ganador;

    private boolean yaJugoUnaCarta;
    private boolean yaAtaco;

    private List<String> log = new ArrayList<>();

    public MotorJuego(Jugador j1, Jugador j2) {
        this.jugador1       = j1;
        this.jugador2       = j2;
        this.numeroTurno    = 1;
        this.juegoTerminado = false;
        this.ganador        = null;
        this.yaJugoUnaCarta = false;
        this.yaAtaco        = false;

        if (Math.random() > 0.5) {
            this.activo   = j1;
            this.oponente = j2;
        } else {
            this.activo   = j2;
            this.oponente = j1;
        }
    }

    public Jugador  getActivo()        { return activo; }
    public Jugador  getOponente()      { return oponente; }
    public int      getNumeroTurno()   { return numeroTurno; }
    public boolean  isJuegoTerminado() { return juegoTerminado; }
    public Jugador  getGanador()       { return ganador; }
    public boolean  esPrimerTurno()    { return numeroTurno == 1; }
    public boolean  yaJugoUnaCarta()   { return yaJugoUnaCarta; }
    public boolean  yaAtaco()          { return yaAtaco; }

    public List<String> getLog()       { return log; }

    public String getUltimoMensaje() {
        if (log.isEmpty()) return "";
        return log.get(log.size() - 1);
    }

    private void log(String mensaje) {
        log.add(mensaje);
    }

    public void iniciarTurno() {
        yaJugoUnaCarta = false;
        yaAtaco        = false;

        log("TURNO " + numeroTurno + " — " + activo.getNombre());

        if (!activo.robarCarta()) {
            log(activo.getNombre() + " no tiene cartas en su mazo. ¡PIERDE!");
            terminarJuego(oponente);
        } else {
            log(activo.getNombre() + " roba una carta.");
        }
    }
}