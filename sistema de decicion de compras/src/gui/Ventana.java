package gui;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JLabel;

import test.Acceso;
import analista.Analizador;
import analista.Producto;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Ventana extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<Producto> buenos;
	private DefaultListModel<Producto> medios;
	private DefaultListModel<Producto> malos;
	private Analizador analisador;
	private JButton actualizar;
	private boolean tipo;
	private volatile boolean ejecutando=false;

	/**
	 * Create the frame.
	 */
	public Ventana(boolean tipo) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList<Producto> buenos = new JList<Producto>();
		buenos.setBounds(10, 30, 180, 380);
		contentPane.add(buenos);
		
		JList<Producto> medios = new JList<Producto>();
		medios.setBounds(200, 30, 180, 380);
		contentPane.add(medios);
		
		JList<Producto> malos = new JList<Producto>();
		malos.setBounds(390, 30, 180, 380);
		contentPane.add(malos);
		
		JLabel lblBuenaOpcin = new JLabel("Buena Opci\u00F3n");
		lblBuenaOpcin.setBackground(Color.LIGHT_GRAY);
		lblBuenaOpcin.setBounds(54, 5, 89, 14);
		contentPane.add(lblBuenaOpcin);
		
		JLabel lblOpcinRegular = new JLabel("Opci\u00F3n Regular");
		lblOpcinRegular.setBounds(243, 5, 89, 14);
		contentPane.add(lblOpcinRegular);
		
		JLabel lblMalaOpcin = new JLabel("Mala Opci\u00F3n");
		lblMalaOpcin.setBounds(442, 5, 84, 14);
		contentPane.add(lblMalaOpcin);
		this.buenos=new DefaultListModel<>();
		this.medios=new DefaultListModel<>();
		this.malos=new DefaultListModel<>();
		buenos.setModel(this.buenos);
		medios.setModel(this.medios);
		malos.setModel(this.malos);

		
		MouseMotionAdapter ma=new MouseMotionAdapter() {
	        @Override
	        public void mouseMoved(MouseEvent e) {
	        	if(e.getSource() instanceof JList<?>){
		            @SuppressWarnings("unchecked")
					JList<Producto> l = (JList<Producto>)e.getSource();
		            ListModel<Producto> m = l.getModel();
		            int index = l.locationToIndex(e.getPoint());
		            if( index>-1 ) {
		            	Producto p=m.getElementAt(index);
		                l.setToolTipText(p.toString()+" comprar almenos: "+(int)Math.ceil(1.5*p.getPromedioVenta())+" Actualmente: "+(int)p.getStock());
		            }
	        	}
	        }
	    };

		buenos.addMouseMotionListener(ma);
		medios.addMouseMotionListener(ma);
		malos.addMouseMotionListener(ma);
		this.tipo=tipo;
		actualizar = new JButton("");
		actualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!ejecutando){
					analizar();
				}
			}
		});
		actualizar.setIcon(new ImageIcon(Ventana.class.getResource("/drawable/actualizar.png")));
		actualizar.setBounds(537, 1, 33, 23);
		contentPane.add(actualizar);
		analizar();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Ventana.this.setTitle(Ventana.this.getTitle()+" - Actualizando");
		try{
			Producto.reiniciarExtremos();
			Acceso bd=new Acceso();
			bd.abrir();
			analisador=new Analizador(bd,Ventana.this.tipo);
			bd.cerrar();
			llenarListas();
		}catch(Exception e){
		}
		Ventana.this.setTitle(Ventana.this.getTitle().replace(" - Actualizando", ""));
		ejecutando=false;
	}
	private void analizar(){
		Thread t=new Thread(this);
		ejecutando=true;
		t.start();
	}
	private void llenarListas(){
		Producto[] p;
		p=analisador.precentarPrioridad(Analizador.IMPORTANCIA_ALTA);
		buenos.removeAllElements();
		for(int i=0;i<p.length;i++){
			buenos.addElement(p[i]);
		}
		p=analisador.precentarPrioridad(Analizador.IMPORTANCIA_MEDIA);
		medios.removeAllElements();
		for(int i=0;i<p.length;i++){
			medios.addElement(p[i]);
		}
		p=analisador.precentarPrioridad(Analizador.IMPORTANCIA_BAJA);
		malos.removeAllElements();
		for(int i=0;i<p.length;i++){
			malos.addElement(p[i]);
		}
	}
}
