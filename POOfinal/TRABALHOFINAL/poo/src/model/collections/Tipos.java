package model.collections;

import model.Duravel;
import model.Perecivel;
import model.TipoCarga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Tipos {
    // Inner class para reordenação da lista de tipo de cargas
    class OrderTipos implements Comparator<TipoCarga> {
        @Override
        public int compare(TipoCarga one, TipoCarga second) {
            return one.getNumero() - second.getNumero();
        }
    }

    private ArrayList<TipoCarga> tipos;

    public Tipos() {
        tipos = new ArrayList<>();
    }

    public ArrayList<TipoCarga> getTipos() {
        return tipos;
    }

    public TipoCarga getTipo(int num) {
        for (TipoCarga t : tipos) {
            if (t.getNumero() == num) {
                return t;
            }
        }
        return null;
    }

    public boolean adicionarTipo(TipoCarga tipo) {
        if (!consultar(tipo.getNumero())) {
            if (tipos.add(tipo)) {
                Collections.sort(tipos, new OrderTipos());
            }
            return true;
        }
        return false;
    }

    public boolean consultar(int num) {
        for (TipoCarga t : tipos) {
            if (t.getNumero() == num) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Num;Descrição;TIPO;Origem_Setor;ValidadeMax_Material;ImpostoIPI\n");

        for (TipoCarga t : tipos) {
            if (t instanceof Perecivel p) {
                s.append(
                        p.getNumero()
                                + ";" + p.getDescricao()
                                + ";PERECIVEL"
                                + ";" + p.getOrigem()
                                + ";" + p.getValidadeMax()
                                + "\n"
                );

            } else if (t instanceof Duravel d) {
                s.append(
                        d.getNumero()
                                + ";" + d.getDescricao()
                                + ";DURAVEL"
                                + ";" + d.getSetor()
                                + ";" + d.getMaterial()
                                + ";" + d.getImpostoIndustrializado()
                                + "\n"
                );
            }
        }
        return s.toString();
    }
}
