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

import com.viish.apis.iso2djavaengine.wrappers.ColorWrapper;
import com.viish.apis.iso2djavaengine.wrappers.WrappersFactory;

public class HighLight
{
	private boolean			enabled;
	private ColorWrapper	color;

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enable)
	{
		this.enabled = enable;
	}

	public ColorWrapper getColor()
	{
		return color;
	}

	public void setColor(ColorWrapper color)
	{
		this.color = color;
	}

	public HighLight(boolean enable, ColorWrapper color)
	{
		this.enabled = enable;
		this.color = color;
	}

	public HighLight(boolean enable)
	{
		this(enable, WrappersFactory.newColor(255, 255, 0, 255 * 25 / 100));
	}
	
	public HighLight(ColorWrapper color)
	{
		this(false, color);
	}
}
