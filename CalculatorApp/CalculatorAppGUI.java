import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorAppGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField display;
    private JButton[] buttons;
    private String currentInput = "";
    private double result = 0;
    private String operator = "";

    public CalculatorAppGUI() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        display = new JTextField();
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4)) ;

        buttons = new JButton[16];
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            panel.add(buttons[i]);
        }

        for (int i = 0; i < 16; i++) {
            buttons[i].addActionListener(new ButtonClickListener());
        }

        frame.add(display, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.matches("[0-9]")) {
                currentInput += command;
                display.setText(currentInput);
            } else if (command.equals(".")) {
                if (!currentInput.contains(".")) {
                    currentInput += ".";
                    display.setText(currentInput);
                }
            } else if (command.equals("C")) {
                currentInput = "";
                result = 0;
                operator = "";
                display.setText("");
            } else if (command.equals("=")) {
                performOperation();
                display.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
                operator = "";
            } else if (command.matches("[+\\-*/]")) {
                if (!currentInput.isEmpty()) {
                    performOperation();
                    currentInput = "";
                }
                operator = command;
            }
        }
    }

    private void performOperation() {
        if (!currentInput.isEmpty()) {
            double input = Double.parseDouble(currentInput);
            switch (operator) {
                case "+":
                    result += input;
                    break;
                case "-":
                    result -= input;
                    break;
                case "*":
                    result *= input;
                    break;
                case "/":
                    if (input != 0) {
                        result /= input;
                    } else {
                        display.setText("Error: Division by zero");
                        result = 0;
                    }
                    break;
                default:
                    result = input;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorAppGUI());
    }
}
