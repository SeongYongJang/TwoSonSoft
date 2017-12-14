package com.twosonsoft.opensource.comp;

// 디렉토리 감시 옵져버 인터페이스
public interface InfPathWatcher
{
	void nofitfyPathChange(String mapperPath, String targetFilename);
}
