package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Service {

	private static Service instance;

	public static Service getInstance() {
		if (instance == null) {
			instance = new Service();
		}
		return instance;
	}
	
	private Service(){}

	public interface CallBack {
		void callBack(long relSize, long size);
	}

	public void change(File file, CallBack callBack) {
		File path = new File("data/");
		System.out.println(path.getAbsolutePath());
		if (!path.isDirectory() && !path.exists()) {
			path.mkdirs();
		}
		new Thread() {
			@Override
			public void run() {
				try {
					try (FileInputStream fileInput = new FileInputStream(file)) {
						int i = 0;
						long relSize = file.length();
						long size = 0;
						int j = 0;
						byte[] buff = new byte[1024];

						while ((i = fileInput.read(buff)) != -1) {
							File resultFile = new File(path.getPath() + "/" + j + ".bin");
							if (!resultFile.exists()) {
								resultFile.createNewFile();
							}
							try (FileOutputStream out = new FileOutputStream(resultFile)) {
								out.write(buff);
								size = 1024 * j;
							}
							callBack.callBack(relSize, size);
							j++;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "转化错误" + e.getMessage());
				}

			}
		}.start();
		JOptionPane.showMessageDialog(null, "转换成功!");

	}

}
