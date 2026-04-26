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
}