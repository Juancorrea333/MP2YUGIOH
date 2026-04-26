import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Collections;

public class VentanaInicio extends JFrame {

    private JTextField txtJugador1;
    private JTextField txtJugador2;
    private JButton    btnIniciar;

    public VentanaInicio() {
        setTitle("Yu-Gi-Oh! - Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 220);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponentes();
    }

    private void initComponentes() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(new JLabel("Nombre Duelista 1:"));
        txtJugador1 = new JTextField();
        panel.add(txtJugador1);

        panel.add(new JLabel("Nombre Duelista 2:"));
        txtJugador2 = new JTextField();
        panel.add(txtJugador2);

        panel.add(new JLabel(""));
        btnIniciar = new JButton("¡INICIAR DUELO!");
        panel.add(btnIniciar);

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        add(new JLabel("  ★ YU-GI-OH! DUELO ★", SwingConstants.CENTER), BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        btnIniciar.addActionListener(e -> iniciarDuelo());
    }

    private void iniciarDuelo() {
        String nombre1 = txtJugador1.getText().trim();
        String nombre2 = txtJugador2.getText().trim();

        if (nombre1.isEmpty() || nombre2.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor ingresa los nombres de ambos duelistas.",
                "Nombres requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (nombre1.equals(nombre2)) {
            JOptionPane.showMessageDialog(this,
                "Los nombres deben ser diferentes.",
                "Nombres iguales", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Jugador j1 = new Jugador(nombre1);
        Jugador j2 = new Jugador(nombre2);

        List<Carta> mazoCompleto = FabricaCartas.crearMazoCompleto();
        Collections.shuffle(mazoCompleto);

        for (int i = 0;  i < 25; i++) j1.agregarAlMazo(mazoCompleto.get(i));
        for (int i = 25; i < 50; i++) j2.agregarAlMazo(mazoCompleto.get(i));

        for (int i = 0; i < 5; i++) {
            j1.robarCarta();
            j2.robarCarta();
        }

        MotorJuego motor = new MotorJuego(j1, j2);
        motor.iniciarTurno();

        VentanaDuelo duelo = new VentanaDuelo(motor);
        duelo.setVisible(true);
        this.dispose();
    }
}
