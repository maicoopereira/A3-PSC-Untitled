
package DAO;
import model.Produto;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;


public class EstoqueDAO {
    public static ArrayList<Produto> estoqueLista = new ArrayList<>();

    public EstoqueDAO(){};
    
    public Connection getEstoqueConnection() {
        
        Connection connection = null; // cria um arquivo de conexao com o BD
        
        try {

            // Carregamento do JDBC Driver
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);

            // Configurar a conexao
            String server = "localhost"; //endereco do MySQL
            String database = "estoque";
            String url = "jdbc:mysql://" + server + ":3306/" + database + "?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = "riyXB3Lu";

            connection = DriverManager.getConnection(url, user, password);

            // Teste de conexao
            if (connection != null) {
                System.out.println("Status: Conectado!");
            } else {
                System.out.println("Status: N�O CONECTADO!");
            }

            return connection;

        } catch (ClassNotFoundException e) {  //Driver n�o encontrado
            System.out.println("O driver nao foi encontrado. " + e.getMessage() );
            return null;

        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar...");
            return null;
        }

        
        
        
    }
    
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

}
