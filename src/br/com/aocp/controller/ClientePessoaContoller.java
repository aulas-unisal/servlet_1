package br.com.aocp.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.aocp.dao.ClienteDao;
import br.com.aocp.entidade.ClientePessoaFisica;
import br.com.aocp.repository.RepositoryCliente;

public class ClientePessoaContoller extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RepositoryCliente repositoryCliente;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");

	public ClientePessoaContoller() {
		super();
		repositoryCliente = new ClienteDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getParameter("action") != null) {
			if (req.getParameter("clienteId") != null) {
				String action = req.getParameter("action");
				String clienteId = req.getParameter("clienteId");
				if (action.equals("delete")) {
					repositoryCliente.deleta(clienteId);
					RequestDispatcher view = req
							.getRequestDispatcher("/listAll.jsp");
					req.setAttribute("clientes",
							repositoryCliente.consultaTodos());
					view.forward(req, resp);
				}
				if (action.equals("edit")) {
					ClientePessoaFisica clientePessoaFisica = repositoryCliente
							.consulta(clienteId);

					RequestDispatcher view = req
							.getRequestDispatcher("/index.jsp");
					req.setAttribute("cliente", clientePessoaFisica);
					view.forward(req, resp);
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String action = req.getParameter("action");

			if ((action != null && !action.equalsIgnoreCase("listar"))
					|| action == null) {

				String idTemp = req.getParameter("idTemp");
				ClientePessoaFisica clientePessoaFisica = new ClientePessoaFisica();
				if (req.getParameter("id") != null
						&& !req.getParameter("id").isEmpty()
						&& req.getParameter("id") != null) {
					clientePessoaFisica.setId(Long.parseLong(req
							.getParameter("id")));
				} else {
					clientePessoaFisica.setId(null);
				}

				clientePessoaFisica.setCpf(req.getParameter("cpf").toString());
				clientePessoaFisica.setDataNacimento(simpleDateFormat.parse(req
						.getParameter("datanascimento")));
				clientePessoaFisica.setEndereco(req.getParameter("endereco"));
				clientePessoaFisica.setNome(req.getParameter("nome"));
				clientePessoaFisica.setNumeroLogradouro(Integer.parseInt(req
						.getParameter("numeroLogradouro")));

				if (action.equals("delete")) {
					repositoryCliente.deleta(clientePessoaFisica.getId());
				} else if (action.equals("save")
						&& (idTemp == null || idTemp.isEmpty())) {
					repositoryCliente.salvar(clientePessoaFisica);
				} else if (action.equals("edit")
						|| (action.equals("save") && idTemp != null && !idTemp
								.isEmpty())) {
					repositoryCliente.atualiza(clientePessoaFisica);
				}

				RequestDispatcher view = req
						.getRequestDispatcher("/listAll.jsp");
				req.setAttribute("clientes", repositoryCliente.consultaTodos());
				view.forward(req, resp);
			} else if (action != null && action.equalsIgnoreCase("listar")) {
				RequestDispatcher view = req
						.getRequestDispatcher("/listAll.jsp");
				req.setAttribute("clientes", repositoryCliente.consultaTodos());
				view.forward(req, resp);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
