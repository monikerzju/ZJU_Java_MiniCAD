import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

//ʵ��Ӧ�ó������Ļ���
//�������ͼ��
//ѡ�к�����϶�ͼ�Σ���R������д��Сд��ɾ��
//ѡ�к��ٵ����ɫ��ť���Ըı���ɫ
//ѡ�к�+-��><�ֱ���Ըı��С�ʹ�ϸ
//��ѡ�е�ͼ�λ���΢�Ӵֱ��
public class CADPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	protected static final int delta = 3;
	private static Point start, end;
	private static int type = 0;
	private static boolean select = false;

	public CADPanel() {
		start = new Point();
		end = new Point();
		this.setBackground(Color.white);
		this.setFocusable(true);
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (select == true) {
					start.x = end.x;
					start.y = end.y;
					end.x = e.getX();
					end.y = e.getY();
					List<Element> ls = ShapeManager.getList();
					for (Element ele : ls)
						if (ele.select == true) {
							ele.moveTo(end.x - start.x, end.y - start.y);
						}
					repaint();
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}

		});
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				start.x = end.x = e.getX();
				start.y = end.y = e.getY();
				select = SelectTester.test(ShapeManager.getList(), start.x, start.y);
				repaint();
				requestFocus();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (select == false) {
					start.x = e.getX();
					start.y = e.getY();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (select == false) {
					end.x = e.getX();
					end.y = e.getY();
					if (Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2) >= delta * delta)
						addElement();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (select == true) {
					List<Element> ls = ShapeManager.getList();
					Element selEle = null;
					for (Element ele : ls)
						if (ele.select == true)
							selEle = ele;
					if (e.getKeyChar() == '+' || e.getKeyChar() == '=')
						selEle.prolong();
					else if (e.getKeyChar() == '_' || e.getKeyChar() == '-')
						selEle.detract();
					else if (e.getKeyChar() == '>' || e.getKeyChar() == '.')
						selEle.wider();
					else if (e.getKeyChar() == '<' || e.getKeyChar() == ',')
						selEle.thinner();
					else if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') {
						select = false;
						ShapeManager.remove(selEle);
					}
					repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});
	}

	//���µ�ͼ�ΰ���������й��죬����ӽ�ShapeManager��
	//typeΪ0����ֱ�ߣ�1������Σ�2�����ַ�����3������Բ��ʵ����ֻ֧��Բ�Σ�
	//������»���JPanel
	public void addElement() {
		type = ToolBar.getButton();
		if (type == 4)
			return;
		Element e;
		if (type == 0)
			e = new Line(start.x, start.y, end.x, end.y);
		else if (type == 1) {
			int tx, ty, tw, th;
			if (start.x < end.x) {
				tx = start.x;
				tw = end.x - start.x;
			} else {
				tx = end.x;
				tw = start.x - end.x;
			}
			if (start.y < end.y) {
				ty = start.y;
				th = end.y - start.y;
			} else {
				ty = end.y;
				th = start.y - end.y;
			}
			e = new Rectangle(tx, ty, tw, th);
		} else if (type == 2)
			e = new StringText(start.x, start.y, JOptionPane.showInputDialog("Please enter the text: "));
		else {
			int tx, ty, tw, th;
			if (start.x < end.x) {
				tx = start.x;
				tw = end.x - start.x;
			} else {
				tx = end.x;
				tw = start.x - end.x;
			}
			if (start.y < end.y)
				ty = start.y;
			else
				ty = end.y;
			th = tw;
			e = new Oval(tx, ty, tw, th);
		}
		ShapeManager.add(e);
		repaint();
	}

	//��ShapeManager�������ͼ�λ�����JPanel��
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		List<Element> ls = ShapeManager.getList();
		for (Element e : ls) {
			e.draw((Graphics2D) g);
		}
	}
}
