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

package com.viish.apis.iso2djavaengine.wrappers.awt;

import java.awt.Polygon;

import com.viish.apis.iso2djavaengine.wrappers.DiamondWrapper;

public class AWTDiamondWrapper implements DiamondWrapper
{
	private Polygon	diamond;

	public AWTDiamondWrapper(int[] Xs, int[] Ys, int n)
	{
		diamond = new Polygon(Xs, Ys, n);
	}

	public boolean contains(int x, int y)
	{
		return diamond.contains(x, y);
	}

	public Object getNativeObject()
	{
		return diamond;
	}
}
