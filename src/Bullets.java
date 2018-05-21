import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 子弹
 * @author Administrator
 *
 */
public class Bullets {
	// 静态全局变量子弹X.Y坐标速度
	public static  int speedX = 10;
	public static  int speedY = 10; 
	//静态全局变量长宽
	public static final int width = 10;
	public static final int length = 10;

	private int x, y;
	//传入子弹方向
	Direction diretion;
	
	private boolean good;
	private boolean live = true;
	private TankClient tc;
	/*
	 * Toolkit 的子类被用于将各种组件绑定到特定本机工具包实现。 
	 * getDefaultToolkit 获取默认工具包。
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	//子弹射击图片
	private static Image[] bulletImages = null;
	// 定义Map键值对，是不同方向对应不同的弹头
	private static Map<String, Image> imgs = new HashMap<String, Image>(); 
	//设置静态代码块，在虚拟机加载类的时候就会加载执行，而且只执行一次
	static {
		// 不同方向的子弹
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
		// 加入Map容器
		imgs.put("L", bulletImages[0]); 

		imgs.put("U", bulletImages[1]);

		imgs.put("R", bulletImages[2]);

		imgs.put("D", bulletImages[3]);

	}
	// 构造函数1，传递位置和方向
	public Bullets(int x, int y, Direction dir) { 
		this.x = x;
		this.y = y;
		this.diretion = dir;
	}

	// 构造函数2，接受另外两个参数
	public Bullets(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;//获得控制
	}
	//子弹移动
	private void move() {
		//判断子弹不同方向所触发在事件
		switch (diretion) {
		case LEFT:
			x -= speedX; // 子弹不断向左进攻
			break;

		case UP:
			y -= speedY;
			break;

		case RIGHT:
			x += speedX; // 子弹不断向右
			break;

		case DOWN:
			y += speedY;
			break;

		case STOP:
			break;
		}
		//满足以下条件即为死亡
		if (x < 0 || y < 0 || x > TankClient.Fram_width
				|| y > TankClient.Fram_length) {
			live = false;
		}
	}
	//若未存活，则移除当前对象
	public void draw(Graphics g) {
		if (!live) {
			tc.bullets.remove(this);
			return;
		}
		// 选择不同方向的子弹
		switch (diretion) { 
		case LEFT:
			/*
			 * img - 要绘制的指定图像
			 * x-x坐标
			 * y-y坐标
			 * ImageObserver observer--转换了更多图像时要通知的对象 因为没有我们设置为null
			 * 如果图像像素仍在改变，则返回 false；否则返回 true。
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
		// 调用move()
		move(); 
	}
	 // 存活状态
	public boolean isLive() {
		return live;
	}
	/*
	 * int height Rectangle 的高度。 
 	   int width Rectangle 的宽度。 
       int x Rectangle 左上角的 X 坐标。 
       int y Rectangle 左上角的 Y 坐标。 
	 * 设置坐标高宽来获取一个矩形
	 */
	public Rectangle getRect() {//画出子弹
		return new Rectangle(x, y, width, length);
	}
	// 当子弹打到坦克时
	public boolean hitTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			// 对每一个坦克，调用hitTank
			if (hitTank(tanks.get(i))) { 
				return true;
			}
		}
		return false;
	}
	// 当子弹打到坦克上
	public boolean hitTank(Tank t) { 
		/*
		 * intersects 相交 
		 * 确定此 Rectangle 是否与指定的 Rectangle 相交。如果两个矩形的交集为非空，则它们是相交的
		 */
		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {

			BombTank e = new BombTank(t.getX(), t.getY(), tc);
			tc.bombTanks.add(e);
			if (t.isGood()) {
				// 受一粒子弹寿命减少50，接受4枪就死,总生命值200
				t.setLife(t.getLife() - 50); 
				// 当寿命为0时，设置寿命为死亡状态
				if (t.getLife() <= 0)
					t.setLive(false); 
			} else {
				t.setLive(false); 
			}

			this.live = false;
			// 射击成功，返回true
			return true; 
		}
		return false; // 否则返回false
	}
	// 子弹打到CommonWall上
	public boolean hitWall(CommonWall w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			this.tc.otherWall.remove(w); // 子弹打到CommonWall墙上时则移除此击中墙
			this.tc.homeWall.remove(w);
			return true;
		}
		return false;
	}
	// 子弹打到金属墙上
	public boolean hitWall(MetalWall w) { 
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			//this.tc.metalWall.remove(w); //子弹不能穿越金属墙
			return true;
		}
		return false;
	}
	// 当子弹打到家时
	public boolean hitHome() { 
		if (this.live && this.getRect().intersects(tc.home.getRect())) {
			this.live = false;
			this.tc.home.setLive(false); // 当家接受一枪时就死亡
			return true;
		}
		return false;
	}

}
