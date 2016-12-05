package test;

import gui.Ventana;

import java.io.*;

import javax.swing.JOptionPane;

import analista.*;

public class Arrancador {
	public static void main(String[] args) throws Exception{
		boolean tipo;
		String nombre;
		File conf=new File("conf.ini");
		if(conf.exists()){
			@SuppressWarnings("resource")
			BufferedReader br=new BufferedReader(new FileReader(conf));
			nombre=br.readLine();
			tipo=Boolean.parseBoolean(br.readLine());
		}else{
			nombre=JOptionPane.showInputDialog(null, "Ingrese el nombre de su negocio");
			int opcion=JOptionPane.showConfirmDialog(null, "¿Decea que el ordenamiento sea generico?\nLa opción generica se adapta mejor a las situaciones", "Tipo de ordenamiento", JOptionPane.YES_NO_OPTION);
			if(opcion==JOptionPane.YES_OPTION){
				tipo=Analizador.GENERICO;
			}else{
				tipo=Analizador.ORDENADO;
			}
			conf.createNewFile();
			BufferedWriter bw=new BufferedWriter(new FileWriter(conf));
			bw.write(nombre+"\n");
			bw.write(Boolean.toString(tipo));
			bw.flush();
			bw.close();
		}
		
		Ventana frame = new Ventana(tipo);
		frame.setTitle(nombre);
		frame.setVisible(true);
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana frame = new Ventana(Analizador.GENERICO);
					frame.setVisible(true);
					frame = new Ventana(Analizador.ORDENADO);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*//*
		final Producto[] pro=new Producto[3];
		pro[0]=new Producto();
		pro[1]=new Producto();
		pro[2]=new Producto();
		
		pro[0].setFrecuenciaVenta(1);
		pro[0].setGanancia(0.5f);
		pro[0].setPromedioVenta(2);
		pro[0].setStock(15);
		pro[0].nombre="Producto 0";
		
		pro[1].setFrecuenciaVenta(1);
		pro[1].setGanancia(0.005f);
		pro[1].setPromedioVenta(0.5f);
		pro[1].setStock(8);
		pro[1].nombre="Producto 1";
		
		pro[2].setFrecuenciaVenta(1);
		pro[2].setGanancia(0.8f);
		pro[2].setPromedioVenta(5);
		pro[2].setStock(0);
		pro[2].nombre="Producto 2";*/
		//Acceso ac=new Acceso();
		/**
		new OrigenProductos() {
			
			@Override
			public Producto[] getProductos() {
				// TODO Auto-generated method stub
				return pro;
			}
		}
		*/
		/*
		Analizador a=null;
		try {
			ac.abrir();
			a=new Analizador(ac,Analizador.GENERICO);
			ac.cerrar();
		} catch (DBAdapterException e) {
			e.printStackTrace();
		}
		if(a==null){
			return;
		}
		Producto[] alto=a.precentarPrioridad(Analizador.IMPORTANCIA_ALTA);
		Producto[] medio=a.precentarPrioridad(Analizador.IMPORTANCIA_MEDIA);
		Producto[] bajo=a.precentarPrioridad(Analizador.IMPORTANCIA_BAJA);
		
		for(int i=0;i<alto.length;i++){
			System.out.println("Alto\t"+alto[i]);
		}
		for(int i=0;i<medio.length;i++){
			System.out.println("Medio\t"+medio[i]);
		}
		for(int i=0;i<bajo.length;i++){
			System.out.println("Bajo\t"+bajo[i]);
		}
		
		System.out.println();*/
	}
}
