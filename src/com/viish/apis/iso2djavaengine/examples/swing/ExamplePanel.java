package com.viish.apis.iso2djavaengine.examples.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import com.viish.apis.iso2djavaengine.AnimationType;
import com.viish.apis.iso2djavaengine.Map;
import com.viish.apis.iso2djavaengine.Orientation;
import com.viish.apis.iso2djavaengine.Sprite;
import com.viish.apis.iso2djavaengine.SpriteType;
import com.viish.apis.iso2djavaengine.wrappers.ImageWrapper;
import com.viish.apis.iso2djavaengine.wrappers.Wrappers;
import com.viish.apis.iso2djavaengine.wrappers.WrappersFactory;
import com.viish.apis.iso2djavaengine.wrappers.awt.AWTGraphicsWrapper;
import com.viish.apis.iso2djavaengine.wrappers.awt.AWTImageWrapper;

public class ExamplePanel extends JPanel implements MouseMotionListener,
		MouseListener, MouseWheelListener {
	private static final long serialVersionUID = 1L;
	private Map map;
	private int offsetX = 0, offsetY = 0;
	private int tempX, tempY;
	public Sprite orc;
	private int sizeX = 9;
	private int sizeY = 13;

	public ExamplePanel() throws IOException {
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);

		map = new Map(sizeX, sizeY, Wrappers.AWT);

		ImageWrapper snow = new AWTImageWrapper(Toolkit.getDefaultToolkit()
				.getImage(this.getClass().getResource("/images/map/snow.png")));
		ImageWrapper grass = new AWTImageWrapper(Toolkit.getDefaultToolkit()
				.getImage(this.getClass().getResource("/images/map/grass.png")));
		ImageWrapper dirt = new AWTImageWrapper(Toolkit.getDefaultToolkit()
				.getImage(this.getClass().getResource("/images/map/dirt.png")));
		ImageWrapper rock = new AWTImageWrapper(Toolkit.getDefaultToolkit()
				.getImage(this.getClass().getResource("/images/map/rock.png")));
		ImageWrapper stone = new AWTImageWrapper(Toolkit.getDefaultToolkit()
				.getImage(this.getClass().getResource("/images/map/stone.png")));
		ImageWrapper[] cells = new ImageWrapper[] { snow, grass, dirt, rock,
				stone };
		map.setCellBordureHeight(0);

		orc = new ExampleSpriteOrc(this.getClass(), "/images/Orc/");
		map.setCharacterSprite(4, 4, orc);

		Random r = new Random();
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				Sprite cell = new Sprite(SpriteType.CELL);
				List<ImageWrapper> anim = new ArrayList<ImageWrapper>();

				int n = r.nextInt(cells.length);
				anim.add(cells[n]);

				cell.addAnimation(AnimationType.IDLE, anim,
						Orientation.NORTH_EAST);
				cell.setCurrentAnimation(AnimationType.IDLE);
				map.setMapSprite(i, j, cell);
			}
		}
	}

	private void showAvailableMovements(Sprite s) {
		int moveSpeed = 7;
		int x = s.getX();
		int y = s.getY();
		Random random = new Random();
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);
		for (int i = -moveSpeed; i <= moveSpeed; i++) {
			for (int j = -moveSpeed; j <= moveSpeed; j++) {
				if (((x + i) >= 0 && (x + i) < sizeX)
						&& ((j + y) >= 0 && (j + y) < sizeY)
						&& (Math.abs(i) + Math.abs(j) <= moveSpeed))
					map.setHighlightedSprite(x + i, y + j, true,
							WrappersFactory.newColor(r, g, b, 25));
			}

		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		map.refresh(new AWTGraphicsWrapper((Graphics2D) g), offsetX, offsetY);
	}

	public int getMapWidth() {
		return map.getWidth();
	}

	public int getMapHeight() {
		return map.getHeight();
	}

	public void mouseDragged(MouseEvent me) {
		offsetX += tempX - me.getX();
		offsetY += tempY - me.getY();
		tempX = me.getX();
		tempY = me.getY();
	}

	public void mousePressed(MouseEvent e) {
		tempX = e.getX();
		tempY = e.getY();
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() > 0)
			map.zoomOut();
		else
			map.zoomIn();
	}

	public void mouseMoved(MouseEvent me) {

	}

	public void mouseClicked(MouseEvent e) {
		Sprite s = map.getHighestSpriteAt(e.getX(), e.getY());
		if (s == null)
			return;

		if (s.getType() == SpriteType.CHARACTER) {
			if (!map.isCellHighlighted(s.getX(), s.getY())) {
				showAvailableMovements(s);
			}
		} else if (s.getType() == SpriteType.CELL) {
			if (map.isCellHighlighted(s.getX(), s.getY())) {
				map.resetAllHighlight();
				if (s.getX() == orc.getX() || s.getY() == orc.getY()) {
					map.moveCharacter(orc.getX(), orc.getY(),
							new int[] { s.getX() }, new int[] { s.getY() });
				} else {
					if (orc.getX() + s.getX() < orc.getY() + s.getY()) {
						map.moveCharacter(orc.getX(), orc.getY(), new int[] {
								orc.getX(), s.getX() },
								new int[] { s.getY(), s.getY() });
					} else {
						map.moveCharacter(orc.getX(), orc.getY(),
								new int[] { s.getX(), s.getX() }, new int[] {
										orc.getY(), s.getY() });
					}
				}
			}
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}
}