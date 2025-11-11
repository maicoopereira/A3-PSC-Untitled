
package DAO;
import model.Produto;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;


public class EstoqueDAO {
    public static ArrayList<Produto> estoqueLista = new ArrayList<>();

    public EstoqueDAO(){};
    
    
    //methods
    public Connection getEstoqueConnection() throws SQLException {     
        try {
            Connection connection = DataBaseConnection.getConnection();
            return connection;

        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar...");
            return null;
        }
        
    }
        //add product to the db.
        public boolean InsertProdutoBD(Produto objeto) {
        String sql = "INSERT INTO produtos(idprodutos,nomeproduto,descricaoproduto,quantidade,precoproduto, datadecadastro, datadeatualizacao) VALUES(?,?,?,?,?,?,?)";


        try {
            PreparedStatement stmt = this.getEstoqueConnection().prepareStatement(sql);

            stmt.setInt(1, objeto.getId());
            stmt.setString(2, objeto.getNome());
            stmt.setString(3, objeto.getDescricao());
            stmt.setInt(4, objeto.getQuantidade());
            stmt.setBigDecimal(5, objeto.getPreco());
            stmt.setTimestamp(6, Timestamp.valueOf(objeto.getDataDeCadastro()));
            stmt.setTimestamp(7, Timestamp.valueOf(objeto.getDataDeAtualizacao()));

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }
    }
        //remove a product from db
        public boolean RemoveProdutoBD(Produto objeto) {
            return false;
        }
        
        //other methods...

    

}
