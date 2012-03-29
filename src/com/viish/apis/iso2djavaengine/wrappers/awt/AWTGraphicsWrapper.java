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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;

import com.viish.apis.iso2djavaengine.wrappers.ColorWrapper;
import com.viish.apis.iso2djavaengine.wrappers.DiamondWrapper;
import com.viish.apis.iso2djavaengine.wrappers.GraphicsWrapper;
import com.viish.apis.iso2djavaengine.wrappers.ImageWrapper;

public class AWTGraphicsWrapper implements GraphicsWrapper
{
	private Graphics2D	graphics;

	public AWTGraphicsWrapper(Graphics2D g2d)
	{
		graphics = g2d;
	}

	public void drawImage(ImageWrapper img, int x, int y)
	{
		graphics.drawImage((Image) img.getRawImage(), x, y, null);
	}

	public void scale(double sx, double sy)
	{
		graphics.scale(sx, sy);
	}

	public void drawText(String text, int x, int y)
	{
		graphics.drawString(text, x, y);
	}

	public void fillDiamond(DiamondWrapper diamond)
	{
		graphics.fillPolygon((Polygon) diamond.getNativeObject());
	}

	public void setColor(ColorWrapper color)
	{
		graphics.setColor((Color) color.getNativeObject());
	}
}
