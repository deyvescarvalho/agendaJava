package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DAO;
import model.JavaBeans;
import model.Usuario;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = { "/controller", "/main", "/insert", "/select", "/update", "/delete", "/report", "/cadastrar", "/logar", "/logout" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();
	Usuario usuario = new Usuario();

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
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		}else if (action.equals("/cadastrar")) {
			cadastrarUsuario(request, response);
		}else if (action.equals("/logar")) {
			logarUsuario(request, response);
		}else if (action.equals("/logout")) {
			logout(request, response);
		}else {
			response.sendRedirect("login.html");
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

	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Document documento = new Document();

		try {
			
			
			ArrayList<JavaBeans> lista = dao.listarContatos();
			
			//crie uma estrutura para o pdf com itextpdf
			
			

			response.setContentType("apllication/pdf");
			response.reset();
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");
			response.containsHeader("asdasd");
			PdfWriter.getInstance(documento, response.getOutputStream());
			documento.open();
			documento.add(new Paragraph("Lista de contatos"));
			documento.add(new Paragraph("  "));
			PdfPTable tabela = new PdfPTable(3);
			//ArrayList<JavaBeans> lista = dao.listarContatos();
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));

			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			
			for (int i = 0; i < lista.size(); i++) {
				System.out.println(lista.get(i).getNome());
				System.out.println(lista.get(i).getFone());
				System.out.println(lista.get(i).getEmail());
			}
			documento.add(tabela);

			documento.close();

		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
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
	
	//cadastrar usuario
	protected void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("Cadastrando usuario...");
		System.out.println("Nome: " + request.getParameter("nome"));

		usuario.setNome(request.getParameter("nome"));
		usuario.setEmail(request.getParameter("email"));
		usuario.setSenha(request.getParameter("senha"));
		dao.cadastrarUsuario(usuario);
		response.sendRedirect("index.html");

	}
	
	//logar usuario
	protected void logarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("Logando usuario...");
		System.out.println("Email: " + request.getParameter("email"));

		usuario.setEmail(request.getParameter("email"));
		usuario.setSenha(request.getParameter("senha"));
		
		if(dao.logarUsuario(usuario)) {
			System.out.println("Usuario logado com sucesso!");
			// set session attribute to mark user as logged in
			HttpSession session = request.getSession(true);
			session.setAttribute("usuarioLogado", usuario.getEmail());
			response.sendRedirect("index.html");
		}else {
			response.sendRedirect("cadastrar.html");
		}

	}

	// logout
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect("login.html");
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
