package com.dosvales.vagoapp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FacesUtil {
	
	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}
	
	public static void uploadFile(String path, InputStream inputStream) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(path);
		byte[] buffer = new byte[6124];
		int bulk;
		while (true) {
			bulk = inputStream.read(buffer);
			if (bulk < 0) {
				break;
			}
			fileOutputStream.write(buffer, 0, bulk);
			fileOutputStream.flush();
		}
		fileOutputStream.close();
		inputStream.close();
	}
	
}
