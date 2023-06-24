package view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;
import model.collections.*;
import model.constants.EstadoCarga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.SortedMap;

public class CarregarDados extends JPanel {
    class Events implements ActionListener { // Inner class para tratamento de eventos
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == confirmar) {
                try {
                    readFile(texto.getText());
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
    private CarregarDados dados;
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

    public CarregarDados(Screen screen, Tipos tipos, ArrayList<Duravel> duraveis,ArrayList<Perecivel> pereciveis,Clientela clientes, Estoque cargas, FilaEstoque fila, Portos portos, SortedMap<String, Navio> navios) {
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
        header.add(new JLabel("CARREGAR DADOS"));
        this.add(header, BorderLayout.NORTH);

        JPanel info = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Nome do arquivo: ");
        info.add(label);
        texto = new JTextField(12);
        info.add(texto);

        this.add(info, BorderLayout.CENTER);
    }

    public void readFile(String fileName) throws IOException {
        Gson gson = new Gson();
        FileReader reader1 = new FileReader(fileName + "-CLIENTES.json");
        Type listaClientes = new TypeToken<ArrayList<Cliente>>(){}.getType();
        ArrayList<Cliente> clienteArrayList = gson.fromJson(reader1, listaClientes);
        for(Cliente c : clienteArrayList) {
            clientes.adicionarCliente(c);
        }
        reader1.close();

        FileReader reader2 = new FileReader(fileName + "-PORTOS.json");
        Type listaPortos = new TypeToken<ArrayList<Porto>>(){}.getType();
        ArrayList<Porto> portoArrayList = gson.fromJson(reader2, listaPortos);
        for(Porto p : portoArrayList) {
            portos.addPorto(p);
            portos.adicionaDistancia(p);
        }
        reader2.close();

        FileReader reader3 = new FileReader(fileName + "-NAVIOS.json");
        Type listaNavios = new TypeToken<ArrayList<NavioWrapper>>(){}.getType();
        ArrayList<NavioWrapper> navioMap = gson.fromJson(reader3, listaNavios);
        for(NavioWrapper n : navioMap) {
            navios.put(n.getKey(), n.getNavio());
        }
        reader3.close();

        FileReader reader4 = new FileReader(fileName + "-TIPOSDURAVEL.json");
        Type listaDuravel = new TypeToken<ArrayList<Duravel>>(){}.getType();
        ArrayList<Duravel> duravel = gson.fromJson(reader4, listaDuravel);
        for(Duravel d : duravel) {
            duraveis.add(d);
            tipos.adicionarTipo(d);
        }
        reader4.close();

        FileReader reader5 = new FileReader(fileName + "-TIPOSPERECIVEL.json");
        Type listaPerecivel = new TypeToken<ArrayList<Perecivel>>(){}.getType();
        ArrayList<Perecivel> perecivel = gson.fromJson(reader5, listaPerecivel);
        for(Perecivel p : perecivel) {
            pereciveis.add(p);
            tipos.adicionarTipo(p);
        }
        reader5.close();

        FileReader reader6 = new FileReader(fileName + "-CARGAS.json");
        Type listaCargas= new TypeToken<ArrayList<Carga>>(){}.getType();
        ArrayList<Carga> cargaArrayList = gson.fromJson(reader6, listaCargas);
        for(Carga carga : cargaArrayList) {
            for (TipoCarga t : tipos.getTipos()) {
                if (t.getNumero() == carga.getTipoCarga().getNumero()) {
                    carga.setTipoCarga(t);
                }
            }
            if (estoque.adicionarCarga(carga) && carga.getEstado() == EstadoCarga.PENDENTE) {
                fila.addCarga(carga);
            }
        }
        reader6.close();
        JOptionPane.showMessageDialog(
                dados,
                "Dados carregados!",
                "Salvamento realizado",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
