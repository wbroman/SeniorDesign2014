package javaPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	public double x1;
	public double y1;
	public double x2;
	public double y2;
	public double x3;
	public double y3;
	public double x4;
	public double y4;
	
	
	//public Regression r1;
	public Regression2 r2;
	//public Regression3 r3;
	
	public static void main(String[] args){
		new Frame().setVisible(true);
	}
	
	public Frame(){
		
		super("Senior Design");
		setSize(600, 65);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		
		
		final JTextArea x1Test= new JTextArea (2,2);
		final JTextArea y1Test= new JTextArea (2,2);
		final JTextArea x2Test= new JTextArea (2,2);
		final JTextArea y2Test= new JTextArea (2,2);
//		final JTextArea x3Test= new JTextArea (2,2);
//		final JTextArea y3Test= new JTextArea (2,2);
//		final JTextArea x4Test= new JTextArea (2,2);
//		final JTextArea y4Test= new JTextArea (2,2);
		add(x1Test);
		add(y1Test);
		add(x2Test);
		add(y2Test);
//		add(x3Test);
//		add(y3Test);
//		add(x4Test);
//		add(y4Test);
		
		JButton button = new JButton("Compute");
		add(button);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				x1 = Double.parseDouble(x1Test.getText());
				y1 = Double.parseDouble(y1Test.getText());
				x2 = Double.parseDouble(x2Test.getText());
				y2 = Double.parseDouble(y2Test.getText());
//				x3 = Double.parseDouble(x3Test.getText());
//				y3 = Double.parseDouble(y3Test.getText());
//				x4 = Double.parseDouble(x4Test.getText());
//				y4 = Double.parseDouble(y4Test.getText());
				/*
				if(!x1Test.getText().isEmpty() && !y1Test.getText().isEmpty() && !x2Test.getText().isEmpty() || !y2Test.getText().isEmpty()){
					x1 = Double.parseDouble(x1Test.getText());
					y1 = Double.parseDouble(y1Test.getText());
					x2 = Double.parseDouble(x2Test.getText());
					y2 = Double.parseDouble(y2Test.getText());
					
					if(!x3Test.getText().isEmpty() && !y3Test.getText().isEmpty()){
						x3 = Double.parseDouble(x3Test.getText());
						y3 = Double.parseDouble(y3Test.getText());
						
						if(!x4Test.getText().isEmpty() && !y4Test.getText().isEmpty()){
							x4 = Double.parseDouble(x4Test.getText());
							y4 = Double.parseDouble(y4Test.getText());
							r3=new Regression3(x1,y1,x2,y2,x3,y3,x4,y4);//initializes the variables
							r3.computation();
						}else{
							r2=new Regression2(x1,y1,x2,y2,x3,y3,x4,y4);//initializes the variables
							r2.computation();
						}
					}else{
						r1=new Regression(x1,y1,x2,y2,x3,y3,x4,y4);//initializes the variables
						r1.computation();
					}
					
						}else{
							if(!x4Test.getText().isEmpty() && !y4Test.getText().isEmpty() && !x3Test.getText().isEmpty() || !y3Test.getText().isEmpty()){
								x4 = Double.parseDouble(x1Test.getText());
								y4 = Double.parseDouble(y1Test.getText());
								x3 = Double.parseDouble(x2Test.getText());
								y3 = Double.parseDouble(y2Test.getText());
								
								if(!x2Test.getText().isEmpty() && !y2Test.getText().isEmpty()){
									x2 = Double.parseDouble(x2Test.getText());
									y2 = Double.parseDouble(y2Test.getText());
									
									if(!x1Test.getText().isEmpty() && !y1Test.getText().isEmpty()){
										x1 = Double.parseDouble(x1Test.getText());
										y1 = Double.parseDouble(y1Test.getText());
									}
								}
								
									}
						}
					*/

				//x1 = Double.parseDouble(x1Test.getText()); //need to convert to doubles
				
				/*
				if(x3 == null && y3 == null && x4 == null && y4 == null){
				r1 = new Regression(x1,y1,x2,y2,x3,y3,x4,y4);
				r1.computation();
				}*/
				
				r2=new Regression2(x1,y1,x2,y2 /*,x3,y3,x4,y4*/);//initializes the variables
				r2.computation();//starts the computation

			
				

				
				
			}
		});
		

	}
}