package br.com.aocp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.aocp.connection.SingletonConnetion;
import br.com.aocp.entidade.ClientePessoaFisica;
import br.com.aocp.entidade.EmailCliente;
import br.com.aocp.entidade.Telefone;
import br.com.aocp.repository.RepositoryCliente;

public class ClienteDao implements RepositoryCliente {
	private Connection connection;

	public ClienteDao() {
		connection = SingletonConnetion.getConnection();
	}

	@Override
	public void salvar(ClientePessoaFisica clientePessoaFisica) {
		String sql = "INSERT INTO cliente_pessoa_fisica(nome, cpf, dataNacimento, endereco, numerologradouro)VALUES ( ?, ?, ?, ?, ?);";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);

			constroiStatement(clientePessoaFisica, insert);
			insert.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}

	}

	@Override
	public void atualiza(ClientePessoaFisica clientePessoaFisica) {
		String sql = "UPDATE cliente_pessoa_fisica SET nome=?, cpf=?, dataNacimento=?, endereco=?, numeroLogradouro=? where id = "
				+ clientePessoaFisica.getId();
		try {
			PreparedStatement update = connection.prepareStatement(sql);

			constroiStatement(clientePessoaFisica, update);
			update.execute();
			connection.commit();
		} catch (Exception e) { 
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}

	}

	@Override
	public void deleta(String clientePessoaFisica) {
		String sql = "DELETE FROM cliente_pessoa_fisica where id = "
				+ clientePessoaFisica;
		try {

			remoeEmailClienteTodos(clientePessoaFisica);

			remoeTelefoneClienteTodos(clientePessoaFisica);

			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public ClientePessoaFisica consulta(Long cod) {
		ClientePessoaFisica retorno = new ClientePessoaFisica();
		try {
			String sql = "select * FROM cliente_pessoa_fisica where id = "
					+ cod;
			PreparedStatement find = connection.prepareStatement(sql);
			ResultSet resultSet = find.executeQuery();
			while (resultSet.next()) {
				controiCliente(resultSet, retorno);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return retorno;
	}

	@Override
	public List<ClientePessoaFisica> consultaTodos() {

		List<ClientePessoaFisica> retorno = new ArrayList<ClientePessoaFisica>();
		String sql = "select * FROM cliente_pessoa_fisica ";

		try {
			PreparedStatement find = connection.prepareStatement(sql);
			ResultSet resultSet = find.executeQuery();
			while (resultSet.next()) {
				ClientePessoaFisica obClientePessoaFisica = new ClientePessoaFisica();
				controiCliente(resultSet, obClientePessoaFisica);
				retorno.add(obClientePessoaFisica);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return retorno;
	}

	private void controiCliente(ResultSet resultSet,
			ClientePessoaFisica obClientePessoaFisica) throws SQLException {
		obClientePessoaFisica.setId(resultSet.getLong("id"));
		obClientePessoaFisica.setCpf(resultSet.getString("cpf"));
		obClientePessoaFisica.setDataNacimento(resultSet
				.getDate("datanacimento"));
		obClientePessoaFisica.setEndereco(resultSet.getString("endereco"));
		obClientePessoaFisica.setNome(resultSet.getString("nome"));
		obClientePessoaFisica.setNumeroLogradouro(resultSet
				.getInt("numerologradouro"));
		obClientePessoaFisica.getEmailClientes().clear();
		obClientePessoaFisica.getEmailClientes().addAll(
				getEmail(obClientePessoaFisica));
		obClientePessoaFisica.getTelefones().clear();
		obClientePessoaFisica.getTelefones().addAll(
				getFones(obClientePessoaFisica));
	}

	private Collection<? extends Telefone> getFones(
			ClientePessoaFisica obClientePessoaFisica) {
		List<Telefone> emailClientes = new ArrayList<Telefone>();
		try {
			String sql = "select * FROM telefone_cliente where clientepessoafisica = "
					+ obClientePessoaFisica.getId();
			PreparedStatement find = connection.prepareStatement(sql);
			ResultSet resultSet = find.executeQuery();
			while (resultSet.next()) {
				Telefone telefone = new Telefone();
				telefone.setClientePessoaFisica(obClientePessoaFisica);
				telefone.setId(resultSet.getLong("id"));
				telefone.setNumero(resultSet.getString("numero"));
				telefone.setTipoTelefone(resultSet.getString("tipotelefone"));
				emailClientes.add(telefone);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return emailClientes;
	}

	private Collection<? extends EmailCliente> getEmail(ClientePessoaFisica id) {
		List<EmailCliente> emailClientes = new ArrayList<EmailCliente>();
		try {
			String sql = "select * FROM email_cliente where clientepessoafisica = "
					+ id.getId();
			PreparedStatement find = connection.prepareStatement(sql);
			ResultSet resultSet = find.executeQuery();
			while (resultSet.next()) {
				EmailCliente emailCliente = new EmailCliente();
				emailCliente.setId(resultSet.getLong("id"));
				emailCliente.setEmail(resultSet.getString("email"));
				emailCliente.setClientePessoaFisica(id);
				emailClientes.add(emailCliente);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return emailClientes;
	}

	private void constroiStatement(ClientePessoaFisica clientePessoaFisica,
			PreparedStatement insert) throws SQLException {
		insert.setString(1, clientePessoaFisica.getNome());
		insert.setString(2, clientePessoaFisica.getCpf());
		insert.setDate(3, new java.sql.Date(clientePessoaFisica
				.getDataNacimento().getTime()));
		insert.setString(4, clientePessoaFisica.getEndereco());
		insert.setInt(5, clientePessoaFisica.getNumeroLogradouro());
	}

	@Override
	public void deleta(Long cod) {
		this.deleta(cod.toString());
	}

	@Override
	public ClientePessoaFisica consulta(String cod) {
		return this.consulta(Long.parseLong(cod));
	}

	@Override
	public void salvarEmailCliente(EmailCliente emailCliente) {
		String sql = "INSERT INTO email_cliente(email, clientepessoafisica) VALUES (?, ?);";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);

			insert.setString(1, emailCliente.getEmail());
			insert.setLong(2, emailCliente.getClientePessoaFisica().getId());
			insert.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public void salvarFoneCliente(Telefone telefone) {
		String sql = "INSERT INTO telefone_cliente(tipotelefone, numero, clientepessoafisica) VALUES (?, ?, ?);";
		try {
			PreparedStatement insert = connection.prepareStatement(sql);

			insert.setString(1, telefone.getTipoTelefone());
			insert.setString(2, telefone.getNumero());
			insert.setLong(3, telefone.getClientePessoaFisica().getId());
			insert.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remoeEmailCliente(String pessoaFisicaEmail) {
		String sql = "DELETE FROM email_cliente where id = "
				+ pessoaFisicaEmail;
		try {
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remoeTelefoneCliente(String pessoaFisicaFone) {
		String sql = "DELETE FROM telefone_cliente where id = "
				+ pessoaFisicaFone;
		try {
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	public void remoeEmailClienteTodos(String pessoaFisicaEmail) {
		String sql = "DELETE FROM email_cliente where clientepessoafisica = "
				+ pessoaFisicaEmail;
		try {
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	public void remoeTelefoneClienteTodos(String pessoaFisicaEmail) {
		String sql = "DELETE FROM telefone_cliente where clientepessoafisica = "
				+ pessoaFisicaEmail;
		try {
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

}
