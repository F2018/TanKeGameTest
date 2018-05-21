import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
/**
 * 河 (可穿过)
 * @author Administrator
 *
 */

public class River {
	//静态全局变量
	public static final int riverWidth = 40;
	public static final int riverLength = 100;
	private int x, y;
	TankClient tc ;
	/*
	 * Toolkit 的子类被用于将各种组件绑定到特定本机工具包实现。 
	 * getDefaultToolkit 获取默认工具包。
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] riverImags = null;
	//设置静态代码块，在虚拟机加载类的时候就会加载执行，而且只执行一次
	static {   //存储图片
		riverImags = new Image[]{
				tk.getImage(CommonWall.class.getResource("Images/river.jpg")),
		};
	}
	
	//River的构造方法,传递x，y和tc对象
	public River(int x, int y, TankClient tc) {    
		this.x = x;
		this.y = y;
		this.tc = tc;//获得控制
	}
	
	public void draw(Graphics g) {
		//在对应X，Y出画河
		g.drawImage(riverImags[0],x, y, null);
	}
	//获取河的宽
	public static int getRiverWidth() {
		return riverWidth;
	}
	//获取河的长
	public static int getRiverLength() {
		return riverLength;
	}
	//x、y的get set方法
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
	//利用Rectangle构造函数 绘制出指定参数的长方形实例(河流)
	public Rectangle getRect() {
		return new Rectangle(x, y, riverWidth, riverLength);
	}


}
