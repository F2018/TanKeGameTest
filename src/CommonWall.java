import java.awt.*;
/**
 * 公共墙(可破坏)
 * @author Administrator
 *
 */
public class CommonWall {
	// 静态全局变量长宽
	public static final int width = 20; 
	public static final int length = 20;
	int x, y;
	TankClient tc;
	/*
	 * Toolkit 的子类被用于将各种组件绑定到特定本机工具包实现。 
	 * getDefaultToolkit 获取默认工具包。
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	//设置静态代码块，在虚拟机加载类的时候就会加载执行，而且只执行一次
	static {	//存储图片
		// 储存commonWall的图片
		wallImags = new Image[] { 
		tk.getImage(CommonWall.class.getResource("Images/commonWall.gif")), };
	}
	//CommonWall的构造方法,传递x，y和tc对象
	public CommonWall(int x, int y, TankClient tc) { 
		this.x = x;
		this.y = y;
		this.tc = tc; // 获得界面控制
	}
	/*
	 * img - 要绘制的指定图像
	 * x-x坐标
	 * y-y坐标
	 * ImageObserver observer--转换了更多图像时要通知的对象 因为没有我们设置为null
	 * 如果图像像素仍在改变，则返回 false；否则返回 true。
	 */
	public void draw(Graphics g) {// 画出commonWall
		g.drawImage(wallImags[0], x, y, null);
	}
	//利用Rectangle构造函数 绘制出指定参数的长方形实例(公共墙)
	public Rectangle getRect() {  
		return new Rectangle(x, y, width, length);
	}
}
