package nl.jpoint.quizapp;

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
	 protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
	  response.setContentType("image/jpeg");
	  OutputStream outputStream = response.getOutputStream();
	  ImageIO.write(generator.generate("http://www.royvanrijn.com"), "jpeg", outputStream);
	  outputStream.close();
	 }
}
