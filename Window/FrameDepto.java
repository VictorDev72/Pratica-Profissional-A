import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;



public class FrameDepto extends JFrame {

    // agrupará os botões abaixo; será colocado no topo do formulário
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


    //construtor da classe framedeptop
    public FrameDepto() throws RuntimeException {

        //aqui é instaciaremos os botoes, toobar, textfields,labes, etc
        setTitle("Manutenção de Departamentos usando Swing");
        setSize(1000,1000);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        tbBotoes = new JToolBar();




        btnInicio = new JButton("Inicio", new ImageIcon("/resources/first.png"));
        btnInicio.setPreferredSize(new Dimension(65, 45));
        btnInicio.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnInicio.setHorizontalTextPosition(SwingConstants.CENTER); //remove uma borda que fica dentro do último botão pressionado
        btnInicio.setFocusPainted(false);

        btnAnterior = new JButton("Voltar", new ImageIcon("/resources/prior.png"));
        btnAnterior.setPreferredSize(new Dimension(65, 45));
        btnAnterior.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnAnterior.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAnterior.setFocusPainted(false);

        btnProximo = new JButton("Avancar", new ImageIcon("/resources/next.png"));
        btnProximo.setPreferredSize(new Dimension(65, 45));
        btnProximo.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnProximo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnProximo.setFocusPainted(false);

        btnFinal = new JButton("Final", new ImageIcon("/resources//last.png"));
        btnFinal.setPreferredSize(new Dimension(65, 45));
        btnFinal.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnFinal.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFinal.setFocusPainted(false);

        btnBuscar = new JButton("Buscar", new ImageIcon("/resources/find.png"));
        btnBuscar.setPreferredSize(new Dimension(65, 45));
        btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBuscar.setFocusPainted(false);

        btnIncluir = new JButton("Incluir", new ImageIcon("/resources/add.png"));
        btnIncluir.setPreferredSize(new Dimension(65, 45));
        btnIncluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnIncluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnIncluir.setFocusPainted(false);

        btnSalvar = new JButton("Atualizar", new ImageIcon("/resources/save.png"));
        btnSalvar.setPreferredSize(new Dimension(65, 45));
        btnSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSalvar.setFocusPainted(false);

        btnExcluir = new JButton("Excluir", new ImageIcon("resource/minus.png"));
        btnExcluir.setPreferredSize(new Dimension(65, 45));
        btnExcluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnExcluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExcluir.setFocusPainted(false);

        btnCancelar = new JButton("Cancelar", new ImageIcon("resource/undo.png"));
        btnCancelar.setPreferredSize(new Dimension(65, 45));
        btnCancelar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnCancelar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCancelar.setFocusPainted(false);

        // Os botões serão dispostos um ao lado do outro, fluindo da esquerda para a direita, de cima para baixo
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

        // os botões apenas serão enfatizados visualmente quando o mouse passar sobre eles
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
        String[] colunas = {"ID","Nome"};
        tabDepto = new JTable(dadosDepto, colunas);
        JScrollPane barraRolagem = new JScrollPane(tabDepto);
        pnlGrade.add(barraRolagem);

        pnlCampos.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // horizontal gap=10, vertical gap=5
        txtNumDepto = new JTextField();
        txtNomeDepto = new JTextField();
        txtNumDepto.setPreferredSize(new Dimension(70, 25));
        txtNomeDepto.setPreferredSize(new Dimension(70, 25));


        pnlCampos.add(new JLabel("ID:"));              // 1, 1

        pnlCampos.add(txtNumDepto);                                 // 1, 2

        pnlCampos.add(new JLabel("Nome:"));                    // 2, 1

        pnlCampos.add(txtNomeDepto);                                // 2, 2


        try
        {
            conexaoDados = ConexaoDB.getConnection();
            preencherDados();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // a partir daqui, dentro do construtor do Frame, codificamos os tratadores de eventos dos botões

        // evento click do btnInicio
    }

    static private void exibirRegistro() throws SQLException
    {
        txtNumDepto.setText(dadosDoSelect.getString("ID"));
        txtNomeDepto.setText(dadosDoSelect.getString("Nome"));

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


    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(
                new Runnable()
                {
                    @Override
                    public void run() {
                        FrameDepto form = null;
                        form = new FrameDepto();

                        // ADICIONE ESTA LINHA - TORNA A JANELA VISÍVEL
                        form.setVisible(true);

                        // Adaptador para o fechamento da janela, matando o processo em execução.
                        form.addWindowListener(
                                new WindowAdapter() {
                                    public void windowClosing(WindowEvent e) {
                                        try {
                                            if (conexaoDados != null && !conexaoDados.isClosed()) {
                                                conexaoDados.close();
                                            }
                                        } catch (SQLException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        System.exit(0);
                                    }
                                }
                        );


                        btnInicio.addActionListener
                                (
                                        new ActionListener() { // ouvinte de ação do btnInicio
                                            @Override
                                            public void actionPerformed(ActionEvent e) // ação realizada
                                            {
                                                try
                                                {
                                                    if (dadosDoSelect.first()) // se acessar o 1º registro
                                                        exibirRegistro(); // esse registro será exibido else

                                                }
                                                catch (SQLException ex)
                                                {
                                                    System.out.println(ex.getMessage());
                                                }
                                            }
                                        }
                                );

                        btnAnterior.addActionListener(
                                new ActionListener(){ // ouvinte de ação do btnAnterior
                                    @Override
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        try
                                        {
                                            if (dadosDoSelect.previous())// se acessar o registro anterior
                                                exibirRegistro(); // esse registro será exibido else

                                        }
                                        catch (SQLException ex)
                                        {
                                            System.out.println(ex.getMessage());
                                        }
                                    }
                                }
                        );

                        btnProximo.addActionListener(
                                new ActionListener() { // ouvinte de ação do btnProximo
                                    @Override
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        try
                                        {
                                            if (dadosDoSelect.next()) // se acessar o registro seguinte
                                                exibirRegistro(); // esse registro será exibido else

                                        }
                                        catch (SQLException ex)
                                        {
                                            System.out.println(ex.getMessage());
                                        }
                                    }
                                }
                        );
                        btnFinal.addActionListener(
                                new ActionListener() { // ouvinte de ação do btnFinal
                                    @Override
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        try
                                        {
                                            if (dadosDoSelect.last()) // se acessar o último registro
                                                exibirRegistro(); // esse registro será exibido else

                                        }
                                        catch (SQLException ex)
                                        {
                                            System.out.println(ex.getMessage());
                                        }
                                    }
                                }
                        );

                        btnBuscar.addActionListener(
                                new ActionListener() { // ouvinte de ação do btnBuscar
                                    @Override
                                    public void actionPerformed(ActionEvent e)
                                    {
                                        try
                                        {
                                            if (dadosDoSelect.last()) // se acessar o último registro
                                                exibirRegistro(); // esse registro será exibido else

                                        }
                                        catch (SQLException ex)
                                        {
                                            System.out.println(ex.getMessage());
                                        }
                                    }
                                }
                        );
                    }
                }
        );
    }

}

