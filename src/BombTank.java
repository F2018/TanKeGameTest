import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/**
 * ��ըЧ��
 * @author Administrator
 *
 */
public class BombTank {
	private int x, y;
	//����BombTank�ĳ�ʼ״̬
	private boolean live = true; 
	private TankClient tc;
	/*
	 * ���������� Abstract Window Toolkit ʵ��ʵ�ֵĳ����ࡣ
	 * Toolkit �����౻���ڽ���������󶨵��ض��������߰�ʵ�֡� 
	 * getDefaultToolkit ��ȡĬ�Ϲ��߰���
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	// ���÷���洢��С����ı�ըЧ��ͼ
	private static Image[] imgs = { 
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/1.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/2.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/3.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/4.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/5.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/6.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/7.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/8.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/9.gif")),
			tk.getImage(BombTank.class.getClassLoader().getResource(
					"images/10.gif")), };
	int step = 0;
	//BoombTank�Ĺ��췽��,����x��y�����tc
	public BombTank(int x, int y, TankClient tc) { 
		this.x = x;
		this.y = y;
		this.tc = tc;// ��ý������
	}
	// ������ըͼ��
	public void draw(Graphics g) { 
		// �����ж� !live=false
		if (!live) { 
			//���û�д����Ƴ�
			tc.bombTanks.remove(this);
			return;
		}
		if (step == imgs.length) {
			live = false;
			step = 0;
			return;
		}
		/*drawImage(Image img,int x,int y,ImageObserver observer)
		 * ����ָ��ͼ���е�ǰ���õ�ͼ��
		 * ͼ������Ͻ�λ�ڸ�ͼ������������ռ�� (x, y)��
		 * ͼ���е�͸�����ز�Ӱ��ô��Ѵ��ڵ����ء� 
		 * img - Ҫ���Ƶ�ָ��ͼ����� img Ϊ null����˷�����ִ���κβ�����
		 * x - x ���ꡣy - y ���ꡣobserver - ת���˸���ͼ��ʱҪ֪ͨ�Ķ��� 
		 */
		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}
