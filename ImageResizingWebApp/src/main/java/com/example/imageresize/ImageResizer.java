package com.example.imageresize;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

/**
 * Servlet implementation class ImageResize
 */
@WebServlet("/ImageResize")
@MultipartConfig
public class ImageResizer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageResizer() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("imageUploaded");
		InputStream fileContent = filePart.getInputStream();
		BufferedImage image = ImageIO.read(fileContent);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "bmp", baos);
		byte[] imageBytes = baos.toByteArray();
		int resizePercentage = Integer.parseInt(request.getParameter("resizePercentage"));
		
		
		RmiClient rmiClient = new RmiClient(resizePercentage, imageBytes);
		ExecutorService executor = Executors.newSingleThreadExecutor();
	    Future<byte[]> future = executor.submit(() -> {
	        rmiClient.resize();
	        return rmiClient.getResizedImage();
	    });
	    
	    try {
	        byte[] resizedImage = future.get();
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html>");
	        out.println("<head><style>body {background-color: #C2DEDC; text-align: center;}</style></head>");
	        out.println("<body>");
	        out.println("<h2 style=\"color: #116A7B;\">Result</h2>");
	        out.println("<p>Original image:</p>");
	        out.println("<img src='data:image/bmp;base64," + Base64.getEncoder().encodeToString(imageBytes) + "'/>");
	        out.println("<p>Resized image:</p>");
	        out.println("<img src='data:image/bmp;base64," + Base64.getEncoder().encodeToString(resizedImage) + "'/><br><br>");
	        out.println("<a href=\"data:image/bmp;base64," + Base64.getEncoder().encodeToString(resizedImage) + "\" download=\"resized-image.bmp\">Click here to download the resized image</a>");
	        out.println("</body></html>");
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        executor.shutdown();
	    }
	}
}