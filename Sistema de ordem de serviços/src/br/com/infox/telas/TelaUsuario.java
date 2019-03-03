package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class TelaUsuario extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUsoId;
	private JTextField txtUsoNome;
	private JTextField txtUsoLogin;
	private JTextField txtUsoSenha;
	private JTextField txtUsoFone;

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	JComboBox<String> cboUsoPerfil = new JComboBox<String>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaUsuario frame = new TelaUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void consultar() {
		String sql = "select * from tbusuario where iduser=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsoId.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtUsoNome.setText(rs.getString(2));
				txtUsoFone.setText(rs.getString(3));
				txtUsoLogin.setText(rs.getString(4));
				txtUsoSenha.setText(rs.getString(5));
				cboUsoPerfil.setSelectedItem(rs.getString(6));
			} else {
				JOptionPane.showMessageDialog(null, "Id não encontrado ou o ID  é inválido");
				txtUsoNome.setText(null);
				txtUsoFone.setText(null);
				txtUsoLogin.setText(null);
				txtUsoSenha.setText(null);
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void adicionar() {
		String sql = "insert into tbusuario values(?,?,?,?,?,?)";
		
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsoId.getText());
			pst.setString(2, txtUsoNome.getText());
			pst.setString(3, txtUsoFone.getText());
			pst.setString(4, txtUsoLogin.getText());
			pst.setString(5, txtUsoSenha.getText());
            pst.setString(6, cboUsoPerfil.getSelectedItem().toString());
			
		   if (txtUsoId.getText().isEmpty() || txtUsoNome.getText().isEmpty() || txtUsoFone.getText().isEmpty()
				   || txtUsoLogin.getText().isEmpty() || txtUsoSenha.getText().isEmpty()) {
			   JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
		} else {
			int adicionado = pst.executeUpdate();
			if(adicionado > 0) {
				JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
				txtUsoNome.setText(null);
				txtUsoFone.setText(null);
				txtUsoLogin.setText(null);
				txtUsoSenha.setText(null);

				txtUsoId.setText(null);
				}
			
		}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
		
	}
	
	private void alterar() {
		String sql="update tbusuario set usuario=?,fone=?,logins=?,senha=?,perfil=? where iduser=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsoNome.getText());
			pst.setString(2, txtUsoFone.getText());
			pst.setString(3, txtUsoLogin.getText());
			pst.setString(4, txtUsoSenha.getText());
			pst.setString(5, cboUsoPerfil.getSelectedItem().toString());
			pst.setString(6, txtUsoId.getText());
			
			if (txtUsoId.getText().isEmpty() || txtUsoNome.getText().isEmpty() || txtUsoFone.getText().isEmpty()
					   || txtUsoLogin.getText().isEmpty() || txtUsoSenha.getText().isEmpty()) {
				   JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				int adicionado = pst.executeUpdate();
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Dados alterados com sucesso");
					txtUsoNome.setText(null);
					txtUsoFone.setText(null);
					txtUsoLogin.setText(null);
					txtUsoSenha.setText(null);

					txtUsoId.setText(null);
					}
				
			}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
	}
	
	
	public void remover() {
		if (txtUsoId.getText().isEmpty() ) {
			   JOptionPane.showMessageDialog(null, "Preencha o campo ID");
		}else {
		
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o "+txtUsoNome.getText());
		if (confirma == JOptionPane.YES_OPTION) {
			String sql = "Delete from tbusuario where iduser=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, txtUsoId.getText());				
				int apagado = pst.executeUpdate();
				if(apagado>0) {
					JOptionPane.showMessageDialog(null, "suário removido com sucesso");
					txtUsoNome.setText(null);
					txtUsoFone.setText(null);
					txtUsoLogin.setText(null);
					txtUsoSenha.setText(null);

					txtUsoId.setText(null);
					
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		} 
		
		}
	}
	
	
	

	public TelaUsuario() {
		conexao = ModuloConexao.conector();
		cboUsoPerfil.setModel(new DefaultComboBoxModel<String>(new String[] {"admin", "user"}));

		
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setSize(new Dimension(640, 480));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setTitle("Usu\u00E1rios");
		setBounds(100, 100, 640, 480);

		JLabel lblId = new JLabel("*ID");

		JLabel lblNomer = new JLabel("*Nome");

		JLabel lblLogin = new JLabel("*Login");

		JLabel lblSenha = new JLabel("*Senha");

		JLabel lblPerfil = new JLabel("*Perfil");

		txtUsoId = new JTextField();
		txtUsoId.setColumns(10);

		txtUsoNome = new JTextField();
		txtUsoNome.setColumns(10);

		txtUsoLogin = new JTextField();
		txtUsoLogin.setColumns(10);

		txtUsoSenha = new JTextField();
		txtUsoSenha.setColumns(10);

		JLabel lblFone = new JLabel("*Fone");

		txtUsoFone = new JTextField();
		txtUsoFone.setColumns(10);

		JButton btnUsuCreate = new JButton("");
		btnUsuCreate.setContentAreaFilled(false);
		btnUsuCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				adicionar();
			}
		});
		btnUsuCreate.setToolTipText("Adicionar");
		btnUsuCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuCreate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/add1.png")));
		btnUsuCreate.setMaximumSize(new Dimension(140, 140));
		btnUsuCreate.setPreferredSize(new Dimension(64, 64));
		btnUsuCreate.setActionCommand("");

		JButton btnUsoRead = new JButton("");
		btnUsoRead.setContentAreaFilled(false);
		btnUsoRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultar();
			}
		});
		btnUsoRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsoRead.setToolTipText("Consultar");
		btnUsoRead.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/pesquisar1.png")));
		btnUsoRead.setMaximumSize(new Dimension(64, 64));
		btnUsoRead.setPreferredSize(new Dimension(64, 64));
		btnUsoRead.setActionCommand("");

		JButton btnUsoUpdate = new JButton("");
		btnUsoUpdate.setContentAreaFilled(false);
		btnUsoUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterar();
			}
		});
		btnUsoUpdate.setToolTipText("Alterar");
		btnUsoUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsoUpdate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/update1.png")));
		btnUsoUpdate.setMaximumSize(new Dimension(64, 64));
		btnUsoUpdate.setPreferredSize(new Dimension(64, 64));
		btnUsoUpdate.setActionCommand("");

		JButton btnUsoDelete = new JButton("");
		btnUsoDelete.setContentAreaFilled(false);
		btnUsoDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnUsoDelete.setToolTipText("Excluir");
		btnUsoDelete.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/delete1.png")));
		btnUsoDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsoDelete.setMaximumSize(new Dimension(140, 140));
		btnUsoDelete.setPreferredSize(new Dimension(64, 64));
		btnUsoDelete.setActionCommand("");
		
		JLabel lblcamposObrigatrios = new JLabel("*Campos obrigat\u00F3rios");

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(130)
					.addComponent(btnUsuCreate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(220)
					.addComponent(btnUsoDelete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(146, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(46)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblFone, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(txtUsoFone, 181, 181, 181))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblSenha)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(txtUsoSenha, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED, 52, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(btnUsoRead, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(29)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblPerfil)
									.addGap(18)
									.addComponent(cboUsoPerfil, 0, 214, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblLogin)
									.addGap(18)
									.addComponent(txtUsoLogin, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
								.addComponent(btnUsoUpdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNomer)
								.addComponent(lblId))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(txtUsoNome, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(txtUsoId, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
									.addComponent(lblcamposObrigatrios)
									.addGap(81)))))
					.addGap(43))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(93)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblId)
						.addComponent(txtUsoId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblcamposObrigatrios))
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNomer)
						.addComponent(txtUsoNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFone)
								.addComponent(txtUsoFone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblLogin)
							.addComponent(txtUsoLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSenha)
						.addComponent(lblPerfil)
						.addComponent(txtUsoSenha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cboUsoPerfil, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnUsuCreate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUsoDelete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUsoUpdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUsoRead, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(92, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);

	}
}
