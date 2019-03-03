package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

import br.com.infox.dal.ModuloConexao;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.HashMap;

import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaOS extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOs;
	private JTextField txtData;
	private ButtonGroup grupo1 = new ButtonGroup();
	private JTextField txtCliPesquisar;
	private JTable tblClientes;
	private JTextField txtCliId;
	private JTextField txtOsEquip;
	private JTextField txtOsDef;
	private JTextField txtOsServ;
	private JTextField txtOsTec;
	private JTextField txtOsValor;
	private JRadioButton rbtOrc = new JRadioButton("Or\u00E7amento");
	private JComboBox cboOsSit = new JComboBox();
	private JRadioButton rbtOs = new JRadioButton("Ordem de Servi\u00E7o");
	private JButton btnOsAdicionar = new JButton("");

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private String tipo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaOS frame = new TelaOS();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void pesquisar() {
		String sql = "select idcli as Id, nomecli as Nome, fonecli as Fone from tbclientes where nomecli like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliPesquisar.getText() + "%");
			rs = pst.executeQuery();
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public void setarCampos() {
		int setar = tblClientes.getSelectedRow();
		try {
			txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);

		}
	}

	public void emitir_os() {
		String sql = "\r\n"
				+ "INSERT INTO tbos(tipo,situacao,equipamento,defeito,servico,tecnico,valor,idcli) values(?,?,?,?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, tipo);
			pst.setString(2, cboOsSit.getSelectedItem().toString());
			pst.setString(3, txtOsEquip.getText());
			pst.setString(4, txtOsDef.getText());
			pst.setString(5, txtOsServ.getText());
			pst.setString(6, txtOsTec.getText());
			pst.setString(7, txtOsValor.getText().replace(",", "."));
			pst.setString(8, txtCliId.getText());

			if (txtCliId.getText().isEmpty() || txtOsEquip.getText().isEmpty() || txtOsDef.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS emitida com sucesso");
					txtCliId.setText(null);
					txtOsValor.setText(null);
					txtOsTec.setText(null);
					txtOsServ.setText(null);
					txtOsDef.setText(null);
					txtOsEquip.setText(null);
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void pesquisar_os() {
		String num_os = JOptionPane.showInputDialog("Numero da OS");
		String sql = "Select * from tbos where os = " + num_os;
		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				txtOs.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				// setando radio button
				String rbtTipo = rs.getString(3);
				if (rbtTipo.equals("Ordem de serviço")) {
					rbtOs.setSelected(true);
					tipo = "Ordem de serviço";
				} else {
					rbtOrc.setSelected(true);
					tipo = "Orçamento";
				}
				cboOsSit.setSelectedItem(rs.getString(4));
				txtOsEquip.setText(rs.getString(5));
				txtOsDef.setText(rs.getString(6));
				txtOsServ.setText(rs.getString(7));
				txtOsTec.setText(rs.getString(8));
				txtOsValor.setText(rs.getString(9));
				txtCliId.setText(rs.getString(10));
				// editando problemas
				btnOsAdicionar.setEnabled(false);
				txtCliPesquisar.setEnabled(false);
				tblClientes.setVisible(false);
				txtCliId.setEnabled(false);

			} else {
				JOptionPane.showMessageDialog(null, "OS não cadastrada");
			}
		} catch (com.microsoft.sqlserver.jdbc.SQLServerException e) {
			JOptionPane.showMessageDialog(null, "Os inválida");
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
	}

	public void alterarOS() {
		String sql = "update tbos set tipo=?,situacao=?,equipamento=?,defeito=?,servico=?,tecnico=?,valor=? where os=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, tipo);
			pst.setString(2, cboOsSit.getSelectedItem().toString());
			pst.setString(3, txtOsEquip.getText());
			pst.setString(4, txtOsDef.getText());
			pst.setString(5, txtOsServ.getText());
			pst.setString(6, txtOsTec.getText());
			pst.setString(7, txtOsValor.getText().replace(",", "."));
			pst.setString(8, txtOs.getText());

			if (txtCliId.getText().isEmpty() || txtOsEquip.getText().isEmpty() || txtOsDef.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
			} else {
				int adicionado = pst.executeUpdate();
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "OS alterada com sucesso");
					txtOs.setText(null);
					txtData.setText(null);
					txtCliId.setText(null);
					txtOsValor.setText(null);
					txtOsTec.setText(null);
					txtOsServ.setText(null);
					txtOsDef.setText(null);
					txtOsEquip.setText(null);
					// habilitar os objetos
					btnOsAdicionar.setEnabled(true);
					txtCliPesquisar.setVisible(true);
					tblClientes.setVisible(true);
					txtCliId.setEnabled(true);
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public void excluir_os() {

		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir a OS: " + txtOs.getText());
		if (confirma == JOptionPane.YES_OPTION) {
			String sql = "Delete from tbos where os=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, txtOs.getText());
				int apagado = pst.executeUpdate();
				if (apagado > 0) {
					JOptionPane.showMessageDialog(null, "OS removido com sucesso");
					txtOs.setText(null);
					txtData.setText(null);
					txtCliId.setText(null);
					txtOsValor.setText(null);
					txtOsTec.setText(null);
					txtOsServ.setText(null);
					txtOsDef.setText(null);
					txtOsEquip.setText(null);
					btnOsAdicionar.setEnabled(true);
					txtCliPesquisar.setVisible(true);
					tblClientes.setVisible(true);
					txtCliId.setEnabled(true);

				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}

	}

	@SuppressWarnings("unused")
	private void imprimir_Os() {
		// IMPRIMINDO UMA OS
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão dessa OS?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// imprimindo relatório com jasper
			
			try {
				// Usando a classe HASHMAP PARA CRIAR UM FILTRO
				HashMap<String, Object> filtro = new HashMap<String, Object>();
				filtro.put("os", Integer.parseInt(txtOs.getText()));
				InputStream is = getClass().getResourceAsStream("C:/Users/JOSEMAR JUNIOR/Desktop/Sistema/OS.jasper");
				JasperPrint print = JasperFillManager.fillReport("C:/Users/JOSEMAR JUNIOR/Desktop/Sistema/OS.jasper", filtro, conexao);
				// linha abaixo exibe o relatório através da classe JasperViewer
				JasperViewer.viewReport(print, false);

				// Exportando para pdf

//				 JasperViewer js = new JasperViewer(print); js.setVisible(true);
//				 JasperExportManager.exportReportToPdfFile(print,
//				 "C://Users//JOSEMAR JUNIOR//Documents//Projects//Relorio.pdf");
//				 

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	public TelaOS() {
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
				// ao abrir o form marcar o rdb orçamento
				rbtOrc.setSelected(true);
				tipo = "Orçamento";
			}
		});
		conexao = ModuloConexao.conector();

		getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		getContentPane().setName("");

		JPanel txtCliPesquisar1 = new JPanel();
		txtCliPesquisar1.setBounds(10, 11, 287, 113);
		txtCliPesquisar1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		JLabel lblNewLabel_2 = new JLabel("Situa\u00E7\u00E3o");
		lblNewLabel_2.setBounds(10, 145, 53, 14);

		cboOsSit.setBounds(67, 142, 230, 20);
		cboOsSit.setModel(new DefaultComboBoxModel(
				new String[] { "NA BANCADA", "ENTREGA OK", "OR\u00C7AMENTO REPROVADO", "AGUARDANDO APROVA\u00C7\u00C3O",
						"AGUARDANDO PE\u00C7AS", "ABANDONADO PELO CLIENTE", "RETORNOU" }));

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(315, 11, 309, 151);
		panel_1.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// chamando o metodo pesquisar cliente
				pesquisar();

			}
		});
		txtCliPesquisar.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/search pequeno.png")));

		JScrollPane scrollPane = new JScrollPane();

		JLabel label = new JLabel("*Id");

		txtCliId = new JTextField();
		txtCliId.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup()
						.addComponent(txtCliPesquisar, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addGap(6).addComponent(lblNewLabel_3).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtCliId, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(label).addComponent(
								txtCliId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
						.addComponent(txtCliPesquisar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)));

		tblClientes = new JTable();
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setarCampos();
			}
		});
		tblClientes.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, { null, null, null },
				{ null, null, null }, { null, null, null }, }, new String[] { "Id", "Nome", "Fone" }) {
			boolean[] columnEditables = new boolean[] { false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(tblClientes);
		panel_1.setLayout(gl_panel_1);
		txtCliPesquisar1.setLayout(null);

		JLabel lblNewLabel = new JLabel("N\u00BA OS");
		lblNewLabel.setBounds(10, 11, 46, 14);
		txtCliPesquisar1.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Data");
		lblNewLabel_1.setBounds(91, 11, 46, 14);
		txtCliPesquisar1.add(lblNewLabel_1);

		txtOs = new JTextField();
		txtOs.setEditable(false);
		txtOs.setBounds(10, 30, 54, 20);
		txtCliPesquisar1.add(txtOs);
		txtOs.setColumns(10);

		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(91, 30, 191, 20);
		txtCliPesquisar1.add(txtData);
		txtData.setColumns(10);

		rbtOrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// atribuindo um texto a variavel tipo se selecionado
				tipo = "Orçamento";
			}
		});
		rbtOrc.setBounds(10, 70, 109, 23);
		txtCliPesquisar1.add(rbtOrc);

		rbtOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// atribuindo um texto a variavel tipo se selecionado
				tipo = "Ordem de serviço";
			}
		});
		rbtOs.setBounds(138, 70, 144, 23);
		txtCliPesquisar1.add(rbtOs);
		setBounds(100, 100, 450, 300);
		setSize(new Dimension(640, 480));
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setTitle("OS");
		setBounds(100, 100, 640, 480);
		grupo1.add(rbtOrc);
		grupo1.add(rbtOs);
		getContentPane().setLayout(null);
		getContentPane().add(lblNewLabel_2);
		getContentPane().add(cboOsSit);
		getContentPane().add(txtCliPesquisar1);
		getContentPane().add(panel_1);

		JLabel lblNewLabel_4 = new JLabel("*Equipamento");
		lblNewLabel_4.setBounds(10, 207, 75, 14);
		getContentPane().add(lblNewLabel_4);

		JLabel lbldefeito = new JLabel("*Defeito");
		lbldefeito.setBounds(39, 232, 46, 14);
		getContentPane().add(lbldefeito);

		JLabel lblServio = new JLabel("Servi\u00E7o");
		lblServio.setBounds(39, 263, 46, 14);
		getContentPane().add(lblServio);

		JLabel lblTcnico = new JLabel("T\u00E9cnico");
		lblTcnico.setBounds(39, 288, 46, 14);
		getContentPane().add(lblTcnico);

		txtOsEquip = new JTextField();
		txtOsEquip.setBounds(89, 204, 513, 20);
		getContentPane().add(txtOsEquip);
		txtOsEquip.setColumns(10);

		txtOsDef = new JTextField();
		txtOsDef.setColumns(10);
		txtOsDef.setBounds(89, 229, 513, 20);
		getContentPane().add(txtOsDef);

		txtOsServ = new JTextField();
		txtOsServ.setColumns(10);
		txtOsServ.setBounds(89, 260, 513, 20);
		getContentPane().add(txtOsServ);

		txtOsTec = new JTextField();
		txtOsTec.setColumns(10);
		txtOsTec.setBounds(89, 285, 208, 20);
		getContentPane().add(txtOsTec);

		JLabel lblValorTotal = new JLabel("Valor Total");
		lblValorTotal.setBounds(367, 288, 62, 14);
		getContentPane().add(lblValorTotal);

		txtOsValor = new JTextField();
		txtOsValor.setText("0");
		txtOsValor.setColumns(10);
		txtOsValor.setBounds(439, 285, 163, 20);
		getContentPane().add(txtOsValor);

		btnOsAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				emitir_os();
			}
		});
		btnOsAdicionar.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/add1.png")));
		btnOsAdicionar.setToolTipText("Adicionar");
		btnOsAdicionar.setPreferredSize(new Dimension(64, 64));
		btnOsAdicionar.setMaximumSize(new Dimension(140, 140));
		btnOsAdicionar.setContentAreaFilled(false);
		btnOsAdicionar.setActionCommand("");
		btnOsAdicionar.setBounds(89, 337, 64, 64);
		getContentPane().add(btnOsAdicionar);

		JButton btnOsExcluir = new JButton("");
		btnOsExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir_os();
			}
		});
		btnOsExcluir.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/deletemenor.png")));
		btnOsExcluir.setToolTipText("Excluir");
		btnOsExcluir.setPreferredSize(new Dimension(64, 64));
		btnOsExcluir.setMaximumSize(new Dimension(140, 140));
		btnOsExcluir.setContentAreaFilled(false);
		btnOsExcluir.setActionCommand("");
		btnOsExcluir.setBounds(373, 337, 64, 64);
		getContentPane().add(btnOsExcluir);

		JButton btnOsPesquisar = new JButton("");
		btnOsPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar_os();
			}
		});
		btnOsPesquisar.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/pesquisar1.png")));
		btnOsPesquisar.setToolTipText("Consultar");
		btnOsPesquisar.setPreferredSize(new Dimension(64, 64));
		btnOsPesquisar.setMaximumSize(new Dimension(64, 64));
		btnOsPesquisar.setContentAreaFilled(false);
		btnOsPesquisar.setActionCommand("");
		btnOsPesquisar.setBounds(185, 337, 64, 64);
		getContentPane().add(btnOsPesquisar);

		JButton btnOsAlterar = new JButton("");
		btnOsAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarOS();
			}
		});
		btnOsAlterar.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/update menor.png")));
		btnOsAlterar.setToolTipText("Alterar");
		btnOsAlterar.setPreferredSize(new Dimension(64, 64));
		btnOsAlterar.setMaximumSize(new Dimension(64, 64));
		btnOsAlterar.setContentAreaFilled(false);
		btnOsAlterar.setActionCommand("");
		btnOsAlterar.setBounds(278, 337, 64, 64);
		getContentPane().add(btnOsAlterar);

		JButton btnOsImprimir = new JButton("");
		btnOsImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				imprimir_Os();
			}
		});
		btnOsImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOsImprimir.setToolTipText("Imprimir OS");
		btnOsImprimir.setContentAreaFilled(false);
		btnOsImprimir.setIcon(new ImageIcon(TelaOS.class.getResource("/br/com/infox/icones/Impressora.png")));
		btnOsImprimir.setSize(new Dimension(64, 64));
		btnOsImprimir.setPreferredSize(new Dimension(64, 64));
		btnOsImprimir.setMaximumSize(new Dimension(64, 64));
		btnOsImprimir.setMinimumSize(new Dimension(64, 64));
		btnOsImprimir.setBounds(469, 337, 64, 64);
		getContentPane().add(btnOsImprimir);
	}
}
