package com.twosonsoft.pilot;

import java.io.File;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import shinhanlife.smt.common.util.CryptoUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController
{

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model)
	{

		return "home";
	}

	@RequestMapping(value = "/download/getDecryptedPdf.do", method = RequestMethod.GET)
	public void getDecryptedPdf(HttpServletResponse response) throws Exception
	{
		// 비지니스 로직에 따른 암호화된 파일 이름을 가져온다
		String EXTERNAL_FILE_PATH = getEncryptedFilename();
		
		// 파일 존재 여부에 대한 에러처리를 수행한다
		File file = null;
		file = new File(EXTERNAL_FILE_PATH);
		if (!file.exists())
		{
			String errorMessage = "Sorry. The file you are looking for does not exist";
			System.out.println(errorMessage);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}
		////////////////////////////////////////////////////////////////////////
		// mime 타입 결정
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null)
		{
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}

		System.out.println("mimetype : " + mimeType);

		///////////////////////////////////////////////////////////////////////////////////
		// 인코딩된 pdf 파일을 입력받아 디코딩된 바이트를 얻는다
		CryptoUtils cryptoUtils = new CryptoUtils();
		// 비지니스 로직에 따른 암호키를 얻어온다
		String key = getCryptoKey();

		// 암호화된 파일을 열어 복호화된 바이트를 얻는다
		byte[] decryptBytes = cryptoUtils.decryptFileBySeed(key, EXTERNAL_FILE_PATH);

		///////////////////////////////////////////////////////////////////////////////////
		// 웹브라우져로 다운로드를 수행하기 위한 준비 작업을 한다
		// response 헤더에 적정 데이터를 입력한다
		// 재전송을 막기 위해 캐시 컨트롤은 반드시 설정한다 => max-age 단위는 초단위
		
		// 비지니스 로직에 따른 다운로드 파일이름을 결정한다
		String downloadFilename = getDecryptedFilename();
		// 헤더 세팅
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + downloadFilename + "\""));
		response.setHeader("Cache-Control", "max-age=3600");

		response.setContentLength((int) decryptBytes.length);

		// 파일을 전송한다
		FileCopyUtils.copy(decryptBytes, response.getOutputStream());
	}

	String getEncryptedFilename()
	{
		String filename = "/Users/seongyong/Downloads/2_seed_enc.pdf";
		return filename;

	}

	String getDecryptedFilename()
	{
		String filename = "2_seed_dec.pdf";
		return filename;
	}

	String getCryptoKey()
	{
		String key = "Mary has one cat";
		return key;
	}

}
