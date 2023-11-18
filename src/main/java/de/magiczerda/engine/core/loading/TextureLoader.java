package de.magiczerda.engine.core.loading;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TextureLoader {

    private static final int BYTES_PER_PIXEL = 4;//3 for RGB, 4 for RGBA

    public static int loadTexture(BufferedImage image) {
        ByteBuffer buffer = ldTexture(image);

        // You now have a ByteBuffer filled with the color data of each pixel.
        // Now just create a texture ID and bind it. Then you can load it using
        // whatever OpenGL method you want, for example:

        int textureID = GL11.glGenTextures(); //Generate texture ID
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID); //Bind texture ID

        //Setup wrap mode
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_REPEAT);

        //Setup texture scaling filtering
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        //Send texel data to OpenGL
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);


        // GL33.glTexImage2DMultisample(GL11.GL_TEXTURE_2D, 4, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), true);

        /* This line is apparently important as fuck..
         * (At least when mipmapping is enabled.) */
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        return textureID;
    }

    public static int loadCubemap(String... cubemapTextures) {
        if(cubemapTextures.length != 6) return -1;
        else { int minID = GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;

            int textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID);

            GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL13.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL13.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL13.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_EDGE);
            GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL13.GL_TEXTURE_WRAP_R, GL13.GL_CLAMP_TO_EDGE);

            for(int ii = 0; ii < cubemapTextures.length; ii++) {
                BufferedImage textureImg = loadImage(cubemapTextures[ii]);
                ByteBuffer imageBuffer = ldTexture(textureImg);



                GL11.glTexImage2D(minID + ii, 0, GL11.GL_RGBA,
                        textureImg.getWidth(), textureImg.getHeight(), 0,
                        GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageBuffer);
            }

            return textureID;
        }
    }



    private static ByteBuffer ldTexture(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip();
        return buffer;
    }



    public static BufferedImage loadImage(String loc) {
        try {
            File file = new File(loc);
            FileInputStream fis = new FileInputStream(file);
            return ImageIO.read(fis);
        } catch (IOException e) {
            System.err.println("Could not find image " + loc);
        }
        return null;
    }

}
