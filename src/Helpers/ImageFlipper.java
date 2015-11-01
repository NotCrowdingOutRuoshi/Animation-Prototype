package Helpers;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImageFlipper {
	private ImageFlipper() {
		
	}
	
	public static Image horizontalFlip(Image img) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-img.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		BufferedImage flippedImage = op.filter(toBufferedImage(img), null);
		return flippedImage;
	}
	
	public static Image[] horizontalFlip(Image[] img) {
		Image[] images = new Image[img.length];
		
		for (int i = 0; i < img.length; i++) {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			Image currentImage = img[i];
			tx.translate(-currentImage.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			BufferedImage flippedImage = op.filter(toBufferedImage(currentImage), null);
			images[i] = flippedImage;
		}
		
		return images;
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
}
