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

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ExampleApplication
{
	ExamplePanel	monPanel;

	public ExampleApplication()
	{
		final JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(900, 510);
		try
		{
			monPanel = new ExamplePanel();
			window.setContentPane(monPanel);
			window.setTitle("Move my Orc ! (Highligth colors are random)");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		window.setVisible(true);

		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				monPanel.repaint();
			}
		}, 0, 100); // Time for Sprites' animation speed
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new ExampleApplication();
			}
		});
	}
}