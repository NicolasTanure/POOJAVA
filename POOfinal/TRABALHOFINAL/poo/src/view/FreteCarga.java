package view;

import model.Carga;
import model.Duravel;
import model.Navio;
import model.collections.FilaEstoque;
import model.constants.EstadoCarga;
import model.constants.Prioridade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Map;

public class FreteCarga extends JPanel {
    class Events implements ActionListener { // Inner class para tratamento de eventos
        @Override
        public void actionPerformed(ActionEvent ev) {
            if (ev.getSource() == fretar) {
                try {
                    fretarCargas();
                } catch (NullPointerException e) {
                    System.err.format("Carga inválida: %s%n", e);
                } catch (Exception e) {
                    System.err.format("");
                }
            } else if (ev.getSource() == voltar) {
                screen.changePanel(0);
            }
        }
    }

    private Screen screen;
    private FilaEstoque fila;
    private LinkedList<Navio> navios;
    private LinkedList<Navio> ocupados;
    private Carga current;
    private JButton fretar;
    private JButton voltar;
    private JRadioButton barato;
    private JRadioButton rapido;
    private JTextArea dadosCarga;
    private JLabel headerInformation;
    private JLabel information;

    public FreteCarga(Screen screen, FilaEstoque fila, LinkedList<Navio> navios, LinkedList<Navio> ocupados) {
        super(new GridLayout(7, 1));
        this.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
        this.screen = screen;
        this.fila = fila;
        this.navios = navios;
        this.ocupados = ocupados;
        this.current = fila.getFirstCarga();
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

        fretar = new JButton("Fretar");
        fretar.addActionListener(new Events());
        painelButtons.add(fretar);

        this.add(painelButtons);
    }

    public void createTextFields() {
        // Criação do header da tela
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel header = new JLabel("FRETAR CARGA");
        header.setFont(new Font("Arial", Font.BOLD, 16));
        painel.add(header);
        this.add(painel);

        JPanel currentCarga = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dadosCarga = new JTextArea(current.toString()); // Referência da primeira Carga
        dadosCarga.setEditable(false);
        dadosCarga.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(dadosCarga);
        scroll.setPreferredSize(new Dimension(570,40));
        currentCarga.add(scroll);
        this.add(currentCarga);

        ButtonGroup bg = new ButtonGroup();
        JPanel tipoFrete = new JPanel(new FlowLayout(FlowLayout.CENTER));
        barato = new JRadioButton("Barato", false);
        bg.add(barato);
        tipoFrete.add(barato);
        rapido = new JRadioButton("Rápido", false);
        bg.add(rapido);
        tipoFrete.add(rapido);
        this.add(tipoFrete);
    }

    public void createInformationFields() {
        // Área que mostra as informações linha 5
        JPanel painelText5 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel textInformation = new JLabel("Informações:");
        textInformation.setFont(new Font("Arial", Font.BOLD, 13));
        painelText5.add(textInformation);
        headerInformation = new JLabel();
        painelText5.add(headerInformation);
        this.add(painelText5);
        // Área que mostra as informações linha 6
        JPanel painelText6 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        information = new JLabel(String.valueOf(fila.getFila().size()));
        painelText6.add(information);
        this.add(painelText6);
    }

