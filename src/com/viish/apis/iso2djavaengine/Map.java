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

import com.viish.apis.iso2djavaengine.wrappers.ColorWrapper;
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
	private HighLight[][]	highlight;
	private List<Point>		walkingPath;
	private int				sizeX, sizeY;
	private int				lastX, lastY;
	private int				offsetX, offsetY;
	private Sprite			walkingSprite, backupSprite;
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
		highlight = new HighLight[sizeX][sizeY];
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
				cell.setXY(x, y);
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
		sprite.setXY(x, y);
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

		if (sprite != null)
			sprite.setXY(x, y);
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
		if (zoom - 0.2 > 0)
			zoom -= 0.2;
	}

	/**
	 * @return Return the width of the map, in pixels
	 */
	public int getWidth()
	{
		Sprite aCell = getMapSprite(0, 0);
		int minX = calculatePositionX(0, 0, aCell.getWidth());
		int maxX = calculatePositionX(sizeX - 1, sizeY - 1, aCell.getWidth());
		return maxX - minX + aCell.getWidth();
	}

	/**
	 * @return Return the height of the map, in pixels
	 */
	public int getHeight()
	{
		Sprite aCell = getMapSprite(0, 0);
		int minY = calculatePositionX(0, sizeY - 1, aCell.getWidth());
		int maxY = calculatePositionX(sizeX - 1, 0, aCell.getWidth());
		return maxY - minY + aCell.getWidth();
	}

	/**
	 * Highlight the cell Sprite at Map coordinates i, j
	 */
	public void setHighlightedSprite(int i, int j, boolean hightlight)
	{
		highlight[i][j] = new HighLight(hightlight);
	}

	/**
	 * Highlight the cell Sprite at Map coordinates i, j
	 */
	public void setHighlightedSprite(int i, int j, boolean hightlight,
			ColorWrapper color)
	{
		highlight[i][j] = new HighLight(hightlight, color);
	}

	/**
	 * Highlight the cell Sprite which is using Sprite s on Map or Character
	 * layout
	 */
	public void setMapSpriteHighlighted(Sprite s, boolean hightlight)
	{
		for (int i = 0; i < sizeX; i++)
		{
			for (int j = 0; j < sizeY; j++)
			{
				Sprite mapSprite = getMapSprite(i, j);
				Sprite charSprite = getCharacterSprite(i, j);
				if ((mapSprite != null && mapSprite.equals(s))
						|| (charSprite != null && charSprite.equals(s)))
				{
					setHighlightedSprite(i, j, hightlight);
					return;
				}
			}
		}
	}

	/**
	 * Reset all cells to not highlighted
	 */
	public void resetAllHighlight()
	{
		for (int i = 0; i < sizeX; i++)
		{
			for (int j = 0; j < sizeY; j++)
			{
				highlight[i][j] = null;
			}
		}
	}

	/**
	 * @return True if the cell at Map coordinates x,y is highlighted, else
	 *         return False
	 */
	public boolean isCellHighlighted(int x, int y)
	{
		return highlight[x][y] != null && highlight[x][y].isEnabled();
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
		DiamondWrapper diamondW = getDiamondForCell(i, j, true);
		return diamondW.contains(x, y);
	}

	/**
	 * Move a character from it's actual position to the ones supplied
	 */
	public void moveCharacter(int fromX, int fromY, int[] toXs, int[] toYs)
	{
		Sprite sprite = getSprite("Characters", fromX, fromY);

		if (toXs.length != toYs.length || sprite == null)
			return;

		walkingSprite = sprite;
		lastX = fromX;
		lastY = fromY;

		walkingPath = new ArrayList<Point>();
		addToPath(walkingPath, walkingSprite, fromX, fromY, toXs[0], toYs[0]);

		for (int i = 1; i < toXs.length; i++)
		{
			addToPath(walkingPath, walkingSprite, toXs[i - 1], toYs[i - 1],
					toXs[i], toYs[i]);
		}

		sprite.setCurrentAnimation(AnimationType.WALK);
		isWalking = true;
	}

	/**
	 * @return the list of points the character will walk on if we ask it for
	 *         this movement
	 */
	public List<Point> simuleCharacterMovement(int fromX, int fromY,
			int[] toXs, int[] toYs)
	{
		Sprite sprite = getSprite("Characters", fromX, fromY);

		if (toXs.length != toYs.length || sprite == null)
			return null;

		List<Point> simuledPath = new ArrayList<Point>();
		addToPath(simuledPath, sprite, fromX, fromY, toXs[0], toYs[0]);

		for (int i = 1; i < toXs.length; i++)
		{
			addToPath(simuledPath, sprite, toXs[i - 1], toYs[i - 1], toXs[i],
					toYs[i]);
		}

		return simuledPath;
	}

	/**
	 * Move a Character's Sprites from a point to another (only in direct line
	 * for now)
	 */
	private List<Point> addToPath(List<Point> path, Sprite sprite, int fromX,
			int fromY, int toX, int toY)
	{
		if (fromX == toX)
		{
			if (fromY < toY)
			{
				for (int i = fromY + 1; i <= toY; i += 1)
				{
					path.add(new Point(fromX, i));
				}
			}
			else
			{
				for (int i = fromY - 1; i >= toY; i -= 1)
				{
					path.add(new Point(fromX, i));
				}
			}
		}
		else if (fromY == toY)
		{
			if (fromX < toX)
			{
				for (int i = fromX + 1; i <= toX; i += 1)
				{
					path.add(new Point(i, fromY));
				}
			}
			else
			{
				for (int i = fromX - 1; i >= toX; i -= 1)
				{
					path.add(new Point(i, fromY));
				}
			}
		}

		return path;
	}

	/**
	 * Refresh the map. Should be called at regular interval.
	 */

	public void refresh(GraphicsWrapper g2d, int offX, int offY)
	{
		offsetX = offX;
		offsetY = offY;

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
	}

	/**
	 * Check if a Sprite is moving, and make it advance on it's path if needed
	 */
	private void moveCharacterSpriteIfNeeded()
	{
		int backupX = -1, backupY = -1;
		Sprite localBackupSprite = null;
		if (backupSprite != null)
		{
			backupX = lastX;
			backupY = lastY;
			localBackupSprite = backupSprite;
		}

		if (isWalking)
		{
			Iterator<Point> it = walkingPath.iterator();
			if (it.hasNext())
			{
				Point next = it.next();
				int nextY = next.getY();
				int nextX = next.getX();
				if (lastX == nextX)
				{
					if (nextY > lastY)
						walkingSprite.setOrientation(Orientation.NORTH_EAST);
					else
						walkingSprite.setOrientation(Orientation.SOUTH_WEST);

					backupSprite = getCharacterSprite(nextX, nextY);
					setCharacterSprite(nextX, nextY, walkingSprite);
					walkingSprite.setXY(nextX, nextY);
					setCharacterSprite(nextX, lastY, null);
					lastY = nextY;
				}
				else if (lastY == nextY)
				{
					if (nextX > lastX)
						walkingSprite.setOrientation(Orientation.SOUTH_EAST);
					else
						walkingSprite.setOrientation(Orientation.NORTH_WEST);

					backupSprite = getCharacterSprite(nextX, nextY);
					setCharacterSprite(nextX, nextY, walkingSprite);
					walkingSprite.setXY(nextX, nextY);
					setCharacterSprite(lastX, nextY, null);
					lastX = nextX;
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

		if (localBackupSprite != null && backupX != -1 && backupY != -1)
		{
			setCharacterSprite(backupX, backupY, localBackupSprite);
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

		g2d.drawImage(img.getNextAnimationImage(), x, y);

		if (isCellHighlighted(i, j))
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
	private void drawHighlight(GraphicsWrapper g2d, int i, int j)
	{
		g2d.setColor(highlight[i][j].getColor());
		DiamondWrapper diamondW = getDiamondForCell(i, j, false);
		g2d.fillDiamond(diamondW);
	}

	/**
	 * @return a Diamond shape representing the cell at Map coordinates i, j
	 */
	private DiamondWrapper getDiamondForCell(int i, int j, boolean useZoom)
	{
		double zoom = this.zoom;
		if (!useZoom)
			zoom = 1;

		int cellWidth = (int) (getMapSprite(0, 0).getWidth());
		int cellHeight = (int) (getMapSprite(0, 0).getHeight());
		int cellLeftTopCornerX = (int) (calculatePositionX(i, j, cellWidth) * zoom);
		int cellLeftTopCornerY = (int) (calculatePositionY(i, j, cellHeight) * zoom);
		int cellMaxEdgeX = (int) ((cellWidth / 2) * zoom);
		int cellMaxEdgeY = (int) (((cellHeight - cellBordureHeight) / 2) * zoom);
		int cellCenterX = cellLeftTopCornerX + cellMaxEdgeX;
		int cellCenterY = cellLeftTopCornerY + cellMaxEdgeY;

		DiamondWrapper diamondW = WrappersFactory.newDiamond(new int[] {
				cellLeftTopCornerX, cellCenterX, cellCenterX + cellMaxEdgeX,
				cellCenterX }, new int[] { cellCenterY, cellLeftTopCornerY,
				cellCenterY, cellCenterY + cellMaxEdgeY }, 4);

		return diamondW;
	}
}
