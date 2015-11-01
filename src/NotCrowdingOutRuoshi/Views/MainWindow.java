package NotCrowdingOutRuoshi.Views;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

public class MainWindow extends JFrame {
	public MainWindow() {		
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(300, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Organ Donation - Animator Prototype");    
        setLocationRelativeTo(null);
        pack();
        
        Player player = new Player();
		add(player);
		player.addKeyListener(player);
		player.setFocusable(true);
		player.requestFocusInWindow();
	}
}
