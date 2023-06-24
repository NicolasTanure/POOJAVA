package view;

import model.Navio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class CadastroNavio extends JPanel {
    // Inner class que reordena lista de Navios
    class OrderNavios implements Comparator<Navio> {
        @Override
        public int compare(Navio one, Navio second) {
            return one.getNome().compareTo(second.getNome());
        }
    }

    private JTextField nomeTextField;
    private JTextField velocidadeTextField;
    private JTextField autonomiaTextField;
    private JTextField custoTextField;
    private JButton confirmar;
    private JButton limpar;
    private JButton imprimir;
    private JButton voltar;
    private JLabel headerInformation;
    private JLabel information;
    private SortedMap<String, Navio> navios;
    private Screen screen;

    public CadastroNavio(Screen screen) {
        super(new BorderLayout());
        this.screen = screen;
        JPanel panel = new JPanel();

        // Criação do header da tela
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel header = new JLabel("CADASTRO DE NAVIO");
        header.setFont(new Font("Arial", Font.BOLD, 16));
        painel.add(header);
        this.add(painel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2,5,7));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 10,20, 10));
        inputPanel.add(new JLabel("Nome:"));
        nomeTextField = new JTextField();
        inputPanel.add(nomeTextField);
        inputPanel.add(new JLabel("Velocidade:"));
        velocidadeTextField = new JTextField();
        inputPanel.add(velocidadeTextField);
        inputPanel.add(new JLabel("Autonomia:"));
        autonomiaTextField = new JTextField();
        inputPanel.add(autonomiaTextField);
        inputPanel.add(new JLabel("Custo por Milha Básico:"));
        custoTextField = new JTextField();
        inputPanel.add(custoTextField);
        panel.add(inputPanel);


        JPanel informacoes = new JPanel(new GridLayout(2,1,5,8));
        JPanel painelText5 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JLabel textInformation = new JLabel("Informações:");
        textInformation.setFont(new Font("Arial", Font.BOLD,13));
        painelText5.add(textInformation);
        headerInformation = new JLabel();
        painelText5.add(headerInformation);
        informacoes.add(painelText5);

        JPanel painelText6 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        information = new JLabel();
        painelText6.add(information);
        informacoes.add(painelText6);

        panel.add(informacoes);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        voltar = new JButton("Voltar");
        voltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.changePanel(0); // Troca para a tela anterior (número 0)
                limparCampos();
            }
        });
        buttonPanel.add(voltar);

        limpar = new JButton("Limpar");
        limpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        buttonPanel.add(limpar);

        imprimir = new JButton("Imprimir");
        imprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imprimirNavios();
            }
        });
        buttonPanel.add(imprimir);

        confirmar = new JButton("Confirmar");
        confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarNavio();
            }
        });
        buttonPanel.add(confirmar);
        
        panel.add(buttonPanel);
        this.add(panel, BorderLayout.CENTER);

        navios = new TreeMap<>();
    }

    private void cadastrarNavio() {
        String nome = nomeTextField.getText().trim();
        String velocidadeStr = velocidadeTextField.getText().trim();
        String autonomiaStr = autonomiaTextField.getText().trim();
        String custoStr = custoTextField.getText().trim();

        if (nome.isEmpty() || velocidadeStr.isEmpty() || autonomiaStr.isEmpty() || custoStr.isEmpty()) {
            exibirMensagemErro("Por favor, preencha todos os campos.");
            return;
        }

        if (navios.containsKey(nome)) {
            exibirMensagemErro("Já existe um navio cadastrado com esse nome.");
            return;
        }

        double velocidade;
        double autonomia;
        double custo;
        double autoMilha;
        double velocidadeNos;

        try {
            velocidade = Double.parseDouble(velocidadeStr);
            velocidadeNos = velocidade; // 1 milha em km/h
        } catch (NumberFormatException e) {
            exibirMensagemErro("Valores inválidos para velocidade.");
            return;
        }

        try {
            autonomia = Double.parseDouble(autonomiaStr);
            autoMilha = autonomia; // Milha náutica
        } catch (NumberFormatException e) {
            exibirMensagemErro("Valores inválidos para autonomia.");
            return;
        }

        try {
            custo = Double.parseDouble(custoStr);
        } catch (NumberFormatException e) {
            exibirMensagemErro("Valores inválidos para custo por milha básico.");
            return;
        }

        Navio navio = new Navio(nome, velocidadeNos, autoMilha, custo);
        navios.put(nome, navio);
        exibirMensagem("Navio cadastrado com sucesso!");
    }

    public SortedMap<String,Navio> getNavios() {
        return navios;
    }

    private void exibirMensagem(String mensagem) {
        if (mensagem.equalsIgnoreCase("Navio cadastrado com sucesso!")) {
            headerInformation.setForeground(Color.GREEN);
            headerInformation.setText("CADASTRADO COM SUCESSO");
            information.setText(mensagem);
        } else {
            headerInformation.setForeground(Color.RED);
            headerInformation.setText("FALHA NO CADASTRO");
            information.setText("Não foi possível cadastrar");
        }
    }

    private void exibirMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(
                this,
                mensagem,
                "ERRO",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void limparCampos() {
        nomeTextField.setText("");
        velocidadeTextField.setText("");
        autonomiaTextField.setText("");
        custoTextField.setText("");
        headerInformation.setText("");
        information.setText("");
    }

    private void imprimirNavios() {
        StringBuilder naviosList = new StringBuilder();
        naviosList.append("Nome;Velocidade;Autonomia;CustoPorMilhasBásico\n");
        for (Map.Entry<String, Navio> entry : navios.entrySet()) {
            naviosList.append(entry.getValue().toString()).append("\n");
        }
        JOptionPane.showMessageDialog(
            this,
            naviosList.toString(),
            "Lista de Navios",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    public LinkedList<Navio> getNaviosDisponiveis() {
        LinkedList<Navio> disponiveis = new LinkedList<>();

        for (Navio n : navios.values()) {
            if (n.getCarga() == null) {
                disponiveis.add(n);
            }
        }
        Collections.sort(disponiveis, new OrderNavios());
        return disponiveis;
    }

    public LinkedList<Navio> getNaviosIndisponiveis() {
        LinkedList<Navio> indisponiveis = new LinkedList<>();

        for (Navio n : navios.values()) {
            if (n.getCarga() != null) {
                indisponiveis.add(n);
            }
        }
        Collections.sort(indisponiveis, new OrderNavios());
        return indisponiveis;
    }
}
