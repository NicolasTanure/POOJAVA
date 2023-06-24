package model;

import model.constants.EstadoCarga;
import model.constants.Prioridade;

public class Carga {
	private int identificador;
	private int peso;
	private double valorDeclarado;
	private int tempoMaximo;
	private double preco;
	private Cliente cliente;
	private Porto destino;
	private Porto origem;
	private TipoCarga tipoCarga;
	private EstadoCarga estado;
	private Prioridade prioridade;
	private Navio navioAlocado;

	public Carga(int id,Cliente c,Porto o,Porto d,int peso,double v,int t,TipoCarga tc) {
		this.identificador = id;
		this.cliente = c;
		this.destino = d;
		this.origem = o;
		this.peso = peso;
		this.valorDeclarado = v;
		this.tempoMaximo = t;
		this.tipoCarga = tc;
		this.estado = EstadoCarga.PENDENTE;
	}

	public int getIdentificador() {
		return identificador;
	}

	public int getPeso() {
		return peso;
	}

	public double getValorDeclarado() {
		return valorDeclarado;
	}

	public int getTempoMaximo() {
		return tempoMaximo;
	}

	public Porto getDestino() {
		return destino;
	}

	public Porto getOrigem() {
		return origem;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public TipoCarga getTipoCarga() {
		return tipoCarga;
	}

	public void setTipoCarga(TipoCarga t) {
		this.tipoCarga = t;
	}

	public void setPreco(double p) {
		this.preco = p;
	}

	public double getPreco() {
		return preco;
	}

	public EstadoCarga getEstado() {
		return estado;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setEstado(EstadoCarga e) {
		this.estado = e;
	}

	public void setPrioridade(Prioridade p) {
		this.prioridade = p;
	}

	public void setNavioAlocado(Navio navioAlocado) {
		this.navioAlocado = navioAlocado;
	}

	public Navio getNavioAlocado() {
		return navioAlocado;
	}

	@Override
	public String toString() {
		return "CARGA: ID:" + identificador + ";"
				+ "Peso:" + peso + ";"
				+ "Valor:" +valorDeclarado + ";"
				+ "Tempo Máx.:" + tempoMaximo + ";"
				+ "Situação:" + estado.getNome() + "\n"
				+ " | TIPO: " + getTipoCarga().toString() + "\n"
				+ " | CLIENTE: " + getCliente().toString() + "\n"
				+ " | ORIGEM: " + getOrigem().toString() + "\n"
				+ " | DESTINO: " + getDestino().toString();
	}
}