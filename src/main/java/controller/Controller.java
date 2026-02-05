package controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = { "/controller", "/main", "/insert", "/select", "/update", "/delete" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		String action = request.getServletPath();
		System.out.println(action);

		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			atualizarContato(request, response);
		} else if (action.equals("/delete")) {
			deletarContato(request, response);
		} else {
			response.sendRedirect("index.html");
		}

		// teste de conexao
		dao.testeConexao();
	}

	// listar contatos

	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArrayList<JavaBeans> lista = dao.listarContatos();

		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);

	}

	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idcon = request.getParameter("idcon");
		System.out.println(idcon);
		contato.setIdcon(idcon);
		dao.listarContato(contato);

		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());

		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);

		System.out.println(contato.getNome());
		System.out.println(contato.getIdcon());

	}
	
	protected void deletarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		contato.setIdcon(request.getParameter("idcon"));
		System.out.println("IDICON CONTROLLER" + request.getParameter("idcon"));
		dao.deletarContato(contato);
		response.sendRedirect("main");
		
	}

	protected void atualizarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		dao.atualizarContato(contato);
		response.sendRedirect("main");

	}

	// listar contatos

	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		dao.inserirContato(contato);
		response.sendRedirect("main");

	}
}
