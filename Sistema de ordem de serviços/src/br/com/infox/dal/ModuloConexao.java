package br.com.infox.dal;

import java.sql.*;
public class ModuloConexao {
  

	
	public static Connection conector() {
		String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=BancoTeste";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
					
					Connection conn = DriverManager.getConnection(connectionUrl,"Josemar","123456");
					return conn;
		}catch(SQLException ex){
			/*JOptionPane.showMessageDialog(null, ex.getMessage());
			System.out.println(ex.getMessage());
			System.out.println(ex.getSQLState());
			System.out.println(ex.getErrorCode());*/
		}catch(Exception e) {
			/*JOptionPane.showMessageDialog(null, e.getMessage());
			System.out.println("Não conectou" + e);*/
		}
		return null;
		
	}
	

	
	
	
}
