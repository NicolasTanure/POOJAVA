package view;

import model.Cliente;
import model.collections.Clientela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroCliente extends JPanel {
    class Events implements ActionListener { // Inner class para tratamento de eventos
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == confirmar) {
                try {
                    createCliente();
                } catch (NumberFormatException e) {
                    System.err.format("Campo(s) inválido(s): %s%n", e);
                    headerInformation.setForeground(Color.RED);
                    headerInformation.setText("FALHA NO CADASTRO");
                    information.setText("Entrada de dado(s) inválido(s)");
                } catch (IllegalArgumentException e) {
                    System.err.format("E/S inválida: %s%n", e);
                    headerInformation.setForeground(Color.RED);
                    headerInformation.setText("FALHA NO CADASTRO");
                    information.setText("Entrada de dado(s) inválido(s)");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (ev.getSource() == limpar) {
                clearFields(cliente);
            } else if (ev.getSource() == imprimir) {
                imprimirClientes();
            } else if (ev.getSource() == voltar) {
                screen.changePanel(0);
                clearFields(cliente);
            }
        }
    }

    private Screen screen;
    private CadastroCliente cliente;
    private Clientela clientes;
    private JButton confirmar;
    private JButton limpar;
    private JButton voltar;
    private JButton imprimir;
    private JTextField textCodigo;
    private JTextField textNome;
    private JTextField textEmail;
    private JLabel headerInformation;
    private JLabel information;

    public CadastroCliente(Screen screen) {
        super(new GridLayout(7,1));
        this.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        clientes = new Clientela();
        this.screen = screen;
        this.cliente = this;
        createUIComponents();
    }

    public Clientela getClientes() {
        return clientes;
    }

    public void createUIComponents() {
        // Cria campos de texto
        createTextFields();
        // Cria os botões
        createButtons();
        // Cria área de informação
        createInformationFields();
    }

    public void createButtons() {
        JPanel painelButtons = new JPanel(new FlowLayout()); // Configura o layout

        voltar = new JButton("Voltar");
        voltar.addActionListener(new Events());
        painelButtons.add(voltar);

        limpar = new JButton("Limpar");
        limpar.addActionListener(new Events());
        painelButtons.add(limpar);

        imprimir = new JButton("Imprimir");
        imprimir.addActionListener(new Events());
        painelButtons.add(imprimir);

        confirmar = new JButton("Confirmar");
        confirmar.addActionListener(new Events());
        painelButtons.add(confirmar);

        this.add(painelButtons);
    }

    public void createTextFields() {
        // Criação do header da tela
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel header = new JLabel("CADASTRO DE CLIENTE");
        header.setFont(new Font("Arial", Font.BOLD, 16));
        painel.add(header);
        this.add(painel);

        // Áreas de texto linha 1
        JPanel painelText1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText1.add(new JLabel("Código: "));
        textCodigo = new JTextField(17);
        painelText1.add(textCodigo);
        this.add(painelText1);

        // Áreas de texto linha 2
        JPanel painelText2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText2.add(new JLabel("Nome: "));
        textNome = new JTextField(18);
        painelText2.add(textNome);
        this.add(painelText2);

        // Áreas de texto linha 3
        JPanel painelText3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText3.add(new JLabel("Email: "));
        textEmail = new JTextField(18);
        painelText3.add(textEmail);
        this.add(painelText3);
    }

    public void createInformationFields() {
        // Área que mostra as informações linha 4
        JPanel painelText4 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel textInformation = new JLabel("Informações:");
        textInformation.setFont(new Font("Arial", Font.BOLD,13));
        painelText4.add(textInformation);
        headerInformation = new JLabel();
        painelText4.add(headerInformation);
        this.add(painelText4);
        // Área que mostra as informações linha 5
        JPanel painelText5 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        information = new JLabel();
        painelText5.add(information);
        this.add(painelText5);
    }

    // Método limpa campos
    public void clearFields(Container box) {
        for (Component component : box.getComponents()) {
            if (component instanceof JTextField) {
                JTextField field = (JTextField) component;
                field.setText("");
            } else if (component instanceof Container) {
                clearFields((Container) component);
            }
        }
        headerInformation.setText("");
        information.setText("");
    }

    public void imprimirClientes() {
        JOptionPane.showMessageDialog(
                this,
                clientes.toString(),
                "Lista de Clientes",
                JOptionPane.PLAIN_MESSAGE
        );
    }


    public void createCliente() throws NumberFormatException {
        boolean create = false;
        String info = "";

        String codigo = textCodigo.getText();
        String nome = textNome.getText();
        String email = textEmail.getText();


        if (!codigo.isEmpty() && !nome.isEmpty() && !email.isEmpty()) {
            int cod = Integer.parseInt(codigo);

            Cliente cliente = new Cliente(cod,nome,email);

            if (clientes.adicionarCliente(cliente)) {
                create = true;
            } else {
                info = "ERRO: " + clientes.getErro();
            }
        } else {
            info = "ERRO: Campos incompletos!";
        }

        if (create) { // Se foi realizado o cadastro
            headerInformation.setForeground(Color.GREEN);
            headerInformation.setText("CADASTRADO COM SUCESSO");
            information.setText("Novo cliente adicionado");
        } else { // Senão foi
            headerInformation.setForeground(Color.RED);
            headerInformation.setText("FALHA NO CADASTRO");
            information.setText(info);
        }
    }
}
