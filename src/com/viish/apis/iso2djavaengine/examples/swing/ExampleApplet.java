package com.viish.apis.iso2djavaengine.examples.swing;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;

public class ExampleApplet extends JApplet
{
	private static final long	serialVersionUID	= 1L;
	ExamplePanel				monPanel;

	public void init()
	{
		this.setSize(900, 510);

		try
		{
			monPanel = new ExamplePanel();
			setContentPane(monPanel);
			setName("Move my Orc ! (Highligth colors are random)");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				monPanel.repaint();
			}
		}, 0, 100); // Time for Sprites' animation speed
	}
}
