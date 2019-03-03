package br.com.infox.telas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.SwingConstants;
import java.awt.Font;

public class TelaLogin extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private JPanel contentPane;
	
	/*public String digest(String password) throws NoSuchAlgorithmException, 
    UnsupportedEncodingException {
 MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
 byte digestMessage[] = algoritmo.digest(password.getBytes("UTF-8"));
 StringBuilder hexPassword = new StringBuilder();
 for (byte aByte : digestMessage) {
    hexPassword.append(String.format("%02X", 0xFF & aByte));
 }
 return hexPassword.toString();
}*/
	
	

	public void logar() {
		String sql = "select * from tbusuario where logins=? and senha=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuario.getText());

			String pegueSenha = String.valueOf(txtSenha.getPassword());
			pst.setString(2, pegueSenha);
			// linha abaixo executa a query
			rs = pst.executeQuery();
			// se estiver certo o login
			if (rs.next()) {
				// a linha abaixo obtem o conteudo do campo perfil da tabela tbusuario
				String perfil = rs.getString(6);
				// System.out.println(perfil);
				TelaPrincipal principal = new TelaPrincipal();
				principal.setVisible(true);
				TelaPrincipal.lblUsuario.setText(rs.getString(2));
				TelaPrincipal.lblUsuario.setForeground(Color.red);
				// faz o tratamento do perfil do usuário
				if (perfil.equals("admin")) {

					TelaPrincipal.MenRel.setEnabled(true);
					TelaPrincipal.MenCadUsu.setEnabled(true);

					principal.setLocationRelativeTo(null);
					this.dispose();
					conexao.close();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválido");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaLogin() {
		setResizable(false);

		conexao = ModuloConexao.conector();
		setTitle("TELA DE ENTRADA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		lblUsurio.setBounds(64, 28, 46, 14);
		contentPane.add(lblUsurio);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(64, 71, 46, 14);
		contentPane.add(lblSenha);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(113, 25, 193, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		txtUsuario.setText("Admin");

		txtSenha = new JPasswordField();
		txtSenha.setBounds(113, 68, 193, 20);
		contentPane.add(txtSenha);
		txtSenha.setText("Admin");
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// chamando o metodo logar
				logar();
			}
		});
		btnLogin.setBounds(217, 99, 89, 23);
		contentPane.add(btnLogin);

		JLabel lblStatus = new JLabel("");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(21, 99, 25, 25);
		contentPane.add(lblStatus);

		if (conexao != null) {
			ImageIcon imagem = new ImageIcon(getClass().getResource("/br/com/infox/icones/databaseConectado.png"));
			Image imag = imagem.getImage().getScaledInstance(lblStatus.getWidth(), lblStatus.getHeight(),
					Image.SCALE_DEFAULT);
			lblStatus.setIcon(new ImageIcon(imag));
			/*
			 * lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource(
			 * "/br/com/infox/icones/databaseConectado.png")));
			 * lblStatus.setText("Conectado");
			 */

		} else {
			ImageIcon imagem = new ImageIcon(getClass().getResource("/br/com/infox/icones/databaseError.png"));
			Image imag = imagem.getImage().getScaledInstance(lblStatus.getWidth(), lblStatus.getHeight(),
					Image.SCALE_DEFAULT);
			lblStatus.setIcon(new ImageIcon(imag));

			/* lblStatus.setText("Não conectado"); */
		}

	}
}
