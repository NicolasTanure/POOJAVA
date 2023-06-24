package view;

import model.*;
import model.collections.*;
import model.constants.EstadoCarga;
import model.constants.Prioridade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;

public class DadosIniciais extends JPanel {
    class Events implements ActionListener { // Inner class para tratamento de eventos
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == confirmar) {
                try {
                    readCSV();

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
    private DadosIniciais dados;
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

    public DadosIniciais(Screen screen, Tipos tipos, ArrayList<Duravel> duraveis, ArrayList<Perecivel> pereciveis,Clientela clientes, Estoque cargas, FilaEstoque fila, Portos portos, SortedMap<String, Navio> navios) {
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
        header.add(new JLabel("CARREGAR DADOS INICIAIS"));
        this.add(header, BorderLayout.NORTH);

        JPanel info = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Nome dos arquivos: ");
        info.add(label);
        texto = new JTextField(12);
        info.add(texto);

        this.add(info, BorderLayout.CENTER);
    }

    public void readCSV() throws IOException {
        String SEPARATOR = File.separator; // Descobre o separador de diretórios do SO
        String location = "";
        ArrayList<String> distancias = null;
        ArrayList<String> cargas = null;

        if (texto.getText().equalsIgnoreCase("EXEMPLO")) {
            Path path = Paths.get("files"); // Pega o diretório

            DirectoryStream<Path> stream = Files.newDirectoryStream(path); // Abre uma stream do diretório inteiro
            for (Path file : stream) {
                if (file.getFileName().equals(Path.of("EXEMPLO-TIPOSCARGAS.CSV"))) {
                    location = path + SEPARATOR + "EXEMPLO-TIPOSCARGAS.CSV";
                    readTipos(location);
                } else if (file.getFileName().equals(Path.of("EXEMPLO-CLIENTES.CSV"))) {
                    location = path + SEPARATOR + "EXEMPLO-CLIENTES.CSV";
                    readClientes(location);
                } else if (file.getFileName().equals(Path.of("EXEMPLO-DISTANCIAS.CSV"))) {
                    location = path + SEPARATOR + "EXEMPLO-DISTANCIAS.CSV";
                    distancias = readDistanciasCargas(location);
                } else if (file.getFileName().equals(Path.of("EXEMPLO-NAVIOS.CSV"))) {
                    location = path + SEPARATOR + "EXEMPLO-NAVIOS.CSV";
                    readNavios(location);
                } else if (file.getFileName().equals(Path.of("EXEMPLO-PORTOS.CSV"))) {
                    location = path + SEPARATOR + "EXEMPLO-PORTOS.CSV";
                    readPortos(location);
                } else if (file.getFileName().equals(Path.of("EXEMPLO-CARGAS.CSV"))) {
                    location = path + SEPARATOR + "EXEMPLO-CARGAS.CSV";
                    cargas = readDistanciasCargas(location);
                } else {
                    System.out.println("ERROO");
                }
            }
            stream.close();
            // Adiciona distâncias
            for (int i=0; i<distancias.size();i++) {
                Scanner linhaScanner = new Scanner(distancias.get(i));
                linhaScanner.useDelimiter(";");

                int origem = Integer.parseInt(linhaScanner.next());
                int destino = Integer.parseInt(linhaScanner.next());
                String distFormatted = linhaScanner.next().replace(',','.');
                double dist = Double.parseDouble(distFormatted);

                if (portos.consultar(origem) && portos.consultar(destino)) {
                    Porto o = portos.getPorto(origem);
                    Porto d = portos.getPorto(destino);

                    portos.adicionaDistancia(o,dist);
                    portos.adicionaDistancia(d,dist);
                }
            }

            // Adiciona Cargas
            for (int i=0; i<cargas.size();i++) {
                Scanner linhaScanner = new Scanner(cargas.get(i));
                linhaScanner.useDelimiter(";");

                int cod = Integer.parseInt(linhaScanner.next());
                int codCliente = Integer.parseInt(linhaScanner.next());
                int codOrigem = Integer.parseInt(linhaScanner.next());
                int codDestino = Integer.parseInt(linhaScanner.next());
                int peso = Integer.parseInt(linhaScanner.next());
                String vFormatted = linhaScanner.next().replace(',','.');
                double valor = Double.parseDouble(vFormatted);
                int tempo = Integer.parseInt(linhaScanner.next());
                int numTipo = Integer.parseInt(linhaScanner.next());
                Prioridade p = Prioridade.valueOf(linhaScanner.next());
                EstadoCarga situacao = EstadoCarga.valueOf(linhaScanner.next());

                Porto o = portos.getPorto(codOrigem);
                Porto d = portos.getPorto(codDestino);
                Cliente c = clientes.getCliente(codCliente);
                TipoCarga t = tipos.getTipo(numTipo);

                if (o != null && d != null && c != null && t != null) {
                    Carga carga = new Carga(cod,c,o,d,peso,valor,tempo,t);
                    carga.setPrioridade(p);
                    carga.setEstado(situacao);
                    if (estoque.adicionarCarga(carga)) {
                        fila.addCarga(carga); // Adiciona na fila
                    }
                }
            }
            JOptionPane.showMessageDialog(
                    dados,
                    "Dados carregados!",
                    "Carregamento realizado",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    dados,
                    "Arquivos não encontrados!",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void readTipos(String path) throws IOException {
        FileReader file = new FileReader(path, Charset.defaultCharset());
        Scanner sc = new Scanner(new BufferedReader(file));
        sc.nextLine(); // Pula header do arquivo

        while (sc.hasNextLine()) {
            String linha = sc.nextLine(); // Recebe a linha inteira
            Scanner linhaScanner = new Scanner(linha);
            linhaScanner.useDelimiter(";"); // Quebra em palavras

            int cod = Integer.parseInt(linhaScanner.next());
            String desc = linhaScanner.next();
            String tipo = linhaScanner.next();
            if (tipo.equalsIgnoreCase("DURAVEL")) {
                String setor = linhaScanner.next();
                String material = linhaScanner.next();
                String imposto = linhaScanner.next();
                String impostoFormatted = imposto.replace(',', '.');
                double impostoIPI = Double.parseDouble(impostoFormatted);

                Duravel d = new Duravel(cod,setor,material,impostoIPI,desc);
                tipos.adicionarTipo(d);
                duraveis.add(d);
            } else if (tipo.equalsIgnoreCase("PERECIVEL")) {
                String origem = linhaScanner.next();
                int validadeMax = Integer.parseInt(linhaScanner.next());

                Perecivel p = new Perecivel(cod,origem,validadeMax,desc);
                tipos.adicionarTipo(p);
                pereciveis.add(p);
            }
            linhaScanner.close();
        }
    }

    public void readClientes(String path) throws IOException {
        FileReader file = new FileReader(path, Charset.defaultCharset());
        Scanner sc = new Scanner(new BufferedReader(file));
        sc.nextLine(); // Pula header do arquivo

        while (sc.hasNextLine()) {
            String linha = sc.nextLine(); // Recebe a linha inteira
            Scanner linhaScanner = new Scanner(linha);
            linhaScanner.useDelimiter(";"); // Quebra em palavras

            int cod = Integer.parseInt(linhaScanner.next());
            String nome = linhaScanner.next();
            String email = linhaScanner.next();

            clientes.adicionarCliente(new Cliente(cod,nome,email));

            linhaScanner.close();
        }
    }

    public void readNavios(String path) throws IOException {
        FileReader file = new FileReader(path, Charset.defaultCharset());
        Scanner sc = new Scanner(new BufferedReader(file));
        sc.nextLine(); // Pula header do arquivo

        while (sc.hasNextLine()) {
            String linha = sc.nextLine(); // Recebe a linha inteira
            Scanner linhaScanner = new Scanner(linha);
            linhaScanner.useDelimiter(";"); // Quebra em palavras

            String nome = linhaScanner.next();
            String vFormatted = linhaScanner.next().replace(',','.');
            double velocidade = Double.parseDouble(vFormatted);
            String autoFormatted = linhaScanner.next().replace(',','.');
            double autonomia = Double.parseDouble(autoFormatted);
            String custoFormatted = linhaScanner.next().replace(',','.');
            double custo = Double.parseDouble(custoFormatted);

            navios.put(nome,new Navio(nome,velocidade,autonomia,custo));

            linhaScanner.close();
        }
    }

    public void readPortos(String path) throws IOException {
        FileReader file = new FileReader(path, Charset.defaultCharset());
        Scanner sc = new Scanner(new BufferedReader(file));
        sc.nextLine(); // Pula header do arquivo

        while (sc.hasNextLine()) {
            String linha = sc.nextLine(); // Recebe a linha inteira
            Scanner linhaScanner = new Scanner(linha);
            linhaScanner.useDelimiter(";"); // Quebra em palavras

            int cod = Integer.parseInt(linhaScanner.next());
            String nome = linhaScanner.next();
            String pais = linhaScanner.next();

            Porto p = new Porto(cod,nome,pais);
            if (portos.addPorto(p)) { // Se foi possível cadastrar
                portos.adicionaDistancia(p);
            }

            linhaScanner.close();
        }
    }

    public ArrayList<String> readDistanciasCargas(String path) throws IOException {
        FileReader file = new FileReader(path, Charset.defaultCharset());
        Scanner sc = new Scanner(new BufferedReader(file));
        sc.nextLine(); // Pula header do arquivo

        ArrayList<String> linhas = new ArrayList<>();

        while (sc.hasNextLine()) {
            String linha = sc.nextLine(); // Recebe a linha inteira
            linhas.add(linha);
        }
        return linhas;
    }

}
