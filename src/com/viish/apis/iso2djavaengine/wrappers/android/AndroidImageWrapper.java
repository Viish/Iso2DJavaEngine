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

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.viish.apis.iso2djavaengine.wrappers.ColorWrapper;
import com.viish.apis.iso2djavaengine.wrappers.ImageWrapper;

public class AndroidImageWrapper implements ImageWrapper
{

	private Bitmap	image;

	public AndroidImageWrapper(Bitmap img)
	{
		image = img;
	}

	public Object getRawImage()
	{
		return image;
	}

	public int getWidth()
	{
		return image.getWidth();
	}

	public int getHeight()
	{
		return image.getHeight();
	}

	public ImageWrapper getSubImage(int x, int y, int width, int height)
	{
		Bitmap cropedBitmap = Bitmap.createBitmap(image, x, y, width, height);
		return new AndroidImageWrapper(cropedBitmap);
	}

	public void replaceColorByTransparency(ColorWrapper colorToRemove)
	{
		int color = (Integer) colorToRemove.getNativeObject();
		int width = getWidth();
		int height = getHeight();

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				int rgb = image.getPixel(i, j);
				if (rgb == color) {
					image.setPixel(i, j, 255 << 24);
				}
			}
		}
	}

	public ImageWrapper resize(int newWidth, int newHeight)
	{
		int width = getWidth();
		int height = getHeight();
		
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		
		Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height, matrix, false);
		return new AndroidImageWrapper(resizedBitmap);
	}
}
