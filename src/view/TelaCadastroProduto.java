package view;

import DAO.EstoqueDAO;
import model.Produto;
import model.Data;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * TelaCadastroProduto: aceita Produto no construtor para edição.
 */
public class TelaCadastroProduto extends JFrame {

    private JTextField txtNome;
    private JTextField txtCategoria;
    private JTextField txtQuantidade;
    private JTextField txtPreco;
    private JTextField txtValidade;
    private JTextArea txtDescricao;

    private boolean editMode = false;
    private Produto produtoEditando = null;

    public TelaCadastroProduto() {
        this(null);
    }

    // construtor para edição
    public TelaCadastroProduto(Produto produto) {
        setTitle(produto == null ? "Cadastrar Produto" : "Editar Produto");
        setSize(520, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel fundo = new JPanel();
        fundo.setBackground(new Color(245, 245, 245));
        fundo.setLayout(null);
        add(fundo);

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBounds(20, 20, 460, 500);
        card.setLayout(null);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));
        fundo.add(card);

        JLabel titulo = new JLabel(produto == null ? "Cadastrar Produto" : "Editar Produto");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setBounds(140, 20, 300, 30);
        card.add(titulo);

        JLabel lblNome = new JLabel("Nome do Produto:");
        lblNome.setBounds(40, 70, 200, 20);
        card.add(lblNome);

        txtNome = criarCampo(40, 95, 380);
        card.add(txtNome);

        JLabel lblDesc = new JLabel("Descrição:");
        lblDesc.setBounds(40, 140, 200, 20);
        card.add(lblDesc);

        txtDescricao = new JTextArea();
        txtDescricao.setBounds(40, 165, 380, 70);
        txtDescricao.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        card.add(txtDescricao);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(40, 245, 200, 20);
        card.add(lblCategoria);

        txtCategoria = criarCampo(40, 270, 380);
        card.add(txtCategoria);

        JLabel lblQuantidade = new JLabel("Quantidade:");
        lblQuantidade.setBounds(40, 315, 200, 20);
        card.add(lblQuantidade);

        txtQuantidade = criarCampo(40, 340, 180);
        card.add(txtQuantidade);

        JLabel lblPreco = new JLabel("Preço (R$):");
        lblPreco.setBounds(240, 315, 200, 20);
        card.add(lblPreco);

        txtPreco = criarCampo(240, 340, 180);
        card.add(txtPreco);

        JLabel lblValidade = new JLabel("Validade (AAAA-MM-DD):");
        lblValidade.setBounds(40, 385, 200, 20);
        card.add(lblValidade);

        txtValidade = criarCampo(40, 410, 380);
        card.add(txtValidade);

        JButton btnSalvar = new JButton(produto == null ? "Salvar Produto" : "Salvar Alterações");
        btnSalvar.setBounds(140, 455, 180, 30);
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(BorderFactory.createEmptyBorder());
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSalvar.addActionListener(e -> salvarProduto());

        card.add(btnSalvar);

        // se vier Produto, preenche campos e marca editMode
        if (produto != null) {
            this.editMode = true;
            this.produtoEditando = produto;
            preencherCamposParaEdicao(produto);
        }
    }

    private JTextField criarCampo(int x, int y, int width) {
        JTextField field = new JTextField();
        field.setBounds(x, y, width, 28);
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        return field;
    }

    private void preencherCamposParaEdicao(Produto p) {
        txtNome.setText(p.getNomeProduto());
        txtDescricao.setText(p.getDescricaoProduto());
        txtCategoria.setText(p.getCategoria());
        txtQuantidade.setText(String.valueOf(p.getQuantidade()));
        txtPreco.setText(String.valueOf(p.getPrecoProduto())); // BigDecimal#toString()
        txtValidade.setText(p.getDataDeValidade() != null ? p.getDataDeValidade().toString() : "");
    }

    private void salvarProduto() {
        try {
            String nome = txtNome.getText().trim();
            String descricao = txtDescricao.getText().trim();
            String categoria = txtCategoria.getText().trim();
            int quantidade = Integer.parseInt(txtQuantidade.getText().trim());
            String precoStr = txtPreco.getText().trim();
            String dataValidadeStr = txtValidade.getText().trim();

            if (nome.isEmpty() || descricao.isEmpty() || categoria.isEmpty() || precoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            // Normaliza preço (aceita vírgula ou ponto)
            BigDecimal preco;
            try {
                preco = new BigDecimal(precoStr.replace(",", "."));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço inválido!");
                return;
            }

            // Valida data (usa Data.setDate)
            LocalDate dataValidade;
            try {
                dataValidade = Data.setDate(dataValidadeStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Validade inválida! Use AAAA-MM-DD");
                return;
            }

            if (!editMode) {
                Produto novo = new Produto(quantidade, nome, descricao, categoria, precoStr, dataValidadeStr);
                EstoqueDAO.InsertProdutoBD(novo);
                JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
                dispose();
            } else {
                // atualizar o produto existente através de setters e chamar o DAO
                produtoEditando.setQuantidade(quantidade);
                produtoEditando.setNomeProduto(nome);
                produtoEditando.setDescricaoProduto(descricao);
                produtoEditando.setCategoria(categoria);
                produtoEditando.setPrecoProduto(precoStr); // setter aceita String
                produtoEditando.setDataDeValidade(dataValidadeStr);
                produtoEditando.setDataDeAtualizacao(); // define atualização para hoje

                try {
                    EstoqueDAO.atualizarDados(produtoEditando); // requer correção do DAO (veja instruções)
                    JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                } catch (Exception ex) {
                    // mesmo se o DAO não persistir, avisamos e fechamos para atualizar visual
                    JOptionPane.showMessageDialog(this, "Tentativa de atualizar produto. Se não persistiu, verifique o DAO: " + ex.getMessage());
                }
                dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + e.getMessage());
        }
    }
}
