package com.twosonsoft.pilot.tiff2pdf;

import org.junit.Test;

public class TiffTest
{
	@Test
	public void testReadTiffHeader()
	{
		Tiff2PDFConverter converter = new Tiff2PDFConverter();
		
		String tiffFilename = "/Users/seongyong/Downloads/tiffsample";
		
		if(converter.isTiffFile(tiffFilename))
		{
			System.out.println("It's a tiff file");
		}
	}
	@Test
	public void testTiff2Pdf() throws Exception
	{
		String tiffFilename = "/Users/seongyong/Downloads/multi.tif";
		String pdfFilename = "/Users/seongyong/Downloads/covertedPdf6.pdf";
		
		Tiff2PDFConverter converter = new Tiff2PDFConverter();
		converter.convertTiff2Pdf(tiffFilename, pdfFilename);
		
	}
	@Test
	public void testImageIO()
	{
		String[] readers = ImageIO.getReaderFileSuffixes();
		for(String reader : readers)
		{
			System.out.println(reader);
		}
	}

}
