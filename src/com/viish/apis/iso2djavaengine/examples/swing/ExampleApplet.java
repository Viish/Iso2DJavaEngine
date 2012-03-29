package com.viish.apis.iso2djavaengine.examples.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ExampleApplet extends JApplet {
	private static final long serialVersionUID = 1L;
	ExamplePanel	monPanel;

	public void init(){
		this.setSize(900, 510);
		
		JPanel pane = new JPanel();
		try
		{
			monPanel = new ExamplePanel();
			JButton rotate = new JButton("Rotate Orc");
			rotate.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					monPanel.orc.rotate();
				}
			});
			pane.setLayout(new BorderLayout());
			pane.add(monPanel, BorderLayout.CENTER);
			JComponent controls = new JPanel();
			BorderLayout controlsLayout = new BorderLayout();
			controls.setLayout(controlsLayout);
			controls.add(rotate, BorderLayout.EAST);
			pane.add(controls, BorderLayout.SOUTH);

			setContentPane(pane);
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
