package storage;

import storage.main.TokenStorage;
import storage.main.FileStorage;

public class testAppendAnchor {
	static FileStorage fs;
	
	public static void main(String[] args) {
		fs = new FileStorage(FileStorage.ICS_NOAUTH_URI);
		//fs.appendAnchorText("LALALA", "HAHAHA");
	}
}
