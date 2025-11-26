package view;

import DAO.EstoqueDAO;

public class Main {
    public static void main(String[] args) {
        // Se o banco estiver vazio, executa principal.Principal para semear com os produtos de exemplo.
        try {
            java.util.List lista = EstoqueDAO.gerarLista();
            if (lista == null || lista.isEmpty()) {
                try {
                    principal.Principal.main(new String[0]);
                } catch (Throwable t) {
                    System.err.println("Aviso: não foi possível executar principal.Principal(): " + t.getMessage());
                }
            }
        } catch (Throwable t) {
            System.err.println("Aviso ao verificar estoque: " + t.getMessage());
        }

        javax.swing.SwingUtilities.invokeLater(() -> new TelaProdutos().setVisible(true));
    }
}
//teste