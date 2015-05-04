package nl.jpoint.quizapp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/qrcode")
public class QRCodeServlet extends HttpServlet {

	private QRCodeGenerator generator = new QRCodeGenerator();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String url = request.getParameter("url");
		if(url == null) {
			// Please add a query parameter with name 'url' to generate a valid QR code.
			url = "http://en.wikipedia.org/wiki/Query_string";
		}

		BufferedImage image = generator.generate(url);
		
		response.setContentType("image/png");
		
		OutputStream outputStream = response.getOutputStream();
		ImageIO.write(image, "png", outputStream);
		outputStream.close();
	}
}
