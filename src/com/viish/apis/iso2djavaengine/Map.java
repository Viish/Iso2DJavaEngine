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

package com.viish.apis.iso2djavaengine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.viish.apis.iso2djavaengine.wrappers.AWTGraphicsWrapper;
import com.viish.apis.iso2djavaengine.wrappers.AWTImageWrapper;


public class Map
{
	private Orientation		currentOrientation;
	private Layout			map;
	private Layout			caracters;

	private List<Integer>	walkingPath;
	private int				sizeX, sizeY, fixedX, fixedY, lastX, lastY;
	private Sprite			walkingSprite;
	boolean					isWalking	= false;
	private List<Layout>	layouts;

	public Map(int width, int height)
	{
		layouts = new ArrayList<Layout>();
		currentOrientation = Orientation.NORTH_EAST; // Actually the only one
														// working
		sizeX = width;
		sizeY = height;

		map = new Layout("Map", sizeX, sizeY);
		caracters = new Layout("Caracters", sizeX, sizeY);
		layouts.add(map);
		layouts.add(caracters);
	}

	/**
	 * Add a layout at the given index. Can be used to add Sprites between the
	 * map and the caracters for exemple.
	 */
	public void addLayout(int n, Layout layout)
	{
		layouts.add(n, layout);
	}

	/**
	 * Add a layout on top of others.
	 */
	public void appendLayout(Layout layout)
	{
		layouts.add(layout);
	}

	/**
	 * Set a Sprite for a specific cell into the choosed layout
	 */
	public boolean setSprite(String layoutName, int x, int y, Sprite cell)
	{
		for (Layout layout : layouts)
		{
			if (layout.getName().equals(layoutName))
			{
				layout.setCell(x, y, cell);
				return true;
			}
		}
		return false;
	}

	/**
	 * Get a Sprite for a specific cell into the choosed layout
	 */
	public Sprite getSprite(String layoutName, int x, int y)
	{
		for (Layout layout : layouts)
		{
			if (layout.getName().equals(layoutName)) { return layout.getCell(x,
					y); }
		}
		return null;
	}

	/**
	 * Convenience method to get a Sprite from the map
	 */
	public Sprite getMapSprite(int x, int y)
	{
		return map.getCell(x, y);
	}

	/**
	 * Convenience method to set a Sprite into the map
	 */
	public void setMapSprite(int x, int y, Sprite sprite)
	{
		map.setCell(x, y, sprite);
	}

	/**
	 * Convenience method to get a Sprite from the caracters
	 */
	public Sprite getCaracterSprite(int x, int y)
	{
		return caracters.getCell(x, y);
	}

	/**
	 * Convenience method to set a Sprite into the caracters
	 */
	public void setCaracterSprite(int x, int y, Sprite sprite)
	{
		caracters.setCell(x, y, sprite);
	}

	/**
	 * Move a caracter's Sprites from a point to another (only in direct line
	 * for now)
	 */
	public void move(int fromX, int fromY, int toX, int toY)
	{
		Sprite sprite = getSprite("Caracters", fromX, fromY);
		walkingSprite = sprite;
		lastX = fromX;
		lastY = fromY;

		walkingPath = new ArrayList<Integer>();
		if (fromX == toX)
		{
			fixedX = fromX;
			fixedY = -1;
			if (fromY < toY)
			{
				sprite.setOrientation(Orientation.NORTH_EAST);
				for (int i = fromY + 1; i <= toY; i += 1)
				{
					walkingPath.add(i);
				}
			}
			else
			{
				sprite.setOrientation(Orientation.SOUTH_WEST);
				for (int i = fromY - 1; i >= toY; i -= 1)
				{
					walkingPath.add(i);
				}
			}
		}
		else if (fromY == toY)
		{
			fixedY = fromY;
			fixedX = -1;
			if (fromX < toX)
			{
				sprite.setOrientation(Orientation.NORTH_WEST);
				for (int i = fromX + 1; i <= toX; i += 1)
				{
					walkingPath.add(i);
				}
			}
			else
			{
				sprite.setOrientation(Orientation.SOUTH_EAST);
				for (int i = fromX - 1; i >= toX; i -= 1)
				{
					walkingPath.add(i);
				}
			}
		}
		sprite.setCurrentAnimation(AnimationType.WALK);
		isWalking = true;
	}

