package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JPanel {
    class Events implements ActionListener {
        /**
         * Inner class que trata os eventos dos JButtons
         * @param e evento recebido que é comparado com
         * qual instância de JButton foi seu disparador
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == finalizar) {
                screen.setVisible(false);
                Runtime.getRuntime().exit(0);
            } else if (e.getSource() == tipos) {
                screen.changePanel(1);
            } else if (e.getSource() == navios) {
                screen.changePanel(2);
            } else if (e.getSource() == portos) {
                screen.changePanel(3);
            } else if (e.getSource() == clientes) {
                screen.changePanel(4);
            } else if (e.getSource() == cargas) {
                screen.changePanel(5);
            } else if (e.getSource() == listarCargas) {
                screen.changePanel(6);
            } else if(e.getSource() == frete){
                screen.changePanel(7);
            } else if (e.getSource() == dadosIniciais) {
                screen.changePanel(8);
            } else if (e.getSource() == estadoCarga) {
                screen.changePanel(9);
            } else if (e.getSource() == dadosSalvar) {
                screen.changePanel(10);
            } else if (e.getSource() == dadosCarregar) {
                screen.changePanel(11);
            }
        }
    }

    private Screen screen;
    private JButton cargas;
    private JButton tipos;
    private JButton portos;
    private JButton navios;
    private JButton clientes;
    private JButton listarCargas;
    private JButton estadoCarga;
    private JButton dadosIniciais;
    private JButton dadosCarregar;
    private JButton dadosSalvar;
    private JButton frete;
    private JButton finalizar;

    public Home(Screen screen) {
        super(new GridLayout(13,1,5,8));
        this.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
        createUIComponents();
        this.screen = screen;
    }

    public void createUIComponents() {
        JPanel boxTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titulo = new JLabel("MENU");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        boxTitulo.add(titulo);
        this.add(boxTitulo);

        JPanel boxCarga = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cargas = new JButton("Cadastrar nova Carga");
        cargas.setPreferredSize(new Dimension(350,25));
        cargas.addActionListener(new Events());
        boxCarga.add(cargas);
        this.add(boxCarga);

        JPanel boxTipo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tipos = new JButton("Cadastrar novo Tipo de Carga");
        tipos.setPreferredSize(new Dimension(350,25));
        tipos.addActionListener(new Events());
        boxTipo.add(tipos);
        this.add(boxTipo);

        JPanel boxPorto = new JPanel(new FlowLayout(FlowLayout.CENTER));
        portos = new JButton("Cadastrar novo Porto");
        portos.setPreferredSize(new Dimension(350,25));
        portos.addActionListener(new Events());
        boxPorto.add(portos);
        this.add(boxPorto);

        JPanel boxNavio = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navios = new JButton("Cadastrar novo Navio");
        navios.setPreferredSize(new Dimension(350,25));
        navios.addActionListener(new Events());
        boxNavio.add(navios);
        this.add(boxNavio);

        JPanel boxCliente = new JPanel(new FlowLayout(FlowLayout.CENTER));
        clientes = new JButton("Cadastrar novo Cliente");
        clientes.setPreferredSize(new Dimension(350,25));
        clientes.addActionListener(new Events());
        boxCliente.add(clientes);
        this.add(boxCliente);

        JPanel boxFrete = new JPanel(new FlowLayout(FlowLayout.CENTER));
        frete = new JButton("Fretar cargas");
        frete.setPreferredSize(new Dimension(350,25));
        frete.addActionListener(new Events());
        boxFrete.add(frete);
        this.add(boxFrete);

        JPanel boxListarCarga = new JPanel(new FlowLayout(FlowLayout.CENTER));
        listarCargas = new JButton("Consultar todas as cargas");
        listarCargas.setPreferredSize(new Dimension(350,25));
        listarCargas.addActionListener(new Events());
        boxListarCarga.add(listarCargas);
        this.add(boxListarCarga);

        JPanel boxEstadoCarga = new JPanel(new FlowLayout(FlowLayout.CENTER));
        estadoCarga = new JButton("Alterar situação de carga");
        estadoCarga.setPreferredSize(new Dimension(350,25));
        estadoCarga.addActionListener(new Events());
        boxEstadoCarga.add(estadoCarga);
        this.add(boxEstadoCarga);

        JPanel boxDadosIniciais = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dadosIniciais = new JButton("Carregar dados inicias");
        dadosIniciais.setPreferredSize(new Dimension(350,25));
        dadosIniciais.addActionListener(new Events());
        boxDadosIniciais.add(dadosIniciais);
        this.add(boxDadosIniciais);

        JPanel boxDadosCarregar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dadosCarregar = new JButton("Carregar dados");
        dadosCarregar.setPreferredSize(new Dimension(350,25));
        dadosCarregar.addActionListener(new Events());
        boxDadosCarregar.add(dadosCarregar);
        this.add(boxDadosCarregar);

        JPanel boxDadosSalvar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dadosSalvar = new JButton("Salvar dados");
        dadosSalvar.setPreferredSize(new Dimension(350,25));
        dadosSalvar.addActionListener(new Events());
        boxDadosSalvar.add(dadosSalvar);
        this.add(boxDadosSalvar);

        JPanel boxFinalizar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        finalizar = new JButton("Finalizar");
        finalizar.addActionListener(new Events());
        boxFinalizar.add(finalizar);
        this.add(boxFinalizar);
    }
}
