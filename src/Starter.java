import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Starter {
	private static JFrame frm;
	public static final int wSize = 600;

	//��ʼ���棬�����Ҳ๤����ToolBar����Frame��ӻ��������Ժ�Components
	//Frameʹ�ñ߽粼�֣�����ΪToolBar���м�ΪToolBar�ĳ�Ա����cadPanel
	//Frame��ʾ����Ļ�м䣬��С�����Ե���
	public Starter() {
		ToolBar t = new ToolBar();
		frm = new JFrame("MiniCAD");
		frm.setSize(wSize, wSize);
		frm.setResizable(false);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setLayout(new BorderLayout());
		frm.add(t, BorderLayout.EAST);
		frm.add(t.cadPanel, BorderLayout.CENTER);
		frm.setLocationRelativeTo(null);
		frm.setVisible(true);
	}

	//��̬��������.cad�ļ������������׳���Ӧ�쳣
	//����IOFunction���read()��̬���������ļ����ݽ���ת��������ShapeManager���List<Shape>��
	public static void open() {
		FileDialog op = new FileDialog(frm, "Open", FileDialog.LOAD);
		op.setVisible(true);
		op.setLocationRelativeTo(null);
		String path = op.getDirectory();
		String fileName = op.getFile();
		if (path == null || fileName == null)
			return;
		try {
			IOFunction.open(path, fileName);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error", "File not found!", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error", "IO exception!", JOptionPane.ERROR_MESSAGE);
		}
	}

	//��̬������д��.cad�ļ���ͬ�������׳��쳣
	//����IOFuncution���save()��������List<Shape>�����ͼ��ת��Ϊ�ı�д���ļ���
	public static void save() {
		FileDialog sv = new FileDialog(frm, "Save", FileDialog.SAVE);
		sv.setVisible(true);
		sv.setLocationRelativeTo(null);
		String path = sv.getDirectory();
		String fileName = sv.getFile();
		if (path == null || fileName == null)
			return;
		try {
			IOFunction.save(path, fileName);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error", "IO exception!", JOptionPane.ERROR_MESSAGE);
		}
	}

	//main()�������
	public static void main(String[] args) {
		new Starter();
	}
}
