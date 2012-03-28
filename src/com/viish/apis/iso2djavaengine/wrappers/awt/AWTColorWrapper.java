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

import java.awt.Color;

import com.viish.apis.iso2djavaengine.wrappers.ColorWrapper;

public class AWTColorWrapper implements ColorWrapper {
	public Color color;
	
	public AWTColorWrapper(int r, int g, int b, int a) {
		color = new Color(r, g, b, a);
	}

	public Object getNativeObject() {
		return color;
	}
}
