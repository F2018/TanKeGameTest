import java.awt.*;
import java.util.Random;
/**
 * Ѫ��
 * @author Administrator
 *
 */
public class GetBlood {
	
	public static final int width = 36;
	public static final int length = 36;

	private int x, y;
	TankClient tc;
	//random() �������ڷ���һ����������������ΧΪ 0.0 =< Math.random < 1.0
	private static Random r = new Random();

	int step = 0; 
	private boolean live = false;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bloodImags = null;
	static {
		bloodImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/hp.png")), };
	}

	private int[][] poition = { { 155, 196 }, { 500, 58 }, { 80, 340 },
			{ 99, 199 }, { 345, 456 }, { 123, 321 }, { 258, 413 } };

	public void draw(Graphics g) {
		//r.nextInt(100)���᷵��һ�����ڵ���0С��100�������
		if (r.nextInt(100) > 98) {
			this.live = true;
			move();
		}
		//����� �򷵻�
		if (!live)
			return;
		g.drawImage(bloodImags[0], x, y, null);

	}

	private void move() {
		step++;
		if (step == poition.length) {
			step = 0;
		}
		x = poition[step][0];
		y = poition[step][1];
		
	}
	//���س�����ʵ��
	public Rectangle getRect() { 
		return new Rectangle(x, y, width, length);
	}
	//�ж��Ƿ񻹻���
	public boolean isLive() {
		return live;
	}
	//��������
	public void setLive(boolean live) {  
		this.live = live;
	}

}
