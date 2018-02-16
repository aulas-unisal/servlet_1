package br.com.aocp.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientePessoaFisica {

	private Long id;
	private String nome;
	private String cpf;
	private java.util.Date dataNacimento;
	private String endereco;
	private Integer numeroLogradouro;

	private List<Telefone> telefones = new ArrayList<Telefone>();

	private List<EmailCliente> emailClientes = new ArrayList<EmailCliente>();

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public List<EmailCliente> getEmailClientes() {
		return emailClientes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNacimento() {
		return dataNacimento;
	}

	public void setDataNacimento(Date dataNacimento) {
		this.dataNacimento = dataNacimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumeroLogradouro() {
		return numeroLogradouro;
	}

	public void setNumeroLogradouro(Integer numeroLogradouro) {
		this.numeroLogradouro = numeroLogradouro;
	}

}
