import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
/**
 * ����(��)
 * @author Administrator
 *
 */
public class Home {
	private int x, y;
	private TankClient tc;
	// ��̬ȫ�ֱ�������
	public static final int width = 30;
	public static final int length = 30; 
	//���û��س�ʼ״��Ϊtrue(���)
	private boolean live = true;
	/*
	 * Toolkit �����౻���ڽ���������󶨵��ض��������߰�ʵ�֡� 
	 * getDefaultToolkit ��ȡĬ�Ϲ��߰���
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit(); 
	private static Image[] homeImags = null;
	//���þ�̬����飬��������������ʱ��ͻ����ִ�У�����ִֻ��һ��
	static {	//�洢ͼƬ
		homeImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/home.jpg")), };
	}
	//Home�Ĺ��췽��,����x��y��tc����
	public Home(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc; // ��ÿ���
	}
	//������Ϸ�����Ľ���
	public void gameOver(Graphics g) {
		tc.tanks.clear();//  ���б����Ƴ�����Ԫ��
		tc.metalWall.clear();
		tc.otherWall.clear();
		tc.bombTanks.clear();
		tc.theRiver.clear();
		tc.trees.clear();
		tc.bullets.clear();
		//����Tank����setLive���� ����falseȷ�����ر�Ϯ��
		tc.homeTank.setLive(false);
		// ���ò���(��Ϸ��������ʾ���������ɫ)
		Color c = g.getColor(); 
		g.setColor(Color.BLACK);
		Font f = g.getFont();
		/*Font ���캯��
		 * public Font(String name:- ��������
            int style,Font ����ʽ����  PLAINΪ��ͨ��ʽ������ 
            int size :Font �İ�ֵ��С
		 */
		g.setFont(new Font(" ", Font.PLAIN, 40));
		/*
		 * drawString:ʹ�ô�ͼ�������ĵĵ�ǰ�������ɫ������ָ�� string �������ı�
		 * ������ַ��Ļ���λ�ڴ�ͼ������������ϵ�� (x, y) λ�ô�
		 * str - Ҫ���Ƶ� string��x - x ���ꡣy - y ���ꡣ
		 */
		g.drawString("�����ˣ�", 220, 250);
		g.drawString("  ��Ϸ������ ", 220, 300);
		g.setFont(f);
		g.setColor(c);

	}
	
	public void draw(Graphics g) {
		if (live) { // ������ش�����ͼ������������
			g.drawImage(homeImags[0], x, y, null);
			//���û�����Χ��ǽ�岢���빫��ǽ�����ƻ�ǽ�壩
			for (int i = 0; i < tc.homeWall.size(); i++) {
				CommonWall w = tc.homeWall.get(i);
				w.draw(g);
			}
		} else {
			gameOver(g); // ���������Ϸ����

		}
	}

	public boolean isLive() { //���ش��״̬
		return live;
	}

	public void setLive(boolean live) { // ��������
		this.live = live;
	}
	//����Rectangle���캯�� ���Ƴ�ָ�������ĳ�����ʵ��(����)
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

}