    private void fretarCargas() throws NullPointerException {
        if (fila.getFila().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Não há mais cargas a serem fretadas!",
                    "ATENÇÃO",
                    JOptionPane.WARNING_MESSAGE
            );
        }
        else {
            Navio navio = null;
            boolean frete = false;
            String info = "";

            Map<Integer, Double> destino = this.current.getDestino().getDistancia();
            int id = this.current.getOrigem().getId();
            double distancia = destino.get(id);

            if (!navios.isEmpty()) {
                for (Navio n : navios) {
                    double tempo = Math.ceil(distancia / (n.getVelocidade()*1.5)/24);
                    if (n.getAutonomia() >= distancia && tempo <= this.current.getTempoMaximo()) {
                        if (null == this.current.getPrioridade()) {
                            if (barato.isSelected()) {
                                this.current.setPrioridade(Prioridade.BARATO);
                                this.current.setEstado(EstadoCarga.LOCADO);
                                this.current.setNavioAlocado(n);
                                n.setCarga(this.current);
                                n.setHistorico(this.current);
                                navio = n;
                                navios.remove(n); // Remove o navio não mais disponível
                                ocupados.add(n); // Cataloga como indisponível

                                frete = true;
                                info = "Carga alocada!";

                            } else if (rapido.isSelected()) {
                                this.current.setPrioridade(Prioridade.RAPIDO);
                                this.current.setEstado(EstadoCarga.LOCADO);
                                this.current.setNavioAlocado(n);
                                n.setCarga(this.current);
                                n.setHistorico(this.current);
                                navio = n;
                                navios.remove(n);
                                ocupados.add(n);

                                frete = true;
                                info = "Carga alocada!";

                            } else {
                                headerInformation.setForeground(Color.RED);
                                headerInformation.setText("ERRO");
                                information.setText("Selecione a prioridade do frete!");
                            }
                        } else {
                            this.current.setEstado(EstadoCarga.LOCADO);
                            this.current.setNavioAlocado(n);
                            n.setCarga(this.current);
                            n.setHistorico(this.current);
                            navio = n;
                            navios.remove(n); // Remove o navio não mais disponível
                            ocupados.add(n); // Cataloga como indisponível

                            frete = true;
                            info = "Carga alocada!";
                        }
                    }
                }

            }
            if (frete) { // Se foi possível fretar
                double preco = calculaFrete(navio, this.current); // Calcula o frete
                this.current.setPreco(preco);

                headerInformation.setForeground(Color.GREEN);
                headerInformation.setText("CARGA ALOCADA");
                information.setText("Carga em transporte... " + "Preço: " + preco);
                if (!fila.getFila().isEmpty()) {
                    fila.removeCarga();
                    this.current = fila.getFirstCarga(); // Senão está vazia, atualiza as informações na tela
                    dadosCarga.setText(this.current.toString());
                } else {
                    dadosCarga.setText("");
                }
            } else { // Senão foi
                info = verificaIndisponiveis(this.current, distancia);
                headerInformation.setForeground(Color.RED);
                headerInformation.setText("NÃO FOI POSSIVEL ALOCAR CARGA");
                information.setText(info);
            }
        }
    }

    public String verificaIndisponiveis(Carga current, double distancia) {
        String info = "";
        boolean adicionaFila = false;

        if (!ocupados.isEmpty()) {
            for (Navio n : ocupados) {
                double time = Math.ceil(distancia / n.getVelocidade()*1.5*24);
                if (n.getAutonomia() >= distancia && time <= current.getTempoMaximo()) {
                    fila.removeCarga();
                    fila.addCarga(this.current);

                    adicionaFila = true;
                    info = "Carga adicionada novamente na fila. Sem navios disponíveis no momento!";
                }
            }
            if (!adicionaFila) { // Nem mesmo os navios indisponíveis são capazes de transportar
                if (!fila.getFila().isEmpty()) {
                    this.current.setEstado(EstadoCarga.CANCELADO);
                    info = "Carga cancelada. Sem navios capazes de transportar!";
                    fila.removeCarga();

                    this.current = fila.getFirstCarga();
                }
            }
        } else {
            this.current.setEstado(EstadoCarga.CANCELADO);
            info = "Carga cancelada. Sem navios capazes de transportar!";
            fila.removeCarga();
            this.current = fila.getFirstCarga();
        }
        return info;
    }

    public double calculaFrete(Navio navio, Carga carga) {
        double precoFinal = 0;
        double precoRegiao = 0;
        double precoPeso = 0;
        double precoPrioridade = 0;

        int idDestino = carga.getDestino().getId();
        double distancia = carga.getOrigem().getDistancia().get(idDestino);

        // Preço por prioridade
        if(carga.getPrioridade() == Prioridade.RAPIDO) {
            precoPrioridade = distancia * (navio.getCustoPorMilhaBasico() * 2);
        }else {
            precoPrioridade = distancia * navio.getCustoPorMilhaBasico();
        }

        // Preço por regiao
        if(carga.getOrigem().getPais().toLowerCase().equals("brasil") && carga.getDestino().getPais().toLowerCase().equals("brasil")) {
            precoRegiao = 10000;
        } else {
            precoRegiao = 50000;
        }

        // Preço por peso
        if(carga.getTipoCarga() instanceof Duravel) {
            Duravel duravel = (Duravel) carga.getTipoCarga();
            precoPeso = carga.getPeso() * 1.5 + (carga.getValorDeclarado() * (duravel.getImpostoIndustrializado()/100));
        } else {
            precoPeso = carga.getPeso() * 2;
        }

        precoFinal = precoRegiao + precoPeso + precoPrioridade;
        return precoFinal;
    }
}


