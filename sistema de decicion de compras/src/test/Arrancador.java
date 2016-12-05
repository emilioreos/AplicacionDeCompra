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
	}
}
