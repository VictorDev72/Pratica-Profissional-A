import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class OliginalRoca {

    private JToolBar tbBotoes;

    // esses serão os botões agrupados no JToolBar e que realizarão operações
    private static JButton btnIncluir, btnSalvar, btnExcluir, btnBuscar, btnProximo, btnAnterior, btnInicio, btnFinal, btnCancelar;

    // lista da tabela armazenada na memória local para navegação entre os registros da tabela
    private static ResultSet dadosDoSelect;

    // campos referentes à tabela de Departamentos no banco de dados
    private static JTextField txtNumDepto, txtNomeDepto;

    // controle para exibir os dados na tabela
    private static JTable tabDepto;

    private static Connection conexaoDados = null; //para dizer q a conexão não existe AINDA

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistema de Atualização de BD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);

        JTabbedPane tabbedPane = new JTabbedPane();


        JPanel categoria = new JPanel();

        btnInicio = new JButton("Inicio", new ImageIcon("/resources/first.png"));
        btnInicio.setPreferredSize(new Dimension(85, 55));
        btnInicio.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnInicio.setHorizontalTextPosition(SwingConstants.CENTER); //remove uma borda que fica dentro do último botão pressionado
        btnInicio.setFocusPainted(false);

        btnAnterior = new JButton("Voltar", new ImageIcon("/resources/prior.png"));
        btnAnterior.setPreferredSize(new Dimension(85, 55));
        btnAnterior.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnAnterior.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAnterior.setFocusPainted(false);

        btnProximo = new JButton("Avancar", new ImageIcon("resources/next.png"));
        btnProximo.setPreferredSize(new Dimension(85, 55));
        btnProximo.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnProximo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnProximo.setFocusPainted(false);

        btnFinal = new JButton("Final", new ImageIcon("resources/last.png"));
        btnFinal.setPreferredSize(new Dimension(85, 55));
        btnFinal.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnFinal.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFinal.setFocusPainted(false);

        btnBuscar = new JButton("Buscar", new ImageIcon("resources/find.png"));
        btnBuscar.setPreferredSize(new Dimension(85, 55));
        btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBuscar.setFocusPainted(false);

        btnIncluir = new JButton("Incluir", new ImageIcon("resources/Add.png"));
        btnIncluir.setPreferredSize(new Dimension(85, 55));
        btnIncluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnIncluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnIncluir.setFocusPainted(false);

        btnSalvar = new JButton("Atualizar", new ImageIcon("resources/Save.png"));
        btnSalvar.setPreferredSize(new Dimension(85, 55));
        btnSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSalvar.setFocusPainted(false);

        btnExcluir = new JButton("Excluir", new ImageIcon("resource/Minus.png"));
        btnExcluir.setPreferredSize(new Dimension(85, 55));
        btnExcluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnExcluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExcluir.setFocusPainted(false);

        btnCancelar = new JButton("Cancelar", new ImageIcon("resource/UNDO.png"));
        btnCancelar.setPreferredSize(new Dimension(85, 55));
        btnCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCancelar.setFocusPainted(false);

        categoria.add(btnProximo);
        categoria.add(btnAnterior);
        categoria.add(btnInicio);
        categoria.add(btnFinal);
        categoria.add(btnBuscar);
        categoria.add(btnIncluir);
        categoria.add(btnExcluir);

        JPanel produtos = new JPanel();

        btnInicio = new JButton("Inicio", new ImageIcon("resources/first.png"));
        btnInicio.setPreferredSize(new Dimension(85, 55));
        btnInicio.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnInicio.setHorizontalTextPosition(SwingConstants.CENTER); //remove uma borda que fica dentro do último botão pressionado
        btnInicio.setFocusPainted(false);

        btnAnterior = new JButton("Voltar", new ImageIcon("resources/prior.png"));
        btnAnterior.setPreferredSize(new Dimension(85, 55));
        btnAnterior.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnAnterior.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAnterior.setFocusPainted(false);

        btnProximo = new JButton("Avancar", new ImageIcon("resources/next.png"));
        btnProximo.setPreferredSize(new Dimension(85, 55));
        btnProximo.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnProximo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnProximo.setFocusPainted(false);

        btnFinal = new JButton("Final", new ImageIcon("resources//last.png"));
        btnFinal.setPreferredSize(new Dimension(85, 55));
        btnFinal.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnFinal.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFinal.setFocusPainted(false);

        btnBuscar = new JButton("Buscar", new ImageIcon("resources/FIND.png"));
        btnBuscar.setPreferredSize(new Dimension(85, 55));
        btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBuscar.setFocusPainted(false);

        btnIncluir = new JButton("Incluir", new ImageIcon("resources/Add.png"));
        btnIncluir.setPreferredSize(new Dimension(85, 55));
        btnIncluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnIncluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnIncluir.setFocusPainted(false);

        btnSalvar = new JButton("Atualizar", new ImageIcon("resources/Save.png"));
        btnSalvar.setPreferredSize(new Dimension(85, 55));
        btnSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSalvar.setFocusPainted(false);

        btnExcluir = new JButton("Excluir", new ImageIcon("resource/Minus.png"));
        btnExcluir.setPreferredSize(new Dimension(85, 55));
        btnExcluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnExcluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExcluir.setFocusPainted(false);

        btnCancelar = new JButton("Cancelar", new ImageIcon("resource/UNDO.png"));
        btnCancelar.setPreferredSize(new Dimension(85, 55));
        btnCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCancelar.setFocusPainted(false);


        produtos.add(btnProximo);
        produtos.add(btnAnterior);
        produtos.add(btnInicio);
        produtos.add(btnFinal);
        produtos.add(btnBuscar);
        produtos.add(btnIncluir);
        produtos.add(btnExcluir);





        tabbedPane.addTab("Categoria", categoria);
        tabbedPane.addTab("Produtos", produtos);

        frame.add(tabbedPane);

        frame.setVisible(true);
    }
}
