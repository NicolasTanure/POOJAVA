package model;

public class TipoCarga {
	private int numero;
	private String descricao;

	public TipoCarga(int numero, String desc) {
		this.numero = numero;
		this.descricao = desc;

	}

	public int getNumero() {
		return numero;
	}

	public String getDescricao() {
		return descricao;
	}
}
