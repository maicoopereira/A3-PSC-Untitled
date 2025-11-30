
package DAO;
import model.Produto;
import java.util.ArrayList;
import java.sql.*;
import java.math.BigDecimal;
import java.time.LocalDate;




public class EstoqueDAO {
    public static ArrayList<Produto> estoqueLista = new ArrayList<>();
    
    
    //methods
    public final Connection getEstoqueConnection() throws SQLException {     
        try {
            Connection conn = DataBaseConnection.getConnection();
            return conn;

        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar...");
            return null;
        }
        
    }
    //add product to the db.
    public static void InsertProdutoBD(Produto objeto) {
        String sql = "INSERT INTO produtos(idprodutos, nomeproduto, descricaoproduto, categoria, quantidade, precoproduto, datadecadastro, datadeatualizacao, datadevalidade) VALUES(?,?,?,?,?,?,?,?,?)";


        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, objeto.getIdProdutos());
            stmt.setString(2, objeto.getNomeProduto());
            stmt.setString(3, objeto.getDescricaoProduto());
            stmt.setString(4, objeto.getCategoria());
            stmt.setInt(5, objeto.getQuantidade());
            stmt.setBigDecimal(6, objeto.getPrecoProduto());
            stmt.setTimestamp(7, Timestamp.valueOf(objeto.getDataDeCadastro().atStartOfDay()));
            stmt.setTimestamp(8, Timestamp.valueOf(objeto.getDataDeAtualizacao().atStartOfDay()));
            stmt.setTimestamp(9, Timestamp.valueOf(objeto.getDataDeValidade().atStartOfDay()));

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        //remove a product from db
    public static String RemoveProdutoBD(Produto objeto) {       
        String sql = "DELETE FROM produtos WHERE idprodutos="+objeto.getIdProdutos();
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement()){
            
            stmt.executeUpdate(sql);
            return "Produto removido com sucesso!";
        } catch (SQLException e) {
            return "Erro ao remover produto! " + e.getMessage();
        }
        

    }
    public static String RemoveProdutoBD(int... args) {       
        String sql;
        for (int arg : args){
            sql = "DELETE FROM produtos WHERE idprodutos=";
            sql = sql + String.valueOf(arg);
        
            System.out.println(sql);
            try (Connection conn = DataBaseConnection.getConnection();
                 Statement stmt = conn.createStatement()){

                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                return "Erro ao remover produto! " + e.getMessage();
            }

        }
        return "Produto removido com sucesso!"; 
    }
        
        //return the bigger id in the db.
    public static int maiorID() {

        String sql = "SELECT MAX(idprodutos) AS max_id FROM produtos";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet result = stmt.executeQuery()) {

            if (result.next()) {  // ESSENCIAL
                int maxId = result.getInt("max_id");

                // Se max_id for NULL, então a tabela está vazia
                if (result.wasNull()) {
                    return 1;
                }

                return maxId + 1;
            }
        } catch (SQLException e) {
            e.getMessage();
            
        }
        return 1;
        
        }
    
    //read method
    public static ArrayList gerarLista(){

        String sql = "SELECT * FROM produtos";
        estoqueLista.clear();

        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery(sql)) {

            while (result.next()){
                int id = result.getInt("idprodutos");
                String nome = result.getString("nomeproduto");
                String descricao = result.getString("descricaoproduto");
                String categoria = result.getString("categoria");
                int quantidade = result.getInt("quantidade");
                BigDecimal preco = result.getBigDecimal("precoproduto");
                Date dataDeCadastroDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeCadastro = dataDeCadastroDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeAtualizacaoDate = result.getDate("datadeatualizacao"); //get a date from the mySQL db
                LocalDate dataDeAtualizacao = dataDeAtualizacaoDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeValidadeDate = result.getDate("datadevalidade"); //get a date from the mySQL db
                LocalDate dataDeValidade = dataDeValidadeDate.toLocalDate(); //convert to LocalDate to code

                Produto novoProduto = new Produto(id, quantidade, nome, descricao, categoria, preco, dataDeCadastro, dataDeAtualizacao, dataDeValidade);

                estoqueLista.add(novoProduto);                   
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return estoqueLista;
    }



//update method - CORRIGIDO
public static boolean atualizarDados(Produto objeto) {
    String sql = "UPDATE produtos SET nomeproduto = ?, descricaoproduto = ?, quantidade = ?, precoproduto = ?, datadeatualizacao = ?, categoria = ?, datadevalidade = ? WHERE idprodutos = ?";

    try (Connection conn = DataBaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, objeto.getNomeProduto());
        stmt.setString(2, objeto.getDescricaoProduto());
        stmt.setInt(3, objeto.getQuantidade());
        stmt.setBigDecimal(4, objeto.getPrecoProduto());
        stmt.setTimestamp(5, Timestamp.valueOf(objeto.getDataDeAtualizacao().atStartOfDay()));
        stmt.setString(6, objeto.getCategoria());
        stmt.setTimestamp(7, Timestamp.valueOf(objeto.getDataDeValidade().atStartOfDay()));
        stmt.setInt(8, objeto.getIdProdutos()); // <--- ID no WHERE

        int affected = stmt.executeUpdate();
        return affected > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    //  SELECT * FROM products ORDER BY price DESC;
    public static ArrayList gerarListaMaioresPrecos(){

        String sql = "SELECT * FROM produtos ORDER BY precoproduto DESC";
        estoqueLista.clear();

        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery()) {

            while (result.next()){
                int id = result.getInt("idprodutos");
                String nome = result.getString("nomeproduto");
                String descricao = result.getString("descricaoproduto");
                String categoria = result.getString("categoria");
                int quantidade = result.getInt("quantidade");
                BigDecimal preco = result.getBigDecimal("precoproduto");
                Date dataDeCadastroDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeCadastro = dataDeCadastroDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeAtualizacaoDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeAtualizacao = dataDeAtualizacaoDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeValidadeDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeValidade = dataDeValidadeDate.toLocalDate(); //convert to LocalDate to code

                Produto novoProduto = new Produto(id, quantidade, nome, descricao, categoria, preco, dataDeCadastro, dataDeAtualizacao, dataDeValidade);

                estoqueLista.add(novoProduto);                   
            }

        }catch (SQLException e){
            e.getMessage();
        }
        return estoqueLista;
    }
    
    public static ArrayList gerarListaMaioresPrecos(BigDecimal precoDeCorte){

        String sql = "SELECT * FROM produtos WHERE precoproduto >= " + precoDeCorte + " ORDER BY precoproduto DESC";
        estoqueLista.clear();

        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery()) {

            while (result.next()){
                int id = result.getInt("idprodutos");
                String nome = result.getString("nomeproduto");
                String descricao = result.getString("descricaoproduto");
                String categoria = result.getString("categoria");
                int quantidade = result.getInt("quantidade");
                BigDecimal preco = result.getBigDecimal("precoproduto");
                Date dataDeCadastroDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeCadastro = dataDeCadastroDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeAtualizacaoDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeAtualizacao = dataDeAtualizacaoDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeValidadeDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeValidade = dataDeValidadeDate.toLocalDate(); //convert to LocalDate to code

                Produto novoProduto = new Produto(id, quantidade, nome, descricao, categoria, preco, dataDeCadastro, dataDeAtualizacao, dataDeValidade);

                estoqueLista.add(novoProduto);                   
            }

        }catch (SQLException e){
            e.getMessage();
        }
        return estoqueLista;
    }
    public static ArrayList gerarListaMaioresPrecos(int totalDeProdutos){

        String sql = "SELECT * FROM produtos ORDER BY precoproduto DESC LIMIT " + totalDeProdutos;
        estoqueLista.clear();

        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery()) {

            while (result.next()){
                int id = result.getInt("idprodutos");
                String nome = result.getString("nomeproduto");
                String descricao = result.getString("descricaoproduto");
                String categoria = result.getString("categoria");
                int quantidade = result.getInt("quantidade");
                BigDecimal preco = result.getBigDecimal("precoproduto");
                Date dataDeCadastroDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeCadastro = dataDeCadastroDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeAtualizacaoDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeAtualizacao = dataDeAtualizacaoDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeValidadeDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeValidade = dataDeValidadeDate.toLocalDate(); //convert to LocalDate to code

                Produto novoProduto = new Produto(id, quantidade, nome, descricao, categoria, preco, dataDeCadastro, dataDeAtualizacao, dataDeValidade);

                estoqueLista.add(novoProduto);                   
            }

        }catch (SQLException e){
            e.getMessage();
        }
        return estoqueLista;
    }
    
    public static ArrayList gerarListaDezMaioresPrecos(){

        String sql = "SELECT * FROM produtos ORDER BY precoproduto DESC LIMIT 10";
        estoqueLista.clear();

        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery()) {

            while (result.next()){
                int id = result.getInt("idprodutos");
                String nome = result.getString("nomeproduto");
                String descricao = result.getString("descricaoproduto");
                String categoria = result.getString("categoria");
                int quantidade = result.getInt("quantidade");
                BigDecimal preco = result.getBigDecimal("precoproduto");
                Date dataDeCadastroDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeCadastro = dataDeCadastroDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeAtualizacaoDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeAtualizacao = dataDeAtualizacaoDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeValidadeDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeValidade = dataDeValidadeDate.toLocalDate(); //convert to LocalDate to code

                Produto novoProduto = new Produto(id, quantidade, nome, descricao, categoria, preco, dataDeCadastro, dataDeAtualizacao, dataDeValidade);

                estoqueLista.add(novoProduto);                   
            }

        }catch (SQLException e){
            e.getMessage();
        }
        return estoqueLista;
    }
    
        //  SELECT * FROM products ORDER BY price ASC;
    public static ArrayList gerarListaMenoresPrecos(){

        String sql = "SELECT * FROM produtos ORDER BY precoproduto ASC";
        estoqueLista.clear();

        try(Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery()) {

            while (result.next()){
                int id = result.getInt("idprodutos");
                String nome = result.getString("nomeproduto");
                String descricao = result.getString("descricaoproduto");
                String categoria = result.getString("categoria");
                int quantidade = result.getInt("quantidade");
                BigDecimal preco = result.getBigDecimal("precoproduto");
                Date dataDeCadastroDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeCadastro = dataDeCadastroDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeAtualizacaoDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeAtualizacao = dataDeAtualizacaoDate.toLocalDate(); //convert to LocalDate to code
                Date dataDeValidadeDate = result.getDate("datadecadastro"); //get a date from the mySQL db
                LocalDate dataDeValidade = dataDeValidadeDate.toLocalDate(); //convert to LocalDate to code

                Produto novoProduto = new Produto(id, quantidade, nome, descricao, categoria, preco, dataDeCadastro, dataDeAtualizacao, dataDeValidade);

                estoqueLista.add(novoProduto);                   
            }

        }catch (SQLException e){
            e.getMessage();
        }
        return estoqueLista;
    }


//  SELECT * FROM products ORDER BY price ASC;

    
    
// SELECT * FROM produtos ORDER BY valorEmEstoque DESC LIMIT 10 
// SELECT * FROM produtos ORDER BY valorEmEstoque ASC LIMIT 10 


    
// SELECT SUM(valorEmEstoque) FROM produtos 
}

