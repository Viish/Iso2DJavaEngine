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

import java.awt.Graphics2D;

public class AWTGraphicsWrapper
{
	private Graphics2D	graphics;

	public AWTGraphicsWrapper(Graphics2D g2d)
	{
		graphics = g2d;
	}

	public void drawImage(AWTImageWrapper img, int x, int y)
	{
		graphics.drawImage(img.getRawImage(), x, y, null);
	}
}
