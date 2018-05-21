import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * 坦克
 * @author Administrator
 *
 */
public class Tank {
	// 坦克速度（游戏级别）---------可以作为扩张来设置级别，速度越快难度越高
	public static  int speedX = 6, speedY =6; 
	public static int count = 0;
	// 设置固定长宽
	public static final int width = 35, length = 35; 
	// 设置坦克初始化状态为静止
	private Direction direction = Direction.STOP; 
	// 设置坦克初始化方向为向上
	private Direction Kdirection = Direction.UP; 
	TankClient tc;
	private boolean good;
	private int x, y;
	private int oldX, oldY;
	private boolean live = true; 
	private int life = 300; // 初始生命值为300
	 // 产生一个随机数,随机模拟坦克的移动路径
	private static Random r = new Random();
	private int step = r.nextInt(10)+5 ;//产生随机路径
	//定义一个显示血量在位置
	private boolean bL = false, bU = false, bR = false, bD = false;
	/*
	 * 此类是所有 Abstract Window Toolkit 实际实现的抽象超类。
	 * Toolkit 的子类被用于将各种组件绑定到特定本机工具包实现。 
	 * getDefaultToolkit 获取默认工具包。
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImags = null; 
	static {
		//传入四个方向在坦克图片
		tankImags = new Image[] {
				tk.getImage(BombTank.class.getResource("Images/tankD.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankU.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankL.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankR.gif")), };

	}
	// Tank的构造函数1  设置敌人出现位置时调用
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	// Tank的构造函数2 设置自身出现位置时调用
	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.direction = dir;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		//进行判断：若坦克被攻击破坏
		if (!live) {
			if (!good) {
				tc.tanks.remove(this); // 删除被破坏掉的坦克
			}
			return;
		}

		if (good)
			// 创造一个血包
			new DrawBloodbBar().draw(g); 
		//根据方向绘制坦克可用的图像
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

		move();   //调用move（）进行坦克的上下左右静止移动
	}
	
	void move() {

		this.oldX = x;
		this.oldY = y;
		//选择移动方向
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
		
		//判断若当前方向不是静止的UP状态，则把当前方向赋值给初始状态
		if (this.direction != Direction.STOP) {
			this.Kdirection = this.direction;
		}
		
		//防止走出规定区域
		if (x < 0)
			x = 0;
		if (y < 40)      
			y = 40;
		
		//超过区域则恢复到边界
		if (x + Tank.width > TankClient.Fram_width)  
			x = TankClient.Fram_width - Tank.width;
		if (y + Tank.length > TankClient.Fram_length)
			y = TankClient.Fram_length - Tank.length;

		if (!good) {
			Direction[] directons = Direction.values();
			if (step == 0) {                  
				step = r.nextInt(12) + 3;  //产生随机路径
				int rn = r.nextInt(directons.length);
				direction = directons[rn];      //产生随机方向
			}
			step--;
			//产生随机数，控制敌人开火
			if (r.nextInt(40) > 38)
				this.fire();
		}
	}
	//转换坐标
	private void changToOldDir() {  
		x = oldX;
		y = oldY;
	}
	
	//键盘事件
	public void keyPressed(KeyEvent e) {  
		int key = e.getKeyCode();
		switch (key) {
		//当按下R时，重新开始游戏 
		case KeyEvent.VK_R:  
			tc.tanks.clear();  //清理
			tc.bullets.clear();
			tc.trees.clear();
			tc.otherWall.clear();
			tc.homeWall.clear();
			tc.metalWall.clear();
			tc.homeTank.setLive(false);
			//当在区域中没有坦克时，就出来坦克      
			if (tc.tanks.size() == 0) {   
				for (int i = 0; i < 20; i++) {
					if (i < 9)                              //设置坦克出现的位置
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
			//设置自己出现的位置
			tc.homeTank = new Tank(300, 560, true, Direction.STOP, tc);
			//将home重置生命
			if (!tc.home.isLive())  
				tc.home.setLive(true);
			new TankClient(); //重新创建面板
			break;
		case KeyEvent.VK_RIGHT: //监听向右键
			bR = true;
			break;
			
		case KeyEvent.VK_LEFT://监听向左键
			bL = true;
			break;
		
		case KeyEvent.VK_UP:  //监听向上键
			bU = true;
			break;
		
		case KeyEvent.VK_DOWN://监听向下键
			bD = true;
			break;
		}
		decideDirection();//调用函数
	}
	
	
	//做判断确定血包位置
	void decideDirection() {
		if (!bL && !bU && bR && !bD)  //右方
			direction = Direction.RIGHT;

		else if (bL && !bU && !bR && !bD)   //左方
			direction = Direction.LEFT;

		else if (!bL && bU && !bR && !bD)  //上方
			direction = Direction.UP;

		else if (!bL && !bU && !bR && bD) //下方
			direction = Direction.DOWN;

		else if (!bL && !bU && !bR && !bD)
			direction = Direction.STOP;  //没有按键，就保持不动 静止状态
	}
	//键盘释放事件
	public void keyReleased(KeyEvent e) {  
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F://F攻击
			fire();
			break;
			
		case KeyEvent.VK_RIGHT://用于非数字键盘向右方向键的常量
			bR = false;
			break;
		
		case KeyEvent.VK_LEFT://用于非数字键盘向左方向键的常量
			bL = false;
			break;
		
		case KeyEvent.VK_UP://用于非数字键盘向上方向键的常量
			bU = false;
			break;
		
		case KeyEvent.VK_DOWN://用于非数字键盘向下方向键的常量
			bD = false;
			break;
			

		}
		decideDirection();  
	}
	//开火方法
	public Bullets fire() {  
		if (!live)
			return null;
		//开火位置
		int x = this.x + Tank.width / 2 - Bullets.width / 2;  
		int y = this.y + Tank.length / 2 - Bullets.length / 2;
		//没有给定方向时，向原来的方向发火
		Bullets m = new Bullets(x, y + 2, good, Kdirection, this.tc);  
		tc.bullets.add(m);                                                
		return m;
	}

	//绘制
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	public boolean isLive() {
		return live;
	}
	//基地存活
	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public boolean collideWithWall(CommonWall w) {  //碰撞到普通墙时
		/*
		 * intersects
		 * 计算此 Rectangle 与指定 Rectangle 的交集。
		 * 返回一个新的 Rectangle，它表示两个矩形的交集。如果两个矩形没有相交，则结果将是一个空矩形
		 */
		if (this.live && this.getRect().intersects(w.getRect())) {
			 this.changToOldDir();    //转换到原来的方向上去
			return true;
		}
		return false;
	}

	public boolean collideWithWall(MetalWall w) {  //撞到金属墙
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();     
			return true;
		}
		return false;
	}

	public boolean collideRiver(River r) {    //撞到河流的时候
		if (this.live && this.getRect().intersects(r.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideHome(Home h) {   //撞到家的时候
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideWithTanks(java.util.List<Tank> tanks) {//撞到坦克时
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

	//life get set 方法
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	//Home血包类
	private class DrawBloodbBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			//home血条颜色
			g.setColor(Color.red);
			//位置
			g.drawRect(375, 585, width, 10);
			int w = width * life / 200;
			//绘制血量矩形的边框
			g.fillRect(375, 585, w, 10);
			g.setColor(c);
		}
	}

	public boolean eat(GetBlood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if(this.life<=100)
			this.life = this.life+100;      //每吃一个，增加100生命点
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