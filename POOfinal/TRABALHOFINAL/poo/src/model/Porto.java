package model;
import java.util.HashMap;
import java.util.Map;


public class Porto {
    private int id;
    private String nome;
    private String pais;
    private Map<Integer,Double> distancia;

    
    public Porto(int id, String nome, String pais) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.distancia = new HashMap<>();
    }



    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public String getPais() {
        return pais;
    }

    public Map<Integer,Double> getDistancia() {return distancia;}

    public void setId(int id) {
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "ID:" + id + ";"
                + "Nome:" + nome + ";"
                + "País:" +pais;
    }
}