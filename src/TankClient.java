import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;

public class TankClient extends Frame implements ActionListener {

	/**
	 * 客户端
	 */
	private static final long serialVersionUID = 1L;
	public static final int Fram_width = 800; // 窗口大小
	public static final int Fram_length = 600;
	public static boolean printable = true;
	//MenuBar 类封装绑定到框架的菜单栏的平台概念
	MenuBar jmb = null;//菜单栏
	//Menu 对象是从菜单栏部署的下拉式菜单组件
	Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null;//四个菜单
	//下拉子菜单选项 
	MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
			jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null;
	//屏幕图像
	Image screenImage = null;

	Tank homeTank = new Tank(300, 560, true, Direction.STOP, this);// 实例化坦克(自家)
	GetBlood blood = new GetBlood(); // 实例化生命
	Home home = new Home(373, 545, this);// 实例化home
	// 实例化对象容器
	List<River> theRiver = new ArrayList<River>();
	List<Tank> tanks = new ArrayList<Tank>();
	List<BombTank> bombTanks = new ArrayList<BombTank>();
	List<Bullets> bullets = new ArrayList<Bullets>();
	List<Tree> trees = new ArrayList<Tree>();
	List<CommonWall> homeWall = new ArrayList<CommonWall>(); 
	List<CommonWall> otherWall = new ArrayList<CommonWall>();
	List<MetalWall> metalWall = new ArrayList<MetalWall>();

	//绘制游戏窗体背景颜色
	public void update(Graphics g) {

		screenImage = this.createImage(Fram_width, Fram_length);

		Graphics gps = screenImage.getGraphics();
		Color c = gps.getColor();
		gps.setColor(Color.cyan);
		gps.fillRect(0, 0, Fram_width, Fram_length);
		gps.setColor(c);
		framPaint(gps);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void framPaint(Graphics g) {

		Color c = g.getColor();
		g.setColor(Color.black); // 设置字体显示属性

		Font f1 = g.getFont();
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("区域内还有敌方坦克: ", 200, 70);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
		g.drawString("" + tanks.size(), 400, 70);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString("剩余生命值: ", 500, 70);
		g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
		g.drawString("" + homeTank.getLife(), 650, 70);
		g.setFont(f1);
		// 判断赢得比赛后状态
		if (tanks.size() == 0 && home.isLive() && homeTank.isLive()) {
			Font f = g.getFont();
			g.setFont(new Font("TimesRoman", Font.BOLD, 60)); 
			this.otherWall.clear();
			g.drawString("你赢了！ ", 310, 300);
			g.setFont(f);
		}
		//坦克被破坏后状态
		if (homeTank.isLive() == false) {
			Font f = g.getFont();
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			tanks.clear();
			bullets.clear();
			this.otherWall.clear();
			g.drawString("你输了！ ", 200, 130);
			g.setFont(f);
		}
		g.setColor(c);

		for (int i = 0; i < theRiver.size(); i++) { // 画出河流 可过河
			River r = theRiver.get(i);
			r.draw(g);
		}
		/*for (int i = 0; i < theRiver.size(); i++) {//不可过
			River r = theRiver.get(i);
			homeTank.collideRiver(r);//碰撞检测
			r.draw(g);
		}*/

		home.draw(g); // 画出home
		homeTank.draw(g);// 画出自己家的坦克
		homeTank.eat(blood);// 充满血--生命值

		for (int i = 0; i < bullets.size(); i++) { // 对每一个子弹
			Bullets m = bullets.get(i);
			m.hitTanks(tanks); // 每一个子弹打到坦克上
			m.hitTank(homeTank); // 每一个子弹打到自己家的坦克上时
			m.hitHome(); // 每一个子弹打到家里时

			for (int j = 0; j < metalWall.size(); j++) { // 每一个子弹打到金属墙上
				MetalWall mw = metalWall.get(j);
				m.hitWall(mw);
			}

			for (int j = 0; j < otherWall.size(); j++) {// 每一个子弹打到其他墙上
				CommonWall w = otherWall.get(j);
				m.hitWall(w);
			}

			for (int j = 0; j < homeWall.size(); j++) {// 每一个子弹打到家的墙上
				CommonWall cw = homeWall.get(j);
				m.hitWall(cw);
			}
			m.draw(g); // 画出效果图
		}

		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i); // 获得键值对的键

			for (int j = 0; j < homeWall.size(); j++) {
				CommonWall cw = homeWall.get(j);
				t.collideWithWall(cw); // 每一个坦克撞到家里的墙时
				cw.draw(g);
			}
			for (int j = 0; j < otherWall.size(); j++) { // 每一个坦克撞到家以外的墙
				CommonWall cw = otherWall.get(j);
				t.collideWithWall(cw);
				cw.draw(g);
			}
			for (int j = 0; j < metalWall.size(); j++) { // 每一个坦克撞到金属墙
				MetalWall mw = metalWall.get(j);
				t.collideWithWall(mw);
				mw.draw(g);
			}
			for (int j = 0; j < theRiver.size(); j++) {
				River r = theRiver.get(j); // 每一个坦克撞到河流时
				t.collideRiver(r);
				r.draw(g);
			}

			t.collideWithTanks(tanks); // 撞到自己的人
			t.collideHome(home);

			t.draw(g);
		}

