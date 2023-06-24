package model.constants;

public enum EstadoCarga {
    PENDENTE("PENDENTE"),
    LOCADO("LOCADO"),
    CANCELADO("CANCELADO"),
    FINALIZADO("FINALIZADO");

    private String estado;

    EstadoCarga(String estado) {
        this.estado = estado;
    }

    public String getNome() {
        return estado;
    }
}
