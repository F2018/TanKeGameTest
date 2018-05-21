import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
/**
 * 爆炸效果
 * @author Administrator
 *
 */
public class BombTank {
	private int x, y;
	//设置BombTank的初始状态
	private boolean live = true; 
	private TankClient tc;
	/*
	 * 此类是所有 Abstract Window Toolkit 实际实现的抽象超类。
	 * Toolkit 的子类被用于将各种组件绑定到特定本机工具包实现。 
	 * getDefaultToolkit 获取默认工具包。
	 */
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	// 利用反射存储从小到大的爆炸效果图
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
	//BoombTank的构造方法,传递x，y坐标和tc
	public BombTank(int x, int y, TankClient tc) { 
		this.x = x;
		this.y = y;
		this.tc = tc;// 获得界面控制
	}
	// 画出爆炸图像
	public void draw(Graphics g) { 
		// 做出判断 !live=false
		if (!live) { 
			//如果没有存活，则移除
			tc.bombTanks.remove(this);
			return;
		}
		if (step == imgs.length) {
			live = false;
			step = 0;
			return;
		}
		/*drawImage(Image img,int x,int y,ImageObserver observer)
		 * 绘制指定图像中当前可用的图像。
		 * 图像的左上角位于该图形上下文坐标空间的 (x, y)。
		 * 图像中的透明像素不影响该处已存在的像素。 
		 * img - 要绘制的指定图像。如果 img 为 null，则此方法不执行任何操作。
		 * x - x 坐标。y - y 坐标。observer - 转换了更多图像时要通知的对象。 
		 */
		g.drawImage(imgs[step], x, y, null);
		step++;
	}
}