		blood.draw(g);

		for (int i = 0; i < trees.size(); i++) { // 画出trees
			Tree tr = trees.get(i);
			tr.draw(g);
		}

		for (int i = 0; i < bombTanks.size(); i++) { // 画出爆炸效果
			BombTank bt = bombTanks.get(i);
			bt.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) { // 画出otherWall
			CommonWall cw = otherWall.get(i);
			cw.draw(g);
		}

		for (int i = 0; i < metalWall.size(); i++) { // 画出metalWall
			MetalWall mw = metalWall.get(i);
			mw.draw(g);
		}

		homeTank.collideWithTanks(tanks);
		homeTank.collideHome(home);

		for (int i = 0; i < metalWall.size(); i++) {// 撞到金属墙
			MetalWall w = metalWall.get(i);
			homeTank.collideWithWall(w);
			w.draw(g);
		}

		for (int i = 0; i < otherWall.size(); i++) {
			CommonWall cw = otherWall.get(i);
			homeTank.collideWithWall(cw);
			cw.draw(g);
		}

		for (int i = 0; i < homeWall.size(); i++) { // 家里的坦克撞到自己家
			CommonWall w = homeWall.get(i);
			homeTank.collideWithWall(w);
			w.draw(g);
		}

	}

	public TankClient() {
		// printable = false;
		// 创建菜单及菜单选项
		jmb = new MenuBar();
		jm1 = new Menu("游戏");
		jm2 = new Menu("暂停/继续");
		jm3 = new Menu("帮助");
		jm4 = new Menu("游戏级别");
		jm1.setFont(new Font("TimesRoman", Font.BOLD, 15));// 设置菜单显示的字体
		jm2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jm4.setFont(new Font("TimesRoman", Font.BOLD, 15));

		jmi1 = new MenuItem("开始新游戏");//设置子菜单
		jmi2 = new MenuItem("退出");
		jmi3 = new MenuItem("暂停");
		jmi4 = new MenuItem("继续");
		jmi5 = new MenuItem("游戏说明");
		jmi6 = new MenuItem("级别1");
		jmi7 = new MenuItem("级别2");
		jmi8 = new MenuItem("级别3");
		jmi9 = new MenuItem("级别4");
		jmi1.setFont(new Font("TimesRoman", Font.BOLD, 15));//设置子菜单显示的字体
		jmi2.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi3.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi4.setFont(new Font("TimesRoman", Font.BOLD, 15));
		jmi5.setFont(new Font("TimesRoman", Font.BOLD, 15));
		//把子菜单添加到菜单栏里
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm2.add(jmi3);
		jm2.add(jmi4);
		jm3.add(jmi5);
		jm4.add(jmi6);
		jm4.add(jmi7);
		jm4.add(jmi8);
		jm4.add(jmi9);

		jmb.add(jm1);
		jmb.add(jm2);
		jmb.add(jm4);
		jmb.add(jm3);
		
		jmi1.addActionListener(this);//添加指定的动作侦听器
		jmi1.setActionCommand("NewGame");// 设置由此菜单项引发的动作事件的命令名
		jmi2.addActionListener(this);
		jmi2.setActionCommand("Exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("Stop");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("Continue");
		jmi5.addActionListener(this);
		jmi5.setActionCommand("help");
		jmi6.addActionListener(this);
		jmi6.setActionCommand("level1"); //级别1-4
		jmi7.addActionListener(this);
		jmi7.setActionCommand("level2");
		jmi8.addActionListener(this);
		jmi8.setActionCommand("level3");
		jmi9.addActionListener(this);
		jmi9.setActionCommand("level4");

		this.setMenuBar(jmb);// 菜单Bar放到JFrame上
		this.setVisible(true); //设置可见

		for (int i = 0; i < 10; i++) { // 家的格局
			if (i < 4)
				//调用CommonWall的构造方法
				homeWall.add(new CommonWall(350, 580 - 21 * i, this));
			else if (i < 7)
				homeWall.add(new CommonWall(372 + 22 * (i - 4), 517, this));
			else
				homeWall.add(new CommonWall(416, 538 + (i - 7) * 21, this));

		}

		for (int i = 0; i < 32; i++) {
			if (i < 16) {
				otherWall.add(new CommonWall(220 + 20 * i, 300, this)); // 普通墙布局
				otherWall.add(new CommonWall(500 + 20 * i, 180, this));
				otherWall.add(new CommonWall(200, 400 + 20 * i, this));
				otherWall.add(new CommonWall(500, 400 + 20 * i, this));
			} else if (i < 32) {
				otherWall.add(new CommonWall(220 + 20 * (i - 16), 320, this));
				otherWall.add(new CommonWall(500 + 20 * (i - 16), 220, this));
				otherWall.add(new CommonWall(220, 400 + 20 * (i - 16), this));
				otherWall.add(new CommonWall(520, 400 + 20 * (i - 16), this));
			}
		}

		for (int i = 0; i < 20; i++) { // 金属墙布局
			if (i < 10) {
				metalWall.add(new MetalWall(140 + 30 * i, 150, this));
				metalWall.add(new MetalWall(600, 400 + 20 * (i), this));
			} else if (i < 20)
				metalWall.add(new MetalWall(140 + 30 * (i - 10), 180, this));
			else
				metalWall.add(new MetalWall(500 + 30 * (i - 10), 160, this));
		}

		for (int i = 0; i < 4; i++) { // 树的布局
			if (i < 4) {
				trees.add(new Tree(0 + 30 * i, 360, this));
				trees.add(new Tree(220 + 30 * i, 360, this));
				trees.add(new Tree(440 + 30 * i, 360, this));
				trees.add(new Tree(660 + 30 * i, 360, this));
			}

		}

		theRiver.add(new River(85, 100, this)); //河布局

		for (int i = 0; i < 20; i++) {  // 初始化20辆坦克
			if (i < 9) // 设置敌军坦克出现的位置
				tanks.add(new Tank(150 + 70 * i, 40, false, Direction.DOWN, this));
			else if (i < 15)
				tanks.add(new Tank(700, 140 + 50 * (i - 6), false, Direction.DOWN,
						this));
			else
				tanks
						.add(new Tank(10, 50 * (i - 12), false, Direction.DOWN,
								this));
		}

		this.setSize(Fram_width, Fram_length); // 设置界面大小 800*600
		this.setLocation(280, 50); // 设置界面出现的位置
		this.setTitle("坦克大战");

		this.addWindowListener(new WindowAdapter() { // 窗口监听关闭
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.setVisible(true);

		this.addKeyListener(new KeyMonitor());// 键盘监听
		new Thread(new PaintThread()).start(); // 线程启动
	}

	public static void main(String[] args) {
		new TankClient(); // 实例化
	}
	
	private class PaintThread implements Runnable {
		public void run() {
			// TODO Auto-generated method stub
			while (printable) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter {//扩展KeyAdapter类即可创建 KeyEvent 侦听器并重写所需事件的方法

		public void keyReleased(KeyEvent e) { // 监听键盘释放
			homeTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) { // 监听键盘按下
			homeTank.keyPressed(e);
		}

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("NewGame")) {
			printable = false;
			Object[] options = { "确定", "取消" };
			int response = JOptionPane.showOptionDialog(this, "您确认要开始新游戏！", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {

				printable = true;
				this.dispose();
				new TankClient();
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); // 线程启动
			}

		} else if (e.getActionCommand().endsWith("Stop")) {
			printable = false;
		} else if (e.getActionCommand().equals("Continue")) {

			if (!printable) {
				printable = true;
				new Thread(new PaintThread()).start(); // 线程启动
			}
			// System.out.println("继续");
		} else if (e.getActionCommand().equals("Exit")) {
			printable = false;
			Object[] options = { "确定", "取消" };
			int response = JOptionPane.showOptionDialog(this, "您确认要退出吗", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.out.println("退出");
				System.exit(0);
			} else {
				printable = true;
				new Thread(new PaintThread()).start(); // 线程启动

			}

		} else if (e.getActionCommand().equals("help")) {
			printable = false;
			JOptionPane.showMessageDialog(null, "用→ ← ↑ ↓控制方向，F键盘发射，R重新开始！",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start(); // 线程启动
		} else if (e.getActionCommand().equals("level1")) {
			Tank.count = 12;
			Tank.speedX = 6;
			Tank.speedY = 6;
			Bullets.speedX = 10;
			Bullets.speedY = 10;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("level2")) {
			Tank.count = 12;
			Tank.speedX = 10;
			Tank.speedY = 10;
			Bullets.speedX = 12;
			Bullets.speedY = 12;
			this.dispose();
			new TankClient();

		} else if (e.getActionCommand().equals("level3")) {
			Tank.count = 20;
			Tank.speedX = 14;
			Tank.speedY = 14;
			Bullets.speedX = 16;
			Bullets.speedY = 16;
			/*
			 * 释放由此 Window、其子组件及其拥有的所有子组件所使用的所有本机屏幕资源。
			 * 即这些 Component 的资源将被破坏，它们使用的所有内存都将返回到操作系统，并将它们标记为不可显示。 
			 */
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("level4")) {
			Tank.count = 20;
			Tank.speedX = 16;
			Tank.speedY = 16;
			Bullets.speedX = 18;
			Bullets.speedY = 18;
			this.dispose();
			new TankClient();
		}
	}
}
