import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JFlatGraphics extends JFrame implements MouseListener, MouseMotionListener, WindowListener {
	public static JFrame paintFrame;
	public static PaintPanel paintPanel;
	public static ArrayList<Color> colors = new ArrayList<>();

	public static boolean mouseDrag;
	public static boolean leftMouse;
	public static boolean rightMouse;
	public static int mouseX;
	public static int mouseY;
	public static boolean frameExit;

	public JFlatGraphics() {
		super("Paint Frame");
	}

	public static void createPaintGUI(int width, int height) {
		paintFrame = new JFlatGraphics();
		paintPanel = new PaintPanel(width, height);
		paintPanel.setSize(width, height);
		paintFrame.setSize(width, height);

		paintFrame.add(paintPanel);
		paintPanel.setVisible(true);
		paintFrame.setVisible(true);
		paintFrame.setResizable(false);
		paintPanel.setBackground(Color.WHITE);
		paintFrame.pack();

		paintPanel.addMouseListener((MouseListener) paintFrame);
		paintPanel.addMouseMotionListener((MouseMotionListener) paintFrame);
		paintFrame.addWindowListener((WindowListener) paintFrame);
		paintFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static int addColor(int r, int g, int b) {
		colors.add(new Color(r, g, b));
		return colors.size() - 1;
	}

	public static int isLeftClick() {
		if (leftMouse)
			return 1;
		return 0;
	}

	public static int isRightClick() {
		if (rightMouse)
			return 1;
		return 0;
	}

	public static int isDrag() {
		if (mouseDrag)
			return 1;
		return 0;
	}

	public static int getMouseX() {
		return mouseX;
	}

	public static int getMouseY() {
		return mouseY;
	}

	public static int getColorSize() {
		return colors.size();
	}

	public static void createLine(int x, int y, int index) {
		PaintPanel.lineColor.add(colors.get(index));
		PaintPanel.current = new ArrayList<>();
		PaintPanel.lines.add(PaintPanel.current);
		PaintPanel.current.add(new Point(x, y));
	}

	public static void addPoint(int x, int y) {
		if (PaintPanel.current != null) {
			Point pt = new Point(x, y);
			Point prev = PaintPanel.current.get(PaintPanel.current.size() - 1);
			if (PaintPanel.current.size() == 1 || !samePoint(prev, pt)) {
				PaintPanel.current.add(pt);
			}
		}
	}

	private static boolean samePoint(Point a, Point b) {
		if (a.x == b.x && a.y == b.y)
			return true;
		return false;
	}

	public static void endLine(int x, int y) {
		if (PaintPanel.current != null && PaintPanel.current.isEmpty()) {
			PaintPanel.current.add(new Point(x, y));
			PaintPanel.lines.remove(PaintPanel.current);
		}
		PaintPanel.current = null;

	}

	public static void refresh() {
		paintPanel.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		leftMouse = SwingUtilities.isLeftMouseButton(e);
		rightMouse = SwingUtilities.isRightMouseButton(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDrag = false;
		leftMouse = false;
		rightMouse = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseDrag = true;
		mouseX = e.getX();
		mouseY = e.getY();
		leftMouse = SwingUtilities.isLeftMouseButton(e);
		rightMouse = SwingUtilities.isRightMouseButton(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		leftMouse = SwingUtilities.isLeftMouseButton(e);
		rightMouse = SwingUtilities.isRightMouseButton(e);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		frameExit = true;
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	public static int paintGUIExited() {
		if (frameExit)
			return 0;
		return 1;
	}

	static class PaintPanel extends JPanel {
		static int w;
		static int h;
		static Color c;
		static List<List<Point>> lines;
		static List<Color> lineColor;
		static List<Point> current;

		public PaintPanel(int w, int h) {
			super();
			this.w = w;
			this.h = h;
			lines = new ArrayList<>();
			lineColor = new ArrayList<>();
		}

		public Dimension getPreferredSize() {
			return new Dimension(w, h);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();

			for (int i = 0; i < lines.size(); i++) {
				List<Point> pts = lines.get(i);
				g2d.setColor(lineColor.get(i));
				Point prev = null;
				// System.out.println(pts.toString());

				for (int j = 0; j < pts.size(); j++) {
					Point pt = pts.get(j);
					if (prev != null) {
						g2d.drawLine(prev.x, prev.y, pt.x, pt.y);
					}
					prev = pt;
				}
			}
		}
	}
}
