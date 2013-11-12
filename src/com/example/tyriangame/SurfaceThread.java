package com.example.tyriangame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class SurfaceThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private SurfacePanel _panel;
	private boolean _run = false;

	public SurfaceThread(SurfaceHolder surfaceHolder, SurfacePanel panel) {
		_surfaceHolder = surfaceHolder;
		_panel = panel;
	}

	public SurfaceHolder getSurfaceHolder() {
		 return _surfaceHolder;
	}

	public void setRunning(boolean run) {
		_run = run;
	}

	@Override
	public void run() {
		Canvas c;
		while (_run) {
			c = null;
			try {
				c = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {
					_panel.onDraw(c);
					_panel.updatePhysics();
				}
			} finally {
				if (c != null) {
					_surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}
}
