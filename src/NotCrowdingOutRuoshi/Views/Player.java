package NotCrowdingOutRuoshi.Views;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import Helpers.ImageFlipper;

public class Player extends JPanel implements KeyListener {
	public static enum STATE {
		IDLE, WALKING
	};
	public static enum DIRECTION {
		LEFT, RIGHT, UP, DOWN
	};

	private Map<Integer, DIRECTION>key2DirectionMapper;
	private Map<Integer, STATE>key2StateMapper;
	private Map<STATE, Map<DIRECTION, Image[]>>state2ImageMapper;
	private DIRECTION direction;
	private STATE state;
	private int frame;
	private Map<DIRECTION, Image[]> idle;
	private Map<DIRECTION, Image[]> walking;
	private Timer timer;
	private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;
    private final int[] validKeyCodes = {
			KeyEvent.VK_SPACE,
			KeyEvent.VK_LEFT,
			KeyEvent.VK_RIGHT,
			KeyEvent.VK_KP_UP,
			KeyEvent.VK_DOWN
	};
	
	public Player() {
		setDoubleBuffered(true);
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL);
		
		idle = new HashMap<DIRECTION, Image[]>();
		idle.put(DIRECTION.RIGHT, loadImage("src\\resources\\Idle\\Right"));
		idle.put(DIRECTION.DOWN, loadImage("src\\resources\\Idle\\Front"));
		walking = new HashMap<DIRECTION, Image[]>();
		walking.put(DIRECTION.RIGHT, loadImage("src\\resources\\Walking\\Right"));
		
		state2ImageMapper = new HashMap<STATE, Map<DIRECTION, Image[]>>();
		state2ImageMapper.put(STATE.IDLE, idle);
		state2ImageMapper.put(STATE.WALKING, walking);
		
		key2StateMapper = new HashMap<Integer, STATE>();
		key2StateMapper.put(KeyEvent.VK_SPACE, STATE.IDLE);
		key2StateMapper.put(KeyEvent.VK_UP, STATE.WALKING);
		key2StateMapper.put(KeyEvent.VK_DOWN, STATE.WALKING);
		key2StateMapper.put(KeyEvent.VK_LEFT, STATE.WALKING);
		key2StateMapper.put(KeyEvent.VK_RIGHT, STATE.WALKING);
		
		key2DirectionMapper = new HashMap<Integer, DIRECTION>();
		key2DirectionMapper.put(KeyEvent.VK_UP, DIRECTION.UP);
		key2DirectionMapper.put(KeyEvent.VK_DOWN, DIRECTION.DOWN);
		key2DirectionMapper.put(KeyEvent.VK_LEFT, DIRECTION.LEFT);
		key2DirectionMapper.put(KeyEvent.VK_RIGHT, DIRECTION.RIGHT);
		
		state = STATE.IDLE;
		frame = 0;
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Image[] images = filterImageDirection(state2ImageMapper.get(state), direction);
        Image currentFrame = images[frame];
        
        g.drawImage(currentFrame, 0, 0, this);
        
        frame = (frame + 1) % images.length;
    }

	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		
		if (isKeyValid(keyCode)) {
			state = convertKeyToState(keyCode);
			direction = convertKeyToDirection(keyCode);
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
	
	private DIRECTION convertKeyToDirection(int keyCode) {
		DIRECTION newDirection = key2DirectionMapper.get(keyCode);
		if (newDirection != null) {
			return newDirection;
		}
		return direction;
	}
	
	private STATE convertKeyToState(int keyCode) {
		STATE newState = key2StateMapper.get(keyCode);
		if (newState != null) {
			return newState;
		}
		return state;
	}
	
	private Image[] filterImageDirection(Map<DIRECTION, Image[]> images, DIRECTION direction) {
		Image[] img = images.get(DIRECTION.RIGHT);
		if (direction == DIRECTION.LEFT) {
			img = ImageFlipper.horizontalFlip(img);
		}
		return img;
	}
	
	private Image filterImageDirection(Map<DIRECTION, Image[]> images, DIRECTION direction, int frame) {
		Image img = images.get(DIRECTION.RIGHT)[frame];
		if (direction == DIRECTION.LEFT) {
			img = ImageFlipper.horizontalFlip(img);
		}
		return img;
	}
	
	private boolean isKeyValid(int keyCode) {
		for (int currentKeyCode : validKeyCodes) {
			if (keyCode == currentKeyCode) {
				return true;
			}
		}
		
		return false;
	}
	
	private Image[] loadImage(String imgResDir) {
		File f = new File(imgResDir);
		File[] files = f.listFiles(imageFilter);
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
	
	private boolean IsImage(File file) {
		Image image = null;
		boolean isValid = true;
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			isValid = false;
		}
		
		if (image == null) {
			isValid = false;
		}
		
	    return isValid;
	}
	
	private class ScheduleTask extends TimerTask {		
        @Override
        public void run() {            
            repaint();
        }
    }
	
	private FilenameFilter imageFilter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			String fileName = dir.getPath() + "\\" + name;
			File file = new File(fileName);
			return IsImage(file);
        }
	};
}
