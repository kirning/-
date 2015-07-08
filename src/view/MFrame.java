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

import view.Service.CallBack;

public class MFrame extends JFrame implements CallBack{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;

	public MFrame(){
		setTitle("受能你好");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		JButton btnNewButton = new JButton("选择文件");
		getContentPane().add(btnNewButton, BorderLayout.CENTER);
		
		progressBar = new JProgressBar();
		getContentPane().add(progressBar, BorderLayout.SOUTH);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);				
				File file = fileChooser.getSelectedFile();
				if(file!=null){
					Service.getInstance().change(file, MFrame.this);
				}else{
					JOptionPane.showMessageDialog(null, "没有选中文件");
				}
			}
		});
	}

	@Override
	public void callBack(long relSize, long size) {
		long max = 0;
		if(max != relSize){
			max = relSize;
			progressBar.setMaximum((int)max);			
		}
		progressBar.setValue((int)size);
	
	}

}
