import java.util.ArrayList;
import java.util.List;

//����ͼ�ι�����
//��ArrayListʵ��
//����ֻ��Ҫһ��ShapeManager
//���Һ�����UI��϶Ƚϵ�
//�������Ϊ��̬�࣬��ʼ��ʱִ��new����
public class ShapeManager {
	private static List<Element> ls = new ArrayList<Element>();

	public static List<Element> getList() {
		return ls;
	}
	
	public static void add(Element e) {
		ls.add(e);
	}
	
	public static void remove(Element e) {
		ls.remove(e);
	}
	
	public static void removeAll() {
		ls.removeAll(ls);
	}
}
