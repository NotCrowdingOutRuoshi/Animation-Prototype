package NotCrowdingOutRuoshi.Views;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Player extends JPanel implements ActionListener {
	private static int IDLE = 0;
	private static int WALKING = 1;
	
	private Map<Integer, Image[]>stateConvertor;
	private int state;
	private int frame;
	private Image[] idle;
	private Image[] walking;
	private Timer timer;
	private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;
	
	public Player() {
		setDoubleBuffered(true);
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
		
		idle = loadImage("src\\resources\\Idle");
		walking = loadImage("src\\resources\\Walking");
		
		stateConvertor = new HashMap<Integer, Image[]>();
		stateConvertor.put(IDLE, idle);
		stateConvertor.put(WALKING, walking);
		
		state = IDLE;
		frame = 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Image[] images = stateConvertor.get(state);
        Image currentFrame = images[frame];
        
        g.drawImage(currentFrame, 0, 0, this);
        
        frame = (frame + 1) % images.length;
    }

	private Image[] loadImage(String imgResDir) {
		File f = new File(imgResDir);
		File[] files = f.listFiles();
		Image[] container = null;
		
		if (files.length > 0) {
			container = new Image[files.length];
			for (int i = 0; i < files.length; i++) {
				ImageIcon img = new ImageIcon(files[i].getPath());
				container[i] = img.getImage();
			}
		}
		
		return container;
	}
	
	private class ScheduleTask extends TimerTask {		
        @Override
        public void run() {            
            repaint();
        }
    }
}
