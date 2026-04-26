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
}