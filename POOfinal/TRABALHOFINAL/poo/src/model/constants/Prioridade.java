package model.constants;

public enum Prioridade {
    BARATO("BARATO"),
    RAPIDO("RAPIDO");

    private String prioridade;

    Prioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getNome() {
        return prioridade;
    }
}
