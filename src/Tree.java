import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * �� (�ɴ���)
 * @author Administrator
 *
 */
public class Tree {
	//��̬ȫ�ֱ���
	public static final int width = 30;
	public static final int length = 30;
	int x, y;
	TankClient tc ;
	/*
	 * Toolkit �����౻���ڽ���������󶨵��ض��������߰�ʵ�֡� 
	 * getDefaultToolkit ��ȡĬ�Ϲ��߰���
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] treeImags = null;
	//���þ�̬����飬��������������ʱ��ͻ����ִ�У�����ִֻ��һ��
	static {	//�洢ͼƬ
		treeImags = new Image[]{
				tk.getImage(CommonWall.class.getResource("Images/tree.gif")),
		};
	}
	
	//Tree�Ĺ��췽��������x��y��tc����
	public Tree(int x, int y, TankClient tc) { 
		this.x = x;
		this.y = y;
		this.tc = tc;//��ÿ���
	}
	//������
	public void draw(Graphics g) {    
		/*
		 * img - Ҫ���Ƶ�ָ��ͼ��
		 * x-x����
		 * y-y����
		 * ImageObserver observer--ת���˸���ͼ��ʱҪ֪ͨ�Ķ��� ��Ϊû����������Ϊnull
		 * ���ͼ���������ڸı䣬�򷵻� false�����򷵻� true��
		 */
		g.drawImage(treeImags[0],x, y, null);
	}
	
}
