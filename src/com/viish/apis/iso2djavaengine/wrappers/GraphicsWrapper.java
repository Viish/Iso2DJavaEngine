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




public interface GraphicsWrapper
{
	/**
	 * Draw an image at screen coordinates x,y
	 */
	public void drawImage(ImageWrapper imageWrapper, int x, int y);
	
	/**
	 * Draw a string at screen coordinates x,y
	 */
	public void drawText(String text, int x, int y);
	
	/**
	 * Scale the map by sx,sy factor
	 */
	public void scale(double sx, double sy);
	
	
	/**
	 * Set the color the map will use to draw
	 */
	public void setColor(ColorWrapper color);
	
	/**
	 * Draw a diamond shape, filled using the current color
	 */
	public void fillDiamond(DiamondWrapper diamond);
}
