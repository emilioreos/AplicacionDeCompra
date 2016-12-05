package analista;

import java.sql.*;

public abstract class DBAdapter implements OrigenProductos{
	private Connection conexion;
	
	private String usuario;
	private String password;

	protected String driver;
	protected String url;

	public DBAdapter(String usuario,String password){
		this.usuario=usuario;
		this.password=password;
	}
	public DBAdapter(String usuario,String password,String driver,String url){
		this.usuario=usuario;
		this.password=password;
		this.driver=driver;
		this.url=url;
	}
	protected void abrirConexion() throws DBAdapterException{
		try{
			Class.forName(driver);
			conexion=DriverManager.getConnection(url,usuario,password);
		}catch(ClassNotFoundException e){
			throw new DBAdapterException("No existe la clase");
		}catch(Exception e){
			throw new DBAdapterException(e.getMessage());
		}
	}
	protected void cerrarConexion()throws DBAdapterException{
		try{
			conexion.close();
		}catch(Exception e){
			throw new DBAdapterException(e.getMessage());
		}
	}
	protected PreparedStatement getPreparedStatement(String sql) throws SQLException{
		return conexion.prepareStatement(sql);
	}
	public abstract Producto[] getProductos();
}