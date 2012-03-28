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

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.viish.apis.iso2djavaengine.wrappers.DiamondWrapper;
import com.viish.apis.iso2djavaengine.wrappers.GraphicsWrapper;
import com.viish.apis.iso2djavaengine.wrappers.Wrappers;
import com.viish.apis.iso2djavaengine.wrappers.WrappersFactory;

public class Map
{
	public static Wrappers	WRAPPER;
	private Orientation		currentOrientation;
	private Layout			map;
	private Layout			characters;
	private boolean[][]		highlight;
	private int				width		= -1, height = -1;
	private int				minW, maxW, minH, maxH;
	private List<Integer>	walkingPath;
	private int				sizeX, sizeY;
	private int				fixedX, fixedY;
	private int				lastX, lastY;
	private int				offsetX, offsetY;
	private Sprite			walkingSprite;
	boolean					isWalking	= false;
	private List<Layout>	layouts;
	private double			zoom		= 1.0;
	private int				cellBordureHeight;

	public Map(int width, int height, Wrappers w)
	{
		WRAPPER = w;
		layouts = new ArrayList<Layout>();
		currentOrientation = Orientation.NORTH_EAST; // Actually the only one
														// working
		sizeX = width;
		sizeY = height;

		map = new Layout("Map", sizeX, sizeY);
		characters = new Layout("Characters", sizeX, sizeY);
		highlight = new boolean[sizeX][sizeY];
		layouts.add(map);
		layouts.add(characters);
	}

