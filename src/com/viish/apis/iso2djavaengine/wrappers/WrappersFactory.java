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
import com.viish.apis.iso2djavaengine.wrappers.awt.AWTColorWrapper;
import com.viish.apis.iso2djavaengine.wrappers.awt.AWTDiamondWrapper;

public class WrappersFactory
{
	public static DiamondWrapper newDiamond(int[] Xs, int[] Ys, int n) {
		switch (Map.WRAPPER)
		{
			case AWT:
				return new AWTDiamondWrapper(Xs, Ys, n);
			default:
				return null;
		}
	}
	
	public static ColorWrapper newColor(int r, int g, int b, int a) {
		switch (Map.WRAPPER)
		{
			case AWT:
				return new AWTColorWrapper(r, g, b, a);
			default:
				return null;
		}
	}
}
