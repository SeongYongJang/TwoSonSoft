package com.twosonsoft.pilot.tiff2pdf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.codec.TiffImage;

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
		RandomAccessFileOrArray file = new RandomAccessFileOrArray(tiffFilename);
		int pages = TiffImage.getNumberOfPages(file);
		
		Iterator<ImageReader> readers = javax.imageio.ImageIO.getImageReadersBySuffix("tiff");

		ImageReader _imageReader = (ImageReader) (readers.next());
		if (_imageReader != null)
		{
			File orgFile = new File(tiffFilename);

			ImageInputStream iis = javax.imageio.ImageIO.createImageInputStream(orgFile);
			_imageReader.setInput(iis, true);

			for (int page = 0; page < pages; page++)
			{
				// change tiff to jpeg
				BufferedImage bufferedImage = _imageReader.read(page);
				BufferedImage img2 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
				// Set the RGB values for converted image (jpg)
				for (int y = 0; y < bufferedImage.getHeight(); y++)
				{
					for (int x = 0; x < bufferedImage.getWidth(); x++)
					{
						img2.setRGB(x, y, bufferedImage.getRGB(x, y));
					}
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				javax.imageio.ImageIO.write(img2, "jpg", baos);
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

}
