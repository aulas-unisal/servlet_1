package br.com.aocp.repository;

import java.util.List;

import br.com.aocp.entidade.ClientePessoaFisica;
import br.com.aocp.entidade.EmailCliente;
import br.com.aocp.entidade.Telefone;

public interface RepositoryCliente {

	void salvar(ClientePessoaFisica clientePessoaFisica);

	void atualiza(ClientePessoaFisica clientePessoaFisica);

	void deleta(String cod);

	void deleta(Long cod);

	ClientePessoaFisica consulta(Long cod);

	ClientePessoaFisica consulta(String cod);

	List<ClientePessoaFisica> consultaTodos();

	void salvarEmailCliente(EmailCliente emailCliente);

	void remoeEmailCliente(String pessoaFisicaEmail);

	void remoeTelefoneCliente(String pessoaFisicaFone);

	void salvarFoneCliente(Telefone telefone);

}
