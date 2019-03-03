package br.com.infox.telas;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JSeparator;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ListSelectionModel;
import java.awt.ComponentOrientation;
import javax.swing.DropMode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class TelaCliente extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	private JTextField txtCliNome;
	private JTextField txtCliEndereco;
	private JTextField txtCliFone;
	private JTextField txtCliEmail;
	private JTextField txtCliPesquisar;
	private JTable tblClientes;
	private JTextField txtCliId;
	private JButton btnCliAdd = new JButton("");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCliente frame = new TelaCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void adicionar() {
		String sql = "insert into TBclientes values(?,?,?,?)";

		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliNome.getText());
			pst.setString(2, txtCliEndereco.getText());
			pst.setString(3, txtCliFone.getText());
			pst.setString(4, txtCliEmail.getText());

			if (txtCliNome.getText().isEmpty() || txtCliFone.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso");
					txtCliNome.setText(null);
					txtCliEndereco.setText(null);
					txtCliFone.setText(null);
					txtCliEmail.setText(null);

				}

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public void pesquisarCliente() {
		String sql = "select idcli as [ID], nomecli AS [NOME], enderecocli AS [ENDEREÇO],fonecli AS [FONE], emailcli AS [EMAIL] from tbclientes where nomecli like ?";
        try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliPesquisar.getText() + "%");
			rs = pst.executeQuery();
			//usando a bb importada
			
			//protocolo, matricula,nome,curso,situacaoprt,data inclusao,formou em
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
        	
        	
        	
		} catch (Exception e) {
		JOptionPane.showMessageDialog(null, e);	
		}
	}
	
	public void setar_campos() {
		int setar = tblClientes.getSelectedRow();
		
		try {
			txtCliId.setText(tblClientes.getModel().getValueAt(setar,0).toString());
			txtCliNome.setText(tblClientes.getModel().getValueAt(setar,1).toString());
			txtCliEndereco.setText(tblClientes.getModel().getValueAt(setar,2).toString());
			txtCliFone.setText(tblClientes.getModel().getValueAt(setar,3).toString());
			txtCliEmail.setText(tblClientes.getModel().getValueAt(setar,4).toString());
			btnCliAdd.setEnabled(false);
		} catch (Exception e) {
			
		}
		
		
	}
	
