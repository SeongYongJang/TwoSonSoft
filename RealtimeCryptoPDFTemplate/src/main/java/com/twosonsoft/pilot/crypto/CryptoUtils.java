package com.twosonsoft.pilot.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * A utility class that encrypts or decrypts a file.
 * 
 * @author www.codejava.net
 *
 */
public class CryptoUtils
{
	// Data File 쓰기
	public void saveFile(String filename, byte[] data) throws Exception
	{
		FileOutputStream outputStream = new FileOutputStream(filename);
		outputStream.write(data);
		outputStream.close();		
	}
	// Seed 알고리즘을 이용하여 파일 내용을 암호화
	public byte[] encryptFileBySeed(String key, String inputFilename) throws Exception
	{
		try
		{
			Seed seed = new Seed();
			File inputFile = new File(inputFilename);
			
			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);
			
			byte[] outputBytes = seed.encrypt(inputBytes, key.getBytes());
			
			inputStream.close();
			
			return outputBytes;
		}
		catch (Exception ex)
		{
			throw new Exception("Error encrypting/decrypting file", ex);
		}
	}
	
	// Seed 알고리즘을 이용하여 파일 내용을 복호화
	public byte[] decryptFileBySeed(String key, String inputFilename) throws Exception
	{
		try
		{
			Seed seed = new Seed();
			File inputFile = new File(inputFilename);
			
			FileInputStream inputStream = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			inputStream.read(inputBytes);
			
			byte[] outputBytes = seed.decrypt(inputBytes, key.getBytes());
			inputStream.close();

			return outputBytes;
		}
		catch (Exception ex)
		{
			throw new Exception("Error encrypting/decrypting file", ex);
		}
	}
}