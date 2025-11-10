import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class FrameDepto extends JFrame
{
    // agrupará os botões abaixo; será colocado no topo do formulário
    private JToolBar tbBotoes;

    private JButton btnIncluir, btnSalvar, btnExcluir, btnBuscar, btnProximo,
            btnAnterior, btnInicio, btnFinal, btnCancelar;

    // lista em memória Local para navegação entre os registros da tabela
    private static ResultSet dadosDoSelect;

    // campos referentes à tabela de Departamentos no banco de dados
    private static JTextField txtIdCat, txtNomeCat;

    //controle de registros em formato tabular
    private static JTable tabDepto;

    // será usada para manter aberta uma conexão ao BD para podermos navegar
    // entre registros
    private static Connection conexaoDados = null;

    private FrameDepto(String titulo)
    {
        //aqui se instanciaremos os botoes, toolbar, textfild, lables, table, etc
        //para que apareçam no formulario
        setTitle(titulo);
        setSize(1000,300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Adiciorenamos os botões ao JToolBar que os agrupará
        tbBotoes = new JToolBar(); // orientação padrão é HORIZONTAL

        // Icon icone = new ImageIcon(getClass().getResource("/resources/first.png"));
        // btnInicio = new JButton("Inicio");
        // btnInicio.setIcon(icone);
        //No entanto, usaremos uma abordagem mais direta de criação dos botões e sua configuração:
        btnInicio = new JButton("Inicio",
                new ImageIcon(getClass().getResource("/resources/first.png")));
        btnInicio.setPreferredSize(new Dimension(65, 45));
        btnInicio.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnInicio.setHorizontalTextPosition(SwingConstants.CENTER);
        //remove uma borda que fica dentro do último botão pressionado
        btnInicio.setFocusPainted(false);

        btnAnterior = new JButton("Voltar",
                new ImageIcon(getClass().getResource("/resources/prior.png")));
        btnAnterior.setPreferredSize(new Dimension(65, 45));
        btnAnterior.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnAnterior.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAnterior.setFocusPainted(false);

        btnProximo = new JButton("Avancar",
                new ImageIcon(getClass().getResource("/resources/next.png")));
        btnProximo.setPreferredSize(new Dimension(65, 45));
        btnProximo.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnProximo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnProximo.setFocusPainted(false);

        btnFinal = new JButton("Final",
                new ImageIcon(getClass().getResource("/resources//last.png")));
        btnFinal.setPreferredSize(new Dimension(65, 45));
        btnFinal.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnFinal.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFinal.setFocusPainted(false);

        btnBuscar = new JButton("Buscar",
                new ImageIcon(getClass().getResource("/resources/find.png")));
        btnBuscar.setPreferredSize(new Dimension(65, 45));
        btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBuscar.setFocusPainted(false);

        btnIncluir = new JButton("Incluir",
                new ImageIcon(getClass().getResource("/resources/add.png")));
        btnIncluir.setPreferredSize(new Dimension(65, 45));
        btnIncluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnIncluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnIncluir.setFocusPainted(false);

        btnSalvar = new JButton("Atualizar",
                new ImageIcon(getClass().getResource("/resources/save.png")));
        btnSalvar.setPreferredSize(new Dimension(65, 45));
        btnSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSalvar.setFocusPainted(false);

        btnExcluir = new JButton("Excluir",
                new ImageIcon(getClass().getResource("/resources/minus.png")));
        btnExcluir.setPreferredSize(new Dimension(65, 45));
        btnExcluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnExcluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExcluir.setFocusPainted(false);

        btnCancelar = new JButton("Cancelar",
                new ImageIcon(getClass().getResource("/resources/undo.png")));
        btnCancelar.setPreferredSize(new Dimension(65, 45));
        btnCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCancelar.setFocusPainted(false);

        // Os botões serão dispostos um ao lado do outro, fluindo da esquerda para a
        // direita, de cima para baixo
        // para isso usamos um gerenciador de layout da clsse FlowLayout:
        // estabelecemos o layout do tbBotoes como flowLayout
        tbBotoes.setLayout(new FlowLayout());


        tbBotoes.add(btnInicio);
        tbBotoes.add(btnAnterior);
        tbBotoes.add(btnProximo);
        tbBotoes.add(btnFinal);
        // coloca um separador entre um botão e o próximo botão
        tbBotoes.addSeparator();

        tbBotoes.add(btnBuscar);
        tbBotoes.addSeparator();
        tbBotoes.add(btnIncluir);
        tbBotoes.add(btnSalvar);
        tbBotoes.add(btnExcluir);
        tbBotoes.add(btnCancelar);
        // os botões apenas serão enfatizados visualmente quando o mouse passar sobre
        // eles
        tbBotoes.setRollover(true);



        JPanel pnlGrade = new JPanel(); // para o JTable com os registros da tabela
        JPanel pnlCampos = new JPanel(); // colocaremos campos de digitação de dados
        JPanel pnlMensagem = new JPanel(); // colocaremos mensagens para o usuário

        JLabel lbMensagem = new JLabel("Mensagem:"); // para exibirmos mensagens
        pnlMensagem.add(lbMensagem);
        pnlMensagem.setLayout(new FlowLayout(FlowLayout.LEFT));

        Container cntForm = getContentPane(); // acessa a área de conteúdo do frame
        cntForm.setLayout(new BorderLayout()); // configura o layout dessa área
        cntForm.add(tbBotoes , BorderLayout.NORTH); // Toolbar fica no topo
        cntForm.add(pnlGrade , BorderLayout.WEST); // Grade na esquerda
        cntForm.add(pnlCampos , BorderLayout.CENTER); // Campos ficam no centro
        cntForm.add(pnlMensagem , BorderLayout.SOUTH); // Mmensagens fica no fundo

        Object [][] dadosDepto = { {0, "", "", ""} };
        String[] colunas = {"Num.Depto","Nome","Gerente","Inicio gerencia"};
        tabDepto = new JTable(dadosDepto, colunas);
        JScrollPane barraRolagem = new JScrollPane(tabDepto);
        pnlGrade.add(barraRolagem);

        pnlCampos.setLayout(new GridLayout(4, 2)); // 4 linhas e 2 colunas
        txtIdCat = new JTextField();
        txtNomeCat = new JTextField();
        pnlCampos.add(new JLabel("Id:"));                     // 1, 1
        pnlCampos.add(txtIdCat);                                       // 1, 2
        pnlCampos.add(new JLabel("Nome:"));                          // 2, 1
        pnlCampos.add(txtNomeCat);                                     // 2, 2


        try
        {
            conexaoDados = ConexaoBD.getConnection();
            preencherDados();
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        btnInicio.addActionListener(
                new ActionListener() { // ouvinte de ação do btnInicio
                    @Override
                    public void actionPerformed(ActionEvent e) // ação realizada
                    {
                        try
                        {
                            if (dadosDoSelect.first()) // se acessar o 1º registro
                                exibirRegistro(); // esse registro será exibido
                            else JOptionPane.showMessageDialog(null, "Sem registros!");
                        }
                        catch (SQLException ex)
                        {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
        );
    }

    static private void preencherDados()
    {
        String sql = "SELECT * FROM daroca.categorias order by id";
        try
        {
            Statement comandoSQL = conexaoDados.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            try
            {
                dadosDoSelect = comandoSQL.executeQuery(sql);
                if (dadosDoSelect.first())
                {
                    exibirRegistro();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,
                            "Registro não encontrado!");
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    static private void exibirRegistro() throws SQLException
    {
        txtIdCat.setText(dadosDoSelect.getString("Id"));
        txtNomeCat.setText(dadosDoSelect.getString("Nome"));
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        FrameDepto form = null;
                            form = new FrameDepto("Manutenção de Departamentos");
                            // Adaptador para o fechamento da janela,
                            // matando o processo em execução
                            form.addWindowListener(
                                    new WindowAdapter()
                                    {
                                        public void windowClosing (WindowEvent e)
                                        {
                                            try
                                            {
                                                conexaoDados.close();
                                            }
                                            catch (SQLException ex)
                                            {
                                                throw new RuntimeException(ex);
                                            }
                                            System.exit(0);
                                        }
                                    }
                            );
                        form.pack();
                        form.setVisible(true);
                    }
                }
        );

    }
}