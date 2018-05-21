import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
/**
 * 基地(家)
 * @author Administrator
 *
 */
public class Home {
	private int x, y;
	private TankClient tc;
	// 静态全局变量长宽
	public static final int width = 30;
	public static final int length = 30; 
	//设置基地初始状体为true(存活)
	private boolean live = true;
	/*
	 * Toolkit 的子类被用于将各种组件绑定到特定本机工具包实现。 
	 * getDefaultToolkit 获取默认工具包。
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit(); 
	private static Image[] homeImags = null;
	//设置静态代码块，在虚拟机加载类的时候就会加载执行，而且只执行一次
	static {	//存储图片
		homeImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/home.jpg")), };
	}
	//Home的构造方法,传递x，y和tc对象
	public Home(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc; // 获得控制
	}
	//绘制游戏结束的界面
	public void gameOver(Graphics g) {
		tc.tanks.clear();//  从列表中移除所有元素
		tc.metalWall.clear();
		tc.otherWall.clear();
		tc.bombTanks.clear();
		tc.theRiver.clear();
		tc.trees.clear();
		tc.bullets.clear();
		//调用Tank类中setLive（） 传入false确定基地被袭击
		tc.homeTank.setLive(false);
		// 设置参数(游戏结束后显示在字体和颜色)
		Color c = g.getColor(); 
		g.setColor(Color.BLACK);
		Font f = g.getFont();
		/*Font 构造函数
		 * public Font(String name:- 字体名称
            int style,Font 的样式常量  PLAIN为普通样式常量。 
            int size :Font 的磅值大小
		 */
		g.setFont(new Font(" ", Font.PLAIN, 40));
		/*
		 * drawString:使用此图形上下文的当前字体和颜色绘制由指定 string 给定的文本
		 * 最左侧字符的基线位于此图形上下文坐标系的 (x, y) 位置处
		 * str - 要绘制的 string。x - x 坐标。y - y 坐标。
		 */
		g.drawString("你输了！", 220, 250);
		g.drawString("  游戏结束！ ", 220, 300);
		g.setFont(f);
		g.setColor(c);

	}
	
	public void draw(Graphics g) {
		if (live) { // 如果基地存活，传入图像并且设置坐标
			g.drawImage(homeImags[0], x, y, null);
			//设置基地周围的墙体并传入公共墙（可破坏墙体）
			for (int i = 0; i < tc.homeWall.size(); i++) {
				CommonWall w = tc.homeWall.get(i);
				w.draw(g);
			}
		} else {
			gameOver(g); // 否则调用游戏结束

		}
	}

	public boolean isLive() { //基地存活状态
		return live;
	}

	public void setLive(boolean live) { // 设置生命
		this.live = live;
	}
	//利用Rectangle构造函数 绘制出指定参数的长方形实例(基地)
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

}