//Metodo para alterar os dados dos clientes
	private void alterarClientes() {
		String sql="update tbclientes set nomecli=?,enderecocli=?,fonecli=?,emailcli=? where idcli=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliNome.getText());
			pst.setString(2, txtCliEndereco.getText());
			pst.setString(3, txtCliFone.getText());
			pst.setString(4, txtCliEmail.getText());
		    pst.setString(5, txtCliId.getText());
			
			if (txtCliNome.getText().isEmpty() || txtCliFone.getText().isEmpty()) {
				   JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				int adicionado = pst.executeUpdate();
				if(adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso");
					txtCliNome.setText(null);
					txtCliEndereco.setText(null);
					txtCliFone.setText(null);
					txtCliEmail.setText(null);
					txtCliId.setText(null);
					btnCliAdd.setEnabled(true);
					}
				
			}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
	}
	
	public void remover() {
	
		
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o Cliente: "+txtCliNome.getText());
		if (confirma == JOptionPane.YES_OPTION) {
			String sql = "Delete from tbclientes where idcli=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, txtCliId.getText());				
				int apagado = pst.executeUpdate();
				if(apagado>0) {
					JOptionPane.showMessageDialog(null, "Cliente removido com sucesso");
					txtCliNome.setText(null);
					txtCliEndereco.setText(null);
					txtCliFone.setText(null);
					txtCliEmail.setText(null);
					txtCliId.setText(null);
					btnCliAdd.setEnabled(true);
					pesquisarCliente();
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		} 
		
		}
	
	
	
	public TelaCliente() {
		conexao = ModuloConexao.conector();
		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		getContentPane().setName("");
		setBounds(100, 100, 450, 300);
		setSize(new Dimension(640, 480));
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setTitle("CLIENTES");
		setBounds(100, 100, 640, 480);

		JLabel label_6 = new JLabel("*Campos obrigat\u00F3rios");

		
		btnCliAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliAdd.setContentAreaFilled(false);
		btnCliAdd.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/add menor.png")));
		btnCliAdd.setDisabledSelectedIcon(null);
		btnCliAdd.setDisabledIcon(null);
		btnCliAdd.setSelectedIcon(null);
		btnCliAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnCliAdd.setBackground(new Color(220, 220, 220));
		btnCliAdd.setToolTipText("Adicionar");
		btnCliAdd.setMaximumSize(new Dimension(64, 64));
		btnCliAdd.setMinimumSize(new Dimension(64, 64));
		btnCliAdd.setPreferredSize(new Dimension(64, 64));

		JButton btnCliAlterar = new JButton("");
		btnCliAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterarClientes();
			}
		});
		btnCliAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliAlterar.setContentAreaFilled(false);
		btnCliAlterar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/update menor.png")));
		btnCliAlterar.setToolTipText("Editar");
		btnCliAlterar.setPreferredSize(new Dimension(64, 64));
		btnCliAlterar.setMinimumSize(new Dimension(64, 64));
		btnCliAlterar.setMaximumSize(new Dimension(64, 64));
		btnCliAlterar.setBackground(new Color(255, 255, 255));

		JButton btnCliRemover = new JButton("");
		btnCliRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				remover();
			}
		});
		btnCliRemover.setContentAreaFilled(false);
		btnCliRemover.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliRemover.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/deletemenor.png")));
		btnCliRemover.setToolTipText("Excluir");
		btnCliRemover.setPreferredSize(new Dimension(64, 64));
		btnCliRemover.setMinimumSize(new Dimension(64, 64));
		btnCliRemover.setMaximumSize(new Dimension(64, 64));
		btnCliRemover.setBackground(new Color(255, 255, 255));

		JLabel lblNewLabel = new JLabel("*Nome");

		JLabel lblEndereo = new JLabel("Endere\u00E7o");

		JLabel lbltelefone = new JLabel("*Telefone");

		JLabel lblEmail = new JLabel("Email");

		txtCliNome = new JTextField();
		txtCliNome.setColumns(10);

		txtCliEndereco = new JTextField();
		txtCliEndereco.setColumns(10);

		txtCliFone = new JTextField();
		txtCliFone.setColumns(10);

		txtCliEmail = new JTextField();
		txtCliEmail.setColumns(10);

		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override //enquanto estiver digitando faz em tempo real
			public void keyReleased(KeyEvent arg0) {
				pesquisarCliente();
			}
		});
		txtCliPesquisar.setColumns(10);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pesquisarCliente();
			}
		});
		btnNewButton.setToolTipText("Pesquisar clientes");
		btnNewButton
				.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/Procura usu\u00E1rio.png")));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setMaximumSize(new Dimension(48, 48));
		btnNewButton.setMinimumSize(new Dimension(48, 48));
		btnNewButton.setPreferredSize(new Dimension(48, 48));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setEnabled(false);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		
		JLabel lblIdCliente = new JLabel("Id Cliente");
		
		txtCliId = new JTextField();
		txtCliId.setHorizontalAlignment(SwingConstants.CENTER);
		txtCliId.setDisabledTextColor(Color.BLACK);
		txtCliId.setSelectedTextColor(Color.BLACK);
		txtCliId.setForeground(Color.BLACK);
		txtCliId.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtCliId.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		txtCliId.setSelectionColor(Color.BLACK);
		txtCliId.setEnabled(false);
		txtCliId.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 576, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtCliPesquisar, GroupLayout.PREFERRED_SIZE, 407, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(38))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(3)
								.addComponent(lblEndereo)
								.addGap(10)
								.addComponent(txtCliEndereco, GroupLayout.PREFERRED_SIZE, 502, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(2)
								.addComponent(lblNewLabel)
								.addGap(23)
								.addComponent(txtCliNome))
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblEmail)
									.addGap(18)
									.addComponent(txtCliEmail, GroupLayout.PREFERRED_SIZE, 502, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lbltelefone)
									.addGap(10)
									.addComponent(txtCliFone, GroupLayout.PREFERRED_SIZE, 502, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnCliAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(37)
									.addComponent(btnCliAlterar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(44)
									.addComponent(btnCliRemover, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(46)
									.addComponent(label_6))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblIdCliente)
							.addGap(10)
							.addComponent(txtCliId, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addGap(459)))
					.addContainerGap(35, Short.MAX_VALUE))
				.addComponent(separator, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(14)
							.addComponent(txtCliPesquisar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIdCliente)
						.addComponent(txtCliId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(9)
							.addComponent(lblNewLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtCliNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lblEndereo))
						.addComponent(txtCliEndereco, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(lbltelefone))
						.addComponent(txtCliFone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtCliEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEmail))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(label_6)
						.addComponent(btnCliAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCliAlterar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCliRemover, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19))
		);

		tblClientes = new JTable();
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// evento usado para setar os campos da tabela.
				setar_campos();
			}
		});
		tblClientes.setCellSelectionEnabled(true);
		tblClientes.setAutoscrolls(false);
		tblClientes.setVerifyInputWhenFocusTarget(false);
		tblClientes.setUpdateSelectionOnSort(false);
		tblClientes.setSurrendersFocusOnKeystroke(true);
		tblClientes.setShowVerticalLines(false);
		tblClientes.setShowHorizontalLines(false);
		tblClientes.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tblClientes.setOpaque(false);
		tblClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		tblClientes.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tblClientes.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"ID", "Nome", "Endere\u00E7o", "Telefone", "E-mail"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				true, true, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblClientes.getColumnModel().getColumn(1).setResizable(false);
		tblClientes.getColumnModel().getColumn(2).setResizable(false);
		tblClientes.getColumnModel().getColumn(3).setResizable(false);
		tblClientes.getColumnModel().getColumn(4).setResizable(false);
		scrollPane.setViewportView(tblClientes);
		getContentPane().setLayout(groupLayout);

	}
}
