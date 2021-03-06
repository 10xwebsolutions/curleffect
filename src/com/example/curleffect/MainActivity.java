package com.example.curleffect;
/*
   Copyright 2012 Harri Singh

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */



import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;

import com.curlimg.CurlPage;
import com.curlimg.CurlView;

/**
 * Simple Activity for curl testing.
 * 
 * @author harism
 */
public class MainActivity extends Activity {
	// ScrollPickerView scrollPickerView;
	private CurlView mCurlView;

	Button slideButton;
	private boolean scrolling = false;

	// ImageView img;
	MediaPlayer pageFlipperAudio;
	//MediaPlayer p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23;
	/** Called when the activity is first created. */
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*
		 * mdplayer=MediaPlayer.create(getApplicationContext(), R.raw.bckmusic);
		 * mdplayer.start(); mdplayer.setLooping(true);
		 */
		pageFlipperAudio = MediaPlayer.create(getApplicationContext(),
			R.raw.pagefilp);
 		

// img=(ImageView)findViewById(R.id.imgs);
		/*
		 * final Handler handler = new Handler(); handler.postDelayed(new
		 * Runnable() {
		 * 
		 * @Override public void run() { img.setVisibility(View.INVISIBLE);
		 * Toast.makeText(getApplicationContext(),
		 * "Swipe Right - Left to Curl page",1).show();
		 * 
		 * //Do something after 100ms } }, 2000);
		 */
		/*
		 * mSplashThread = new Thread(){
		 * 
		 * @Override public void run(){ try { synchronized(this){ // Wait given
		 * period of time or exit on touch wait(2000); } }
		 * catch(InterruptedException ex){ }
		 * 
		 * img.setVisibility(View.GONE);
		 * 
		 * // Run next activity
		 * 
		 * } };
		 * 
		 * mSplashThread.start();
		 */

		int index = 0;
		if (getLastNonConfigurationInstance() != null) {
			index = (Integer) getLastNonConfigurationInstance();
		}
		mCurlView = (CurlView) findViewById(R.id.curlView1);
		mCurlView.setPageProvider(new PageProvider());
		mCurlView.setSizeChangedObserver(new SizeChangedObserver());
		mCurlView.setSoundEffectsEnabled(true);
		mCurlView.setCurrentIndex(index);
		mCurlView.setBackgroundColor(Color.BLACK);
		mCurlView.setAllowLastPageCurl(false);
		// //////////////////////////////////////////////////////

		// //////////////////////////////////////////////////
		// scrollPickerView =
		// (ScrollPickerView)findViewById(R.id.scrollPickerView1);
		// scrollPickerView.addSlot(getResources().getStringArray(R.array.custom_list),
		// 1, ScrollPickerView.ScrollType.Ranged);
		// scrollPickerView.setSlotIndex(0, 1);
		/*
		 * scrollPickerView.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub
		 * 
		 * } });
		 */

