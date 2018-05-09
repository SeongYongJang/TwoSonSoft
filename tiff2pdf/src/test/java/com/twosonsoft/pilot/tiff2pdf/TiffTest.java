package com.twosonsoft.pilot.tiff2pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

public class TiffTest
{
	@Test
	public void testReadTiffHeader()
	{
		Tiff2PDFConverter converter = new Tiff2PDFConverter();

		String tiffFilename = "/Users/seongyong/Downloads/k1.tif";

		if (converter.isTiffFile(tiffFilename))
		{
			System.out.println("It's a tiff file");
		}
	}

	@Test
	public void testTiff2Pdf() throws Exception
	{
		String tiffFilename = "/Users/seongyong/Downloads/k1.tif";
		String pdfFilename = "/Users/seongyong/Downloads/jai1.pdf";

		Tiff2PDFConverter converter = new Tiff2PDFConverter();
		converter.convertTiff2Pdf(tiffFilename, pdfFilename);

	}


	@Test
	public void testImageIO()
	{
		String[] readers = ImageIO.getReaderFileSuffixes();
		for (String reader : readers)
		{
			System.out.println(reader);
		}
	}

}
