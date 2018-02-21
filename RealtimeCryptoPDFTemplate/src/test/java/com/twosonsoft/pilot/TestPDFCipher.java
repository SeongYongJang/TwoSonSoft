package com.twosonsoft.pilot;

import org.junit.Test;

import shinhanlife.smt.common.util.CryptoUtils;

public class TestPDFCipher
{

	@Test
	public void test() throws Exception
	{
		String key = "Mary has one cat";
		String inputFilename = "/Users/seongyong/Downloads/2.pdf";
		String encryptedFilename = "/Users/seongyong/Downloads/2_seed_enc.pdf";
		String decryptedFilename ="/Users/seongyong/Downloads/2_seed_dec.pdf";

		try
		{
			CryptoUtils cryptoUtils = new CryptoUtils();
			
			// Encode Test
			byte[] encryptBytes = cryptoUtils.encryptFileBySeed(key, inputFilename);
			cryptoUtils.saveFile(encryptedFilename, encryptBytes);
			
			// Decode Test
			byte[] decryptBytes = cryptoUtils.decryptFileBySeed(key, encryptedFilename);
			cryptoUtils.saveFile(decryptedFilename, decryptBytes);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

}
