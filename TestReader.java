package com.test.tiff;


import javax.imageio.ImageIO;

import org.junit.Test;

public class TestReader {

	@Test
	public void test() {
		String[] readers = ImageIO.getReaderFileSuffixes();
		
		for(String reader : readers) {
			System.out.println(reader);
		}
	}

}
/*
must be 
/////////////////////////////
tif
jpg
tiff
bmp
gif
png
wbmp
jpeg
/////////////////////////////////
*/
