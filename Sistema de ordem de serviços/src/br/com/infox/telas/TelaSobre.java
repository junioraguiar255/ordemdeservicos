package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;

public class TelaSobre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaSobre frame = new TelaSobre();
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
	public TelaSobre() {
		setResizable(false);
		setTitle("Sobre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 373, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sistema de Controle de Ordem de Servi\u00E7os");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(70, 31, 246, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblDesenvolvidoPorJosemar = new JLabel("Desenvolvido por Josemar Aguiar Junior");
		lblDesenvolvidoPorJosemar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDesenvolvidoPorJosemar.setBounds(71, 310, 245, 14);
		contentPane.add(lblDesenvolvidoPorJosemar);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\JOSEMAR JUNIOR\\Desktop\\logo.png"));
		lblNewLabel_1.setBounds(48, 56, 346, 243);
		contentPane.add(lblNewLabel_1);
	}

}
