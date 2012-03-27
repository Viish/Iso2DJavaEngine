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

import java.util.ArrayList;
import java.util.List;
import com.viish.apis.iso2djavaengine.wrappers.ImageWrapper;

public class Sprite
{
	private int				animationCounter;
	private List<Animation>	animations;
	private Animation		currentAnimation;
	private Orientation		currentOrientation;
	private SpriteType		type	= SpriteType.UNDEFINED;
	private Object			linkedObject;

	public Sprite()
	{
		animations = new ArrayList<Animation>();
		animationCounter = 0;
		type = SpriteType.UNDEFINED;
		linkedObject = null;
	}

	public Sprite(SpriteType t)
	{
		this();
		type = t;
	}

	public Sprite(Object object)
	{
		this();
		linkedObject = object;
	}

	public Sprite(SpriteType t, Object object)
	{
		this();
		type = t;
		linkedObject = object;
	}

	/**
	 * @return the type of Sprite : MapCell or Caracter.
	 */
	public SpriteType getType()
	{
		return type;
	}

	/**
	 * Use one or more pictures to create a Sprite animation for the given
	 * action and orientation
	 */
	public void addAnimation(AnimationType type, List<ImageWrapper> images,
			Orientation orientation)
	{
		Animation anim = new Animation(type, images, orientation);
		animations.add(anim);

		if (currentAnimation == null)
			currentAnimation = anim;
	}

	/**
	 * @return the currently displayed image for the current Sprite's animation
	 */
	public ImageWrapper getCurrentAnimationImage()
	{
		return currentAnimation.getImage(animationCounter);
	}

	/**
	 * @return the next image for the current Sprite's animation. If last image
	 *         reached, return the first one.
	 */
	public ImageWrapper getNextAnimationImage()
	{
		int currentAnimationSize = currentAnimation.getSize();
		ImageWrapper nextImage = currentAnimation.getImage(animationCounter);

		if (animationCounter == currentAnimationSize - 1)
			animationCounter = 0;
		else
			animationCounter++;

		return nextImage;
	}

	/**
	 * Tell a Sprite to change it's current animation. Animation counter is
	 * reset to the first image of the animation.
	 */
	public void setCurrentAnimation(AnimationType type)
	{
		animationCounter = 0;

		for (Animation anim : animations)
		{
			if (anim.getType().equals(type)
					&& anim.getOrientation().equals(currentOrientation))
			{
				currentAnimation = anim;
				return;
			}
		}
	}

	/**
	 * Tell a Srite to change it's orientation. It doesn't change the animation
	 * counter.
	 */
	public void setOrientation(Orientation orientation)
	{
		currentOrientation = orientation;
		for (Animation anim : animations)
		{
			if (anim.getType().equals(currentAnimation.getType())
					&& anim.getOrientation().equals(orientation))
			{
				currentAnimation = anim;
				return;
			}
		}
	}

	/**
	 * Only usefull for tests
	 */
	public void rotate()
	{
		setOrientation(Orientation.next(currentOrientation));
	}

	/**
	 * @return the current orientation of the Sprite.
	 */
	public String getOrientation()
	{
		return currentOrientation.toString();
	}

	/**
	 * @return the width of the current animation image
	 */
	public int getWidth()
	{
		return getCurrentAnimationImage().getWidth();
	}

	/**
	 * @return the height of the current animation image
	 */
	public int getHeight()
	{
		return getCurrentAnimationImage().getHeight();
	}

	/**
	 * Attach an object to this Sprite
	 */
	public void setLinkedObject(Object o)
	{
		linkedObject = o;
	}

	/**
	 * @return the attached object if there is one, else return null
	 */
	public Object getLinkedObject()
	{
		return linkedObject;
	}
}
