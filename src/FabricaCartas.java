import java.util.*;
public class FabricaCartas {
    private FabricaCartas() {}
    public static List<Carta> crearMazoCompleto() {
        List<Carta> mazo = new ArrayList<>();
        mazo.addAll(crearMonstruos());
        mazo.addAll(crearMagicas());
        mazo.addAll(crearTrampas());
        return mazo;
    }
    private static List<Carta> crearMonstruos() {
        List<Carta> lista = new ArrayList<>();
        lista.add(new Monstruo("Slime del Pantano",     300,  200, 1));
        lista.add(new Monstruo("Rata del Laberinto",    550,  400, 2));
        lista.add(new Monstruo("Goblin Ladrón",         600,  500, 2));
        lista.add(new Monstruo("Guerrero Humilde",     1200,  800, 3));
        lista.add(new Monstruo("Maga de la Luna",      1000, 1200, 3));
        lista.add(new Monstruo("Arquero del Viento",   1100,  900, 3));
        lista.add(new Monstruo("Golem de Piedra",       800, 1500, 3));
        lista.add(new Monstruo("Espectro Errante",     1300,  700, 3));
        lista.add(new Monstruo("Guerrero Llameante",   1500, 1200, 4));
        lista.add(new Monstruo("Kuriboh Evolucionado", 1400, 1300, 4));
        lista.add(new Monstruo("Jinzo Jr.",            1300, 1100, 4));
        lista.add(new Monstruo("Señor del Tiempo",     1600,  900, 4));
        lista.add(new Monstruo("Dragón Bebé",          1400, 1000, 4));
        lista.add(new Monstruo("Mago de la Oscuridad", 1700, 1400, 4));
        lista.add(new Monstruo("Caballero Celestial",  2000, 1600, 5));
        lista.add(new Monstruo("Bestia de las Sombras",1900, 1700, 5));
        lista.add(new Monstruo("Fénix Ardiente",       2100, 1200, 5));
        lista.add(new Monstruo("Dragón del Caos",      2200, 1800, 6));
        lista.add(new Monstruo("Espectro Oscuro",      2300, 1500, 6));
        lista.add(new Monstruo("Golem de Hierro",      1800, 2400, 6));
        lista.add(new Monstruo("Mago Oscuro",          2500, 2100, 7));
        lista.add(new Monstruo("Dragón Blanco",        3000, 2500, 7));
        lista.add(new Monstruo("Exodia el Prohibido",  2800, 2400, 7));
        lista.add(new Monstruo("Dragón Azul de Ojos Blancos", 3000, 2500, 8));
        lista.add(new Monstruo("Señor de los Dragones",       2800, 2300, 8));
        lista.add(new Monstruo("Arcángel Bahamut",            2600, 2700, 8));
        lista.add(new Monstruo("Fénix Divino",               3200, 2700, 9));
        lista.add(new Monstruo("Coloso Eterno",              3100, 3000, 9));
        lista.add(new Monstruo("Dragón Supremo",             3500, 3000, 11));
        lista.add(new Monstruo("Dios del Abismo",            4000, 3500, 12));
        return lista;
    }