		// moveViewToScreenCenter(scrollPickerView);
		// This is something somewhat experimental. Before uncommenting next
		// line, please see method comments in CurlView.
		// mCurlView.setEnableTouchPressure(true);
	}

	/*
	 * private void moveViewToScreenCenter( View view ) { RelativeLayout root =
	 * (RelativeLayout) findViewById( R.id.rel ); DisplayMetrics dm = new
	 * DisplayMetrics(); this.getWindowManager().getDefaultDisplay().getMetrics(
	 * dm ); int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();
	 * 
	 * int originalPos[] = new int[2]; view.getLocationOnScreen( originalPos );
	 * 
	 * int xDest = dm.widthPixels/2; xDest -= (view.getMeasuredWidth()/2); int
	 * yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) -
	 * statusBarOffset;
	 * 
	 * TranslateAnimation anim = new TranslateAnimation( 0, 0 , 0, -350 );
	 * anim.setDuration(5000); anim.setFillAfter( true );
	 * view.startAnimation(anim); }
	 */
	@Override
	public void onPause() {
		super.onPause();
		mCurlView.onPause();
		stopAudio();
	}

	@Override
	public void onResume() {
		super.onResume();
		mCurlView.onResume();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return mCurlView.getCurrentIndex();
	}

	/**
	 * Bitmap provider.
	 */
	// /////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////////////
	private class PageProvider implements CurlView.PageProvider {

		// Bitmap resources.
		private int[] mBitmapIds = {
				R.drawable.android1,
				R.drawable.android2,
				R.drawable.android3,
				R.drawable.android4,
				R.drawable.android5,
				R.drawable.android6,
				R.drawable.android7,
				R.drawable.android8
		/*
		 * R.drawable.page_dr, R.drawable.page_ds, R.drawable.page_dt,
		 * R.drawable.page_du, R.drawable.page_dv, R.drawable.page_dw,
		 * R.drawable.page_dx, R.drawable.page_dy, R.drawable.page_dz,
		 */
		};

		@Override
		public int getPageCount() {
			return mBitmapIds.length;
		}
		private   Bitmap codec(Bitmap src, Bitmap.CompressFormat format,
				int quality) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			src.compress(format, quality, os);
	 
			byte[] array = os.toByteArray();
			return BitmapFactory.decodeByteArray(array, 0, array.length);
		}
		@SuppressLint("NewApi") private Bitmap loadBitmap(int width, int height, int index) {
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			
			b.eraseColor(0xFFFFFFFF);
			Canvas c = new Canvas(b);
			Drawable d = getResources().getDrawable(mBitmapIds[index]);

			int margin = 0;
			int border = 0;
			Rect r = new Rect(margin, margin, width - margin, height - margin);

			int imageWidth = r.width() - (border * 2);
			int imageHeight = imageWidth * d.getIntrinsicHeight()
					/ d.getIntrinsicWidth();
			if (imageHeight > r.height() - (border * 2)) {
				imageHeight = r.height() - (border * 2);
				imageWidth = imageHeight * d.getIntrinsicWidth()
						/ d.getIntrinsicHeight();
			}

			/*
			 * r.left += ((r.width() - imageWidth) / 2) - border; r.right =
			 * r.left + imageWidth + border + border; r.top += ((r.height() -
			 * imageHeight) / 2) - border; r.bottom = r.top + imageHeight +
			 * border + border;
			 */

			Paint p = new Paint();
			p.setColor(0xFFC0C0C0);
			c.drawRect(r, p);
			r.left += border;
			r.right -= border;
			r.top += border;
			r.bottom -= border;

			d.setBounds(r);
			d.draw(c);
			/*mCurlView.playSoundEffect(R.raw.s1);*/
          //  b=codec(b, Bitmap.CompressFormat.JPEG, 70);
           
			return b;
		}

		private Bitmap loadBitmapback(int width, int height) {
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			b.eraseColor(0xFFFFFFFF);
			Canvas c = new Canvas(b);
			Drawable d = getResources().getDrawable(R.drawable.coverpage);

			int margin = 0;
			int border = 0;
			Rect r = new Rect(margin, margin, width - margin, height - margin);

			int imageWidth = r.width() - (border * 2);
			int imageHeight = imageWidth * d.getIntrinsicHeight()
					/ d.getIntrinsicWidth();
			if (imageHeight > r.height() - (border * 2)) {
				imageHeight = r.height() - (border * 2);
				imageWidth = imageHeight * d.getIntrinsicWidth()
						/ d.getIntrinsicHeight();
			}

			/*
			 * r.left += ((r.width() - imageWidth) / 2) - border; r.right =
			 * r.left + imageWidth + border + border; r.top += ((r.height() -
			 * imageHeight) / 2) - border; r.bottom = r.top + imageHeight +
			 * border + border;
			 */

			Paint p = new Paint();
			p.setColor(0xFFC0C0C0);
			c.drawRect(r, p);
			r.left += border;
			r.right -= border;
			r.top += border;
			r.bottom -= border;

			d.setBounds(r);
			d.draw(c);

			return b;
		}

		@Override
		public void updatePage(CurlPage page, int width, int height, int index) {

			/*
			 * switch (index) { // First case is image on front side, solid
			 * colored back. case 0: { Bitmap front = loadBitmap(width, height,
			 * 0); page.setTexture(front, CurlPage.SIDE_FRONT);
			 * page.setColor(Color.rgb(180, 180, 180), CurlPage.SIDE_BACK);
			 * break; } // Second case is image on back side, solid colored
			 * front. case 1: { Bitmap back = loadBitmap(width, height, 2);
			 * page.setTexture(back, CurlPage.SIDE_BACK);
			 * page.setColor(Color.rgb(127, 140, 180), CurlPage.SIDE_FRONT);
			 * break; } // Third case is images on both sides. case 2: { Bitmap
			 * front = loadBitmap(width, height, 1); Bitmap back =
			 * loadBitmap(width, height, 3); page.setTexture(front,
			 * CurlPage.SIDE_FRONT); page.setTexture(back, CurlPage.SIDE_BACK);
			 * break; } // Fourth case is images on both sides - plus they are
			 * blend against // separate colors. case 3: { Bitmap front =
			 * loadBitmap(width, height, 2); Bitmap back = loadBitmap(width,
			 * height, 1); page.setTexture(front, CurlPage.SIDE_FRONT);
			 * page.setTexture(back, CurlPage.SIDE_BACK);
			 * page.setColor(Color.argb(127, 170, 130, 255),
			 * CurlPage.SIDE_FRONT); page.setColor(Color.rgb(255, 190, 150),
			 * CurlPage.SIDE_BACK); break; } // Fifth case is same image is
			 * assigned to front and back. In this // scenario only one texture
			 * is used and shared for both sides. case 4:
			 */
			/*
			 * if(index==mBitmapIds.length){ Bitmap front =
			 * loadBitmapback(width, height); Bitmap back = loadBitmap(width,
			 * height, index); page.setTexture(front, CurlPage.SIDE_FRONT);
			 * page.setTexture(back, CurlPage.SIDE_BACK); }
			 */
			/*
			 * Bitmap back = loadBitmap(width, height, index);
			 * page.setTexture(back, CurlPage.SIDE_BACK);
			 * page.setColor(Color.rgb(127, 140, 180), CurlPage.SIDE_FRONT);
			 *//*
				 * Bitmap front = loadBitmap(width, height, index);
				 * page.setTexture(front, CurlPage.SIDE_FRONT);
				 * page.setColor(Color.rgb(180, 180, 180), CurlPage.SIDE_BACK);
				 *//*
					 * Bitmap front =loadBitmap(width, height, 2); Bitmap back =
					 * loadBitmap(width, height, index); page.setTexture(front,
					 * CurlPage.SIDE_FRONT); page.setTexture(back,
					 * CurlPage.SIDE_BACK); page.setColor(Color.argb(127, 170,
					 * 130, 255), CurlPage.SIDE_FRONT);
					 * page.setColor(Color.rgb(255, 190, 150),
					 * CurlPage.SIDE_BACK);
					 */
//Toast.makeText(getApplicationContext(), ""+index, 1).show();
			Bitmap front = loadBitmap(width, height, index);
			page.setTexture(front, CurlPage.SIDE_BOTH);
			page.setColor(Color.argb(127, 255, 255, 255), CurlPage.SIDE_BACK);
			//pageFlipperAudio.start();
			stopAudio();
			pageFlipperAudio.start();
			

			// break;
			// }
		}

	}
public void stopAudio()
{
	if(pageFlipperAudio.isPlaying())pageFlipperAudio.stop();
	
}
	/**
	 * CurlView size changed observer.
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		@Override
		public void onSizeChanged(int w, int h) {
			 
			   if (w > h) { mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
			   mCurlView.setMargins(.0f, .0f, .0f, .0f); } else {
			  
			mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
			mCurlView.setMargins(.0f, .0f, .0f, .0f);
			 }
		}
	}

}