package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.enums.FormaTransacao;
import model.enums.TipoTransacao;

public class Transacao {

	private long id;

	private double valor;

	private String descricao;

	private Date dataTransacao;

	private Categoria categoria;

	private TipoTransacao tipoTransacao;

	private FormaTransacao formaTransacao;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public FormaTransacao getFormaTransacao() {
		return formaTransacao;
	}

	public void setFormaTransacao(FormaTransacao formaTransacao) {
		this.formaTransacao = formaTransacao;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}
	
	public String getDataTransacaoFormatted() {
		SimpleDateFormat spdf = new SimpleDateFormat("dd/MM/YYYY");
		return spdf.format(this.dataTransacao);
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

}
