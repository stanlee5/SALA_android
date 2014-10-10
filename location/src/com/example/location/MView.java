package com.example.location;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MView extends View {

	int INF=10000;
	ArrayList<Float> x = null;
	ArrayList<Float> y = null;
	ArrayList<Float> z = null;
	
	Paint p = null;
	Paint b = null;
	Paint c = null;
	Paint t = null;
	double startTime;
	
	public MView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		x = new ArrayList<Float>();
		y = new ArrayList<Float>();
		z = new ArrayList<Float>();
		p = new Paint();
		p.setColor(Color.GREEN);
		c = new Paint();
		c.setColor(Color.YELLOW);
		b = new Paint();
		b.setColor(Color.WHITE);
		t = new Paint();
		t.setColor(Color.WHITE);
		t.setTextSize(32);
	}

	double xval, yval, zval;
	double xx,yy;
	
	double sx, sy;	// 위치
	double vx, vy;	// 속도
	double vx0,vy0;
	
	double timestamp, tval, dT;
	float ns2s =  1.0f / 1000000000.0f;

	
	@Override
	public void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);		
		canvas.drawText("x 가속도", 50, 30, t);
		canvas.drawLine(0, 100, 1000, 100, b);
		canvas.drawLine(0, 100 + multiple*range, 1000, 100 + multiple*range, b);
		canvas.drawLine(0, 100 - multiple*range, 1000, 100 - multiple*range, b);
		
		for(int i = 0; i < x.size() - 1; i++)
		{			
			canvas.drawLine(i, 100 + multiple*x.get(i), i + 1, 100 + multiple*x.get(i + 1), p);
			canvas.drawRect(i - 1, 99 + multiple*x.get(i), i + 1, 101 + multiple*x.get(i), c);
		}
		
		canvas.drawText("y 가속도", 50, 230, t);
		canvas.drawLine(0, 300, 1000, 300, b);
		canvas.drawLine(0, 300 + multiple*range, 1000, 300 + multiple*range, b);
		canvas.drawLine(0, 300 - multiple*range, 1000, 300 - multiple*range, b);
		for(int i = 0; i < y.size() - 1; i++)
		{			
			canvas.drawLine(i, 300 + multiple*y.get(i), i + 1, 300 + multiple*y.get(i + 1), p);
			canvas.drawRect(i - 1, 299 + multiple*y.get(i), i + 1, 301 + multiple*y.get(i), c);
		}
		
		canvas.drawText("x 값:" + xval, 100, 550, t);
		canvas.drawText("y 값:" + yval, 100, 600, t);
		canvas.drawText("z 값:" + zval, 100, 650, t);

		canvas.drawText("time : " + (timestamp-startTime)*ns2s, 100, 750, t);
		canvas.drawText("dT : " + dT, 100, 800, t);
		canvas.drawText("x 속도 : " + vx, 100, 850, t);
		canvas.drawText("y 속도 : " + vy, 100, 900, t);
		
		canvas.drawText("x 좌표 : " + sx, 100, 1000, t);
		canvas.drawText("y 좌표 : " + sy, 100, 1050, t);

	}
	
	final int range = 10;
	final int multiple = 5;

	
	public void calculate ()
	{
		if (timestamp==0)
		{
			startTime = tval;
			xx=xval;
			yy=yval;
		}
		else
			dT = (tval - timestamp)*ns2s;
		
		timestamp = tval;
		
		vx += dT * (xval-xx);
		vy += dT * (yval-yy);
		xx = xval;
		yy = yval;
		
		sx += dT * (vx + vx0)/2;
		sy += dT * (vy + vy0)/2;
		vx0=vx;
		vy0=vy;
	}
	
	public void add(float X, float Y, float Z, long T) 
	{
		if(x.size() >= 430)
		{
			x.remove(0);
			y.remove(0);
			z.remove(0);
		}
		xval=X;
		yval=Y;
		zval=Z;
		tval=T;
		
		calculate ();
		x.add(X);
		y.add(Y);
		z.add(Z);
		invalidate ();
	}
}
