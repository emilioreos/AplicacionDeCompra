package analista;

import java.util.ArrayList;

public class Analizador{
	public static final byte IMPORTANCIA_ALTA=0;
	public static final byte IMPORTANCIA_MEDIA=1;
	public static final byte IMPORTANCIA_BAJA=2;
	
	public static final boolean GENERICO=false;
	public static final boolean ORDENADO=true;
	
	private static final int N=3;
	
	private Producto[] centroides=new Producto[N];
	private Producto[] altos;
	private Producto[] medios;
	private Producto[] bajos;
	
	private boolean[] analizados=new boolean[N];
	
	public Analizador(OrigenProductos o){
		this(o.getProductos(),GENERICO);
	}
	public Analizador(OrigenProductos o,boolean tipo){
		this(o.getProductos(),tipo);
	}
	public Analizador(Producto[] prods,boolean tipo){
		for(byte i=0;i<N;i++){
			centroides[i]=new Centroide(i);
			analizados[i]=false;
		}
		if(tipo){
			analisisPrimario(prods);
		}else{
			aprender(prods);
		}
	}
	/*private void ordenarCentroides(){
		Centroide c=new Centroide(Producto.CENTROIDE_MAYOR);
		double[] dists=new double[N];
		for(int i=0;i<N;i++){
			dists[i]=Math.pow(c.getStock()-centroides[i].getStock(), 2)+
				Math.pow(c.getFrecuenciaVenta()-centroides[i].getFrecuenciaVenta(), 2)+
				Math.pow(c.getGanancia()-centroides[i].getGanancia(), 2)+
				Math.pow(c.getPromedioVenta()-centroides[i].getPromedioVenta(), 2); 
		}
		for(int i=0;i<N;i++){
			for(int j=i+1;j<N;j++){
				if(dists[i]>dists[j]){
					double temp=dists[i];
					Producto p=centroides[i];
					dists[i]=dists[j];
					dists[j]=temp;
					centroides[i]=centroides[j];
					centroides[j]=p;
				}
			}
		}
		for(int i=0;i<N;i++){
			System.out.println("G: "+centroides[i].getGanancia()+" FV: "+centroides[i].getFrecuenciaVenta()+" PV: "+centroides[i].getPromedioVenta()+" S: "+centroides[i].getStock());
		}
	}*/
	private double calcularDistancia(Producto dato,Producto centroide){
		double res=Math.pow(dato.getStock()-centroide.getStock(), 2)+
		2*Math.pow(dato.getFrecuenciaVenta()-centroide.getFrecuenciaVenta(), 2)+
		2*Math.pow(dato.getGanancia()-centroide.getGanancia(), 2)+
		Math.pow(dato.getPromedioVenta()-centroide.getPromedioVenta(), 2); 
		return res;
	}
	private void aprender(Producto[] datos){
		for(int t=2;t<40;t++){
			for(int i=0;i<datos.length;i++){
				double distancia=Double.MAX_VALUE;
				int n=0;
				for(int j=0;j<N;j++){
					double dist=calcularDistancia(datos[i],centroides[j]);
					if(dist<distancia){
						distancia=dist;
						n=j;
					}
				}
				float stock,frecuenciaVenta,ganancia,promedioVenta;
				stock=(datos[i].getStock()-centroides[n].getStock())/t;
				frecuenciaVenta=(datos[i].getFrecuenciaVenta()-centroides[n].getFrecuenciaVenta())/t;
				ganancia=(datos[i].getGanancia()-centroides[n].getGanancia())/t;
				promedioVenta=(datos[i].getPromedioVenta()-centroides[n].getPromedioVenta())/t;
				centroides[n].setStock(centroides[n].getStock()+stock);
				centroides[n].setFrecuenciaVenta(centroides[n].getFrecuenciaVenta()+frecuenciaVenta);
				centroides[n].setGanancia(centroides[n].getGanancia()+ganancia);
				centroides[n].setPromedioVenta(centroides[n].getPromedioVenta()+promedioVenta);
			}
		}
		//ordenarCentroides();
		analisisPrimario(datos);
	}
	private void analisisPrimario(Producto[] datos){
		ArrayList<Producto> bajos=new ArrayList<Producto>(),
				medios=new ArrayList<Producto>(),
				altos=new ArrayList<Producto>();
		for(int i=0;i<datos.length;i++){
			double distancia=Double.MAX_VALUE;
			int n=0;
			for(int j=0;j<N;j++){
				double dist=calcularDistancia(datos[i],centroides[j]);
				if(dist<distancia){
					distancia=dist;
					n=j;
				}
			}
			if(n==0){
				altos.add(datos[i]);
			}else if(n==1){
				medios.add(datos[i]);
			}else if(n==2){
				bajos.add(datos[i]);
			}
		}
		this.altos=altos.toArray(new Producto[0]);
		this.medios=medios.toArray(new Producto[0]);
		this.bajos=bajos.toArray(new Producto[0]);
		
	}
	public Producto[] precentarPrioridad(byte prioridad){
		Producto[] porAnalizar;
		if(prioridad==IMPORTANCIA_ALTA){
			if(analizados[prioridad]){
				return altos;
			}
			porAnalizar=altos;
		}else if(prioridad==IMPORTANCIA_MEDIA){
			if(analizados[prioridad]){
				return medios;
			}
			porAnalizar=medios;
		}else if(prioridad==IMPORTANCIA_BAJA){
			if(analizados[prioridad]){
				return bajos;
			}
			porAnalizar=bajos;
		}else{
			throw new IllegalArgumentException("La prioridad "+prioridad+" no es balida");
		}
		analizados[prioridad]=true;
		paraPrecentacion(porAnalizar);
		return porAnalizar;
	}
	private Producto[] paraPrecentacion(Producto[] productos){
		Centroide c=new Centroide(Producto.CENTROIDE_MAYOR);
		double[] dists=new double[productos.length];
		for(int i=0;i<productos.length;i++){
			dists[i]=calcularDistancia(c,productos[i]);
		}
		for(int i=0;i<productos.length;i++){
			for(int j=i+1;j<productos.length;j++){
				if(dists[i]>dists[j]){
					double temp=dists[i];
					Producto p=productos[i];
					dists[i]=dists[j];
					dists[j]=temp;
					productos[i]=productos[j];
					productos[j]=p;
				}
			}
		}
		return productos;
	}
}