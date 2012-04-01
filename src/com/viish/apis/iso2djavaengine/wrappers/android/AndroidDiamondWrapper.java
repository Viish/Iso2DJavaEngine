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

import android.graphics.Path;

import com.viish.apis.iso2djavaengine.wrappers.DiamondWrapper;

public class AndroidDiamondWrapper implements DiamondWrapper
{
	private Path	diamond;
	private int 	nbPoints;
	private int[] 	xPoints, yPoints;

	public AndroidDiamondWrapper(int[] Xs, int[] Ys, int n)
	{
		nbPoints = n;
		xPoints = Xs;
		yPoints = Ys;
		
		diamond = new Path();
		diamond.moveTo(Xs[0], Ys[0]);
		for (int i = 1; i < n; i++)
		{
			for (int j = 1; j < n; j++)
			{
				diamond.lineTo(Xs[i], Ys[i]);
			}
		}
		diamond.lineTo(Xs[0], Ys[0]);
	}
	
	private boolean isBoundingBoxContains(int x, int y)
	{
		Integer minX = null, maxX = null, minY = null, maxY = null;
		for (int i = 0; i < nbPoints; i++)
		{
			int pX = xPoints[i];
			int pY = yPoints[i];
			minX = (pX < minX || minX == null ? pX : minX);
			maxX = (pX > maxX || maxX == null ? pX : maxX);
			minY = (pY < minY || minY == null ? pY : minY);
			maxY = (pY > maxY || maxY == null ? pY : maxY);
		}
		
		return (x >= minX && x <= maxX && y >= minY && y <= maxY);
	}

	public boolean contains(int x, int y)
	{
        if (nbPoints <= 2 || !isBoundingBoxContains(x, y)) {
            return false;
        }
        int hits = 0;

        int lastx = xPoints[nbPoints - 1];
        int lasty = yPoints[nbPoints - 1];
        int curx, cury;

        for (int i = 0; i < nbPoints; lastx = curx, lasty = cury, i++) {
            curx = xPoints[i];
            cury = yPoints[i];

            if (cury == lasty) {
                continue;
            }

            int leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
	}

	public Object getNativeObject()
	{
		return diamond;
	}
}
