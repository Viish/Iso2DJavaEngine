 /**
    Iso2DJavaEngine  
    Copyright (C) 2012 Sylvain "Viish" Berfini

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.viish.apis.iso2djavaengine.exemples;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.viish.apis.iso2djavaengine.AnimationType;
import com.viish.apis.iso2djavaengine.Map;
import com.viish.apis.iso2djavaengine.Orientation;
import com.viish.apis.iso2djavaengine.Sprite;
import com.viish.apis.iso2djavaengine.SpriteType;
import com.viish.apis.iso2djavaengine.wrappers.AWTGraphicsWrapper;
import com.viish.apis.iso2djavaengine.wrappers.AWTImageWrapper;


public class ExempleMain
{
	ExemplePanel	monPanel;
	boolean			walkAxelBack	= false;

	public ExempleMain()
	{
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1400, 1000);
		JPanel pane = new JPanel();
		try
		{
			monPanel = new ExemplePanel();
			JButton rotate = new JButton("Rotate Axel");
			JButton walk = new JButton("Walk Axel");
			rotate.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					monPanel.axel.rotate();
				}
			});
			walk.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (!walkAxelBack)
					{
						monPanel.move(7, 11, 7, 4);
						walkAxelBack = true;
					}
					else
					{
						monPanel.move(7, 4, 7, 11);
						walkAxelBack = false;
					}
				}
			});
			pane.setLayout(new BorderLayout());
			pane.add(monPanel, BorderLayout.CENTER);
			pane.add(rotate, BorderLayout.SOUTH);
			pane.add(walk, BorderLayout.NORTH);

			window.setContentPane(pane);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		window.setVisible(true);
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new ExempleMain();
			}
		});
	}
}

class ExemplePanel extends JPanel implements MouseMotionListener, MouseListener
{
	private static final long	serialVersionUID	= 1L;
	private Map					map;
	private int					offsetX				= 500, offsetY = 0;
	private int					tempX, tempY;
	public Sprite				axel;

	public ExemplePanel() throws IOException
	{
		addMouseListener(this);
		addMouseMotionListener(this);

		int sizeX = 9;
		int sizeY = 13;
		map = new Map(sizeX, sizeY);
		AWTImageWrapper img = new AWTImageWrapper(ImageIO.read(this
				.getClass().getResource("images/tile.png")));
		AWTImageWrapper img2 = new AWTImageWrapper(ImageIO.read(this
				.getClass().getResource("images/tile2.png")));

		Random r = new Random();
		for (int i = 0; i < sizeX; i++)
		{
			for (int j = 0; j < sizeY; j++)
			{
				Sprite cell = new Sprite(SpriteType.CELL);
				List<AWTImageWrapper> anim = new ArrayList<AWTImageWrapper>();
				if (r.nextBoolean())
					anim.add(img);
				else
					anim.add(img2);
				cell.addAnimation(AnimationType.IDLE, anim,
						Orientation.NORTH_EAST);
				cell.setCurrentAnimation(AnimationType.IDLE);
				map.setMapSprite(i, j, cell);
			}
		}

		axel = new ExempleSprite(this.getClass(), "images/axel");
		map.setCaracterSprite(7, 11, axel);

		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				repaint();
			}
		}, 0, 150); // Time for Sprites' animation speed
	}

	public void move(int fromX, int fromY, int toX, int toY)
	{
		map.move(fromX, fromY, toX, toY);
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		map.refresh(new AWTGraphicsWrapper((Graphics2D) g), offsetX, offsetY);
	}

	public void mouseDragged(MouseEvent me)
	{
		offsetX += tempX - me.getX();
		offsetY += tempY - me.getY();
		tempX = me.getX();
		tempY = me.getY();
	}

	public void mousePressed(MouseEvent e)
	{
		tempX = e.getX();
		tempY = e.getY();
	}

	public void mouseMoved(MouseEvent me)
	{

	}

	public void mouseClicked(MouseEvent e)
	{

	}

	public void mouseEntered(MouseEvent e)
	{

	}

	public void mouseExited(MouseEvent e)
	{

	}

	public void mouseReleased(MouseEvent e)
	{}
}