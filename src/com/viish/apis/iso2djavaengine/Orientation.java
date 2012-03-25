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

public enum Orientation
{
	NORTH_WEST, NORTH_EAST, SOUTH_WEST, SOUTH_EAST;

	public static Orientation next(Orientation current)
	{
		switch (current)
		{
			case NORTH_EAST:
				return NORTH_WEST;
			case NORTH_WEST:
				return SOUTH_WEST;
			case SOUTH_WEST:
				return SOUTH_EAST;
			case SOUTH_EAST:
				return NORTH_EAST;
			default:
				return null;
		}
	}
}
