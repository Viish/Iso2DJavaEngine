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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.viish.apis.iso2djavaengine.wrappers.ColorWrapper;
import com.viish.apis.iso2djavaengine.wrappers.DiamondWrapper;
import com.viish.apis.iso2djavaengine.wrappers.GraphicsWrapper;
import com.viish.apis.iso2djavaengine.wrappers.ImageWrapper;

public class AndroidGraphicsWrapper implements GraphicsWrapper {
	
	private Canvas	canvas;
	private Paint   paint;

	public AndroidGraphicsWrapper(Canvas c)
	{
		canvas = c;
		paint = new Paint();
	}

	public void drawImage(ImageWrapper imageWrapper, int x, int y) {
		canvas.drawBitmap((Bitmap) imageWrapper.getRawImage(), (float) x, (float) y, null);
	}

	public void drawText(String text, int x, int y) {
		canvas.drawText(text, x, y, paint);
	}

	public void scale(double sx, double sy) {
		canvas.scale((float) sx, (float) sy);
	}

	public void setColor(ColorWrapper color) {
		paint.setColor((Integer) color.getNativeObject());
	}

	public void fillDiamond(DiamondWrapper diamond) {
		paint.setStyle(Paint.Style.FILL);
		canvas.drawPath((Path) diamond.getNativeObject(), paint);
		paint.setStyle(Paint.Style.STROKE);
	}

}