	/**
	 * Add a layout at the given index. Can be used to add Sprites between the
	 * map and the characters for example. The lower the index is, sooner the
	 * sprite is displayed
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
	 * Get a Sprite for a specific cell into the chosen layout
	 */
	public Sprite getSprite(String layoutName, int x, int y)
	{
		for (Layout layout : layouts)
		{
			if (layout.getName().equals(layoutName))
			{
				return layout.getCell(x, y);
			}
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
	 * Convenience method to get a Sprite from the characters
	 */
	public Sprite getCharacterSprite(int x, int y)
	{
		return characters.getCell(x, y);
	}

	/**
	 * Convenience method to set a Sprite into the characters
	 */
	public void setCharacterSprite(int x, int y, Sprite sprite)
	{
		characters.setCell(x, y, sprite);
	}

	/**
	 * Zoom the map
	 */
	public void zoomIn()
	{
		zoom += 0.2;
	}

	/**
	 * UnZoom the map
	 */
	public void zoomOut()
	{
		zoom -= 0.2;
	}

	/**
	 * @return Return the width of the map, in pixels
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @return Return the height of the map, in pixels
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Highlight the cell Sprite at Map coordinates i, j
	 */
	public void setHighlightedSprite(int i, int j, boolean hightlight)
	{
		highlight[i][j] = hightlight;
	}
	
	/**
	 * Highlight the cell Sprite which is using Sprite s on Map or Character layout
	 */
	public void setMapHighlightedSprite(Sprite s, boolean hightlight) {
		for (int i = 0; i < sizeX; i++) 
		{
			for (int j = 0; j < sizeY; j++)
			{
				Sprite mapSprite = getMapSprite(i, j);
				Sprite charSprite = getCharacterSprite(i, j);
				if ((mapSprite != null && mapSprite.equals(s)) || (charSprite != null && charSprite.equals(s)))
				{
					setHighlightedSprite(i, j, hightlight);
					return;
				}
			}
		}
	}

	/**
	 * Define the height of the cell border if there is one (used to mask the
	 * border when a cell is in front of another)
	 */
	public void setCellBordureHeight(int w)
	{
		this.cellBordureHeight = w;
	}

	/**
	 * @return the Sprite in the characters layout at the screen's coordinates
	 *         x,y
	 */
	public Sprite getCharacterSpriteAt(int x, int y)
	{
		for (int j = 0; j < sizeY; j++)
		{
			for (int i = sizeX - 1; i >= 0; i--)
			{
				if (isPointInsideCell(x, y, i, j))
				{
					return getCharacterSprite(i, j);
				}
			}
		}

		return null;
	}

	/**
	 * @return the Sprite in the map layout at the screen's coordinates x,y
	 */
	public Sprite getMapSpriteAt(int x, int y)
	{
		for (int j = 0; j < sizeY; j++)
		{
			for (int i = sizeX - 1; i >= 0; i--)
			{
				if (isPointInsideCell(x, y, i, j))
				{
					return getMapSprite(i, j);
				}
			}
		}

		return null;
	}

	/**
	 * @return the top layout's sprite (not null) at that screen coordinates, or
	 *         null if none is found
	 */
	public Sprite getHighestSpriteAt(int x, int y)
	{
		int cellX = -1, cellY = -1;

		for (int j = 0; j < sizeY; j++)
		{
			for (int i = sizeX - 1; i >= 0; i--)
			{
				if (isPointInsideCell(x, y, i, j))
				{
					cellX = i;
					cellY = j;
					break;
				}
			}
		}
		if (cellX == -1 || cellY == -1)
			return null;

		for (int k = layouts.size() - 1; k >= 0; k--)
		{
			Layout layout = layouts.get(k);
			Sprite sprite = getSprite(layout.getName(), cellX, cellY);
			if (sprite != null)
				return sprite;
		}

		return null;
	}

	/**
	 * @return True if the coordinates x,y are in the cell i,j else return False
	 */
	private boolean isPointInsideCell(int x, int y, int i, int j)
	{
		DiamondWrapper diamondW = getDiamondForCell(i, j);
		return diamondW.contains(x, y);
	}

	/**
	 * Move a Character's Sprites from a point to another (only in direct line
	 * for now)
	 */
	public void move(int fromX, int fromY, int toX, int toY)
	{
		Sprite sprite = getSprite("Characters", fromX, fromY);
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
	public void refresh(GraphicsWrapper g2d, int offX, int offY)
	{
		offsetX = offX;
		offsetY = offY;

		AffineTransform at = g2d.getTransform();
		g2d.scale(zoom, zoom);

		switch (currentOrientation)
		{
		// TODO : Draw Sprites from other layouts
			case NORTH_EAST:
				for (int j = sizeY - 1; j >= 0; j--)
				{
					for (int i = 0; i < sizeX; i++)
					{
						if (getMapSprite(i, j) != null)
							drawMapImage(g2d, getMapSprite(i, j), i, j);
					}
				}

				if (height == -1 || width == -1)
				{
					Sprite aCell = getMapSprite(0, 0);
					width = maxW - minW + aCell.getWidth();
					height = maxH - minH + aCell.getHeight();
				}

				moveCharacterSpriteIfNeeded();

				for (int j = sizeY - 1; j >= 0; j--)
				{
					for (int i = 0; i < sizeX; i++)
					{
						if (getCharacterSprite(i, j) != null)
							drawCharacterImage(g2d, getCharacterSprite(i, j),
									getMapSprite(i, j), i, j);
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

		g2d.setTransform(at);
	}

	/**
	 * Check if a Sprite is moving, and make it advance on it's path if needed
	 */
	private void moveCharacterSpriteIfNeeded()
	{
		if (isWalking)
		{
			Iterator<Integer> it = walkingPath.iterator();
			if (it.hasNext())
			{
				int next = it.next();
				if (fixedX != -1)
				{
					setCharacterSprite(fixedX, next, walkingSprite);
					setCharacterSprite(fixedX, lastY, null);
					lastY = next;
				}
				else if (fixedY != -1)
				{
					setCharacterSprite(next, fixedY, walkingSprite);
					setCharacterSprite(lastX, fixedY, null);
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

	/**
	 * @return the X coordinate of the top left corner of the picture to draw
	 */
	private int calculatePositionX(int i, int j, int cellWidth)
	{
		int x;

		if (i % 2 == 0)
		{
			x = (j + (i / 2)) * cellWidth;
		}
		else
		{
			x = (j + (i / 2)) * cellWidth + (cellWidth / 2);
		}
		x -= (j * cellWidth / 2);
		x -= offsetX;

		return x;
	}

	/**
	 * @return the Y coordinate of the top left corner of the picture to draw
	 */
	private int calculatePositionY(int i, int j, int cellHeight)
	{
		int y;
		int margin = (i * cellBordureHeight + ((sizeX - j) * cellBordureHeight));

		y = (i * cellHeight / 2);
		y += ((sizeY - j - 1) * cellHeight / 2) - margin;
		y -= offsetY;

		return y;
	}

	/**
	 * Draw a Sprite's image, used only for cell map
	 */
	private void drawMapImage(GraphicsWrapper g2d, Sprite img, int i, int j)
	{
		int x = calculatePositionX(i, j, img.getWidth());
		int y = calculatePositionY(i, j, img.getHeight());

		if (width == -1 || height == -1)
		{
			minW = (x < minW ? x : minW);
			maxW = (x > maxW ? x : maxW);
			minH = (x < minH ? x : minH);
			maxH = (x > maxH ? x : maxH);
		}

		g2d.drawImage(img.getNextAnimationImage(), x, y);
		
		if (highlight[i][j])
			drawHighlight(g2d, i, j);
	}

	/**
	 * Draw a Sprite's image, used only for characters
	 */
	private void drawCharacterImage(GraphicsWrapper g2d, Sprite img,
			Sprite cell, int i, int j)
	{
		int cellWidth = cell.getWidth();
		int cellHeight = cell.getHeight();
		int x = calculatePositionX(i, j, cellWidth);
		int y = calculatePositionY(i, j, cellHeight);

		x += (cellWidth - img.getWidth()) / 2;
		y += -img.getHeight() + cellHeight / 2;
		g2d.drawImage(img.getNextAnimationImage(), x, y);
	}
	
	/**
	 * Draw the highlight for the Sprite at Map coordinates i, j
	 */
	private void drawHighlight(GraphicsWrapper g2d, int i, int j) {
		int transparency = 50;
		Color color = new Color(255, 255, 0, 255 * transparency / 100);
		g2d.setColor(color);
		
		DiamondWrapper diamondW = getDiamondForCell(i, j);
		g2d.fillDiamond(diamondW);
	}
	
	/**
	 * @return a Diamond shape representing the cell at Map coordinates i, j
	 */
	private DiamondWrapper getDiamondForCell(int i, int j) 
	{
		int cellWidth = (int) (getMapSprite(0, 0).getWidth());
		int cellHeight = (int) (getMapSprite(0, 0).getHeight());
		int cellLeftTopCornerX = (int) (calculatePositionX(i, j, cellWidth) * zoom);
		int cellLeftTopCornerY = (int) (calculatePositionY(i, j, cellHeight) * zoom);
		int cellMaxEdgeX = (int) ((cellWidth / 2) * zoom);
		int cellMaxEdgeY = (int) (((cellHeight - cellBordureHeight) / 2) * zoom);
		int cellCenterX = cellLeftTopCornerX + cellMaxEdgeX;
		int cellCenterY = cellLeftTopCornerY + cellMaxEdgeY;

		DiamondWrapper diamondW = WrappersFactory.newDiamondWrapper(new int[] {
				cellLeftTopCornerX, cellCenterX, cellCenterX + cellMaxEdgeX,
				cellCenterX }, new int[] { cellCenterY, cellLeftTopCornerY,
				cellCenterY, cellCenterY + cellMaxEdgeY }, 4);
		
		return diamondW;
	}
}
