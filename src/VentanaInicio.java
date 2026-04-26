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
}