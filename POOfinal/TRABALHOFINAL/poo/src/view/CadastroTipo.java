package view;

import model.Duravel;
import model.Perecivel;
import model.TipoCarga;
import model.collections.Tipos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CadastroTipo extends JPanel {
    class Events implements ActionListener { // Inner class para tratamento de eventos
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == confirmar) {
                try {
                    createTipoCarga();
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
                clearFields(tipo);
            } else if (ev.getSource() == imprimir) {
                imprimirTipos();
            } else if (ev.getSource() == voltar) {
                screen.changePanel(0);
                clearFields(tipo);
            }
        }
    }

    private Screen screen;
    private CadastroTipo tipo;
    private Tipos tipos;
    private JButton confirmar;
    private JButton limpar;
    private JButton voltar;
    private JButton imprimir;
    private ArrayList<Duravel> duraveis;
    private ArrayList<Perecivel> pereciveis;
    private JTextField textTipoID;
    private JTextField textSetor;
    private JTextField textMaterial;
    private JTextField textImposto;
    private JTextField textOrigem;
    private JTextField textValidadeMax;
    private JTextField textDesc;
    private JLabel headerInformation;
    private JLabel information;

    public CadastroTipo(Screen screen) {
        super(new GridLayout(8,1));
        this.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        tipos = new Tipos();
        duraveis = new ArrayList<>();
        pereciveis = new ArrayList<>();
        this.screen = screen;
        this.tipo = this;
        createUIComponents();
    }

    public Tipos getTipos() {
        return tipos;
    }

    public ArrayList<Duravel> getDuraveis() {
        return duraveis;
    }

    public ArrayList<Perecivel> getPereciveis() {
        return pereciveis;
    }

    public void setInformation(String information) {
        this.information.setText(information);
    }

    public void setHeaderInformation(String headerInformation) {
        this.headerInformation.setText(headerInformation);
    }

    public void setHeaderInformation(Color color) {
        this.headerInformation.setForeground(color);
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
        JLabel header = new JLabel("CADASTRO DE TIPO DE CARGA");
        header.setFont(new Font("Arial", Font.BOLD, 16));
        painel.add(header);
        this.add(painel);

        // Áreas de texto linha 1
        JPanel painelText1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText1.add(new JLabel("ID tipo de carga: "));
        textTipoID = new JTextField(10);
        painelText1.add(textTipoID);
        painelText1.add(new JLabel("Setor da carga: "));
        textSetor = new JTextField(12);
        painelText1.add(textSetor);
        this.add(painelText1);

        // Áreas de texto linha 2
        JPanel painelText2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText2.add(new JLabel("Material principal: "));
        textMaterial = new JTextField(14);
        painelText2.add(textMaterial);
        painelText2.add(new JLabel("Imposto IPI: "));
        textImposto = new JTextField(9);
        painelText2.add(textImposto);
        this.add(painelText2);

        // Áreas de texto linha 3
        JPanel painelText3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText3.add(new JLabel("Origem: "));
        textOrigem = new JTextField(15);
        painelText3.add(textOrigem);
        painelText3.add(new JLabel("Válidade máxima (dias): "));
        textValidadeMax = new JTextField(7);
        painelText3.add(textValidadeMax);
        this.add(painelText3);

        // Áreas de texto linha 4
        JPanel painelText4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText4.add(new JLabel("Descrição: "));
        textDesc = new JTextField(35);
        painelText4.add(textDesc);
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
        information = new JLabel();
        painelText6.add(information);
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

    public void imprimirTipos() {
        JOptionPane.showMessageDialog(
                this,
                tipos.toString(),
                "Lista de Tipos",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    // Método que cria instâncias de Tipo de carga
    public void createTipoCarga() throws NumberFormatException {
        boolean create = false;
        String info = "";

        String tipoId = textTipoID.getText();
        String desc = textDesc.getText();
        String validadeMax = textValidadeMax.getText();
        String imposto = textImposto.getText();
        String origem = textOrigem.getText();
        String material = textMaterial.getText();
        String setor = textSetor.getText();

        // Válida os campos preenchidos
        if ((!tipoId.isEmpty()) && (!origem.isEmpty()) && (!validadeMax.isEmpty()) && (!desc.isEmpty())
                && (imposto.isEmpty()) && (material.isEmpty()) && (setor.isEmpty())) {

            int tipo = Integer.parseInt(tipoId); // Realiza conversões
            int validade = Integer.parseInt(validadeMax);

            TipoCarga tipoCarga = new Perecivel(tipo, origem, validade, desc);
            // Cadastra o tipo de carga
            if (tipos.adicionarTipo(tipoCarga)) {
                pereciveis.add((Perecivel) tipoCarga);
                create = true;
            } else {
                info = "Tipo de carga já existente!";
            }
        } else if ((!tipoId.isEmpty()) && (origem.isEmpty()) && (validadeMax.isEmpty()) && (!desc.isEmpty())
                && (!imposto.isEmpty()) && (!material.isEmpty()) && (!setor.isEmpty())) {

            int tipo = Integer.parseInt(tipoId); // Realiza conversões
            double percentual = Double.parseDouble(imposto);

            TipoCarga tipoCarga = new Duravel(tipo, setor, material, percentual, desc);
            // Cadastra o tipo de carga
            if (tipos.adicionarTipo(tipoCarga)) {
                duraveis.add((Duravel) tipoCarga);
                create = true;
            } else {
                info = "Tipo de carga já existente!";
            }
        } else {
            info = "ERRO: Campos inválidos! PERECÍVEL: ID,Origem,Validade Máx. e Descrição\n" +
                    " | DURÁVEL: ID,Setor,Material,Imposto IPI e Descrição";
        }

        if (create) { // Se foi realizado o cadastro
            headerInformation.setForeground(Color.GREEN);
            headerInformation.setText("CADASTRADO COM SUCESSO");
            information.setText("Novo tipo de carga adicionado");
        } else { // Senão foi
            headerInformation.setForeground(Color.RED);
            headerInformation.setText("FALHA NO CADASTRO");
            information.setText(info);
        }
    }
}
