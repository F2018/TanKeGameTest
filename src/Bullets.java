import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * �ӵ�
 * @author Administrator
 *
 */
public class Bullets {
	// ��̬ȫ�ֱ����ӵ�X.Y�����ٶ�
	public static  int speedX = 10;
	public static  int speedY = 10; 
	//��̬ȫ�ֱ�������
	public static final int width = 10;
	public static final int length = 10;

	private int x, y;
	//�����ӵ�����
	Direction diretion;
	
	private boolean good;
	private boolean live = true;
	private TankClient tc;
	/*
	 * Toolkit �����౻���ڽ���������󶨵��ض��������߰�ʵ�֡� 
	 * getDefaultToolkit ��ȡĬ�Ϲ��߰���
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	//�ӵ����ͼƬ
	private static Image[] bulletImages = null;
	// ����Map��ֵ�ԣ��ǲ�ͬ�����Ӧ��ͬ�ĵ�ͷ
	private static Map<String, Image> imgs = new HashMap<String, Image>(); 
	//���þ�̬����飬��������������ʱ��ͻ����ִ�У�����ִֻ��һ��
	static {
		// ��ͬ������ӵ�
		bulletImages = new Image[] { 
				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletL.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletU.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletR.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletD.gif")),

		};
		// ����Map����
		imgs.put("L", bulletImages[0]); 

		imgs.put("U", bulletImages[1]);

		imgs.put("R", bulletImages[2]);

		imgs.put("D", bulletImages[3]);

	}
	// ���캯��1������λ�úͷ���
	public Bullets(int x, int y, Direction dir) { 
		this.x = x;
		this.y = y;
		this.diretion = dir;
	}

	// ���캯��2������������������
	public Bullets(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;//��ÿ���
	}
	//�ӵ��ƶ�
	private void move() {
		//�ж��ӵ���ͬ�������������¼�
		switch (diretion) {
		case LEFT:
			x -= speedX; // �ӵ������������
			break;

		case UP:
			y -= speedY;
			break;

		case RIGHT:
			x += speedX; // �ӵ���������
			break;

		case DOWN:
			y += speedY;
			break;

		case STOP:
			break;
		}
		//��������������Ϊ����
		if (x < 0 || y < 0 || x > TankClient.Fram_width
				|| y > TankClient.Fram_length) {
			live = false;
		}
	}
	//��δ�����Ƴ���ǰ����
	public void draw(Graphics g) {
		if (!live) {
			tc.bullets.remove(this);
			return;
		}
		// ѡ��ͬ������ӵ�
		switch (diretion) { 
		case LEFT:
			/*
			 * img - Ҫ���Ƶ�ָ��ͼ��
			 * x-x����
			 * y-y����
			 * ImageObserver observer--ת���˸���ͼ��ʱҪ֪ͨ�Ķ��� ��Ϊû����������Ϊnull
			 * ���ͼ���������ڸı䣬�򷵻� false�����򷵻� true��
			 */
			g.drawImage(imgs.get("L"), x, y, null);
			break;

		case UP:
			g.drawImage(imgs.get("U"), x, y, null);
			break;

		case RIGHT:
			g.drawImage(imgs.get("R"), x, y, null);
			break;

		case DOWN:
			g.drawImage(imgs.get("D"), x, y, null);
			break;

		}
		// ����move()
		move(); 
	}
	 // ���״̬
	public boolean isLive() {
		return live;
	}
	/*
	 * int height Rectangle �ĸ߶ȡ� 
 	   int width Rectangle �Ŀ�ȡ� 
       int x Rectangle ���Ͻǵ� X ���ꡣ 
       int y Rectangle ���Ͻǵ� Y ���ꡣ 
	 * ��������߿�����ȡһ������
	 */
	public Rectangle getRect() {//�����ӵ�
		return new Rectangle(x, y, width, length);
	}
	// ���ӵ���̹��ʱ
	public boolean hitTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			// ��ÿһ��̹�ˣ�����hitTank
			if (hitTank(tanks.get(i))) { 
				return true;
			}
		}
		return false;
	}
	// ���ӵ���̹����
	public boolean hitTank(Tank t) { 
		/*
		 * intersects �ཻ 
		 * ȷ���� Rectangle �Ƿ���ָ���� Rectangle �ཻ������������εĽ���Ϊ�ǿգ����������ཻ��
		 */
		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {

			BombTank e = new BombTank(t.getX(), t.getY(), tc);
			tc.bombTanks.add(e);
			if (t.isGood()) {
				// ��һ���ӵ���������50������4ǹ����,������ֵ200
				t.setLife(t.getLife() - 50); 
				// ������Ϊ0ʱ����������Ϊ����״̬
				if (t.getLife() <= 0)
					t.setLive(false); 
			} else {
				t.setLive(false); 
			}

			this.live = false;
			// ����ɹ�������true
			return true; 
		}
		return false; // ���򷵻�false
	}
	// �ӵ���CommonWall��
	public boolean hitWall(CommonWall w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			this.tc.otherWall.remove(w); // �ӵ���CommonWallǽ��ʱ���Ƴ��˻���ǽ
			this.tc.homeWall.remove(w);
			return true;
		}
		return false;
	}
	// �ӵ��򵽽���ǽ��
	public boolean hitWall(MetalWall w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			//this.tc.metalWall.remove(w); //�ӵ����ܴ�Խ����ǽ
			return true;
		}
		return false;
	}
	// ���ӵ��򵽼�ʱ
	public boolean hitHome() { 
		if (this.live && this.getRect().intersects(tc.home.getRect())) {
			this.live = false;
			this.tc.home.setLive(false); // ���ҽ���һǹʱ������
			return true;
		}
		return false;
	}

}