	/**
	 * Refresh the map. Should be called at static interval.
	 */
	public void refresh(AWTGraphicsWrapper g2d, int offsetX, int offsetY)
	{
		switch (currentOrientation)
		{
		// TODO : Draw Sprites from other layouts
			case NORTH_EAST:
				for (int j = sizeY - 1; j >= 0; j--)
				{
					for (int i = 0; i < sizeX; i++)
					{
						if (getMapSprite(i, j) != null)
							drawMapImage(g2d, getMapSprite(i, j)
									.getNextAnimationImage(), j, i, offsetX,
									offsetY);
					}
				}

				moveCaracterSpriteIfNeeded();

				for (int j = sizeY - 1; j >= 0; j--)
				{
					for (int i = 0; i < sizeX; i++)
					{
						if (getCaracterSprite(i, j) != null)
							drawCaracterImage(g2d, getCaracterSprite(i, j)
									.getNextAnimationImage(),
									getMapSprite(i, j)
											.getCurrentAnimationImage(), j, i,
									offsetX, offsetY);
					}
				}
				break;
			// TODO : be able to rotate the map
			case NORTH_WEST:
				break;
			case SOUTH_EAST:
				break;
			case SOUTH_WEST:
				break;
		}
	}

	private void moveCaracterSpriteIfNeeded()
	{
		if (isWalking)
		{
			Iterator<Integer> it = walkingPath.iterator();
			if (it.hasNext())
			{
				int next = it.next();
				if (fixedX != -1)
				{
					setCaracterSprite(fixedX, next, walkingSprite);
					setCaracterSprite(fixedX, lastY, null);
					lastY = next;
				}
				else if (fixedY != -1)
				{
					setCaracterSprite(next, fixedY, walkingSprite);
					setCaracterSprite(lastX, fixedY, null);
					lastX = next;
				}
				it.remove();
			}
			else
			{
				walkingSprite.setCurrentAnimation(AnimationType.IDLE);
				isWalking = false;
				walkingPath = null;
			}
		}
	}

	private void drawMapImage(AWTGraphicsWrapper g2d, AWTImageWrapper img,
			int i, int j, int offsetX, int offsetY)
	{
		int x;
		int y;
		int margin = (j * 12 + (sizeX - i) * 12); // Hack to display in a good
													// way
													// the cells
		int cellHeight = img.getHeight();
		int cellWidth = img.getWidth();

		if (j % 2 == 0)
		{
			x = (i + (j / 2)) * cellWidth;
			x = x + ((sizeX - i) * cellWidth / 2);
			y = (j * cellHeight / 2);
			y = y + ((sizeX - i) * cellHeight / 2) - margin;
		}
		else
		{
			x = (i + (j / 2)) * cellWidth + (cellWidth / 2);
			x = x + ((sizeX - i) * cellWidth / 2);
			y = (j * cellHeight / 2);
			y = y + ((sizeX - i) * cellHeight / 2) - margin;
		}
		x -= offsetX;
		y -= offsetY;
		g2d.drawImage(img, x, y);
	}

	private void drawCaracterImage(AWTGraphicsWrapper g2d,
			AWTImageWrapper img, AWTImageWrapper cell, int i, int j,
			int offsetX, int offsetY)
	{
		int x;
		int y;
		int margin = (j * 12 + (sizeX - i) * 12); // Hack to display in a good
													// way
													// the cells
		int cellHeight = cell.getHeight();
		int cellWidth = cell.getWidth();

		if (j % 2 == 0)
		{
			x = (i + (j / 2)) * cellWidth;
			x = x + ((sizeX - i) * cellWidth / 2);
			y = (j * cellHeight / 2);
			y = y + ((sizeX - i) * cellHeight / 2) - margin;
		}
		else
		{
			x = (i + (j / 2)) * cellWidth + (cellWidth / 2);
			x = x + ((sizeX - i) * cellWidth / 2);
			y = (j * cellHeight / 2);
			y = y + ((sizeX - i) * cellHeight / 2) - margin;
		}
		x -= offsetX;
		y -= offsetY;

		x += (cellWidth - img.getWidth()) / 2;
		y += -img.getHeight() + cellHeight / 2;
		g2d.drawImage(img, x, y);
	}
}
