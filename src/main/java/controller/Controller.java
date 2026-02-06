package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;

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
@WebServlet(urlPatterns = { "/controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
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
		// NOTE: removed writing to response here because it may corrupt binary outputs like PDFs

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

	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Document documento = new Document();

		try {
			
			ArrayList<JavaBeans> lista = dao.listarContatos();
			
			// montando estrutura do PDF com iText
			Font fonteCabecalho = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
			Font fonteCorpo = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.BLACK);
			
			// reset the response before setting content type/headers
			response.reset();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=contatos.pdf");
			
			PdfWriter.getInstance(documento, response.getOutputStream());
			documento.open();
			documento.add(new Paragraph("Lista de contatos", fonteCabecalho));
			documento.add(new Paragraph("  "));
			
			PdfPTable tabela = new PdfPTable(new float[] {3f, 2f, 4f});
			tabela.setWidthPercentage(100);
			
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome", fonteCabecalho));
			col1.setBackgroundColor(BaseColor.DARK_GRAY);
			col1.setHorizontalAlignment(Element.ALIGN_CENTER);
			col1.setPadding(5);
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone", fonteCabecalho));
			col2.setBackgroundColor(BaseColor.DARK_GRAY);
			col2.setHorizontalAlignment(Element.ALIGN_CENTER);
			col2.setPadding(5);
			PdfPCell col3 = new PdfPCell(new Paragraph("Email", fonteCabecalho));
			col3.setBackgroundColor(BaseColor.DARK_GRAY);
			col3.setHorizontalAlignment(Element.ALIGN_CENTER);
			col3.setPadding(5);

			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			
			for (JavaBeans jb : lista) {
				PdfPCell cNome = new PdfPCell(new Paragraph(jb.getNome() != null ? jb.getNome() : "", fonteCorpo));
				PdfPCell cFone = new PdfPCell(new Paragraph(jb.getFone() != null ? jb.getFone() : "", fonteCorpo));
				PdfPCell cEmail = new PdfPCell(new Paragraph(jb.getEmail() != null ? jb.getEmail() : "", fonteCorpo));
				cNome.setPadding(4);
				cFone.setPadding(4);
				cEmail.setPadding(4);
				
				tabela.addCell(cNome);
				tabela.addCell(cFone);
				tabela.addCell(cEmail);
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