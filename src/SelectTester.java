import java.util.List;

//�������ڼ��CADPanel�ϵ�ͼ���Ƿ�ѡ��
//����ӵ��һ��public����test()�����෽����test()�б�����
//lineDist()���Լ��������λ����ֱ�ߵľ��룬���С��delta������Ϊѡ��
//����ζ��ֱ�߱�ֵ�ʱ�򣬺ܿ��ܼ�ʹ����ֱ�����γɵľ����ڲ���Ҳ����ѡ��ֱ��
//inRect()�����λ���Ƿ��ڳ�������
//inOval()�����λ���Ƿ���Բ���ڣ������㵽Բ�ĵľ��룬�Ͱ뾶���Ƚ�
//inText()�����λ���Ƿ����ַ����ڣ��������ͨ��FontManager��ʵ��
public class SelectTester {
	private static final int delta = 5;

	public static boolean test(List<Element> ls, int x, int y) {
		boolean flag = false;
		for (Element e : ls) {
			e.select = false;
			if (flag == false) {
				if (e instanceof Line) {
					if (Math.abs(lineDist((Line) e, x, y)) <= delta) {
						e.select = true;
						flag = true;
					}
				} else if (e instanceof Rectangle) {
					if (inRect((Rectangle) e, x, y) == true) {
						e.select = true;
						flag = true;
					}
				} else if (e instanceof StringText) {
					if (inText((StringText) e, x, y) == true) {
						e.select = true;
						flag = true;
					}
				} else {
					if (inOval((Oval) e, x, y) == true) {
						e.select = true;
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	private static int minOfTwo(int a, int b) {
		if (a > b)
			return b;
		return a;
	}

	private static int lineDist(Line e, int x, int y) {
		if (e.x1 == e.x2) {
			if ((y <= e.y1 && y <= e.y2) || (y >= e.y1 && y >= e.y2))
				return minOfTwo((int) Math.sqrt(Math.pow(x - e.x1, 2) + Math.pow(y - e.y1, 2)),
						(int) Math.sqrt(Math.pow(x - e.x2, 2) + Math.pow(y - e.y2, 2)));
			else
				return Math.abs(x - (int) e.x1);
		} else {
			if ((x <= e.x1 && x <= e.x2) || (x >= e.x1 && x >= e.x2))
				return minOfTwo((int) Math.sqrt(Math.pow(x - e.x1, 2) + Math.pow(y - e.y1, 2)),
						(int) Math.sqrt(Math.pow(x - e.x2, 2) + Math.pow(y - e.y2, 2)));
			else {
				float k = 1.0f * (e.y1 - e.y2) / (e.x1 - e.x2);
				float b = 1.0f * e.y1 - k * e.x1;
				return Math.abs((int) (k * x + b - y));
			}
		}
	}

	private static boolean inRect(Rectangle e, int x, int y) {
		return x >= e.x && x <= e.x + e.width && y >= e.y && y <= e.y + e.height;
	}

	private static boolean inText(StringText e, int x, int y) {
		return x >= e.x && x <= e.x + e.fm.stringWidth(e.content) && y <= e.y && y >= e.y - e.fm.getHeight();
	}

	private static boolean inOval(Oval e, int x, int y) {
		return Math.pow(x - e.x - e.height / 2, 2) + Math.pow(y - e.y - e.height / 2, 2) <= Math.pow(e.height / 2, 2);
	}
}
