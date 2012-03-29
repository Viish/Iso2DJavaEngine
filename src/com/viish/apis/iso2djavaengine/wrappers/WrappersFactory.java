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

package com.viish.apis.iso2djavaengine.wrappers;

import com.viish.apis.iso2djavaengine.Map;
import com.viish.apis.iso2djavaengine.wrappers.android.AndroidColorWrapper;
import com.viish.apis.iso2djavaengine.wrappers.android.AndroidDiamondWrapper;
import com.viish.apis.iso2djavaengine.wrappers.awt.AWTColorWrapper;
import com.viish.apis.iso2djavaengine.wrappers.awt.AWTDiamondWrapper;

public class WrappersFactory
{
	/**
	 * @param Xs
	 *            list of X coordinates of diamond's vertexes
	 * @param Ys
	 *            list of Y coordinates of diamond's vertexes
	 * @param n
	 *            the number of vertexes
	 * @return a DimondWrapper representing the described diamond
	 */
	public static DiamondWrapper newDiamond(int[] Xs, int[] Ys, int n)
	{
		switch (Map.WRAPPER)
		{
			case AWT:
				return new AWTDiamondWrapper(Xs, Ys, n);
			case ANDROID:
				return new AndroidDiamondWrapper(Xs, Ys, n);
			default:
				return null;
		}
	}

	/**
	 * 
	 * @param r
	 *            the red value, between 0 and 255
	 * @param g
	 *            the green value, between 0 and 255
	 * @param b
	 *            the blue value, between 0 and 255
	 * @param a
	 *            the alpha value, in percentage, between 0 and 100, the lower
	 *            the more transparent
	 * @return a ColorWrapper object representing this color
	 */
	public static ColorWrapper newColor(int r, int g, int b, int a)
	{
		switch (Map.WRAPPER)
		{
			case AWT:
				return new AWTColorWrapper(r, g, b, a);
			case ANDROID:
				return new AndroidColorWrapper(r, g, b, a);
			default:
				return null;
		}
	}
}
