package model.collections;

import model.Porto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Portos {
    // Inner class para reordenação da lista de Portos
    class OrderPortos implements Comparator<Porto> {
        @Override
        public int compare(Porto one, Porto second) {
            return one.getId() - second.getId();
        }
    }

    private ArrayList<Porto> listaPorto;

    public Portos() {
        this.listaPorto = new ArrayList<>();
    }

    public ArrayList<Porto> getListaPorto() {
        return listaPorto;
    }

    public Porto getPorto(int id) {
        for (Porto p : listaPorto) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean addPorto(Porto p) {
        if (!consultar(p.getId())) {
            if (listaPorto.add(p)) {
                adicionaDistancia(p);
                Collections.sort(listaPorto, new OrderPortos());
                return true;
            }
        }
        return false;
    }

    public boolean consultar(int id) {
        for(Porto p : listaPorto) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void adicionaDistancia(Porto p) {
        if (!listaPorto.isEmpty()) {
            for (Porto porto : listaPorto) {
                porto.getDistancia().put(p.getId(), 100.0);
                p.getDistancia().put(porto.getId(), 100.0);
            }
        }
    }

    public void adicionaDistancia(Porto p, double dist) {
        if (!listaPorto.isEmpty()) {
            for (Porto porto : listaPorto) {
                porto.getDistancia().put(p.getId(), dist);
                p.getDistancia().put(porto.getId(), dist);
            }
        }
    }

    public void setListaPorto(ArrayList<Porto> listaPorto) {
        this.listaPorto = listaPorto;
    }

    @Override
    public String toString() {
        StringBuilder sc = new StringBuilder();
        sc.append("id;nome;pais\n");

        for (Porto p : listaPorto) {
            sc.append(
                    p.getId() + ";"
                    + p.getNome() + ";"
                    + p.getPais() + "\n"
            );
        }
        return sc.toString();
    }
}

