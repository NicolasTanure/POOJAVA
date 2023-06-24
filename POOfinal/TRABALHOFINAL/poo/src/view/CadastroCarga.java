package view;

import model.*;
import model.collections.*;
import model.constants.EstadoCarga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroCarga extends JPanel {
    class Events implements ActionListener { // Inner class para tratamento de eventos
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == confirmar) {
                try {
                    createCarga();
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
                clearFields(carga);
            } else if (ev.getSource() == voltar) {
                screen.changePanel(0);
                clearFields(carga);
            }
        }
    }

    private Screen screen;
    private CadastroCarga carga;
    private Estoque estoque;
    private FilaEstoque fila;
    private Tipos tipos;
    private Portos portos;
    private Clientela clientes;
    private JButton confirmar;
    private JButton limpar;
    private JButton voltar;
    private JTextField textCodigo;
    private JTextField textCliente;
    private JTextField textOrigem;
    private JTextField textDestino;
    private JTextField textPeso;
    private JTextField textValorDeclarado;
    private JTextField textTempoMax;
    private JTextField textTipo;
    private JLabel headerInformation;
    private JTextArea information;

    public CadastroCarga(Screen screen, Tipos tipos, Portos portos, Clientela clientes) {
        super(new GridLayout(8,1));
        this.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        estoque = new Estoque();
        fila = new FilaEstoque();
        this.screen = screen;
        this.carga = this;
        this.tipos = tipos;
        this.portos = portos;
        this.clientes = clientes;
        createUIComponents();
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public FilaEstoque getFila() {
        return fila;
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

        confirmar = new JButton("Confirmar");
        confirmar.addActionListener(new Events());
        painelButtons.add(confirmar);

        this.add(painelButtons);
    }

    public void createTextFields() {
        // Criação do header da tela
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel header = new JLabel("CADASTRO DE CARGA");
        header.setFont(new Font("Arial", Font.BOLD, 16));
        painel.add(header);
        this.add(painel);

        // Áreas de texto linha 1
        JPanel painelText1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText1.add(new JLabel("Código: "));
        textCodigo = new JTextField(10);
        painelText1.add(textCodigo);
        painelText1.add(new JLabel("ID Cliente: "));
        textCliente = new JTextField(10);
        painelText1.add(textCliente);
        this.add(painelText1);

        // Áreas de texto linha 2
        JPanel painelText2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText2.add(new JLabel("ID Porto origem: "));
        textOrigem = new JTextField(10);
        painelText2.add(textOrigem);
        painelText2.add(new JLabel("ID Porto destino: "));
        textDestino = new JTextField(10);
        painelText2.add(textDestino);
        this.add(painelText2);

        // Áreas de texto linha 3
        JPanel painelText3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText3.add(new JLabel("Peso (kg): "));
        textPeso = new JTextField(10);
        painelText3.add(textPeso);
        painelText3.add(new JLabel("Valor declarado: "));
        textValorDeclarado = new JTextField(10);
        painelText3.add(textValorDeclarado);
        this.add(painelText3);

        // Áreas de texto linha 4
        JPanel painelText4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText4.add(new JLabel("Tempo máx. (dias): "));
        textTempoMax = new JTextField(10);
        painelText4.add(textTempoMax);
        painelText4.add(new JLabel("ID Tipo de Carga: "));
        textTipo = new JTextField(10);
        painelText4.add(textTipo);
        this.add(painelText4);
    }

    public void createInformationFields() {
        // Área que mostra as informações linha 5
        JPanel painelText5 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel textInformation = new JLabel("Informações:");
        textInformation.setFont(new Font("Arial", Font.BOLD,13));
        painelText5.add(textInformation);
        headerInformation = new JLabel();
        painelText5.add(headerInformation);
        this.add(painelText5);
        // Área que mostra as informações linha 6
        JPanel painelText6 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        information = new JTextArea();
        information.setEditable(false);
        information.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(information);
        scroll.setPreferredSize(new Dimension(570,40));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        painelText6.add(scroll);
        this.add(painelText6);
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

    public void createCarga() throws NumberFormatException {
        boolean create = false;
        String info = "";
        Carga carga = null;

        String id = textCodigo.getText();
        String cliente = textCliente.getText();
        String origem = textOrigem.getText();
        String destino = textDestino.getText();
        String peso = textPeso.getText();
        String valor = textValorDeclarado.getText();
        String tempoMax = textTempoMax.getText();
        String tipo = textTipo.getText();

        if (!id.isEmpty() && !cliente.isEmpty() && !origem.isEmpty() && !destino.isEmpty()
            && !peso.isEmpty() && !valor.isEmpty() && !tempoMax.isEmpty() && !tipo.isEmpty()) {

            int codCliente = Integer.parseInt(cliente);
            Cliente c = clientes.getCliente(codCliente);

            if (c != null) {
                int codOrigem = Integer.parseInt(origem);
                int codDestino = Integer.parseInt(destino);
                Porto o = portos.getPorto(codOrigem);
                Porto d = portos.getPorto(codDestino);

                if (o != null && d != null) {
                    if (o != d) {
                        int tc = Integer.parseInt(tipo);
                        TipoCarga t = tipos.getTipo(tc);

                        if (t != null) {
                            int cod = Integer.parseInt(id);
                            int kg = Integer.parseInt(peso);
                            int dias = Integer.parseInt(tempoMax);
                            double real = Double.parseDouble(valor);

                            if (kg > 0 && real >= 0 && cod >= 0) {
                                if (t instanceof Perecivel && dias > ((Perecivel) t).getValidadeMax()) {
                                    info = "ERRO: Tempo máximo ultrapassa a validade máxima do tipo de carga!";
                                } else {
                                    carga = new Carga(cod, c, o, d, kg, real, dias, t);

                                    if (estoque.adicionarCarga(carga)) {
                                        if (carga.getEstado() == EstadoCarga.PENDENTE) {
                                            fila.addCarga(carga); // Adiciona na fila
                                        }
                                        create = true;
                                    } else {
                                        info = "ERRO: Carga já existente!";
                                    }
                                }
                            } else {
                                info = "ERRO: Valores inválidos! Cheque os campos Peso, ID Carga e Valor declarado.";
                            }
                        } else {
                            info = "ERRO: Tipo de carga não cadastrado!";
                        }
                    } else {
                        info = "ERRO: Porto de origem e de destino iguais!";
                    }
                } else {
                    info = "ERRO: Porto(s) não cadastrado(s)!";
                }
            } else {
                info = "ERRO: Cliente não cadastrado!";
            }
        } else {
            info = "ERRO: Campo(s) inválido(s)!";
        }

        if (create) { // Se foi realizado o cadastro
            headerInformation.setForeground(Color.GREEN);
            headerInformation.setText("CADASTRADO COM SUCESSO");
            information.setText(carga.toString());
        } else { // Senão foi
            headerInformation.setForeground(Color.RED);
            headerInformation.setText("FALHA NO CADASTRO");
            information.setText(info);
        }
    }
}
