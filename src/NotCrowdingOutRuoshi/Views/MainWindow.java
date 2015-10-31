package NotCrowdingOutRuoshi.Views;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MainWindow extends JFrame {
	public MainWindow() {
		add(new Player());
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
        pack();
        
        setTitle("Organ Donation - Animator Prototype");    
        setLocationRelativeTo(null);
	}
}
