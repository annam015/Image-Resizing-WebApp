package com.example.imageresize;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.imageio.ImageIO;

public class RmiServerImpl extends UnicastRemoteObject implements RmiServerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RmiServerImpl() throws RemoteException {
		super();
	}

	@Override
	public byte[] resizePhoto(int resizePercentage, byte[] imageBytes) throws RemoteException {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
	        BufferedImage image = ImageIO.read(byteArrayInputStream);
	             
	        int width = (int)(image.getWidth() * (resizePercentage / 100.00));
            int height = (int)(image.getHeight() * (resizePercentage / 100.00));
	        
	        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = resizedImage.createGraphics();	        
	        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g.drawImage(image, 0, 0, width, height, null);
	        g.dispose();
	        
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "bmp", baos);            
            return baos.toByteArray();
		} catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
	}
}