package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {

	// parametros de conexão

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "Ciss.123";

	// metodos conexao

	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	
	
	
	public void inserirContato(JavaBeans contato) {

		String create = "INSERT INTO contatos (nome,fone,email) VALUES (?,?,?)";
		try {
			Connection con = conectar();
			// preparar a query para execução no banco de dados

			PreparedStatement pst = con.prepareStatement(create);

			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.executeUpdate();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// metodo para verificar se o usuario existe no banco de dados na tabela usuarios
	
	public boolean logarUsuario(Usuario usuario) {
		String read = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
		System.out.println("Logando usuario na DAO...");
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, usuario.getEmail());
			pst.setString(2, usuario.getSenha());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
	
	public void atualizarContato(JavaBeans contato) {
		
	
		String update = "UPDATE contatos SET nome = ?, fone = ?, email = ?  WHERE idcon = ?";
		try {
			Connection con = conectar();
			// preparar a query para execução no banco de dados

			PreparedStatement pst = con.prepareStatement(update);

			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4,contato.getIdcon());
			pst.executeUpdate();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}


	public ArrayList<JavaBeans> listarContatos() {
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "SELECT * FROM contatos order by idcon";

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);

				contatos.add(new JavaBeans(idcon, nome, fone, email));

			}
			con.close();
			return contatos;

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public void listarContato(JavaBeans contato) {

		String read = "SELECT * FROM contatos where idcon = ?";

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));

			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public void deletarContato(JavaBeans contato) {
		
		String delete = "DELETE FROM contatos where idcon = ?";
		
		System.out.println("Agora na DAO"+contato.getIdcon());
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			pst.close();
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
	}

	public void testeConexao() {

		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}
	}

	public void cadastrarUsuario(Usuario usuario) {
		
		System.out.println("Cadastrando usuario na DAO...");
		System.out.println("Nome: " + usuario.getNome());

		String create = "INSERT INTO usuarios (nome,email,senha) VALUES (?,?,?)";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, usuario.getNome());
			pst.setString(2, usuario.getEmail());
			pst.setString(3, usuario.getSenha());
			pst.executeUpdate();
			pst.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}