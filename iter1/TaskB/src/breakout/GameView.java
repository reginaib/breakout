package breakout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


import breakout.utils.Circle;
import breakout.utils.Point;
import breakout.utils.Rect;
import breakout.utils.Vector;

@SuppressWarnings("serial")
public class GameView extends JPanel {
	

	
	long prevTimestamp = 0;
	
	public BreakoutState breakoutState;
	private Timer ballTimer;
	private boolean leftKeyDown = false;
	private boolean rightKeyDown = false;
	
	private void gameChanged() {
		repaint(10);
	}
	
	private void startMovingBalls() {
		ballTimer = new Timer(Constants.BALL_DELAYMS, actionEvent -> {
			long timestamp = System.currentTimeMillis();
			moveBalls(timestamp);
		});
		ballTimer.start();
	}
	
	/**
	 * Create a new GameView for playing breakout starting from a given breakoutState.
	 * @param breakoutState initial state for the game.
	 */
	public GameView(BreakoutState breakoutState) {
		this.breakoutState = breakoutState;

		setBackground(Color.black);
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT -> { rightKeyDown = true; break; }
				case KeyEvent.VK_LEFT -> { leftKeyDown = true; break; }		
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT -> { rightKeyDown = false; break; }
				case KeyEvent.VK_LEFT -> { leftKeyDown = false; break; }
				}
			}
		});
		startMovingBalls();
	}
	
	private void moveBalls(long timestamp) {
		if (prevTimestamp != 0) {
			int elapsedTime = (int) (timestamp - prevTimestamp);
			// very high elapsed times (for example during debugging) are annoying.
			elapsedTime = Math.min(elapsedTime, Constants.MAX_ELAPSED_TIME);

			int curPaddleDir = 0;
			if (leftKeyDown && !rightKeyDown) {
				breakoutState.movePaddleLeft(elapsedTime);
				curPaddleDir = -1;
			}
			if (!leftKeyDown && rightKeyDown) {
				breakoutState.movePaddleRight(elapsedTime);
				curPaddleDir = 1;
			}
			breakoutState.tick(curPaddleDir, elapsedTime);
			if (breakoutState.isDead()) {
				JOptionPane.showMessageDialog(this, "Game over :-(");
				System.exit(0);
			}
			if (breakoutState.isWon()) {
				JOptionPane.showMessageDialog(this, "Gewonnen!");
				System.exit(0);
			}
			gameChanged();
		}
		prevTimestamp = timestamp;
	}

	@Override
	public Dimension getPreferredSize() {
		Point size = toGUICoord(breakoutState.getBottomRight().plus(new Vector(200,200)));
		return new Dimension(size.getX(), size.getY());
	}
	
	@Override
	public boolean isFocusable() {
		return true;
	}
	
	// Convert point in the game coordinate system to the GUI coordinate system.
	private Point toGUICoord(Point loc) {
		return new Point(loc.getX()/50, loc.getY()/50).plus(new Vector(5,5));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Point topRight = toGUICoord(breakoutState.getBottomRight());
		g.setColor(Color.black);
		g.drawRect(0,0,topRight.getX(),topRight.getY());
		
		paintBlocks(g);
		paintBalls(g);
		paintPaddle(g);
		
		// domi: this fixes a visual latency bug on my system...
		Toolkit.getDefaultToolkit().sync();
	}

	private void paintPaddle(Graphics g) {
		//paddle
		PaddleState paddle = breakoutState.getPaddle();
		g.setColor( breakoutState.getCurPaddleColor() );
		Rect loc = paddle.getLocation();
		Point tl = loc.getTopLeft();
		Point br = loc.getBottomRight();
		paintPaddle(g, tl, br);
	}

	private void paintPaddle(Graphics g, Point tlg, Point brg) {
		Point tl = toGUICoord(tlg);
		Point br = toGUICoord(brg);
		g.fillRect(tl.getX(),tl.getY(),br.getX()-tl.getX(),br.getY()-tl.getY());
	}

	private void paintBalls(Graphics g) {
		//ball
		g.setColor(Constants.BALL_COLOR);
		for (Ball ball : breakoutState.getBalls()) {
			Circle c = ball.getLocation();
			Point tl = c.getTopLeftPoint();
			Point br = c.getBottomRightPoint(); 
			paintBall(g, tl, br);
		}
	}

	private void paintBall(Graphics g, Point tlg, Point brg) {
		Point tl = toGUICoord(tlg);
		Point br = toGUICoord(brg);
		g.fillOval(tl.getX(),tl.getY(),br.getX()-tl.getX(), br.getY()-tl.getY());
	}
	
	private void paintBlock(Graphics g, Point tlg, Point brg) {
		Point tl = toGUICoord(tlg);
		Point br = toGUICoord(brg);
		g.fillRect(tl.getX(),tl.getY(),br.getX()-tl.getX(),br.getY()-tl.getY());
	}

	private void paintBlocks(Graphics g) {
		// blocks
		g.setColor(Constants.BLOCK_COLOR);
		for (BlockState block : breakoutState.getBlocks()) {
			Rect loc = block.getLocation();
			Point tl = loc.getTopLeft();
			Point br = loc.getBottomRight();
			paintBlock(g, tl, br);
		}
	}

}
