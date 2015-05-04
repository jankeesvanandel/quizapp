package nl.jpoint.quizapp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeGenerator {

	private static final int SIZE = 512;

	public BufferedImage generate(String url) {
		try {
			Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, SIZE, SIZE, hintMap);
			int width = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, width, width);
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < width; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			return image;
		} catch (WriterException e) {
			throw new RuntimeException("Problem creating QR code", e);
		}
	}

}
