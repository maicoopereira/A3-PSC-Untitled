package principal;

import view.TelaProdutos;

public class Principal {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> new TelaProdutos().setVisible(true));

      }
}  

