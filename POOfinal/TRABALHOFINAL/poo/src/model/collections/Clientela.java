package model.collections;

import model.Cliente;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Clientela {
    // Inner class para reordenação da lista de clientes
    class OrderClientes implements Comparator<Cliente> {
        @Override
        public int compare(Cliente one, Cliente second) {
            return one.getCod() - second.getCod();
        }
    }

    private LinkedList<Cliente> clientes;
    private String erro;

    public Clientela() {
        clientes = new LinkedList<>();
    }

    public String getErro() {
        return erro;
    }

    public Cliente getCliente(int cod) {
        for (Cliente c : clientes) {
            if (c.getCod() == cod) {
                return c;
            }
        }
        return null;
    }

    public boolean adicionarCliente(Cliente c) {
        if (!consultar(c)) {
            if (clientes.add(c)) {
                Collections.sort(clientes, new OrderClientes());
            }
            return true;
        }
        return false;
    }

    public boolean consultar(Cliente c) {
        for (Cliente cl : clientes) {
            if (cl.getCod() == c.getCod()) {
                erro = "Código já existente!";
                return true;
            }
            else if (cl.getNome().equals(c.getNome())) {
                erro = "Nome já existente!";
                return true;
            }
            else if (cl.getEmail().equals(c.getEmail())) {
                erro = "Email já existente!";
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Códido;Nome;Email\n");

        for (Cliente c : clientes) {
            s.append(
                   c.getCod() + ";"
                   + c.getNome() + ";"
                    + c.getEmail() + "\n"
            );
        }
        return s.toString();
    }

    public LinkedList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(LinkedList<Cliente> clientes) {
        this.clientes = clientes;
    }
}
