import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaDuelo extends JFrame {

    private MotorJuego motor;

    private JLabel lblTurno;
    private JLabel lblJugadorActivo;
    private JLabel lblLpPropio;
    private JLabel lblLpRival;
    private JLabel lblMazoPropio;
    private JLabel lblMazoRival;

    private DefaultListModel<String> modeloCampoRival  = new DefaultListModel<>();
    private DefaultListModel<String> modeloCampoPropio = new DefaultListModel<>();
    private DefaultListModel<String> modeloMano        = new DefaultListModel<>();

    private JList<String> lstCampoRival;
    private JList<String> lstCampoPropio;
    private JList<String> lstMano;

    private JTextArea txtLog;

    private JButton btnJugarCarta;
    private JButton btnAtacar;
    private JButton btnPasarTurno;

    public VentanaDuelo(MotorJuego motor) {
        this.motor = motor;
        setTitle("Yu-Gi-Oh! - Duelo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 620);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponentes();
        actualizarVista();
    }

    private void initComponentes() {
        setLayout(new BorderLayout(5, 5));

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(),  BorderLayout.CENTER);
        add(crearPanelMano(),     BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        lblTurno         = new JLabel("Turno: 1", SwingConstants.LEFT);
        lblJugadorActivo = new JLabel("Turno de: ...", SwingConstants.CENTER);
        lblLpRival       = new JLabel("LP Rival: 8000", SwingConstants.RIGHT);
        lblMazoRival     = new JLabel("Mazo Rival: 20", SwingConstants.RIGHT);
        lblLpPropio      = new JLabel("Tus LP: 8000", SwingConstants.LEFT);
        lblMazoPropio    = new JLabel("Tu mazo: 20", SwingConstants.LEFT);

        lblJugadorActivo.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(lblTurno);
        panel.add(lblJugadorActivo);
        panel.add(lblLpRival);
        panel.add(lblLpPropio);
        panel.add(lblMazoPropio);
        panel.add(lblMazoRival);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JPanel panelCampos = new JPanel(new GridLayout(2, 1, 5, 5));

        lstCampoRival  = new JList<>(modeloCampoRival);
        lstCampoPropio = new JList<>(modeloCampoPropio);

        JScrollPane scrollRival  = new JScrollPane(lstCampoRival);
        JScrollPane scrollPropio = new JScrollPane(lstCampoPropio);

        scrollRival.setBorder(BorderFactory.createTitledBorder("Campo Rival"));
        scrollPropio.setBorder(BorderFactory.createTitledBorder("Tu Campo"));

        panelCampos.add(scrollRival);
        panelCampos.add(scrollPropio);

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("Log del Duelo"));

        panel.add(panelCampos);
        panel.add(scrollLog);

        return panel;
    }

    private JPanel crearPanelMano() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        lstMano = new JList<>(modeloMano);
        lstMano.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstMano.setVisibleRowCount(3);
        JScrollPane scrollMano = new JScrollPane(lstMano);
        scrollMano.setBorder(BorderFactory.createTitledBorder("Tu Mano"));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnJugarCarta = new JButton("Jugar Carta");
        btnAtacar     = new JButton("Atacar");
        btnPasarTurno = new JButton("Pasar Turno");

        panelBotones.add(btnJugarCarta);
        panelBotones.add(btnAtacar);
        panelBotones.add(btnPasarTurno);

        panel.add(scrollMano,   BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        btnJugarCarta.addActionListener(e -> accionJugarCarta());
        btnAtacar.addActionListener(e -> accionAtacar());
        btnPasarTurno.addActionListener(e -> accionPasarTurno());

        return panel;
    }
    private void accionJugarCarta() {
        int indiceCarta = lstMano.getSelectedIndex();
        if (indiceCarta < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una carta de tu mano.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (motor.yaJugoUnaCarta()) {
            JOptionPane.showMessageDialog(this, "Ya jugaste una carta este turno.", "Acción no permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Carta carta = motor.getActivo().getMano().get(indiceCarta);

        if (carta.esMonstruo()) {
            accionJugarMonstruo(indiceCarta, carta.comoMonstruo());
        } else {
            String error = motor.jugarCarta(indiceCarta, Posicion.ATAQUE, -1);
            if (error != null) {
                JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.WARNING_MESSAGE);
            }
        }

        actualizarVista();
        verificarFin();
    }

    private void accionJugarMonstruo(int indiceCarta, Monstruo monstruo) {
        int indiceSacrificio  = -1;
        int indiceSacrificio2 = -1;

        if (monstruo.getNivel() > 4) {
            int sacrificiosRequeridos = monstruo.getNivel() >= 7 ? 2 : 1;
            List<Monstruo> campo = motor.getActivo().getCampo();

            if (campo.size() < sacrificiosRequeridos) {
                JOptionPane.showMessageDialog(this,
                    "Necesitas " + sacrificiosRequeridos + " monstruo(s) en campo para sacrificar.",
                    "Sacrificio requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Primer sacrificio
            String[] opciones = new String[campo.size()];
            for (int i = 0; i < campo.size(); i++) {
                opciones[i] = i + ": " + campo.get(i).getNombre() + " ATK:" + campo.get(i).getAtk();
            }
            String eleccion = (String) JOptionPane.showInputDialog(this,
                "Elige el monstruo a sacrificar (1/" + sacrificiosRequeridos + "):",
                "Sacrificio", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
            if (eleccion == null) return;
            indiceSacrificio = Integer.parseInt(eleccion.split(":")[0]);

            // Segundo sacrificio (nivel 7+)
            if (sacrificiosRequeridos == 2) {
                // Lista sin el primero
                String[] opciones2 = new String[campo.size() - 1];
                int idx = 0;
                for (int i = 0; i < campo.size(); i++) {
                    if (i != indiceSacrificio) {
                        opciones2[idx++] = i + ": " + campo.get(i).getNombre() + " ATK:" + campo.get(i).getAtk();
                    }
                }
                String eleccion2 = (String) JOptionPane.showInputDialog(this,
                    "Elige el segundo monstruo a sacrificar (2/2):",
                    "Segundo Sacrificio", JOptionPane.PLAIN_MESSAGE, null, opciones2, opciones2[0]);
                if (eleccion2 == null) return;
                // El índice en opciones2 apunta al campo original
                int idxOriginal = Integer.parseInt(eleccion2.split(":")[0]);
                // Ajustar índice en campo reducido (el primer sacrificio ya se removerá)
                indiceSacrificio2 = idxOriginal > indiceSacrificio ? idxOriginal - 1 : idxOriginal;
            }
        }

        String[] posOpciones = {"Ataque", "Defensa"};
        int posEleccion = JOptionPane.showOptionDialog(this,
            "¿En qué posición invocar a " + monstruo.getNombre() + "?",
            "Posición de invocación",
            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, posOpciones, posOpciones[0]);

        if (posEleccion < 0) return;

        Posicion posicion = posEleccion == 0 ? Posicion.ATAQUE : Posicion.DEFENSA;
        String error = motor.jugarCarta(indiceCarta, posicion, indiceSacrificio, indiceSacrificio2);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void accionAtacar() {
        if (motor.esPrimerTurno()) {
            JOptionPane.showMessageDialog(this, "No se puede atacar en el primer turno.", "Acción no permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (motor.yaAtaco()) {
            JOptionPane.showMessageDialog(this, "Ya atacaste este turno.", "Acción no permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Monstruo> campoPropio = motor.getActivo().getCampo();
        if (campoPropio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes monstruos en campo.", "Sin monstruos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] atacantes = new String[campoPropio.size()];
        for (int i = 0; i < campoPropio.size(); i++) {
            Monstruo m = campoPropio.get(i);
            atacantes[i] = i + ": " + m.getNombre() + " ATK:" + m.getAtk() + " " + m.getPosicion();
        }
        String eleccionAtacante = (String) JOptionPane.showInputDialog(this,
            "Elige tu monstruo atacante:",
            "Atacar", JOptionPane.PLAIN_MESSAGE, null, atacantes, atacantes[0]);
        if (eleccionAtacante == null) return;
        int indiceAtacante = Integer.parseInt(eleccionAtacante.split(":")[0]);

        int indiceDefensor = -1;
        List<Monstruo> campoRival = motor.getOponente().getCampo();

        if (!campoRival.isEmpty()) {
            String[] defensores = new String[campoRival.size()];
            for (int i = 0; i < campoRival.size(); i++) {
                Monstruo m = campoRival.get(i);
                defensores[i] = i + ": " + m.getNombre() + " ATK:" + m.getAtk() + " DEF:" + m.getDef() + " " + m.getPosicion();
            }
            String eleccionDefensor = (String) JOptionPane.showInputDialog(this,
                "Elige el monstruo rival a atacar:",
                "Seleccionar objetivo", JOptionPane.PLAIN_MESSAGE, null, defensores, defensores[0]);
            if (eleccionDefensor == null) return;
            indiceDefensor = Integer.parseInt(eleccionDefensor.split(":")[0]);
        }

        String error = motor.atacar(indiceAtacante, indiceDefensor);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.WARNING_MESSAGE);
        }

        actualizarVista();
        verificarFin();
    }

        private void accionPasarTurno() {
        motor.pasarTurno();
        motor.iniciarTurno();
        actualizarVista();
        verificarFin();

        if (!motor.isJuegoTerminado()) {
            JOptionPane.showMessageDialog(this,
                "Turno " + motor.getNumeroTurno() + " — Le toca a: " + motor.getActivo().getNombre(),
                "Cambio de turno", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarVista() {
        Jugador activo   = motor.getActivo();
        Jugador oponente = motor.getOponente();

        lblTurno.setText("Turno: " + motor.getNumeroTurno());
        lblJugadorActivo.setText("Turno de: " + activo.getNombre());
        lblLpPropio.setText("Tus LP: " + activo.getLp());
        lblLpRival.setText("LP Rival (" + oponente.getNombre() + "): " + oponente.getLp());
        lblMazoPropio.setText("Tu mazo: " + activo.getMazo().size() + " cartas");
        lblMazoRival.setText("Mazo rival: " + oponente.getMazo().size() + " cartas");

        modeloCampoRival.clear();
        for (Monstruo m : oponente.getCampo()) {
            modeloCampoRival.addElement(m.getNombre()
                + " | ATK:" + m.getAtk()
                + " DEF:" + m.getDef()
                + " | " + m.getPosicion());
        }
        if (oponente.tieneTrampas()) {
            modeloCampoRival.addElement("[ " + oponente.getTrampas().size() + " trampa(s) boca abajo ]");
        }

        modeloCampoPropio.clear();
        for (Monstruo m : activo.getCampo()) {
            String puede = m.puedeAtacar() ? " ⚔" : "";
            modeloCampoPropio.addElement(m.getNombre()
                + " | ATK:" + m.getAtk()
                + " DEF:" + m.getDef()
                + " | " + m.getPosicion() + puede);
        }
        if (activo.tieneTrampas()) {
            modeloCampoPropio.addElement("[ " + activo.getTrampas().size() + " trampa(s) boca abajo ]");
        }

        modeloMano.clear();
        for (Carta c : activo.getMano()) {
            if (c.esMonstruo()) {
                Monstruo m = c.comoMonstruo();
                modeloMano.addElement("[MONSTRUO] " + m.getNombre()
                    + " ATK:" + m.getAtk()
                    + " DEF:" + m.getDef()
                    + " LVL:" + m.getNivel());
            } else if (c instanceof Magica) {
                modeloMano.addElement("[MAGICA] " + c.getNombre()
                    + " - " + ((Magica) c).getDescripcion());
            } else if (c instanceof Trampa) {
                modeloMano.addElement("[TRAMPA] " + c.getNombre()
                    + " - " + ((Trampa) c).getDescripcion());
            }
        }

        txtLog.setText("");
        for (String msg : motor.getLog()) {
            txtLog.append(msg + "\n");
        }
        txtLog.setCaretPosition(txtLog.getDocument().getLength());

        boolean esActivo = !motor.isJuegoTerminado();
        btnJugarCarta.setEnabled(esActivo && !motor.yaJugoUnaCarta());
        btnAtacar.setEnabled(esActivo && !motor.esPrimerTurno() && !motor.yaAtaco());
        btnPasarTurno.setEnabled(esActivo);
    }

    private void verificarFin() {
        if (motor.isJuegoTerminado()) {
            VentanaFin fin = new VentanaFin(motor.getGanador().getNombre());
            fin.setVisible(true);
            this.dispose();
        }
    }
}