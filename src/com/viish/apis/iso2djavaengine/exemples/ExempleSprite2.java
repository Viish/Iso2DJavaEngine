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

package com.viish.apis.iso2djavaengine.exemples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import com.viish.apis.iso2djavaengine.AnimationType;
import com.viish.apis.iso2djavaengine.Orientation;
import com.viish.apis.iso2djavaengine.Sprite;
import com.viish.apis.iso2djavaengine.SpriteType;
import com.viish.apis.iso2djavaengine.wrappers.ImageWrapper;
import com.viish.apis.iso2djavaengine.wrappers.awt.AWTImageWrapper;

public class ExempleSprite2 extends Sprite
{
	public ExempleSprite2(Class<?> classe, String path) throws IOException
	{
		super(SpriteType.CHARACTER, "Vyers");
		if (!path.endsWith("/"))
			path += "/";

		List<ImageWrapper> axelStandSW = new ArrayList<ImageWrapper>();
		axelStandSW.add(new AWTImageWrapper(ImageIO.read(classe
				.getResource(path + "SW/stand_0.png"))));
		axelStandSW.add(new AWTImageWrapper(ImageIO.read(classe
				.getResource(path + "SW/stand_1.png"))));
		axelStandSW.add(new AWTImageWrapper(ImageIO.read(classe
				.getResource(path + "SW/stand_2.png"))));
		axelStandSW.add(new AWTImageWrapper(ImageIO.read(classe
				.getResource(path + "SW/stand_3.png"))));
		axelStandSW.add(new AWTImageWrapper(ImageIO.read(classe
				.getResource(path + "SW/stand_4.png"))));
		addAnimation(AnimationType.IDLE, axelStandSW, Orientation.SOUTH_WEST);

		setCurrentAnimation(AnimationType.IDLE);
		setOrientation(Orientation.SOUTH_WEST);
	}
}
