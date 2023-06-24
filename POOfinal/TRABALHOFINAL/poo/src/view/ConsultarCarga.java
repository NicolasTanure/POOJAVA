package view;

import model.Carga;
import model.Duravel;
import model.Perecivel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class ConsultarCarga extends JPanel {
    class Events implements ActionListener { // Inner class para tratamento de eventos
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == voltar) {
                screen.changePanel(0);
            }
        }
    }

    private Screen screen;
    private LinkedList<Carga> cargas;
    private ArrayList<Duravel> duraveis;
    private ArrayList<Perecivel> pereciveis;
    private JButton voltar;

    public ConsultarCarga(Screen screen, LinkedList<Carga> cargas, ArrayList<Duravel> duraveis, ArrayList<Perecivel> pereciveis) {
        super(new BorderLayout());
        this.screen = screen;
        this.cargas = cargas;
        this.duraveis = duraveis;
        this.pereciveis = pereciveis;
        createUIComponents();
    }

    public void createUIComponents() {
        createButtons();
        JPanel header = new JPanel(new FlowLayout());
        header.add(new JLabel("LISTA DE CARGAS"));
        this.add(header, BorderLayout.NORTH);

        DefaultListModel<String> listaCarga = new DefaultListModel<>();
        listaCarga.addElement("Código;Peso;ValorDeclarado;TempoMáx;Situação;TipoCarga;Cliente;Origem;Destino");
        for (Carga c : cargas) {
            if (c.getNavioAlocado() != null) {
                listaCarga.addElement(c.toString()
                        + " | NAVIO: " + c.getNavioAlocado().toString()
                        + " | FRETE: Prioridade:" + c.getPrioridade().getNome()
                        + ";Preço:" + c.getPreco()
                );
            } else {
                listaCarga.addElement(c.toString());
            }
        }

        JList<String> consulta = new JList<>(listaCarga);
        JScrollPane scroll = new JScrollPane(consulta);

        this.add(scroll, BorderLayout.CENTER);
    }

    public void createButtons() {
        JPanel painelButtons = new JPanel(new FlowLayout()); // Configura o layout

        voltar = new JButton("Voltar");
        voltar.addActionListener(new Events());
        painelButtons.add(voltar);

        this.add(painelButtons, BorderLayout.SOUTH);
    }
}
