package model.collections;

import model.Carga;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Estoque {
    // Inner class que reordena a fila de cargas
    class OrderCarga implements Comparator<Carga> {
        @Override
        public int compare(Carga one, Carga second) {
            return one.getIdentificador() - second.getIdentificador();
        }
    }

    private LinkedList<Carga> cargas;

    public Estoque() {
        cargas = new LinkedList<>();
    }

    public LinkedList<Carga> getCargas() {
        return cargas;
    }

    public Carga getCarga(int id) {
        for (Carga c : cargas) {
            if (c.getIdentificador() == id) {
                return c;
            }
        }
        return null;
    }

    public boolean adicionarCarga(Carga c) {
        if (!consultar(c.getIdentificador())) {
            if (cargas.add(c)) {
                Collections.sort(cargas, new OrderCarga());
            }
            return true;
        }
        return false;
    }

    public boolean consultar(int id) {
        for (Carga c : cargas) {
            if (c.getIdentificador() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Código;Cliente;Origem;Destino;Peso;ValorDeclarado;TempoMáximo;TipoCarga;Prioridade;Situação\n");

        for (Carga c : cargas) {
            s.append(c.toString() + "\n");
        }
        return s.toString();
    }

    public void setCargas(LinkedList<Carga> cargas) {
        this.cargas = cargas;
    }
}
