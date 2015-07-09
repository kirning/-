package view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Service {

	private static Service instance;
	File path = new File("data/");

	public static Service getInstance() {
		if (instance == null) {
			instance = new Service();
		}
		return instance;
	}

	private Service() {
		path = new File("data/");
	}

	public interface CallBack {
		void callBack(long relSize, long size);
	}

	public void clearData() {
		if (path.exists()) {
			delAllFile(path.getAbsolutePath());
			JOptionPane.showMessageDialog(null, "清空成功!");

		}
	}

	public void openData() {
		if (path.exists()) {
			try {
				Desktop.getDesktop().open(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	


	public void change(File file, CallBack callBack) {
		// 如果data这个目录不存在，则创建之
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
						File p = Utils.createDirectory(path.getPath() + "/" + file.getName());
						// 每次读取1024b的文件，然后写入新的文件
						while ((i = fileInput.read(buff)) != -1) {
							File resultFile = new File(p.getPath() + "/" + j + ".bin");
							if (!resultFile.exists()) {
								resultFile.createNewFile();
							}
							// 写入新文件
							try (FileOutputStream out = new FileOutputStream(resultFile)) {
								out.write(buff);
								size = 1024 * j;
							}
							// 改变进度条的进度
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

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径

	private void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	private boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

}
