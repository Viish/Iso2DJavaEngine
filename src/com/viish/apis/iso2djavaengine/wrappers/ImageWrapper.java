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


public interface ImageWrapper
{
	/**
	 * @return the real object, depending the technology
	 */
	public Object getRawImage();

	/**
	 * @return the width of the image, in pixels
	 */
	public int getWidth();

	/**
	 * @return the height of the image, in pixels
	 */
	public int getHeight();
	
	/**
	 * @return a new ImageWrapper, created using a piece of the one calling this method
	 */
	public ImageWrapper getSubImage(int x, int y, int width, int height);
	
	/**
	 * Set the transparency to 100% to all pixels with the given color
	 */
	public void replaceColorByTransparency(ColorWrapper colorToRemove);
	
	/**
	 * @return a new ImageWrapper, copy of the original but with different dimensions
	 */
	public ImageWrapper resize(int width, int height);
}
