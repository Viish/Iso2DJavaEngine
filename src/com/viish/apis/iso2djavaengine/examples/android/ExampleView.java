package com.viish.apis.iso2djavaengine.examples.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.viish.apis.iso2djavaengine.AnimationType;
import com.viish.apis.iso2djavaengine.Map;
import com.viish.apis.iso2djavaengine.Orientation;
import com.viish.apis.iso2djavaengine.Sprite;
import com.viish.apis.iso2djavaengine.SpriteType;
import com.viish.apis.iso2djavaengine.wrappers.ImageWrapper;
import com.viish.apis.iso2djavaengine.wrappers.Wrappers;
import com.viish.apis.iso2djavaengine.wrappers.WrappersFactory;
import com.viish.apis.iso2djavaengine.wrappers.android.AndroidGraphicsWrapper;
import com.viish.apis.iso2djavaengine.wrappers.android.AndroidImageWrapper;

public class ExampleView extends View implements OnTouchListener {

	private Map map;
	private int offsetX = 0, offsetY = 0;
	private int tempX, tempY;
	public Sprite orc, skeleton, currentSelected;
	private int sizeX = 9;
	private int sizeY = 13;

	public ExampleView(Context context) {
		super(context);

		setOnTouchListener(this);
		
		map = new Map(sizeX, sizeY, Wrappers.ANDROID);
		ImageWrapper snow = new AndroidImageWrapper(
				BitmapFactory.decodeResource(getResources(), R.drawable.snow));
		ImageWrapper grass = new AndroidImageWrapper(
				BitmapFactory.decodeResource(getResources(), R.drawable.grass));
		ImageWrapper dirt = new AndroidImageWrapper(
				BitmapFactory.decodeResource(getResources(), R.drawable.dirt));
		ImageWrapper rock = new AndroidImageWrapper(
				BitmapFactory.decodeResource(getResources(), R.drawable.rock));
		ImageWrapper stone = new AndroidImageWrapper(
				BitmapFactory.decodeResource(getResources(), R.drawable.stone));
		ImageWrapper[] cells = new ImageWrapper[] { snow, grass, dirt, rock,
				stone };
		map.setCellBordureHeight(0);

		try {
			orc = new ExampleSpriteOrc(getContext());
			skeleton = new ExampleSpriteSkeleton(getContext());
			map.setCharacterSprite(4, 4, orc);
			map.setCharacterSprite(1, 1, skeleton);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Random r = new Random();
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				Sprite cell = new Sprite(SpriteType.CELL);
				List<ImageWrapper> anim = new ArrayList<ImageWrapper>();

				int n = r.nextInt(cells.length);
				anim.add(cells[n]);

				cell.addAnimation(AnimationType.IDLE, anim,
						Orientation.NORTH_EAST);
				cell.setCurrentAnimation(AnimationType.IDLE);
				map.setMapSprite(i, j, cell);
			}
		}

	}

	private void showAvailableMovements(Sprite s) {
		int moveSpeed = 7;
		int x = s.getX();
		int y = s.getY();
		for (int i = -moveSpeed; i <= moveSpeed; i++) {
			for (int j = -moveSpeed; j <= moveSpeed; j++) {
				if (((x + i) >= 0 && (x + i) < sizeX)
						&& ((j + y) >= 0 && (j + y) < sizeY)
						&& (Math.abs(i) + Math.abs(j) <= moveSpeed)
						&& map.getCharacterSprite(x + i, j + y) == null)
					map.setHighlightedSprite(x + i, y + j, true,
							WrappersFactory.newColor(255, 255, 0, 25));
			}

		}
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		map.refresh(new AndroidGraphicsWrapper(canvas), offsetX, offsetY);
	}

	public boolean onTouch(View v, MotionEvent e) {

		if (e.getAction() == MotionEvent.ACTION_DOWN) {

		} else if (e.getAction() == MotionEvent.ACTION_UP) {
			Sprite s = map.getHighestSpriteAt((int) e.getX(), (int) e.getY());
			if (s == null)
			{
				map.resetAllHighlight();
				return true;
			}

			if (s.getType() == SpriteType.CHARACTER)
			{
				if (currentSelected != null)
				{
					if (currentSelected.equals(s))
					{
						currentSelected = null;
						map.resetAllHighlight();
					}
					else
					{
						currentSelected = s;
						map.resetAllHighlight();
						showAvailableMovements(s);
					}
				}
				else
				{
					currentSelected = s;
					if (!map.isCellHighlighted(s.getX(), s.getY()))
					{
						showAvailableMovements(s);
					}
				}
			}
			else if (s.getType() == SpriteType.CELL)
			{
				if (map.isCellHighlighted(s.getX(), s.getY()))
				{
					map.resetAllHighlight();
					if (s.getX() == currentSelected.getX()
							|| s.getY() == currentSelected.getY())
					{
						map.moveCharacter(currentSelected.getX(),
								currentSelected.getY(), new int[] { s.getX() },
								new int[] { s.getY() });
						currentSelected = null;
					}
					else
					{
						if (currentSelected.getX() + s.getX() < currentSelected
								.getY() + s.getY())
						{
							map.moveCharacter(currentSelected.getX(),
									currentSelected.getY(), new int[] {
											currentSelected.getX(), s.getX() },
									new int[] { s.getY(), s.getY() });
							currentSelected = null;
						}
						else
						{
							map.moveCharacter(currentSelected.getX(),
									currentSelected.getY(),
									new int[] { s.getX(), s.getX() }, new int[] {
											currentSelected.getY(), s.getY() });
							currentSelected = null;
						}
					}
				}
				else
				{
					map.resetAllHighlight();
					currentSelected = null;
				}
			}

		} else if (e.getAction() == MotionEvent.ACTION_MOVE) {

		}

		return true;
	}
}
