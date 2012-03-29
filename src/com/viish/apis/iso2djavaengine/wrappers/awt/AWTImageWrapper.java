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
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.viish.apis.iso2djavaengine.wrappers.ColorWrapper;
import com.viish.apis.iso2djavaengine.wrappers.ImageWrapper;

public class AWTImageWrapper implements ImageWrapper
{
	private BufferedImage	image;

	public AWTImageWrapper(Image img)
	{
		new ImageIcon(img);
		image = toBufferedImage(img);
	}

	public BufferedImage getRawImage()
	{
		return image;
	}

	public int getWidth()
	{
		return image.getWidth(null);
	}

	public int getHeight()
	{
		return image.getHeight(null);
	}

	public ImageWrapper getSubImage(int x, int y, int width, int height)
	{
		Image subImage = null;
		subImage = image.getSubimage(x, y, width, height);
		return new AWTImageWrapper(subImage);
	}

	public void replaceColorByTransparency(ColorWrapper colorToRemove)
	{
		Color color = (Color) colorToRemove.getNativeObject();
		int width = getWidth();
		int height = getHeight();
		int[] rgbs = new int[width * height];
		image.getRGB(0, 0, width, height, rgbs, 0, width);
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				if (rgbs[i * j] == color.getRGB())
					image.setRGB(i, j, 255 << 24);
			}
		}
	}

	public ImageWrapper resize(int width, int height)
	{
		Image resizedImage = image.getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		return new AWTImageWrapper(resizedImage);
	}

	private BufferedImage toBufferedImage(Image image)
	{
		if (image instanceof BufferedImage)
		{
			return ((BufferedImage) image);
		}
		else
		{
			BufferedImage bufferedImage = new BufferedImage(
					image.getWidth(null), image.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();

			return bufferedImage;
		}
	}
}
