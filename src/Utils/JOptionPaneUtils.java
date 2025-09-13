package Utils;
import javax.swing.*;
import java.awt.*;

public class JOptionPaneUtils {

    public static void ShowMessage(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    public static void ShowMessageTimed(String message, int milliseconds) {
        JOptionPane pane = new JOptionPane(
                message,
                JOptionPane.INFORMATION_MESSAGE
        );

        JDialog dialog = pane.createDialog("Alerta");

        Timer timer = new Timer(milliseconds, e -> dialog.dispose());
        timer.setRepeats(false); // que se ejecute solo una vez
        timer.start();

        dialog.setVisible(true);
    }

    public static String InputString(String message) {
        while (true) {
            String input = JOptionPane.showInputDialog(message + ": ");

            if (input == null) { // Cancelar
                ShowMessage("Entrada cancelada.");
                return null; // o "" si prefieres
            }

            if (input.trim().isEmpty()) { // Cadena vacía
                ShowMessage("Por favor, ingrese un texto válido.");
            } else {
                return input.trim(); // devolvemos sin espacios extra
            }
        }
    }

    public static int InputInt(String message) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(message + ": ");
                if (input == null) { // Cancelar
                    ShowMessage("Input cancelled ❌");
                    return -1;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                ShowMessage("Por favor, ingrese un número entero válido.");
            }
        }
    }


    public static double InputDouble(String message) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(message + ": ");
                if (input == null) { // Cancelar
                    ShowMessage("Entrada cancelada.");
                    return -1;
                }
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                ShowMessage("Por favor, ingrese un número decimal válido.");
            }
        }
    }


    public static boolean InputBoolean(String message) {
        int option = JOptionPane.showConfirmDialog(
                null,
                message,
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );
        return option == JOptionPane.YES_OPTION;
    }

    public static int InputOption(String message, String title, String[] options) {
        int seleccion = JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        return seleccion;
    }

    public static void messagemenu(StringBuilder inventary){
        JTextArea textArea = new JTextArea(inventary.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "Store Inventory", JOptionPane.INFORMATION_MESSAGE);
    }
}


