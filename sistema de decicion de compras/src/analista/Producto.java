package analista;

public class Producto{
	protected static float maximaGanancia=0;
	protected static float maximaFrecuenciaVenta=0;
	protected static float maximoPromedioVenta=0;
	protected static float maximoStock=0;
	protected static float minimaGanancia=Float.MAX_VALUE;
	protected static float minimaFrecuenciaVenta=Float.MAX_VALUE;
	protected static float minimoPromedioVenta=Float.MAX_VALUE;
	protected static float minimoStock=Float.MAX_VALUE;
	static final byte CENTROIDE_MAYOR=0;
	static final byte CENTROIDE_MEDIO=1;
	static final byte CENTROIDE_MENOR=2;
	
	public String nombre;
	protected float stock;
	protected float ganancia;
	protected float frecuenciaVenta;
	protected float promedioVenta;

	private byte banderas=0;
	
	public void setStock(float stock){
		if((banderas&1)>0){
			throw new IllegalStateException();
		}
		this.stock=stock;
		banderas=(byte)(banderas|1);
		if(stock>maximoStock){
			maximoStock=stock;
		}
		if(stock<minimoStock){
			minimoStock=stock;
		}
	}
	public void setGanancia(float ganancia){
		if((banderas&2)>0){
			throw new IllegalStateException();
		}
		this.ganancia=ganancia;
		banderas=(byte)(banderas|2);
		if(ganancia>maximaGanancia){
			maximaGanancia=ganancia;
		}
		if(ganancia<minimaGanancia){
			minimaGanancia=ganancia;
		}
	}
	public void setFrecuenciaVenta(float frecuencia){
		if((banderas&4)>0){
			throw new IllegalStateException();
		}
		frecuenciaVenta=frecuencia;
		banderas=(byte)(banderas|4);
		if(frecuencia>maximaFrecuenciaVenta){
			maximaFrecuenciaVenta=frecuencia;
		}
		if(frecuencia<minimaFrecuenciaVenta){
			minimaFrecuenciaVenta=frecuencia;
		}
	}
	public void setPromedioVenta(float promedio){
		if((banderas&8)>0){
			throw new IllegalStateException();
		}
		promedioVenta=promedio;
		banderas=(byte)(banderas|8);
		if(promedio>maximoPromedioVenta){
			maximoPromedioVenta=promedio;
		}
		if(promedio<minimoPromedioVenta){
			minimoPromedioVenta=promedio;
		}
	}
	public float getStock(){
		return stock;
	}
	public float getGanancia(){
		return ganancia;
	}
	public float getFrecuenciaVenta(){
		return frecuenciaVenta;
	}
	public float getPromedioVenta(){
		return promedioVenta;
	}
	@Override
	public String toString(){
		return nombre;
	}
	public static void reiniciarExtremos(){
		maximaGanancia=0;
		maximaFrecuenciaVenta=0;
		maximoPromedioVenta=0;
		maximoStock=0;
		minimaGanancia=Float.MAX_VALUE;
		minimaFrecuenciaVenta=Float.MAX_VALUE;
		minimoPromedioVenta=Float.MAX_VALUE;
		minimoStock=Float.MAX_VALUE;
	}
}