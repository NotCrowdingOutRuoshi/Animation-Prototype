package NotCrowdingOutRuoshi.Views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import javafx.scene.input.KeyCode;

public class Player extends JPanel implements KeyListener {
	public static enum STATUS {
		IDLE, WALKING
	};
	public static enum DIRECTION {
		LEFT, RIGHT, UP, DOWN
	};
	
	private Map<STATUS, Image[]>state2ImageMapper;
	private Map<Integer, STATUS>key2StateMapper;
	private Map<Integer, DIRECTION>key2DirectionMapper;
	private STATUS state;
	private int frame;
	private DIRECTION direction;
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
		
		state2ImageMapper = new HashMap<STATUS, Image[]>();
		state2ImageMapper.put(STATUS.IDLE, idle);
		state2ImageMapper.put(STATUS.WALKING, walking);
		
		key2StateMapper = new HashMap<Integer, STATUS>();
		key2StateMapper.put(KeyEvent.VK_SPACE, STATUS.IDLE);
		key2StateMapper.put(KeyEvent.VK_UP, STATUS.WALKING);
		key2StateMapper.put(KeyEvent.VK_DOWN, STATUS.WALKING);
		key2StateMapper.put(KeyEvent.VK_LEFT, STATUS.WALKING);
		key2StateMapper.put(KeyEvent.VK_RIGHT, STATUS.WALKING);
		
		key2DirectionMapper = new HashMap<Integer, DIRECTION>();
		key2DirectionMapper.put(KeyEvent.VK_UP, DIRECTION.UP);
		key2DirectionMapper.put(KeyEvent.VK_DOWN, DIRECTION.DOWN);
		key2DirectionMapper.put(KeyEvent.VK_LEFT, DIRECTION.LEFT);
		key2DirectionMapper.put(KeyEvent.VK_RIGHT, DIRECTION.RIGHT);
		
		state = STATUS.IDLE;
		frame = 0;
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Image[] images = state2ImageMapper.get(state);
        Image currentFrame = images[frame];
        
        if (direction == DIRECTION.LEFT) {
        	currentFrame = getHorizontalFlippedImage(currentFrame);
        }
        
        g.drawImage(currentFrame, 0, 0, this);
        
        frame = (frame + 1) % images.length;
    }
	
	private Image getHorizontalFlippedImage(Image img) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-img.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		BufferedImage flippedImage = op.filter(toBufferedImage(img), null);
		return flippedImage;
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
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	private class ScheduleTask extends TimerTask {		
        @Override
        public void run() {            
            repaint();
        }
    }

	@Override
	public void keyPressed(KeyEvent arg0) {
		STATUS newState = key2StateMapper.get(arg0.getKeyCode());
		DIRECTION newDirection = key2DirectionMapper.get(arg0.getKeyCode());
		if (newState != null && newDirection != null) {
			direction = newDirection;
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
