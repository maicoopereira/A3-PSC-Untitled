
package DAO;
import model.Produto;
import java.util.ArrayList;
import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;



public class EstoqueDAO {
    public static ArrayList<Produto> estoqueLista = new ArrayList<>();
    
    
    
    
    public EstoqueDAO(){};
    
    
    //methods
    public Connection getEstoqueConnection() throws SQLException {     
        try {
            Connection conn = DataBaseConnection.getConnection();
            return conn;

        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar...");
            return null;
        }
        
    }
    //add product to the db.
    public void InsertProdutoBD(Produto objeto) {
        String sql = "INSERT INTO produtos(idprodutos, nomeproduto, descricaoproduto, quantidade, precoproduto, datadecadastro, datadeatualizacao) VALUES(?,?,?,?,?,?,?)";


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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        //remove a product from db
    public void RemoveProdutoBD(Produto objeto) {
        String sql = "DELETE FROM produtos WHERE produtoid="+objeto.getID;
        int result;
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement()){
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
        
        //return the bigger id in the db.
    public int maiorID() {
            
        String sql = "SELECT MAX(ProdutoId) AS max_id FROM produtos";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet result = stmt.executeQuery(sql)) {
                if (result.next()) {
                    return Integer.parseInt(result.getString("max_id"));
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
        }
    
    //read method
        public String lerDados(){
            String sql = "SELECT * FROM produtos";
            try(PreparedStatement stmt = this.getEstoqueConnection().prepareStatement(sql);
                ResultSet result = stmt.executeQuery();) {
                while (result.next()){
                    int id = result.getInt("idprodutos");
                    String name = result.getString("nomeproduto");
                    String descricao = result.getString("descricaoproduto");
                    int quantidade = result.getInt("quantidade");
                    BigDecimal preco = result.getBigDecimal("precoproduto");
                    Date dataDeCadastroDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                    LocalDate dataDeCadastro = dataDeCadastroDate.toLocalDate(); //convert to LocalDate to code
                    Date dataDeAtualizacaoDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                    LocalDate dataDeAtualizacao = dataDeAtualizacaoDate.toLocalDate(); //convert to LocalDate to code
                    
                    
                    
                    
                }
                
            }catch (SQLException e){
                e.printStackTrace();
            }
            return "SELECT id, name, email FROM users";
        }
    
    
    
    //update method
        public void atualizarDados(Produto objeto) {
        
        String sql = "UPDATE produtos SET nomeproduto = ?, descricaoproduto = ?, quantidade = ?, precoproduto = ?, datadeatualizacao = ?, categoria = ?, datadevalidade = ? WHERE ProdutoId = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, objeto.getNome);
            stmt.setString(2, objeto.getDescricao);
            stmt.setInt(3, objeto.getQuantidade);
            stmt.setBigDecimal(4, objeto.getPreco);
            stmt.setDate(5, objeto.getDataDeAtualizacao);
            stmt.setString(6, objeto.getCategoria);
            stmt.setDate(7, objeto.getDataDeValidade);
            
            stmt.execute();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
