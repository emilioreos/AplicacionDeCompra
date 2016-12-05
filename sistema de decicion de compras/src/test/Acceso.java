package test;

import analista.DBAdapter;
import analista.DBAdapterException;
import analista.Producto;

import java.sql.*;
import java.util.ArrayList;

public class Acceso extends DBAdapter {
	
	private PreparedStatement selectProductos;
	private PreparedStatement selectFechas;
	
	private static final String selectProductosStr="select p.id,p.descripcion,(p.precio/p.precioCompra)-1 as ganancia,p.existencia,sum(dv.cantidad)/count(dv.producto) as ventasHechas,count(dv.producto)/? as frecuenciaVenta from producto as p left join detalleVenta as dv on dv.producto=p.id group by p.id"; 
	private static final String selectFechasStr="select min(fecha),max(fecha) from venta";
	
	public Acceso() {
		super("root", "root","com.mysql.jdbc.Driver","jdbc:mysql://localhost/abarrotes");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Producto[] getProductos() {
		// TODO Auto-generated method stub
		Producto[] ps=null;
		try {
			if(selectProductos==null){
				selectProductos=getPreparedStatement(selectProductosStr);
			}
			if(selectFechas==null){
				selectFechas=getPreparedStatement(selectFechasStr);
			}
			ResultSet rs=selectFechas.executeQuery();
			float dias=1;
			if(rs.next()){
				Date fe=rs.getDate(2);
				if(fe==null){
					return null;
				}
				long dif=fe.getTime()-rs.getDate(1).getTime();
				dif=dif/1000;
				dif=dif/60;
				dif=dif/60;
				dias=dif/24f;
				if(dias>365){
					dias=365;
				}else if(dias<1){
					dias=1;
				}
			}else{
				return null;
			}
			selectProductos.setFloat(1, dias);
			ArrayList<Producto> pros=new ArrayList<>();
			rs=selectProductos.executeQuery();
			for(;rs.next();){
				Producto p=new Producto();
				p.nombre=rs.getString(2);
				p.setGanancia(rs.getFloat(3));
				p.setStock(rs.getFloat(4));
				p.setPromedioVenta(rs.getFloat(5));
				p.setFrecuenciaVenta(rs.getFloat(6));
				pros.add(p);
			}
			rs.close();
			ps=pros.toArray(new Producto[0]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ps;
	}
	public void abrir() throws DBAdapterException{
		abrirConexion();
	}
	public void cerrar() throws DBAdapterException{
		cerrarConexion();
	}
}
