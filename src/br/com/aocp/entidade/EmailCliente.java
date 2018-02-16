package br.com.aocp.entidade;

public class EmailCliente {

	private Long id;
	private String email;
	private ClientePessoaFisica clientePessoaFisica;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ClientePessoaFisica getClientePessoaFisica() {
		return clientePessoaFisica;
	}

	public void setClientePessoaFisica(ClientePessoaFisica clientePessoaFisica) {
		this.clientePessoaFisica = clientePessoaFisica;
	}

}
