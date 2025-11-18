package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import DAO.EstoqueDAO;

public class Produto {
    private int id;
    private String nome;
    private String descricaoProduto;
    private int quantidade;
    private BigDecimal preco;
    private LocalDate dataCadastro;
    private LocalDate dataAtualizacao;
    private LocalDate dataValidade;
    private String categoria;

    //estanciar produto da interface
public Produto(String nome, String descricaoProduto, String preco,
    LocalDate dataValidade, String categoria) {
    this.id = EstoqueDAO.maiorID();
    this.nome = nome;
    this.descricaoProduto = descricaoProduto;
    this.preco = convertePreco(preco);
    this.dataCadastro = LocalDate.now();
    this.dataAtualizacao = LocalDate.now();
    this.dataValidade = dataValidade;
    this.categoria = categoria;
    }

    public Produto(int id, String nome, String descricaoProduto, int quantidade, BigDecimal preco, LocalDate dataCadastro, LocalDate dataAtualizacao, LocalDate dataValidade, String categoria) {
        this.id = id;
        this.nome = nome;
        this.descricaoProduto = descricaoProduto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
        this.dataValidade = dataValidade;
        this.categoria = categoria;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = EstoqueDAO.maiorID();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public void adicionarEstoque (int quant) {
        if (quant > 0) {
            this.quantidade += quant;
        this.dataAtualizacao = LocalDate.now();
        }
    }
    public void removerEstoque (int quant) {
        if ( quant <= 0) {
            return;
        }
        if (quant > this.quantidade) {
            return;
        }
        this.quantidade -= quant;
        this.dataAtualizacao = LocalDate.now();
    }
    public BigDecimal convertePreco(String precoString){
        String cleanString = precoString.replace(",", ".");
        BigDecimal precoConvertido = new BigDecimal(cleanString);
        return precoConvertido;
    }
}
