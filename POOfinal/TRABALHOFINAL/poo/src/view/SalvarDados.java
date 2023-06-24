package view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Duravel;
import model.Navio;
import model.NavioWrapper;
import model.Perecivel;
import model.collections.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

public class SalvarDados extends JPanel {
    class Events implements ActionListener { // Inner class para tratamento de eventos
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == confirmar) {
                try {
                    saveToFile(texto.getText());
                    JOptionPane.showMessageDialog(
                            dados,
                            "Dados salvos!",
                            "Salvamento realizado",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(
                            dados,
                            "Arquivos não encontrados!",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE
                    );
                    System.err.format("Arquivo não encontrado!: %s%n", e);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(
                            dados,
                            "E/S interrompida!",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE
                    );
                    System.err.format("Erro de E/S!: %s%n", e);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(
                            dados,
                            "E/S inválida!",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE
                    );
                    System.err.format("E/S inválida!: %s%n", e);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.format("Erro de %s%n", e.getCause());
                }
            } else if (ev.getSource() == voltar) {
                screen.changePanel(0);
            }
        }
    }

    private Screen screen;
    private SalvarDados dados;
    private Tipos tipos;
    private ArrayList<Duravel> duraveis;
    private ArrayList<Perecivel> pereciveis;
    private Clientela clientes;
    private Estoque estoque;
    private FilaEstoque fila;
    private Portos portos;
    private SortedMap<String,Navio> navios;
    private JTextField texto;
    private JButton confirmar;
    private JButton voltar;

    public SalvarDados(Screen screen, Tipos tipos, ArrayList<Duravel> duraveis, ArrayList<Perecivel> pereciveis, Clientela clientes, Estoque cargas, FilaEstoque fila, Portos portos, SortedMap<String, Navio> navios) {
        super(new BorderLayout());
        this.screen = screen;
        this.dados = this;
        this.tipos = tipos;
        this.duraveis = duraveis;
        this.pereciveis = pereciveis;
        this.clientes = clientes;
        this.estoque = cargas;
        this.fila = fila;
        this.portos = portos;
        this.navios = navios;
        createUIComponents();
    }

    public void createUIComponents() {
        createTextFields();
        createButtons();
    }

    public void createButtons() {
        JPanel painelButtons = new JPanel(new FlowLayout()); // Configura o layout

        voltar = new JButton("Voltar");
        voltar.addActionListener(new Events());
        painelButtons.add(voltar);

        confirmar = new JButton("Confirmar");
        confirmar.addActionListener(new Events());
        painelButtons.add(confirmar);

        this.add(painelButtons, BorderLayout.SOUTH);
    }

    public void createTextFields() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.add(new JLabel("SALVAR DADOS"));
        this.add(header, BorderLayout.NORTH);

        JPanel info = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Nome do arquivo: ");
        info.add(label);
        texto = new JTextField(12);
        info.add(texto);

        this.add(info, BorderLayout.CENTER);
    }

    public void saveToFile(String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Classe de lib externa

        // Converte a lista de objetos em JSON e escreve no arquivo
        FileWriter writerDuravel = new FileWriter(fileName + "-TIPOSDURAVEL"+".json");
        gson.toJson(duraveis, writerDuravel);
        writerDuravel.close();

        FileWriter writerPerecivel = new FileWriter(fileName + "-TIPOSPERECIVEL"+".json");
        gson.toJson(pereciveis, writerPerecivel);
        writerPerecivel.close();

        FileWriter writerCargas = new FileWriter(fileName + "-CARGAS"+".json");
        gson.toJson(estoque.getCargas(), writerCargas);
        writerCargas.close();

        FileWriter writerClientes = new FileWriter(fileName + "-CLIENTES"+".json");
        gson.toJson(clientes.getClientes(), writerClientes);
        writerClientes.close();

        ArrayList<NavioWrapper> naviosNovo = new ArrayList<>();
        for (Map.Entry<String, Navio> entry : navios.entrySet()) {
            String key = entry.getKey();
            Navio navio = entry.getValue();
            naviosNovo.add(new NavioWrapper(key, navio));
        }
        FileWriter writerNavios = new FileWriter(fileName + "-NAVIOS"+".json");
        gson.toJson(naviosNovo, writerNavios);
        writerNavios.close();

        FileWriter writerPortos = new FileWriter(fileName + "-PORTOS"+".json");
        gson.toJson(portos.getListaPorto(), writerPortos);
        writerPortos.close();
    }

}
