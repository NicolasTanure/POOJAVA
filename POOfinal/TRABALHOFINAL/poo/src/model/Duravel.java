package model;

public class Duravel extends TipoCarga {
    private String setor;
    private String material;
    private double impostoIndustrializado;
    private Duravel tipo;

    public Duravel(int numero, String s, String m, double i, String desc) {
        super(numero, desc);
        this.tipo = this;
        this.setor = s;
        this.material = m;
        this.impostoIndustrializado = i;
    }

    public Duravel getTipo() {
        return tipo;
    }

    public String getSetor() {
        return setor;
    }

    public String getMaterial() {
        return material;
    }

    public double getImpostoIndustrializado() {
        return impostoIndustrializado;
    }

    @Override
    public String toString() {
        return "Nº:" + getNumero() + ";"
                + "Desc:" + getDescricao() + ";"
                + "DURAVEL" + ";"
                + "Setor:" + setor + ";"
                + "Material:" + material + ";"
                + "Imposto:" + impostoIndustrializado;
    }
}

