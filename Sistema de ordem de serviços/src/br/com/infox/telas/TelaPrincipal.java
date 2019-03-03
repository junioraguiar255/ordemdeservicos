package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javax.swing.JDesktopPane;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.text.DateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.awt.event.InputEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import br.com.infox.dal.ModuloConexao;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.sql.*;

public class TelaPrincipal extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lblData = new JLabel("Data");
	private JMenuItem MenCadCli;
	public static JMenuItem MenCadUsu = new JMenuItem("Usu\u00E1rios");
	public static JMenu MenRel = new JMenu("Relat\u00F3rio");
	public static JLabel lblUsuario = new JLabel("Usu\u00E1rio");
	Connection conexao = null;

	private void formWindowActivated(java.awt.event.WindowEvent evt) {
		// as linhas abaixo substituem a label data do sistema ao iniciar o form
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
		lblData.setText(formatador.format(data));
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaPrincipal() {
		conexao = ModuloConexao.conector();

		getContentPane().setBackground(new Color(255, 255, 255));
		getContentPane().setForeground(Color.BLUE);
		setResizable(false);
		setTitle("SISTEMA - CONTROLE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 545);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/infox/icones/logo.png")));
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblData.setFont(new Font("Tahoma", Font.BOLD, 18));

		JDesktopPane Desktop = new JDesktopPane();
		Desktop.setBounds(new Rectangle(0, 0, 640, 480));
		Desktop.setBackground(SystemColor.activeCaption);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addComponent(Desktop, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE).addGap(14)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(64).addComponent(lblUsuario,
								GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(64).addComponent(lblData,
								GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblLogo))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addGap(96)
						.addComponent(lblUsuario, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE).addGap(40)
						.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE).addGap(11)
						.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE))
						.addComponent(Desktop, GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
				.addContainerGap()));
		getContentPane().setLayout(groupLayout);

		JMenuBar Menu = new JMenuBar();
		setJMenuBar(Menu);

		JMenu MenCad = new JMenu("Cadastros");
		Menu.add(MenCad);

		MenCadCli = new JMenuItem("Cliente");
		MenCadCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente usuario = new TelaCliente();
				usuario.setVisible(true);
				Dimension desktopSize = Desktop.getSize();
				Dimension jInternalFrameSize = usuario.getSize();
				usuario.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
						(desktopSize.height - jInternalFrameSize.height) / 2);
				Desktop.add(usuario);
			}
		});
		MenCadCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.CTRL_MASK));
		MenCad.add(MenCadCli);

		JMenuItem MenCadOS = new JMenuItem("OS");
		MenCadOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaOS usuario2 = new TelaOS();
				usuario2.setVisible(true);
				Dimension desktopSize = Desktop.getSize();
				Dimension jInternalFrameSize = usuario2.getSize();
				usuario2.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
						(desktopSize.height - jInternalFrameSize.height) / 2);
				Desktop.add(usuario2);
			}
		});
		MenCad.add(MenCadOS);
		MenCadUsu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaUsuario usuario = new TelaUsuario();
				usuario.setVisible(true);
				Dimension desktopSize = Desktop.getSize();
				Dimension jInternalFrameSize = usuario.getSize();
				usuario.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
						(desktopSize.height - jInternalFrameSize.height) / 2);
				Desktop.add(usuario);

			}
		});
		MenCadUsu.setEnabled(false);

		MenCad.add(MenCadUsu);
		MenRel.setEnabled(false);

		Menu.add(MenRel);

		JMenuItem MenRelSer = new JMenuItem("Servi\u00E7os");
		MenRelSer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// METODO PARA GERAR RELATORIO DE SERVIÇOS
				int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja emitir relatório?",
						"Atenção", JOptionPane.YES_NO_OPTION);
				if (confirma == JOptionPane.YES_OPTION) {
					// imprimindo relatório com jasper
					try {
                        
						
						InputStream is = getClass().getResourceAsStream("/br/com//infox/jasper/servico.jasper");
						JasperPrint print = JasperFillManager.fillReport(
								is, null,
								conexao);
						// linha abaixo exibe o relatório através da classe JasperViewer
						JasperViewer.viewReport(print, false);

						// Exportando para pdf

//						 JasperViewer js = new JasperViewer(print); js.setVisible(true);
//						 JasperExportManager.exportReportToPdfFile(print,
//						 "C://Users//JOSEMAR JUNIOR//Documents//Projects//Relorio.pdf");
//						 

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}
			}
		});
		MenRel.add(MenRelSer);

		JMenuItem menRelCli = new JMenuItem("Clientes");
		menRelCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// METODO PARA GERAR RELATORIO DE CLIENTES
				int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja emitir relatório?",
						"Atenção", JOptionPane.YES_NO_OPTION);
				if (confirma == JOptionPane.YES_OPTION) {
					// imprimindo relatório com jasper
					try {
						InputStream is = getClass().getResourceAsStream("/br/com//infox/jasper/RelClientes.jasper");
						JasperPrint print = JasperFillManager.fillReport(
								is, null,
								conexao);
						// linha abaixo exibe o relatório através da classe JasperViewer
						JasperViewer.viewReport(print, false);

						// Exportando para pdf
						/*
						 * JasperViewer js = new JasperViewer(print); js.setVisible(true);
						 * JasperExportManager.exportReportToPdfFile(print,
						 * "C://Users//JOSEMAR JUNIOR//Documents//Projects//Relorio.pdf");
						 */

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}
			}
		});
		MenRel.add(menRelCli);

		JMenu MenAju = new JMenu("Ajuda");
		Menu.add(MenAju);

		JMenuItem MenAjuSob = new JMenuItem("Sobre");
		MenAjuSob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Chamando a tela Sobre
				TelaSobre tela = new TelaSobre();
				tela.setVisible(true);
			}
		});
		MenAju.add(MenAjuSob);

		JMenu MenOpc = new JMenu("Op\u00E7\u00F5es");
		Menu.add(MenOpc);

		JMenuItem MenOpcSai = new JMenuItem("Sair");
		MenOpcSai.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		MenOpcSai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// exibe cx de dialogo com s ou n
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção",
						JOptionPane.YES_NO_OPTION);
				if (sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		MenOpc.add(MenOpcSai);
		formWindowActivated(null);
	}
}
