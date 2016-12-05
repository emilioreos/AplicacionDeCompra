package analista;


class Centroide extends Producto{
	/**
	 * Genera un centroide con valores stock, ganancia, frecuenciaVenta 
	 * y promendioVenta aleatorios entre sus valores maximo y minimo
	 * */
	Centroide(){
		stock=(float)((Math.random()*(maximoStock-minimoStock))+minimoStock);
		ganancia=(float)((Math.random()*(maximaGanancia-minimaGanancia))+minimaGanancia);
		frecuenciaVenta=(float)((Math.random()*(maximaFrecuenciaVenta-minimaFrecuenciaVenta))+minimaFrecuenciaVenta);
		promedioVenta=(float)((Math.random()*(maximoPromedioVenta-minimoPromedioVenta))+minimoPromedioVenta);
	}
	/**
	 * Genera un centroide del tipo solicitado puede ser CENTROIDE_MAYOR, CENTROIDE_MEDIO o CENTROIDE_MENOR;
	 * pone sus valores stock, ganancia, frecuenciaVenta y promedioVenta al maximo punto medio o menor respectivamente
	 * @param centroide si es 0 este constructor genera un centroide con el valor maximo si es 1 es un centroide medio si es 2 es un centroide inferior
	 * @throws IllegalArgumentException en caso de que se mante un valor inadecuado en centroide
	 * */
	Centroide(byte centroide){
		if(centroide==0){
			stock=minimoStock;
			ganancia=maximaGanancia;
			frecuenciaVenta=maximaFrecuenciaVenta;
			promedioVenta=maximoPromedioVenta;
		}else if(centroide==1){
			stock=((maximoStock - minimoStock)/2)+minimoStock;
			ganancia=((maximaGanancia - minimaGanancia)/2)+minimaGanancia;
			frecuenciaVenta=((maximaFrecuenciaVenta - minimaFrecuenciaVenta)/2)+minimaFrecuenciaVenta;
			promedioVenta=((maximoPromedioVenta - minimoPromedioVenta)/2)+minimoPromedioVenta;
		}else if(centroide==2){
			stock=maximoStock;
			ganancia=minimaGanancia;
			frecuenciaVenta=minimaFrecuenciaVenta;
			promedioVenta=minimoPromedioVenta;
		}else{
			throw new IllegalArgumentException();
		} 
	}
	@Override
	public void setStock(float stock){
		this.stock=stock;
	}
	@Override
	public void setGanancia(float ganancia){
		this.ganancia=ganancia;
	}
	@Override
	public void setFrecuenciaVenta(float frecuencia){
		frecuenciaVenta=frecuencia;
	}
	@Override
	public void setPromedioVenta(float promedio){
		promedioVenta=promedio;
	}
	
}
