package com.twosonsoft.pilot.tiff2pdf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

public class Tiff2PDFConverter
{
	public boolean isTiffFile(String tiffFilename)
	{
		boolean isTiff = false;
		// Read tiff file header
		// open file as binary mode
		// read 2 bytes
		// if read bytes is 'II' or 'MM' it is TIFF Header
		FileInputStream fis = null;
		InputStreamReader isr = null;
		try
		{
			fis = new FileInputStream(tiffFilename);
			// 파일의 내용을 문자로 읽기 위해서 InputStreamReader 로 생성
			isr = new InputStreamReader(fis);
			char[] header = new char[2];
			isr.read(header, 0, 2);

			String headerMarker = new String(header);

			if ("MM".equals(headerMarker) || "II".equals(headerMarker))
			{
				isTiff = true;
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				fis.close();
				isr.close();
			}
			catch (Exception e)
			{
			}

		}

		return isTiff;
	}

	public void convertTiff2Pdf(String tiffFilename, String pdfFilename) throws Exception
	{
		// create pdf document
		Document pdf = new Document();
		// set margin 0
		pdf.setMargins(0, 0, 0, 0);

		PdfWriter.getInstance(pdf, new FileOutputStream(pdfFilename));
		// open pdf document
		pdf.open();
		// add tiff file to pdf document
		addTiffImageToPdf(pdf, tiffFilename);
		// close pdf document
		pdf.close();

	}

	public void convertMultiTiff2Pdf(List<String> files, String pdfFilename) throws Exception
	{
		// create pdf document
		Document pdf = new Document();
		// set margin 0
		pdf.setMargins(0, 0, 0, 0);

		PdfWriter.getInstance(pdf, new FileOutputStream(pdfFilename));
		// open pdf document
		pdf.open();
		for (String tiffFilename : files)
		{
			// add tiff file to pdf document
			addTiffImageToPdf(pdf, tiffFilename);
		}
		// close pdf document
		pdf.close();

	}

	void addTiffImageToPdf(Document pdf, String tiffFilename) throws Exception
	{
		ImageInputStream is = javax.imageio.ImageIO.createImageInputStream(new File(tiffFilename));
		if (is == null || is.length() == 0)
		{
			// handle error
		}
		Iterator<javax.imageio.ImageReader> iterator = javax.imageio.ImageIO.getImageReaders(is);
		if (iterator == null || !iterator.hasNext())
		{
			throw new IOException("Image file format not supported by ImageIO: " + tiffFilename);
		}
		// We are just looking for the first reader compatible:
		ImageReader reader = (ImageReader) iterator.next();
		iterator = null;
		reader.setInput(is);
		int nbPages = reader.getNumImages(true);

		for (int i = 0; i < nbPages; i++)
		{
			BufferedImage img = reader.read(i);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			javax.imageio.ImageIO.write(img, "jpg", baos);

			baos.flush();
			// Convert byteArrayoutputSteam to ByteArray
			byte[] imageInByte = baos.toByteArray();

			Image imgToSave = Image.getInstance(imageInByte);
			float w = imgToSave.getWidth();
			float h = imgToSave.getHeight();
			// convert horizontal image to vertical
			if (w > h)
			{
				imgToSave.setRotationDegrees(90);
			}

			imgToSave.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
			pdf.add(imgToSave);
			baos.close();
		}

	}

}
