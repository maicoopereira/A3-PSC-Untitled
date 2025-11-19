package model;


import DAO.EstoqueDAO;
import java.time.LocalDate;
import java.math.BigDecimal;



public class Produto {
    int idProdutos, quantidade;
    String nomeProduto, descricaoProduto, categoria;
    BigDecimal precoProduto;
    LocalDate dataDeCadastro, dataDeAtualizacao, dataDeValidade;
    
    
    public Produto(int quantidade, String nomeProduto, String descricaoProduto, String categoria, String precoProduto, String dataDeValidade) {
        //Construtor usado para interface;
        this.idProdutos = EstoqueDAO.maiorID();
        this.quantidade = quantidade;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto; 
        this.categoria = categoria;
        this.precoProduto = convertPrecoProduto(precoProduto);
        this.dataDeCadastro = LocalDate.now();
        this.dataDeAtualizacao = LocalDate.now();
        this.dataDeValidade = Data.setDate(dataDeValidade);
    }
    
    public Produto(int idProdutos, int quantidade, String nomeProduto, String descricaoProduto, String categoria, BigDecimal precoProduto, LocalDate dataDeCadastro, LocalDate dataDeAtualizacao, LocalDate dataDeValidade) {
        //construtor exclusivo para montar lista DAO;
        this.idProdutos = idProdutos;
        this.quantidade = quantidade;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.categoria = categoria;
        this.precoProduto = precoProduto;
        this.dataDeCadastro = dataDeCadastro;
        this.dataDeAtualizacao = dataDeAtualizacao;
        this.dataDeValidade = dataDeValidade;
    }
    
    public int getIdProdutos() {
        return idProdutos;

    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(String precoProduto) {
        this.precoProduto = convertPrecoProduto(precoProduto);
    }

    public LocalDate getDataDeCadastro() {
        return dataDeCadastro;
    }

    public LocalDate getDataDeAtualizacao() {
        return dataDeAtualizacao;
    }

    public void setDataDeAtualizacao() {
        this.dataDeAtualizacao = LocalDate.now();
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(String dataDeValidade) {
        this.dataDeValidade = Data.setDate(dataDeValidade);
    }
        
    public final BigDecimal convertPrecoProduto(String precoProduto) {
        String cleanString = precoProduto.replace(",", ".");
        BigDecimal precoBigDecimal = new BigDecimal(cleanString);
        return precoBigDecimal;
    }
    
}

