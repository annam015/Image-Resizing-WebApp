package com.example.imageresize;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class RmiClient {
	
	private int resizePercentage;
	private byte[] imageBytes;
	private byte[] resizedImage;

	public RmiClient(int resizePercentage, byte[] imageBytes) {
		this.resizePercentage = resizePercentage;
		this.imageBytes = Arrays.copyOf(imageBytes, imageBytes.length);
	}
	
	public byte[] getResizedImage() { return this.resizedImage; }

	public void resize() {
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
			int width = image.getWidth();
			int height = image.getHeight();

			BufferedImage firstHalf = image.getSubimage(0, 0, width, height / 2);
			ByteArrayOutputStream baosFirstHalf = new ByteArrayOutputStream();
			ImageIO.write(firstHalf, "bmp", baosFirstHalf);
			byte[] firstHalfBytes = baosFirstHalf.toByteArray();

			BufferedImage secondHalf = image.getSubimage(0, height / 2, width, height / 2);
			ByteArrayOutputStream baosSecondHalf = new ByteArrayOutputStream();
			ImageIO.write(secondHalf, "bmp", baosSecondHalf);
			byte[] secondHalfBytes = baosSecondHalf.toByteArray();
			
			String url1 = "rmi://172.17.0.2:1099/SAMPLE-SERVER-ADDV1";
			RmiServerInterface remoteObject1 = (RmiServerInterface) Naming.lookup(url1);
			byte[] result1 = remoteObject1.resizePhoto(resizePercentage, firstHalfBytes);

			String url2 = "rmi://172.17.0.3:1099/SAMPLE-SERVER-ADDV2";
			RmiServerInterface remoteObject2 = (RmiServerInterface) Naming.lookup(url2);
			byte[] result2 = remoteObject2.resizePhoto(resizePercentage, secondHalfBytes);
			
			this.resizedImage = combine(result1, result2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private byte[] combine(byte[] firstHalf, byte[] secondHalf) throws IOException {
		BufferedImage firstHalfImage = ImageIO.read(new ByteArrayInputStream(firstHalf));
		BufferedImage secondHalfImage = ImageIO.read(new ByteArrayInputStream(secondHalf));

		int width = firstHalfImage.getWidth();
		int height = firstHalfImage.getHeight() + secondHalfImage.getHeight();

		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(firstHalfImage, 0, 0, null);
		g.drawImage(secondHalfImage, 0, firstHalfImage.getHeight(), null);
		g.dispose();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizedImage, "bmp", baos);
		return baos.toByteArray();
	}

}