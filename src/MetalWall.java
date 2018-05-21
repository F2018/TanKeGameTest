import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
/**
 * ����ǽ(�����ƻ�)
 * @author Administrator
 *
 */
public class MetalWall {
	// ��̬ȫ�ֱ�������
	public static final int width = 30; 
	public static final int length = 30;
	private int x, y;
	TankClient tc;
	/*
	 * Toolkit �����౻���ڽ���������󶨵��ض��������߰�ʵ�֡� 
	 * getDefaultToolkit ��ȡĬ�Ϲ��߰���
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	//���þ�̬����飬��������������ʱ��ͻ����ִ�У�����ִֻ��һ��
	static {	//�洢ͼƬ
		wallImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/metalWall.gif")), };
	}
	//MetalWall�Ĺ��췽��,����x��y��tc����
	public MetalWall(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;// ��ÿ���
	}
	// ������ǽ
	public void draw(Graphics g) { 
		/*
		 * img - Ҫ���Ƶ�ָ��ͼ��
		 * x-x����
		 * y-y����
		 * ImageObserver observer--ת���˸���ͼ��ʱҪ֪ͨ�Ķ��� ��Ϊû����������Ϊnull
		 * ���ͼ���������ڸı䣬�򷵻� false�����򷵻� true��
		 */
		g.drawImage(wallImags[0], x, y, null);
	}
	//����Rectangle���캯�� ���Ƴ�ָ�������ĳ�����ʵ��(����ǽ)
	public Rectangle getRect() { 
		return new Rectangle(x, y, width, length);
	}
}
