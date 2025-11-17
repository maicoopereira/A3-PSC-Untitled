package principal;
import model.Produto;
import DAO.EstoqueDAO;
import java.time.LocalDate;
import view.TelaPrincipal;

public class Principal {

    public static void main(String[] args) {
        
        Produto p1 = new Produto(5, "cerveja Heineken", "cervejinha gelada", "bebidas alcoolicas", "5,99", "20-07-2025");
        System.out.println(EstoqueDAO.maiorID());
        System.out.println(p1.getIdProdutos());
        
        EstoqueDAO.InsertProdutoBD(p1);
        System.out.println(EstoqueDAO.gerarLista().toString());
        
        EstoqueDAO.RemoveProdutoBD(1,2,3,4,5,6,7,8,9,10);
        System.out.println(EstoqueDAO.gerarLista());
        
        
        TelaPrincipal objetotela = new TelaPrincipal();
        objetotela.setVisible(true);
    }
    
    
}
