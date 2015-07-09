package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.Service.CallBack;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JList;

public class MFrame extends JFrame implements CallBack {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private JButton btnClear;
	private Service service;
	private JButton btnOpenData;
	private static String version = "2.0";
	private JPanel panel_1;
	private JList list;
	public MFrame() {
		service = Service.getInstance();
		setTitle("受能你好 "+version);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 244);
		this.setLocationRelativeTo(null);
		JButton btnNewButton = new JButton("选择文件");
		getContentPane().add(btnNewButton, BorderLayout.CENTER);

		progressBar = new JProgressBar();
		getContentPane().add(progressBar, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		
		btnOpenData = new JButton("打开文件目录");
		panel_1.add(btnOpenData);
		
		btnClear = new JButton("清空");
		panel_1.add(btnClear);
		
		list = new JList();
		panel.add(list, BorderLayout.CENTER);

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("bin", "bin"));
				fileChooser.showOpenDialog(null);
				File file = fileChooser.getSelectedFile();
				if (file != null) {
					service.change(file, MFrame.this);
				}
			}
		});
		
		initEvent();
	}

	private void initEvent() {
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.clearData();
			}
		});
		
		btnOpenData.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				service.openData();
			}
		});
		
	}

	@Override
	public void callBack(long relSize, long size) {
		long max = 0;
		if (max != relSize) {
			max = relSize;
			progressBar.setMaximum((int) max);
		}
		progressBar.setValue((int) size);

	}

}
