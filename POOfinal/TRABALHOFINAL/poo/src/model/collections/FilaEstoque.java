package model.collections;

import model.Carga;

import java.util.LinkedList;
import java.util.Queue;

public class FilaEstoque {
    private Queue<Carga> fila;
    private String classID;

    public FilaEstoque() {
        fila = new LinkedList<>();
        this.classID = "FILA";
    }

    public Queue<Carga> getFila() {
        return fila;
    }

    public Carga getFirstCarga() {
        return fila.peek();
    }

    public boolean addCarga(Carga c) {
        if (!consultar(c.getIdentificador())) {
            return fila.offer(c);
        }
        return false;
    }

    public boolean consultar(int id) {
        for (Carga c : fila) {
            if (c.getIdentificador() == id) {
                return true;
            }
        }
        return false;
    }

    public Carga removeCarga() {
        return fila.poll();
    }

    public void setFila(Queue<Carga> fila) {
        this.fila = fila;
    }

    public String getClassID() {
        return classID;
    }
}


