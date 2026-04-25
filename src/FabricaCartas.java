import java.util.*;

public class FabricaCartas {
    private FabricaCartas() {}
    /** Devuelve la lista completa de 50 cartas sin mezclar. */
    public static List<Carta> crearMazoCompleto() {
        List<Carta> mazo = new ArrayList<>();
        mazo.addAll(crearMonstruos());
        mazo.addAll(crearMagicas());
        mazo.addAll(crearTrampas());
        return mazo;
    }
} 