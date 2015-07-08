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
		//如果data这个目录不存在，则创建之
		File path = new File("data/");		
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

						//每次读取1024b的文件，然后写入新的文件
						while ((i = fileInput.read(buff)) != -1) {							
							File resultFile = new File(path.getPath() + "/" + j + ".bin");
							if (!resultFile.exists()) {
								resultFile.createNewFile();
							}
							//写入新文件
							try (FileOutputStream out = new FileOutputStream(resultFile)) {
								out.write(buff);
								size = 1024 * j;
							}
							//改变进度条的进度
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
