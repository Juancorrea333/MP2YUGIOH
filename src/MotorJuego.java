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

    public String jugarCarta(int indiceCarta, Posicion posicion, int indiceSacrificio) {
        if (yaJugoUnaCarta) {
            return "Ya jugaste una carta este turno.";
        }

        List<Carta> mano = activo.getMano();

        if (indiceCarta < 0 || indiceCarta >= mano.size()) {
            return "Carta no válida.";
        }

        Carta carta = mano.get(indiceCarta);

        if (carta.esMonstruo() && carta.comoMonstruo().getNivel() > 4) {
            if (!activo.tieneMonstruos()) {
                return "Necesitas sacrificar un monstruo para invocar un monstruo de nivel 5 o más.";
            }
            List<Monstruo> campo = activo.getCampo();
            if (indiceSacrificio < 0 || indiceSacrificio >= campo.size()) {
                return "Índice de sacrificio no válido.";
            }
            activo.removerMonstruo(campo.get(indiceSacrificio));
            log("¡Sacrificio realizado!");
        }

        if (carta.esMonstruo()) {
            carta.comoMonstruo().setPosicion(posicion);
        }

        mano.remove(indiceCarta);
        carta.jugar(activo, oponente);
        log(activo.getNombre() + " juega: " + carta.getNombre());

        if (carta.esMonstruo()) {
            Monstruo m = carta.comoMonstruo();
            if (m.getPosicion() == Posicion.ATAQUE) {
                m.habilitarAtaque();
            } else {
                log(m.getNombre() + " está en DEFENSA y no puede atacar este turno.");
            }
            revisarTrampas(CondicionTrampa.AL_INVOCAR);
        }

        yaJugoUnaCarta = true;
        verificarFinDeJuego();
        return null;
    }

    public String atacar(int indiceAtacante, int indiceDefensor) {
        if (esPrimerTurno()) {
            return "No se puede atacar en el primer turno.";
        }
        if (yaAtaco) {
            return "Ya realizaste un ataque este turno.";
        }

        List<Monstruo> campo = activo.getCampo();
        if (indiceAtacante < 0 || indiceAtacante >= campo.size()) {
            return "Monstruo atacante no válido.";
        }

        Monstruo atacante = campo.get(indiceAtacante);

        if (!atacante.puedeAtacar()) {
            return atacante.getNombre() + " no puede atacar (ya atacó o está en DEFENSA).";
        }

        revisarTrampas(CondicionTrampa.EN_ATAQUE);
        if (juegoTerminado) {
            yaAtaco = true;
            return null;
        }

        if (!oponente.tieneMonstruos()) {
            resolverAtaqueDirecto(atacante);
        } else {
            List<Monstruo> campoRival = oponente.getCampo();
            if (indiceDefensor < 0 || indiceDefensor >= campoRival.size()) {
                return "Monstruo defensor no válido.";
            }
            resolverCombate(atacante, campoRival.get(indiceDefensor));
        }

        atacante.agotarAtaque();
        yaAtaco = true;
        verificarFinDeJuego();
        return null;
    }
}