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

package com.viish.apis.iso2djavaengine.wrappers.android;

import android.graphics.Path;

import com.viish.apis.iso2djavaengine.wrappers.DiamondWrapper;

public class AndroidDiamondWrapper implements DiamondWrapper
{
	private Path	diamond;

	public AndroidDiamondWrapper(int[] Xs, int[] Ys, int n)
	{
		diamond = new Path();
		diamond.moveTo(Xs[0], Ys[0]);
		for (int i = 1; i < n; i++)
		{
			for (int j = 1; j < n; j++)
			{
				diamond.lineTo(Xs[i], Ys[i]);
			}
		}
		diamond.lineTo(Xs[0], Ys[0]);
	}

	public boolean contains(int x, int y)
	{
		// TODO
		return false;
	}

	public Object getNativeObject()
	{
		return diamond;
	}
}
