package br.com.aocp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.aocp.dao.ClienteDao;
import br.com.aocp.entidade.ClientePessoaFisica;
import br.com.aocp.entidade.EmailCliente;
import br.com.aocp.repository.RepositoryCliente;

public class ClienteEmailContoller extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private RepositoryCliente repositoryCliente;

	public ClienteEmailContoller() {
		super();
		repositoryCliente = new ClienteDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action.equals("delet") && req.getParameter("idEmail") != null) {
			String clienteId = req.getParameter("idTemp");
			ClientePessoaFisica pessoaFisica = new ClientePessoaFisica();
			pessoaFisica.setId(Long.parseLong(clienteId));
			repositoryCliente.remoeEmailCliente(req.getParameter("idEmail"));
			RequestDispatcher view = req.getRequestDispatcher("/index.jsp");
			req.setAttribute("cliente",
					repositoryCliente.consulta(pessoaFisica.getId()));

			view.forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			if (req.getParameter("action") != null) {
				if (req.getParameter("idTemp") != null) {
					String action = req.getParameter("action");
					String clienteId = req.getParameter("idTemp");
					if (action.equals("save")
							&& req.getParameter("email") != null) {

						ClientePessoaFisica pessoaFisica = new ClientePessoaFisica();
						pessoaFisica.setId(Long.parseLong(clienteId));

						EmailCliente emailCliente = new EmailCliente();
						emailCliente.setEmail(req.getParameter("email"));
						emailCliente.setClientePessoaFisica(pessoaFisica);
						repositoryCliente.salvarEmailCliente(emailCliente);
						RequestDispatcher view = req
								.getRequestDispatcher("/index.jsp");
						req.setAttribute("cliente", repositoryCliente
								.consulta(pessoaFisica.getId()));
						view.forward(req, resp);
					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
