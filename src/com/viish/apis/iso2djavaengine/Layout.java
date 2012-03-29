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

public class Layout
{
	String		name;
	Sprite[][]	cells;

	public Layout(String name, Sprite[][] cells)
	{
		this.name = name;
		this.cells = cells;
	}

	public Layout(String name, int sizeX, int sizeY)
	{
		this.name = name;
		this.cells = new Sprite[sizeX][sizeY];
	}

	public String getName()
	{
		return name;
	}

	public void setCell(int x, int y, Sprite cell)
	{
		cells[x][y] = cell;
	}

	public Sprite getCell(int x, int y)
	{
		return cells[x][y];
	}
}
