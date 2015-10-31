package NotCrowdingOutRuoshi.Views;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import javafx.scene.input.KeyCode;

public class Player extends JPanel implements KeyListener {
	private static int IDLE = 0;
	private static int WALKING = 1;
	
	private Map<Integer, Image[]>stateImageMapper;
	private Map<Integer, Integer>keyStateMapper;
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
		
		stateImageMapper = new HashMap<Integer, Image[]>();
		stateImageMapper.put(IDLE, idle);
		stateImageMapper.put(WALKING, walking);
		
		keyStateMapper = new HashMap<Integer, Integer>();
		keyStateMapper.put(KeyEvent.VK_SPACE, IDLE);
		keyStateMapper.put(KeyEvent.VK_UP, WALKING);
		keyStateMapper.put(KeyEvent.VK_DOWN, WALKING);
		keyStateMapper.put(KeyEvent.VK_LEFT, WALKING);
		keyStateMapper.put(KeyEvent.VK_RIGHT, WALKING);
		
		state = IDLE;
		frame = 0;
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Image[] images = stateImageMapper.get(state);
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		Integer newState = keyStateMapper.get(arg0.getKeyCode());
		if (newState != null) {
			state = newState;
			frame = 0;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
