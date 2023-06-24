package view;

import model.Carga;
import model.collections.Estoque;
import model.collections.FilaEstoque;
import model.constants.EstadoCarga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlterarSituacao extends JPanel {
    class Events implements ActionListener { // Inner class para tratamento de eventos
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == confirmar) {
                try {
                    alterar();
                } catch (NumberFormatException e) {
                    System.err.format("Campo(s) inválido(s): %s%n", e);
                    headerInformation.setForeground(Color.RED);
                    headerInformation.setText("FALHA AO ALTERAR SITUAÇÃO");
                    information.setText("Entrada de dado(s) inválido(s)");
                } catch (IllegalArgumentException e) {
                    System.err.format("E/S inválida: %s%n", e);
                    headerInformation.setForeground(Color.RED);
                    headerInformation.setText("FALHA AO ALTERAR SITUAÇÃO");
                    information.setText("Entrada de dado(s) inválido(s)");
                } catch (NullPointerException e) {
                    System.err.format("E/S inválida: %s%n", e);
                    headerInformation.setForeground(Color.RED);
                    headerInformation.setText("FALHA AO ALTERAR SITUAÇÃO");
                    information.setText("Carga não encontrada!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (ev.getSource() == voltar) {
                screen.changePanel(0);
            }
        }
    }

    private Screen screen;
    private Estoque cargas;
    private FilaEstoque fila;
    private JButton voltar;
    private JButton confirmar;
    private JTextField textID;
    private JRadioButton pendente;
    private JRadioButton cancelada;
    private JRadioButton finalizada;
    private JLabel headerInformation;
    private JLabel information;

    public AlterarSituacao(Screen screen, Estoque cargas, FilaEstoque fila) {
        super(new GridLayout(7,1));
        this.screen = screen;
        this.cargas = cargas;
        this.fila = fila;
        createUIComponents();
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

        confirmar = new JButton("Confirmar");
        confirmar.addActionListener(new Events());
        painelButtons.add(confirmar);

        this.add(painelButtons);
    }

    public void createTextFields() {
        // Criação do header da tela
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel header = new JLabel("ALTERAR SITUAÇÃO");
        header.setFont(new Font("Arial", Font.BOLD, 16));
        painel.add(header);
        this.add(painel);

        // Áreas de texto linha 1
        JPanel painelText1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelText1.add(new JLabel("ID da carga: "));
        textID = new JTextField(10);
        painelText1.add(textID);
        this.add(painelText1);

        // Áreas de texto linha 2
        ButtonGroup bg = new ButtonGroup();
        JPanel situacao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pendente = new JRadioButton("Pendente", false);
        bg.add(pendente);
        situacao.add(pendente);
        cancelada = new JRadioButton("Cancelada", false);
        bg.add(cancelada);
        situacao.add(cancelada);
        finalizada = new JRadioButton("Finalizada", false);
        bg.add(finalizada);
        situacao.add(finalizada);
        this.add(situacao);
    }

    public void createInformationFields() {
        // Área que mostra as informações linha 3
        JPanel painelText5 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel textInformation = new JLabel("Informações:");
        textInformation.setFont(new Font("Arial", Font.BOLD,13));
        painelText5.add(textInformation);
        headerInformation = new JLabel();
        painelText5.add(headerInformation);
        this.add(painelText5);
        // Área que mostra as informações linha 4
        JPanel painelText6 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        information = new JLabel();
        painelText6.add(information);
        this.add(painelText6);
    }

    public void alterar() throws NumberFormatException, NullPointerException {
        boolean alterar = false;
        String info = "";

        int id = Integer.parseInt(textID.getText());
        Carga c = cargas.getCarga(id);
        if (c != null) {
            if (c.getEstado() == EstadoCarga.FINALIZADO) {
                headerInformation.setForeground(Color.RED);
                headerInformation.setText("FALHA AO ALTERAR SITUAÇÃO");
                information.setText("Carga em situação FINALIZADA! Impossível alterar.");
            } else if (c.getEstado() == EstadoCarga.LOCADO) {
                if (pendente.isSelected()) {
                    if (fila.addCarga(c)) {
                        c.setEstado(EstadoCarga.PENDENTE);
                        c.setNavioAlocado(null);
                        c.setPrioridade(null); // Não possui mais uma prioridade
                        alterar = true;
                    }
                } else if (cancelada.isSelected()) {
                    c.setEstado(EstadoCarga.CANCELADO);
                    c.setNavioAlocado(null);
                    c.setPrioridade(null);
                    alterar = true;
                } else if (finalizada.isSelected()) {
                    c.setEstado(EstadoCarga.FINALIZADO);
                    c.setNavioAlocado(null);
                    c.setPrioridade(null);
                    alterar = true;
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Selecione uma situação!",
                            "ATENÇÃO",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            } else if (c.getEstado() == EstadoCarga.PENDENTE) {
                if (pendente.isSelected()) {
                    info = "Carga já está em situação PENDENTE! Impossível alterar.";
                } else if (cancelada.isSelected()) {
                    if (fila.getFila().remove(c)) {
                        c.setEstado(EstadoCarga.CANCELADO);
                        alterar = true;
                    }
                } else if (finalizada.isSelected()) {
                    if (fila.getFila().remove(c)) {
                        c.setEstado(EstadoCarga.FINALIZADO);
                        alterar = true;
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Selecione uma situação!",
                            "ATENÇÃO",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            } else if (c.getEstado() == EstadoCarga.CANCELADO) {
                if (pendente.isSelected()) {
                    c.setEstado(EstadoCarga.PENDENTE);
                    if (fila.addCarga(c)) {
                        c.setEstado(EstadoCarga.PENDENTE);
                        alterar = true;
                    }
                } else if (cancelada.isSelected()) {
                    info = "Carga já está em situação CANCELADA! Impossível alterar.";
                } else if (finalizada.isSelected()) {
                    c.setEstado(EstadoCarga.FINALIZADO);
                    alterar = true;
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Selecione uma situação!",
                            "ATENÇÃO",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
            }
        } else {
            info = "Carga não encontrada!";
        }

        if (alterar) { // Se foi possível alterar
            headerInformation.setForeground(Color.GREEN);
            headerInformation.setText("SITUAÇÃO ALTERADA");
            information.setText(c.toString());
        } else { // Senão foi
            headerInformation.setForeground(Color.RED);
            headerInformation.setText("FALHA AO ALTERAR SITUAÇÃO");
            information.setText(info);
        }

    }

}
