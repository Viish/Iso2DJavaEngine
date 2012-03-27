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

import java.util.List;
import com.viish.apis.iso2djavaengine.wrappers.ImageWrapper;


public class Animation
{
	private List<ImageWrapper>	images;
	private AnimationType			type;
	private Orientation				orientation;

	public Animation(AnimationType aType, List<ImageWrapper> imgList,
			Orientation orient)
	{
		type = aType;
		images = imgList;
		orientation = orient;
	}

	public Orientation getOrientation()
	{
		return orientation;
	}

	public AnimationType getType()
	{
		return type;
	}

	public ImageWrapper getImage(int n)
	{
		return images.get(n);
	}

	public int getSize()
	{
		return images.size();
	}
}
