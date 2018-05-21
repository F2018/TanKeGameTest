import java.awt.*;
/**
 * ����ǽ(���ƻ�)
 * @author Administrator
 *
 */
public class CommonWall {
	// ��̬ȫ�ֱ�������
	public static final int width = 20; 
	public static final int length = 20;
	int x, y;
	TankClient tc;
	/*
	 * Toolkit �����౻���ڽ���������󶨵��ض��������߰�ʵ�֡� 
	 * getDefaultToolkit ��ȡĬ�Ϲ��߰���
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	//���þ�̬����飬��������������ʱ��ͻ����ִ�У�����ִֻ��һ��
	static {	//�洢ͼƬ
		// ����commonWall��ͼƬ
		wallImags = new Image[] { 
		tk.getImage(CommonWall.class.getResource("Images/commonWall.gif")), };
	}
	//CommonWall�Ĺ��췽��,����x��y��tc����
	public CommonWall(int x, int y, TankClient tc) { 
		this.x = x;
		this.y = y;
		this.tc = tc; // ��ý������
	}
	/*
	 * img - Ҫ���Ƶ�ָ��ͼ��
	 * x-x����
	 * y-y����
	 * ImageObserver observer--ת���˸���ͼ��ʱҪ֪ͨ�Ķ��� ��Ϊû����������Ϊnull
	 * ���ͼ���������ڸı䣬�򷵻� false�����򷵻� true��
	 */
	public void draw(Graphics g) {// ����commonWall
		g.drawImage(wallImags[0], x, y, null);
	}
	//����Rectangle���캯�� ���Ƴ�ָ�������ĳ�����ʵ��(����ǽ)
	public Rectangle getRect() {  
		return new Rectangle(x, y, width, length);
	}
}
