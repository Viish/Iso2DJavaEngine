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

public class ExampleSpriteSkeleton extends Sprite
{
	public ExampleSpriteSkeleton(Class<?> classe, String path) throws IOException
	{
		super(SpriteType.CHARACTER, "Skeleton");
		if (!path.endsWith("/"))
			path += "/";
		
		ImageWrapper all = new AWTImageWrapper(Toolkit.getDefaultToolkit().getImage(classe
				.getResource(path + "Skeleton.png")));

		List<ImageWrapper> standNW = new ArrayList<ImageWrapper>();
		ImageWrapper stand_0_NW = all.getSubImage(1614, 330, 103, 112).resize(50, 57);
		standNW.add(stand_0_NW);
		addAnimation(AnimationType.IDLE, standNW, Orientation.NORTH_WEST);
		
		List<ImageWrapper> walkNW = new ArrayList<ImageWrapper>();
		ImageWrapper walk_0_NW = all.getSubImage(79, 330, 103, 112).resize(50, 57);
		walkNW.add(walk_0_NW);
		ImageWrapper walk_1_NW = all.getSubImage(331, 323, 103, 112).resize(50, 57);
		walkNW.add(walk_1_NW);
		ImageWrapper walk_2_NW = all.getSubImage(587, 325, 103, 112).resize(50, 57);
		walkNW.add(walk_2_NW);
		ImageWrapper walk_3_NW = all.getSubImage(842, 323, 103, 112).resize(50, 57);
		walkNW.add(walk_3_NW);
		addAnimation(AnimationType.WALK, walkNW, Orientation.NORTH_WEST);
		
		List<ImageWrapper> standNE = new ArrayList<ImageWrapper>();
		ImageWrapper stand_0_NE = all.getSubImage(1614, 838, 103, 112).resize(50, 57);
		standNE.add(stand_0_NE);
		addAnimation(AnimationType.IDLE, standNE, Orientation.NORTH_EAST);
		
		List<ImageWrapper> walkNE = new ArrayList<ImageWrapper>();
		ImageWrapper walk_0_NE = all.getSubImage(81, 837, 103, 112).resize(50, 57);
		walkNE.add(walk_0_NE);
		ImageWrapper walk_1_NE = all.getSubImage(340, 839, 103, 112).resize(50, 57);
		walkNE.add(walk_1_NE);
		ImageWrapper walk_2_NE = all.getSubImage(605, 844, 103, 112).resize(50, 57);
		walkNE.add(walk_2_NE);
		ImageWrapper walk_3_NE = all.getSubImage(858, 835, 103, 112).resize(50, 57);
		walkNE.add(walk_3_NE);
		addAnimation(AnimationType.WALK, walkNE, Orientation.NORTH_EAST);
		
		List<ImageWrapper> standSE = new ArrayList<ImageWrapper>();
		ImageWrapper stand_0_SE = all.getSubImage(1614, 1355, 103, 112).resize(50, 57);
		standSE.add(stand_0_SE);
		addAnimation(AnimationType.IDLE, standSE, Orientation.SOUTH_EAST);
		
		List<ImageWrapper> walkSE = new ArrayList<ImageWrapper>();
		ImageWrapper walk_0_SE = all.getSubImage(80, 1355, 103, 112).resize(50, 57);
		walkSE.add(walk_0_SE);
		ImageWrapper walk_1_SE = all.getSubImage(334, 1349, 103, 112).resize(50, 57);
		walkSE.add(walk_1_SE);
		ImageWrapper walk_2_SE = all.getSubImage(590, 1351, 103, 112).resize(50, 57);
		walkSE.add(walk_2_SE);
		ImageWrapper walk_3_SE = all.getSubImage(847, 1348, 103, 112).resize(50, 57);
		walkSE.add(walk_3_SE);
		addAnimation(AnimationType.WALK, walkSE, Orientation.SOUTH_EAST);
		
		List<ImageWrapper> standSW = new ArrayList<ImageWrapper>();
		ImageWrapper stand_0_SW = all.getSubImage(1614, 1860, 103, 112).resize(50, 57);
		standSW.add(stand_0_SW);
		addAnimation(AnimationType.IDLE, standSW, Orientation.SOUTH_WEST);
		
		List<ImageWrapper> walkSW = new ArrayList<ImageWrapper>();
		ImageWrapper walk_0_SW = all.getSubImage(74, 1862, 103, 112).resize(50, 57);
		walkSW.add(walk_0_SW);
		ImageWrapper walk_1_SW = all.getSubImage(325, 1857, 103, 112).resize(50, 57);
		walkSW.add(walk_1_SW);
		ImageWrapper walk_2_SW = all.getSubImage(578, 1869, 103, 112).resize(50, 57);
		walkSW.add(walk_2_SW);
		ImageWrapper walk_3_SW = all.getSubImage(834, 1867, 103, 112).resize(50, 57);
		walkSW.add(walk_3_SW);
		addAnimation(AnimationType.WALK, walkSW, Orientation.SOUTH_WEST);

		setCurrentAnimation(AnimationType.IDLE);
		setOrientation(Orientation.NORTH_WEST);
	}
}
