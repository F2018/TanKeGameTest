import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
/**
 * �� (�ɴ���)
 * @author Administrator
 *
 */

public class River {
	//��̬ȫ�ֱ���
	public static final int riverWidth = 40;
	public static final int riverLength = 100;
	private int x, y;
	TankClient tc ;
	/*
	 * Toolkit �����౻���ڽ���������󶨵��ض��������߰�ʵ�֡� 
	 * getDefaultToolkit ��ȡĬ�Ϲ��߰���
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] riverImags = null;
	//���þ�̬����飬��������������ʱ��ͻ����ִ�У�����ִֻ��һ��
	static {   //�洢ͼƬ
		riverImags = new Image[]{
				tk.getImage(CommonWall.class.getResource("Images/river.jpg")),
		};
	}
	
	//River�Ĺ��췽��,����x��y��tc����
	public River(int x, int y, TankClient tc) {    
		this.x = x;
		this.y = y;
		this.tc = tc;//��ÿ���
	}
	
	public void draw(Graphics g) {
		//�ڶ�ӦX��Y������
		g.drawImage(riverImags[0],x, y, null);
	}
	//��ȡ�ӵĿ�
	public static int getRiverWidth() {
		return riverWidth;
	}
	//��ȡ�ӵĳ�
	public static int getRiverLength() {
		return riverLength;
	}
	//x��y��get set����
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	//����Rectangle���캯�� ���Ƴ�ָ�������ĳ�����ʵ��(����)
	public Rectangle getRect() {
		return new Rectangle(x, y, riverWidth, riverLength);
	}


}
