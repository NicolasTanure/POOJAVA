package model;

public class Cliente {
    private int cod;
    private String nome;
    private String email;
    private String classID;
    
    public Cliente(int cod, String nome, String email) {
        this.cod = cod;
        this.nome = nome;
        this.email = email;

    }

    
    public int getCod() {
        return cod;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Cód:" + cod + ";"
                + "Nome:" + nome + ";"
                + "Email:" + email;
    }
}
