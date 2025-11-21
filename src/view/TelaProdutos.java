package view;

import DAO.EstoqueDAO;
import model.Produto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

public class TelaProdutos extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private RoundedButton btnAdicionar;
    private JTextField campoBusca;

    // KPIs
    private JLabel lblProdutosCadastrados;
    private JLabel lblEstoqueBaixo;
    private JLabel lblCategorias;
    private JLabel lblTotalEstoque;

    // threshold para considerar "estoque baixo"
    private final int ESTOQUE_BAIXO_THRESHOLD = 50;

    // Modo local (sem tocar no DAO)
    private boolean useLocalOnly = false;
    private List<Produto> localProducts = new ArrayList<>();

    public TelaProdutos() {
        setTitle("Stockly — Produtos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1160, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(true);

        // SIDEBAR simples
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(247, 247, 247));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(18, 12, 18, 12));

        JLabel logo = new JLabel("Stockly");
        logo.setFont(new Font("Inter", Font.BOLD, 22));
        logo.setForeground(new Color(10, 80, 50));
        logo.setBorder(new EmptyBorder(6,6,18,6));
        sidebar.add(logo);
        sidebar.add(menuItemAtivo("Produtos"));
        sidebar.add(Box.createVerticalGlue());

        add(sidebar, BorderLayout.WEST);

        // CONTEÚDO PRINCIPAL
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        add(main, BorderLayout.CENTER);

        // Topo: busca + botão
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(Color.WHITE);
        topo.setBorder(new EmptyBorder(18, 22, 18, 22));

        campoBusca = new RoundedTextField("Buscar produto, categoria...");
        campoBusca.setPreferredSize(new Dimension(420, 40));
        topo.add(campoBusca, BorderLayout.WEST);

        btnAdicionar = new RoundedButton("+ Adicionar Produto", new Color(46, 204, 113));
        btnAdicionar.setPreferredSize(new Dimension(200, 42));
        btnAdicionar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAdicionar.addActionListener(e -> abrirCadastro());
        topo.add(btnAdicionar, BorderLayout.EAST);
        main.add(topo, BorderLayout.NORTH);

        // CARDS KPI
        JPanel cards = new JPanel(new GridLayout(1, 4, 16, 0));
        cards.setBorder(new EmptyBorder(0, 22, 18, 22));
        cards.setBackground(Color.WHITE);

        lblProdutosCadastrados = new JLabel("0");
        lblEstoqueBaixo = new JLabel("0");
        lblCategorias = new JLabel("0");
        lblTotalEstoque = new JLabel("0");

        cards.add(kpiCard("Produtos cadastrados", lblProdutosCadastrados, ""));
        cards.add(kpiCard("Estoque baixo", lblEstoqueBaixo, ""));
        cards.add(kpiCard("Categorias", lblCategorias, ""));
        cards.add(kpiCard("Total em estoque", lblTotalEstoque, ""));

        // Tabela com coluna de ações
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Produto", "Categoria", "Qtd", "Preço", "Validade", "Ações"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return column == 6; }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(42);
        tabela.setShowGrid(false);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getTableHeader().setPreferredSize(new Dimension(tabela.getWidth(), 42));
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(new Color(245, 245, 245));
        tabela.setFillsViewportHeight(true);

        // Renderer para coluna Qtd (badge)
        tabela.getColumnModel().getColumn(3).setCellRenderer(new QuantityRenderer());

        // Renderer + Editor para Ações (botões reais que funcionam)
        tabela.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tabela.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor());

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(Color.WHITE);

        JPanel tabelaPanel = new JPanel(new BorderLayout());
        tabelaPanel.setBorder(new EmptyBorder(16, 22, 22, 22));
        tabelaPanel.setBackground(Color.WHITE);
        tabelaPanel.add(scroll, BorderLayout.CENTER);

        // content (cards + tabela)
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.add(cards, BorderLayout.NORTH);
        content.add(tabelaPanel, BorderLayout.CENTER);
        main.add(content, BorderLayout.CENTER);

        // ações
        campoBusca.addActionListener(e -> aplicarFiltro());

        // Tenta carregar do DAO; se vazio, ativa modo local (sem tocar no DAO)
        try {
            List<Produto> produtosExistentes = EstoqueDAO.gerarLista();
            if (produtosExistentes == null || produtosExistentes.isEmpty()) {
                useLocalOnly = true;
                localProducts.clear();
                Produto fake = new Produto(
                        1,
                        32,
                        "Arroz 5Kg",
                        "Arroz branco tipo 1",
                        "Alimentos",
                        new BigDecimal("19.90"),
                        LocalDate.now(),
                        LocalDate.now(),
                        LocalDate.parse("2025-12-10")
                );
                localProducts.add(fake);
                // botão ADD permanece habilitado; ele vai adicionar localmente
                btnAdicionar.setToolTipText("Modo de teste local: adicionar adiciona produtos apenas localmente.");
            }
        } catch (Throwable t) {
            useLocalOnly = true;
            localProducts.clear();
            Produto fake = new Produto(
                    1,
                    32,
                    "Arroz 5Kg",
                    "Arroz branco tipo 1",
                    "Alimentos",
                    new BigDecimal("19.90"),
                    LocalDate.now(),
                    LocalDate.now(),
                    LocalDate.parse("2025-12-10")
            );
            localProducts.add(fake);
            btnAdicionar.setToolTipText("Modo de teste local: adicionar adiciona produtos apenas localmente.");
        }

        // carregar dados
        carregarProdutos();

        setVisible(true);
    }

    // abre a tela de cadastro (adiciona localmente se em modo local)
    private void abrirCadastro() {
        if (useLocalOnly) {
            showLocalAddDialog();
            return;
        }
        TelaCadastroProduto tela = new TelaCadastroProduto();
        tela.setVisible(true);
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent evt) {
                carregarProdutos();
            }
        });
    }

    // editar: abre TelaCadastroProduto (DAO) ou diálogo local se useLocalOnly
    private void editarProduto(int id) {
        if (useLocalOnly) {
            Produto p = buscarProdutoPorId(id);
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Produto não encontrado (id=" + id + ")");
                return;
            }
            showLocalEditDialog(p);
            return;
        }
        Produto p = buscarProdutoPorId(id);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Produto não encontrado (id=" + id + ")");
            return;
        }
        TelaCadastroProduto tela = new TelaCadastroProduto(p); // construtor para edição
        tela.setVisible(true);
        tela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent evt) {
                carregarProdutos();
            }
        });
    }

    // deletar (usa DAO se possível; senão remove do localProducts)
    private void deletarProduto(int id) {
        int r = JOptionPane.showConfirmDialog(this, "Deseja excluir o produto id=" + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            if (useLocalOnly) {
                localProducts.removeIf(p -> p.getIdProdutos() == id);
                carregarProdutos();
                JOptionPane.showMessageDialog(this, "Produto removido (modo local).");
                return;
            }
            try {
                String retorno = EstoqueDAO.RemoveProdutoBD(id);
                carregarProdutos();
                JOptionPane.showMessageDialog(this, retorno);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover via DAO: " + ex.getMessage());
                carregarProdutos();
            }
        }
    }

    // diálogo simples para adicionar produto localmente
    private void showLocalAddDialog() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        JTextField nome = new JTextField();
        JTextField descricao = new JTextField();
        JTextField categoria = new JTextField();
        JTextField quantidade = new JTextField();
        JTextField preco = new JTextField();
        JTextField validade = new JTextField();

        panel.add(new JLabel("Nome:"));
        panel.add(nome);
        panel.add(new JLabel("Descrição:"));
        panel.add(descricao);
        panel.add(new JLabel("Categoria:"));
        panel.add(categoria);
        panel.add(new JLabel("Quantidade:"));
        panel.add(quantidade);
        panel.add(new JLabel("Preço (ex: 19.90):"));
        panel.add(preco);
        panel.add(new JLabel("Validade (AAAA-MM-DD):"));
        panel.add(validade);

        int res = JOptionPane.showConfirmDialog(this, panel, "Adicionar produto (modo local)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            try {
                String n = nome.getText().trim();
                String d = descricao.getText().trim();
                String c = categoria.getText().trim();
                int q = Integer.parseInt(quantidade.getText().trim());
                BigDecimal pr = new BigDecimal(preco.getText().trim().replace(",", "."));
                LocalDate val = LocalDate.parse(validade.getText().trim());

                // id local: máximo + 1
                int newId = 1;
                for (Produto p : localProducts) if (p.getIdProdutos() >= newId) newId = p.getIdProdutos() + 1;

                Produto novo = new Produto(newId, q, n, d, c, pr, LocalDate.now(), LocalDate.now(), val);
                localProducts.add(novo);
                carregarProdutos();
                JOptionPane.showMessageDialog(this, "Produto adicionado (modo local).");
            } catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(this, "Quantidade/Preço inválidos.");
            } catch (DateTimeParseException dtp) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use AAAA-MM-DD.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + ex.getMessage());
            }
        }
    }

    // diálogo simples para editar produto localmente (altera o objeto existente)
    private void showLocalEditDialog(Produto p) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        JTextField nome = new JTextField(p.getNomeProduto());
        JTextField descricao = new JTextField(p.getDescricaoProduto());
        JTextField categoria = new JTextField(p.getCategoria());
        JTextField quantidade = new JTextField(String.valueOf(p.getQuantidade()));
        JTextField preco = new JTextField(String.valueOf(p.getPrecoProduto()));
        JTextField validade = new JTextField(p.getDataDeValidade() != null ? p.getDataDeValidade().toString() : "");

        panel.add(new JLabel("Nome:"));
        panel.add(nome);
        panel.add(new JLabel("Descrição:"));
        panel.add(descricao);
        panel.add(new JLabel("Categoria:"));
        panel.add(categoria);
        panel.add(new JLabel("Quantidade:"));
        panel.add(quantidade);
        panel.add(new JLabel("Preço (ex: 19.90):"));
        panel.add(preco);
        panel.add(new JLabel("Validade (AAAA-MM-DD):"));
        panel.add(validade);

        int res = JOptionPane.showConfirmDialog(this, panel, "Editar produto (modo local)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            try {
                String n = nome.getText().trim();
                String d = descricao.getText().trim();
                String c = categoria.getText().trim();
                int q = Integer.parseInt(quantidade.getText().trim());
                BigDecimal pr = new BigDecimal(preco.getText().trim().replace(",", "."));
                LocalDate val = LocalDate.parse(validade.getText().trim());

                p.setNomeProduto(n);
                p.setDescricaoProduto(d);
                p.setCategoria(c);
                p.setQuantidade(q);
                p.setPrecoProduto(pr.toString());
                p.setDataDeValidade(val.toString());
                p.setDataDeAtualizacao();

                carregarProdutos();
                JOptionPane.showMessageDialog(this, "Produto atualizado (modo local).");
            } catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(this, "Quantidade/Preço inválidos.");
            } catch (DateTimeParseException dtp) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use AAAA-MM-DD.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao editar: " + ex.getMessage());
            }
        }
    }

    // filtro simples por nome/categoria/id
    private void aplicarFiltro() {
        String texto = campoBusca.getText().trim().toLowerCase();
        List<Produto> todos = useLocalOnly ? localProducts : EstoqueDAO.gerarLista();
        if (texto.isEmpty()) {
            carregarProdutos();
            return;
        }
        modeloTabela.setRowCount(0);
        List<Produto> filtrados = new ArrayList<>();
        for (Produto p : todos) {
            if (p.getNomeProduto().toLowerCase().contains(texto) ||
                p.getCategoria().toLowerCase().contains(texto) ||
                String.valueOf(p.getIdProdutos()).contains(texto)) {
                modeloTabela.addRow(new Object[]{
                        p.getIdProdutos(),
                        p.getNomeProduto(),
                        p.getCategoria(),
                        p.getQuantidade(),
                        p.getPrecoProduto(),
                        p.getDataDeValidade(),
                        "" // placeholder para ações
                });
                filtrados.add(p);
            }
        }
        atualizarKPIs(filtrados);
    }

    // carrega produtos do DAO (ou do localProducts) e atualiza tabela + KPIs
    private void carregarProdutos() {
        modeloTabela.setRowCount(0);
        List<Produto> produtos;
        if (useLocalOnly) {
            produtos = localProducts;
        } else {
            produtos = EstoqueDAO.gerarLista();
            if (produtos == null) produtos = new ArrayList<>();
        }

        for (Produto p : produtos) {
            modeloTabela.addRow(new Object[]{
                    p.getIdProdutos(),
                    p.getNomeProduto(),
                    p.getCategoria(),
                    p.getQuantidade(),
                    p.getPrecoProduto(),
                    p.getDataDeValidade(),
                    "" // placeholder para ações
            });
        }
        atualizarKPIs(produtos);
    }

    // Atualiza os labels dos KPI com dados reais (calculados a partir da lista)
    private void atualizarKPIs(List<Produto> produtos) {
        int totalProdutos = produtos == null ? 0 : produtos.size();
        int estoqueBaixo = 0;
        Set<String> categorias = new HashSet<>();
        int totalUnidades = 0;
        if (produtos != null) {
            for (Produto p : produtos) {
                if (p.getQuantidade() < ESTOQUE_BAIXO_THRESHOLD) estoqueBaixo++;
                categorias.add(p.getCategoria());
                totalUnidades += p.getQuantidade();
            }
        }

        lblProdutosCadastrados.setText(String.valueOf(totalProdutos));
        lblEstoqueBaixo.setText(String.valueOf(estoqueBaixo));
        lblCategorias.setText(String.valueOf(categorias.size()));
        lblTotalEstoque.setText(String.valueOf(totalUnidades));
    }

    // busca local por id (considera modo local)
    private Produto buscarProdutoPorId(int id) {
        if (useLocalOnly) {
            for (Produto p : localProducts) if (p.getIdProdutos() == id) return p;
            return null;
        }
        List<Produto> produtos = EstoqueDAO.gerarLista();
        if (produtos == null) return null;
        for (Produto p : produtos) if (p.getIdProdutos() == id) return p;
        return null;
    }

    // UI helpers (kpiCard agora recebe JLabel)
    private JPanel kpiCard(String titulo, JLabel valorLabel, String variacao) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230), 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        JLabel t = new JLabel(titulo);
        t.setFont(new Font("SansSerif", Font.PLAIN, 13));

        valorLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        JLabel var = new JLabel(variacao);
        var.setFont(new Font("SansSerif", Font.BOLD, 12));
        var.setForeground(variacao.startsWith("-") ? new Color(220,50,50) : new Color(0,150,80));

        JPanel mid = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        mid.setOpaque(false);
        mid.add(valorLabel);
        mid.add(var);
        card.add(t, BorderLayout.NORTH);
        card.add(mid, BorderLayout.CENTER);
        return card;
    }

    private JButton menuItem(String texto) {
        JButton b = new JButton(texto);
        b.setFocusPainted(false);
        b.setBackground(new Color(247,247,247));
        b.setBorder(BorderFactory.createEmptyBorder(10,14,10,14));
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setForeground(new Color(90,90,90));
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return b;
    }

    private JButton menuItemAtivo(String texto) {
        JButton b = menuItem(texto);
        b.setBackground(new Color(230,255,240));
        b.setForeground(new Color(0,130,70));
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        return b;
    }

    // Botão padrão arredondado (mantive)
    private JButton iconButton(String symbol) {
        JButton b = new JButton(symbol);
        b.setPreferredSize(new Dimension(32, 32));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        b.setContentAreaFilled(false);
        return b;
    }

    // Campo de busca arredondado simples
    class RoundedTextField extends JTextField {
        RoundedTextField(String placeholder) {
            super(placeholder);
            setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
            setOpaque(false);
            setBackground(new Color(255,255,255));
            setFont(new Font("SansSerif", Font.PLAIN, 13));
            setForeground(new Color(90,90,90));
            setMargin(new Insets(10, 12, 10, 12));
        }
    }

    // Botão arredondado
    class RoundedButton extends JButton {
        private final Color bg;
        RoundedButton(String text, Color bg) {
            super(text);
            this.bg = bg;
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /* ---------- Renderers / Editors ---------- */

    // Renderer para Quantidade (badge colorido)
    class QuantityRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lbl = new JLabel(String.valueOf(value));
            lbl.setOpaque(true);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            int qtd = 0;
            try { qtd = Integer.parseInt(String.valueOf(value)); } catch (Exception ex) {}
            if (qtd >= 80) lbl.setBackground(new Color(0, 180, 70));
            else if (qtd >= 50) lbl.setBackground(new Color(255, 200, 60));
            else lbl.setBackground(new Color(255, 90, 90));
            lbl.setForeground(Color.WHITE);
            lbl.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
            return lbl;
        }
    }

    // Renderer para Ações (apenas visual)
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 6, 6));
            setOpaque(false);
            JButton editar = new JButton("✎");
            editar.setPreferredSize(new Dimension(32, 32));
            editar.setFocusPainted(false);
            editar.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
            editar.setContentAreaFilled(false);

            JButton deletar = new JButton("🗑");
            deletar.setPreferredSize(new Dimension(32, 32));
            deletar.setFocusPainted(false);
            deletar.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
            deletar.setContentAreaFilled(false);

            add(editar);
            add(deletar);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor para Ações (botões funcionais)
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final JButton editar;
        private final JButton deletar;
        private int currentId = -1;

        public ButtonEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
            panel.setOpaque(false);

            editar = new JButton("✎");
            editar.setPreferredSize(new Dimension(32, 32));
            editar.setFocusPainted(false);
            editar.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
            editar.setContentAreaFilled(false);

            deletar = new JButton("🗑");
            deletar.setPreferredSize(new Dimension(32, 32));
            deletar.setFocusPainted(false);
            deletar.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
            deletar.setContentAreaFilled(false);

            // ações:
            editar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(() -> editarProduto(currentId));
                    fireEditingStopped();
                }
            });

            deletar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(() -> deletarProduto(currentId));
                    fireEditingStopped();
                }
            });

            panel.add(editar);
            panel.add(deletar);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            Object idObj = table.getValueAt(row, 0);
            try {
                currentId = Integer.parseInt(String.valueOf(idObj));
            } catch (Exception ex) {
                currentId = -1;
            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    // main para testes rápidos
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaProdutos::new);
    }
}
