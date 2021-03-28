package ant.costanti;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LettoreImmagini {

	static public Image leggiImmagine(String imagefilename) {
		try {
			return  ImageIO.read(new File(imagefilename));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	static public BufferedImage ricoloraImmagine(BufferedImage image, Color vecchio, Color nuovo) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color originale = new Color(image.getRGB(x, y),true);
                if (originale.equals(vecchio)) {
                    image.setRGB(x, y, nuovo.getRGB());
                }
            }
        }
        return image;
    }

	static public Image leggiImmagineRicolorata(String imagefilename, Color vecchio, Color nuovo) {
		final BufferedImage image = (BufferedImage)leggiImmagine(imagefilename);
		return ricoloraImmagine(image, vecchio, nuovo);
	}

}
