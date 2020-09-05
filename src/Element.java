import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

//���ĵ����ඨ�������ֻ���ͼ�ε����Ժͷ���
//�����ඨ���˹�ͬ�����Լ����ǵ�Ĭ��ֵ
//�����˳��󷽷���Ҫ�����־�����඼ʵ����Щ����
public abstract class Element {
	public Color eColor = Color.black;
	public float strokeWidth = 1.0f;
	public boolean select = false;
	private Stroke s;

	public void draw(Graphics2D g2d) {
		g2d.setColor(eColor);
		if (select == true)
			s = new BasicStroke(strokeWidth + 3.0f);
		else
			s = new BasicStroke(strokeWidth);
		g2d.setStroke(s);
	}

	public void setColor(Color c) {
		this.eColor = c;
	}

	public abstract void moveTo(int dx, int dy);

	public abstract void prolong();

	public abstract void detract();

	public void wider() {
		strokeWidth++;
	};

	public void thinner() {
		strokeWidth--;
		if (strokeWidth < 1.0f)
			strokeWidth = 1.0f;
	}
}

//ֱ����
class Line extends Element {
	public float x1, y1, x2, y2;

	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}

	public void moveTo(int dx, int dy) {
		x1 += dx;
		x2 += dx;
		y1 += dy;
		y2 += dy;
	}

	//б�ʲ�������ֱ�ӳ�
	//б�ʴ�����ͨ��б�ʼ���ֱ���Ҷ˵�x������1��y����Ҫ������
	//��Ҫע��prolong()��detract()�����ܱ�ֱ֤��б��һ�ɲ��䣬��ԭ�����ʧ��
	@Override
	public void prolong() {
		if (x1 == x2) {
			if (y1 < y2)
				y2++;
			else
				y1++;
		} else if (x1 < x2) {
			x2 += 1;
			y2 += (y2 - y1) / (x2 - x1);
		} else {
			x1 += 1;
			y1 += (y2 - y1) / (x2 - x1);
		}
	}

	@Override
	public void detract() {
		if (Math.abs((int) (x2 - x1)) >= CADPanel.delta && Math.abs((int) (y2 - y1)) >= CADPanel.delta) {
			if (x1 == x2) {
				if (y1 < y2)
					y2--;
				else
					y1--;
			} else if (x1 < x2) {
				x2 -= 1;
				y2 -= (y2 - y1) / (x2 - x1);
			} else {
				x1 -= 1;
				y1 -= (y2 - y1) / (x2 - x1);
			}
		}
	}
}

//��������
class Rectangle extends Element {
	public float x, y, width, height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		g2d.drawRect((int) x, (int) y, (int) width, (int) height);
	}

	public void moveTo(int dx, int dy) {
		x += dx;
		y += dy;
	}

	//���������ӳ��Ϳ�
	@Override
	public void prolong() {
		height += height / width;
		width++;
	}

	@Override
	public void detract() {
		height -= height / width;
		width--;
		if (width <= CADPanel.delta)
			width = CADPanel.delta;
		else if (height <= CADPanel.delta)
			height = CADPanel.delta;
	}
}

//��Բ�ࣨ����MiniCAD��֧����Բ��ֻ֧��Բ�Σ�
class Oval extends Element {
	public int x, y, width, height;

	public Oval(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		g2d.drawOval(x, y, width, height);
	}

	public void moveTo(int dx, int dy) {
		x += dx;
		y += dy;
	}

	//����ϵͳֻ֧��Բ��
	//����ֱ��ʹ��width++��height++
	@Override
	public void prolong() {
		width++;
		height++;
	}

	@Override
	public void detract() {
		width--;
		height--;
		if (width <= CADPanel.delta)
			width = CADPanel.delta;
		if (height <= CADPanel.delta)
			height = CADPanel.delta;
	}
}

//�ַ�����
class StringText extends Element {
	public float x, y;
	public String content;
	public Font f;
	public FontMetrics fm = null;

	public StringText(int x, int y, String content) {
		this.x = x;
		this.y = y;
		this.content = content == null ? "NULL" : content;
		f = new Font("SansSarif", Font.BOLD, 20);
	}

	//��������
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		fm = g2d.getFontMetrics(f);
		Font tf;
		if (select == true)
			tf = new Font("SansSarif", Font.BOLD, this.f.getSize() + 3);
		else
			tf = f;
		g2d.setFont(tf);
		g2d.drawString(content, x, y);
	}

	public void moveTo(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	//����������ֺ�
	@Override
	public void prolong() {
		f = new Font("SansSarif", Font.BOLD, f.getSize() + 1);
	}

	@Override
	public void detract() {
		f = new Font("SansSarif", Font.BOLD, f.getSize() > CADPanel.delta ? f.getSize() - 1 : CADPanel.delta);
	}
}
