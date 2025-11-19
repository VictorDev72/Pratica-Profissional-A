import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OliginalRoca {

    // Componentes da interface
    private static JTextField txtIdCategoria, txtNomeCategoria;
    
    private static JTable tabCategoria;
    
    private static Connection conexaoDados = null;
    
    private static ResultSet dadosDoSelect;

    // Botões
    private static JButton btnIncluir, btnSalvar, btnExcluir, btnBuscar, btnProximo, btnAnterior, btnInicio, btnFinal, btnCancelar;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            criarGUI();
        });
    }

    private static void criarGUI() {
        JFrame frame = new JFrame("Sistema de Atualização de Banco de Dados");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Painel para Categoria
        JPanel panelCategoria = criarPanelCategoria();
        tabbedPane.addTab("Categoria", panelCategoria);

        // Painel para Produtos
        JPanel panelProdutos = criarPanelProdutos();
        tabbedPane.addTab("Produtos", panelProdutos);

        frame.add(tabbedPane, BorderLayout.CENTER);

        // Conectar ao banco e carregar dados iniciais
        conectarBanco();
        preencherDados();

        frame.setVisible(true);
    }

    private static JPanel criarPanelCategoria() {
        JPanel panel = new JPanel(new BorderLayout());

        // Toolbar com botões
        JToolBar toolbar = criarToolbar();
        panel.add(toolbar, BorderLayout.NORTH);

        // Painel de campos
        JPanel camposPanel = new JPanel(new GridLayout(2, 2));
        camposPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtIdCategoria = new JTextField();
        txtNomeCategoria = new JTextField();

        camposPanel.add(new JLabel("ID:"));
        camposPanel.add(txtIdCategoria);
        camposPanel.add(new JLabel("Nome:"));
        camposPanel.add(txtNomeCategoria);

        panel.add(camposPanel, BorderLayout.SOUTH);

        // Tabela para exibir dados
        String[] colunas = {"ID", "Nome"};
        Object[][] dados = {};
        tabCategoria = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabCategoria);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private static JPanel criarPanelProdutos() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Painel de Produtos "));
        return panel;
    }

    private static JToolBar criarToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        // Criar botões com ícones (usando texto por enquanto)
        btnInicio = new JButton("Inicio");
        btnAnterior = new JButton("Voltar");
        btnProximo = new JButton("Avançar");
        btnFinal = new JButton("Final");
        btnBuscar = new JButton("Buscar");
        btnIncluir = new JButton("Incluir");
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnCancelar = new JButton("Cancelar");

        // Adicionar action listeners
        btnInicio.addActionListener(e -> navegarParaInicio());
        btnAnterior.addActionListener(e -> navegarAnterior());
        btnProximo.addActionListener(e -> navegarProximo());
        btnFinal.addActionListener(e -> navegarParaFinal());
        btnBuscar.addActionListener(e -> buscarCategoria());
        btnIncluir.addActionListener(e -> incluirCategoria());
        btnSalvar.addActionListener(e -> salvarCategoria());
        btnExcluir.addActionListener(e -> excluirCategoria());
        btnCancelar.addActionListener(e -> cancelarOperacao());

        // Adicionar botões à toolbar
        toolbar.add(btnInicio);
        toolbar.add(btnAnterior);
        toolbar.add(btnProximo);
        toolbar.add(btnFinal);
        toolbar.addSeparator();
        toolbar.add(btnBuscar);
        toolbar.addSeparator();
        toolbar.add(btnIncluir);
        toolbar.add(btnSalvar);
        toolbar.add(btnExcluir);
        toolbar.add(btnCancelar);

        return toolbar;
    }

    private static void conectarBanco() {
        try {
            conexaoDados = ConexaoBD.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void preencherDados() {
        String sql = "SELECT * FROM daroca.categorias ORDER BY id";

        try (Statement comandoSQL = conexaoDados.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {

            dadosDoSelect = comandoSQL.executeQuery(sql);

            if (dadosDoSelect.first()) {
                exibirRegistro();
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum registro encontrado!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + ex.getMessage());
        }
    }

    private static void exibirRegistro() {
        try {
            if (dadosDoSelect != null && !dadosDoSelect.isClosed()) {
                txtIdCategoria.setText(dadosDoSelect.getString("id"));
                txtNomeCategoria.setText(dadosDoSelect.getString("nome"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void atualizarTabela() {
        try {
            // Voltar ao início do ResultSet para preencher a tabela
            dadosDoSelect.beforeFirst();

            // Contar número de registros
            int rowCount = 0;
            while (dadosDoSelect.next()) {
                rowCount++;
            }

            // Criar array de dados
            Object[][] dados = new Object[rowCount][2];
            dadosDoSelect.beforeFirst();

            int i = 0;
            while (dadosDoSelect.next()) {
                dados[i][0] = dadosDoSelect.getString("id");
                dados[i][1] = dadosDoSelect.getString("nome");
                i++;
            }

            // Atualizar tabela
            String[] colunas = {"ID", "Nome"};
            tabCategoria.setModel(new javax.swing.table.DefaultTableModel(dados, colunas));

            // Voltar para o primeiro registro
            dadosDoSelect.first();
            exibirRegistro();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Métodos de navegação
    private static void navegarParaInicio() {
        try {
            if (dadosDoSelect.first()) {
                exibirRegistro();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void navegarAnterior() {
        try {
            if (dadosDoSelect.previous()) {
                exibirRegistro();
            } else {
                dadosDoSelect.first();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void navegarProximo() {
        try {
            if (dadosDoSelect.next()) {
                exibirRegistro();
            } else {
                dadosDoSelect.last();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void navegarParaFinal() {
        try {
            if (dadosDoSelect.last()) {
                exibirRegistro();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Métodos CRUD com PreparedStatement
    private static void buscarCategoria() {
        String nome = JOptionPane.showInputDialog("Digite o nome da categoria para buscar:");

        if (nome != null && !nome.trim().isEmpty()) {
            String sql = "SELECT * FROM daroca.categorias WHERE nome LIKE ? ORDER BY id";

            try (PreparedStatement stmt = conexaoDados.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {

                stmt.setString(1, "%" + nome + "%");
                dadosDoSelect = stmt.executeQuery();

                if (dadosDoSelect.first()) {
                    exibirRegistro();
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(null, "Categoria não encontrada!");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro na busca: " + ex.getMessage());
            }
        }
    }

    private static void incluirCategoria() {
        String nome = JOptionPane.showInputDialog("Digite o nome da nova categoria:");

        if (nome != null && !nome.trim().isEmpty()) {
            String sql = "INSERT INTO daroca.categorias (nome) VALUES (?)";

            try (PreparedStatement stmt = conexaoDados.prepareStatement(sql)) {
                stmt.setString(1, nome.trim());

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Categoria incluída com sucesso!");
                    preencherDados(); // Recarregar dados
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao incluir: " + ex.getMessage());
            }
        }
    }

    private static void salvarCategoria() {
        String id = txtIdCategoria.getText().trim();
        String nome = txtNomeCategoria.getText().trim();

        if (id.isEmpty() || nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            return;
        }

        String sql = "UPDATE daroca.categorias SET nome = ? WHERE id = ?";

        try (PreparedStatement stmt = conexaoDados.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, Integer.parseInt(id));

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Categoria atualizada com sucesso!");
                preencherDados(); // Recarregar dados
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID inválido!");
        }
    }

    private static void excluirCategoria() {
        String id = txtIdCategoria.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Selecione uma categoria para excluir!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir esta categoria?", "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM daroca.categorias WHERE id = ?";

            try (PreparedStatement stmt = conexaoDados.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(id));

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null, "Categoria excluída com sucesso!");
                    preencherDados(); // Recarregar dados
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID inválido!");
            }
        }
    }

    private static void cancelarOperacao() {
        // Limpar campos
        txtIdCategoria.setText("");
        txtNomeCategoria.setText("");
        preencherDados(); // Recarregar dados originais
    }
}
