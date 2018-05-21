import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * ̹��
 * @author Administrator
 *
 */
public class Tank {
	// ̹���ٶȣ���Ϸ����---------������Ϊ���������ü����ٶ�Խ���Ѷ�Խ��
	public static  int speedX = 6, speedY =6; 
	public static int count = 0;
	// ���ù̶�����
	public static final int width = 35, length = 35; 
	// ����̹�˳�ʼ��״̬Ϊ��ֹ
	private Direction direction = Direction.STOP; 
	// ����̹�˳�ʼ������Ϊ����
	private Direction Kdirection = Direction.UP; 
	TankClient tc;
	private boolean good;
	private int x, y;
	private int oldX, oldY;
	private boolean live = true; 
	private int life = 300; // ��ʼ����ֵΪ300
	 // ����һ�������,���ģ��̹�˵��ƶ�·��
	private static Random r = new Random();
	private int step = r.nextInt(10)+5 ;//�������·��
	//����һ����ʾѪ����λ��
	private boolean bL = false, bU = false, bR = false, bD = false;
	/*
	 * ���������� Abstract Window Toolkit ʵ��ʵ�ֵĳ����ࡣ
	 * Toolkit �����౻���ڽ���������󶨵��ض��������߰�ʵ�֡� 
	 * getDefaultToolkit ��ȡĬ�Ϲ��߰���
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImags = null; 
	static {
		//�����ĸ�������̹��ͼƬ
		tankImags = new Image[] {
				tk.getImage(BombTank.class.getResource("Images/tankD.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankU.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankL.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankR.gif")), };

	}
	// Tank�Ĺ��캯��1  ���õ��˳���λ��ʱ����
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	// Tank�Ĺ��캯��2 �����������λ��ʱ����
	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.direction = dir;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		//�����жϣ���̹�˱������ƻ�
		if (!live) {
			if (!good) {
				tc.tanks.remove(this); // ɾ�����ƻ�����̹��
			}
			return;
		}

		if (good)
			// ����һ��Ѫ��
			new DrawBloodbBar().draw(g); 
		//���ݷ������̹�˿��õ�ͼ��
		switch (Kdirection) {
		case DOWN:
			g.drawImage(tankImags[0], x, y, null);
			break;

		case UP:
			g.drawImage(tankImags[1], x, y, null);
			break;
		case LEFT:
			g.drawImage(tankImags[2], x, y, null);
			break;

		case RIGHT:
			g.drawImage(tankImags[3], x, y, null);
			break;

		}

		move();   //����move��������̹�˵��������Ҿ�ֹ�ƶ�
	}
	
	void move() {

		this.oldX = x;
		this.oldY = y;
		//ѡ���ƶ�����
		switch (direction) {  
		case LEFT:
			x -= speedX;
			break;
		case UP:
			y -= speedY;
			break;
		case RIGHT:
			x += speedX;
			break;
		case DOWN:
			y += speedY;
			break;
		case STOP:
			break;
		}
		
		//�ж�����ǰ�����Ǿ�ֹ��UP״̬����ѵ�ǰ����ֵ����ʼ״̬
		if (this.direction != Direction.STOP) {
			this.Kdirection = this.direction;
		}
		
		//��ֹ�߳��涨����
		if (x < 0)
			x = 0;
		if (y < 40)      
			y = 40;
		
		//����������ָ����߽�
		if (x + Tank.width > TankClient.Fram_width)  
			x = TankClient.Fram_width - Tank.width;
		if (y + Tank.length > TankClient.Fram_length)
			y = TankClient.Fram_length - Tank.length;

		if (!good) {
			Direction[] directons = Direction.values();
			if (step == 0) {                  
				step = r.nextInt(12) + 3;  //�������·��
				int rn = r.nextInt(directons.length);
				direction = directons[rn];      //�����������
			}
			step--;
			//��������������Ƶ��˿���
			if (r.nextInt(40) > 38)
				this.fire();
		}
	}
	//ת������
	private void changToOldDir() {  
		x = oldX;
		y = oldY;
	}
	
	//�����¼�
	public void keyPressed(KeyEvent e) {  
		int key = e.getKeyCode();
		switch (key) {
		//������Rʱ�����¿�ʼ��Ϸ 
		case KeyEvent.VK_R:  
			tc.tanks.clear();  //����
			tc.bullets.clear();
			tc.trees.clear();
			tc.otherWall.clear();
			tc.homeWall.clear();
			tc.metalWall.clear();
			tc.homeTank.setLive(false);
			//����������û��̹��ʱ���ͳ���̹��      
			if (tc.tanks.size() == 0) {   
				for (int i = 0; i < 20; i++) {
					if (i < 9)                              //����̹�˳��ֵ�λ��
						tc.tanks.add(new Tank(150 + 70 * i, 40, false,
								Direction.RIGHT, tc));
					else if (i < 15)
						tc.tanks.add(new Tank(700, 140 + 50 * (i -6), false,
								Direction.DOWN, tc));
					else
						tc.tanks.add(new Tank(10,  50 * (i - 12), false,
								Direction.LEFT, tc));
				}
			}
			//�����Լ����ֵ�λ��
			tc.homeTank = new Tank(300, 560, true, Direction.STOP, tc);
			//��home��������
			if (!tc.home.isLive())  
				tc.home.setLive(true);
			new TankClient(); //���´������
			break;
		case KeyEvent.VK_RIGHT: //�������Ҽ�
			bR = true;
			break;
			
		case KeyEvent.VK_LEFT://���������
			bL = true;
			break;
		
		case KeyEvent.VK_UP:  //�������ϼ�
			bU = true;
			break;
		
		case KeyEvent.VK_DOWN://�������¼�
			bD = true;
			break;
		}
		decideDirection();//���ú���
	}
	
	
	//���ж�ȷ��Ѫ��λ��
	void decideDirection() {
		if (!bL && !bU && bR && !bD)  //�ҷ�
			direction = Direction.RIGHT;

		else if (bL && !bU && !bR && !bD)   //��
			direction = Direction.LEFT;

		else if (!bL && bU && !bR && !bD)  //�Ϸ�
			direction = Direction.UP;

		else if (!bL && !bU && !bR && bD) //�·�
			direction = Direction.DOWN;

		else if (!bL && !bU && !bR && !bD)
			direction = Direction.STOP;  //û�а������ͱ��ֲ��� ��ֹ״̬
	}
	//�����ͷ��¼�
	public void keyReleased(KeyEvent e) {  
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F://F����
			fire();
			break;
			
		case KeyEvent.VK_RIGHT://���ڷ����ּ������ҷ�����ĳ���
			bR = false;
			break;
		
		case KeyEvent.VK_LEFT://���ڷ����ּ�����������ĳ���
			bL = false;
			break;
		
		case KeyEvent.VK_UP://���ڷ����ּ������Ϸ�����ĳ���
			bU = false;
			break;
		
		case KeyEvent.VK_DOWN://���ڷ����ּ������·�����ĳ���
			bD = false;
			break;
			

		}
		decideDirection();  
	}
	//���𷽷�
	public Bullets fire() {  
		if (!live)
			return null;
		//����λ��
		int x = this.x + Tank.width / 2 - Bullets.width / 2;  
		int y = this.y + Tank.length / 2 - Bullets.length / 2;
		//û�и�������ʱ����ԭ���ķ��򷢻�
		Bullets m = new Bullets(x, y + 2, good, Kdirection, this.tc);  
		tc.bullets.add(m);                                                
		return m;
	}

	//����
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	public boolean isLive() {
		return live;
	}
	//���ش��
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public boolean collideWithWall(CommonWall w) {  //��ײ����ͨǽʱ
		/*
		 * intersects
		 * ����� Rectangle ��ָ�� Rectangle �Ľ�����
		 * ����һ���µ� Rectangle������ʾ�������εĽ����������������û���ཻ����������һ���վ���
		 */
		if (this.live && this.getRect().intersects(w.getRect())) {
			 this.changToOldDir();    //ת����ԭ���ķ�����ȥ
			return true;
		}
		return false;
	}

	public boolean collideWithWall(MetalWall w) {  //ײ������ǽ
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();     
			return true;
		}
		return false;
	}

	public boolean collideRiver(River r) {    //ײ��������ʱ��
		if (this.live && this.getRect().intersects(r.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideHome(Home h) {   //ײ���ҵ�ʱ��
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideWithTanks(java.util.List<Tank> tanks) {//ײ��̹��ʱ
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRect().intersects(t.getRect())) {
					this.changToOldDir();
					t.changToOldDir();
					return true;
				}
			}
		}
		return false;
	}

	//life get set ����
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	//HomeѪ����
	private class DrawBloodbBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			//homeѪ����ɫ
			g.setColor(Color.red);
			//λ��
			g.drawRect(375, 585, width, 10);
			int w = width * life / 200;
			//����Ѫ�����εı߿�
			g.fillRect(375, 585, w, 10);
			g.setColor(c);
		}
	}

	public boolean eat(GetBlood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if(this.life<=100)
			this.life = this.life+100;      //ÿ��һ��������100������
			else
				this.life = 200;
			b.setLive(false);
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}