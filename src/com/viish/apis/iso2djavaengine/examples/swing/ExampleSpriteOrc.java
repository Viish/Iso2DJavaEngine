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

package com.viish.apis.iso2djavaengine.examples.swing;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.viish.apis.iso2djavaengine.AnimationType;
import com.viish.apis.iso2djavaengine.Orientation;
import com.viish.apis.iso2djavaengine.Sprite;
import com.viish.apis.iso2djavaengine.SpriteType;
import com.viish.apis.iso2djavaengine.wrappers.ImageWrapper;
import com.viish.apis.iso2djavaengine.wrappers.awt.AWTImageWrapper;

public class ExampleSpriteOrc extends Sprite
{
	public ExampleSpriteOrc(Class<?> classe, String path) throws IOException
	{
		super(SpriteType.CHARACTER, "Vyers");
		if (!path.endsWith("/"))
			path += "/";

		List<ImageWrapper> orcStandNW = new ArrayList<ImageWrapper>();
		orcStandNW.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "NW/stand_0.png"))));
		addAnimation(AnimationType.IDLE, orcStandNW, Orientation.NORTH_WEST);
		
		List<ImageWrapper> orcStandNE = new ArrayList<ImageWrapper>();
		orcStandNE.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "NE/stand_0.png"))));
		addAnimation(AnimationType.IDLE, orcStandNE, Orientation.NORTH_EAST);
		
		List<ImageWrapper> orcWalkNW = new ArrayList<ImageWrapper>();
		orcWalkNW.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "NW/walk_1.png"))));
		orcWalkNW.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "NW/stand_0.png"))));
		orcWalkNW.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "NW/walk_2.png"))));
		addAnimation(AnimationType.WALK, orcWalkNW, Orientation.NORTH_WEST);
		
		List<ImageWrapper> orcWalkNE = new ArrayList<ImageWrapper>();
		orcWalkNE.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "NE/walk_1.png"))));
		orcWalkNE.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "NE/stand_0.png"))));
		orcWalkNE.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "NE/walk_2.png"))));
		addAnimation(AnimationType.WALK, orcWalkNE, Orientation.NORTH_EAST);
		
		List<ImageWrapper> orcStandSE = new ArrayList<ImageWrapper>();
		orcStandSE.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "SE/stand_0.png"))));
		addAnimation(AnimationType.IDLE, orcStandSE, Orientation.SOUTH_EAST);
		
		List<ImageWrapper> orcWalkSE = new ArrayList<ImageWrapper>();
		orcWalkSE.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "SE/walk_1.png"))));
		orcWalkSE.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "SE/stand_0.png"))));
		orcWalkSE.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "SE/walk_2.png"))));
		addAnimation(AnimationType.WALK, orcWalkSE, Orientation.SOUTH_EAST);
		
		List<ImageWrapper> orcStandSW = new ArrayList<ImageWrapper>();
		orcStandSW.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "SW/stand_0.png"))));
		addAnimation(AnimationType.IDLE, orcStandSW, Orientation.SOUTH_WEST);
		
		List<ImageWrapper> orcWalkSW = new ArrayList<ImageWrapper>();
		orcWalkSW.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "SW/walk_1.png"))));
		orcWalkSW.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "SW/stand_0.png"))));
		orcWalkSW.add(new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "SW/walk_2.png"))));
		addAnimation(AnimationType.WALK, orcWalkSW, Orientation.SOUTH_WEST);

		setCurrentAnimation(AnimationType.IDLE);
		setOrientation(Orientation.NORTH_EAST);
	}
}
