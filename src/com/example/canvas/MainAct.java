package com.example.canvas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import java.util.Random;

class
Sort
extends Thread
{
   int[] data;
   final int SIZE = 40;
   Handler h;
   View v;
   int pos1, pos2, pos3, pos4;
   int pivot_val;
   int stack_depth;

   private void
   swap(int[] a, int i, int j)
   {
      int t = a[i];
      a[i] = a[j];
      a[j] = t;
   }

   private void
   update(int t)
   {
      h.post(new Runnable() {
         public void run() {
            v.invalidate();
         }
      });
      try {
         Thread.sleep(t); }
      catch (InterruptedException e) { }
   }

   // 6 253889347574
   //      l       r
   // 6 253489347578
   //       l    r
   // 6 253459347878
   //        l r
   // 6 253454397878
   //         rl
   // 3 253454697878
   // p       rl   q
   // L LLLLLL RRRRR
   private void
   sort(int l, int r)
   {
      final int p = l;
      final int q = r;
      final int m = data[(l + r) / 2];

      if (l >= r) return;

      pivot_val = m;
      pos1 = pos2 = l;
      pos3 = pos4 = r;
      update(250);

      while (l <= r) {
         while (data[l] < m) { pos2 = ++l; update(100); }
         while (data[r] > m) { pos3 = --r; update(100); }

         if (l <= r)
            swap(data, l++, r--);

         pos2 = l;
         pos3 = r;
         update(250);
      }

      stack_depth++;
      sort(p, l - 1);
      sort(l, q);
      stack_depth--;
   }

   private void
   init()
   {
      data = new int[SIZE];
      for (int i = 0; i < SIZE; i++)
         data[i] = i + 4;
      Random r = new Random();
if (r.nextInt(2) == 0) {
      for (int i = 0; i < 100; i++) {
         int a = r.nextInt(40);
         int b = r.nextInt(40);
         swap(data, a, b); }
} else {
      for (int i = 0; i < SIZE; i++)
         data[i] = r.nextInt(40) + 4;
}
   }

   @Override
   public void
   run()
   {
      while (true) {
         init();
         sort(0, SIZE - 1);

         pivot_val = -1;
         pos1 = SIZE;
         pos2 = SIZE;
         pos3 = SIZE;
         pos4 = SIZE;
         update(10000);
      }
   }

   public
   Sort()
   {
      data = new int[SIZE];
   }

   public int
   getData(int index)
   {
      if (index < 0 || index >= SIZE)
         return 0;
      return data[index];
   }

   public void
   setHandler(Handler handler, View view)
   {
      h = handler;
      v = view;
   }
}

class
MyView
extends View
{
   Paint paint;
   Sort s;

   public
   MyView(Context c)
   {
      super(c);
      paint = new Paint();
      paint.setColor(Color.WHITE);
      paint.setTextSize(48);
      paint.setStyle(Paint.Style.FILL);
   }

   @Override
   public void
   onDraw(Canvas canvas)
   {
      paint.setColor(Color.WHITE);
      for (int i = 0; i < 40; i++) {
         final int x = i * 16;
         final int y = 0;
         final int W = 14;
         final int h = s.getData(i) * 16;

         if (s.getData(i) == s.pivot_val)
            paint.setColor(Color.argb(0xff, 0, 0xff, 0xff));
         else if (i >= s.pos1 && i < s.pos2)
            paint.setColor(Color.argb(0xff, 0xff, 0, 0xff));
         else if (i <= s.pos4 && i > s.pos3)
            paint.setColor(Color.argb(0xff, 0xff, 0, 0xff));
         else if (i >= s.pos1 && i <= s.pos4)
            paint.setColor(Color.argb(0xff, 0xff, 0xff, 0));
         else
            paint.setColor(Color.WHITE);
         canvas.drawRect(x, y, x + W, y + h, paint); }

      paint.setColor(Color.argb(0xff, 0, 0xff, 0xff));
      final int piv = s.pivot_val * 16;
      canvas.drawLine(0, piv, 1000, piv, paint);

      paint.setColor(Color.WHITE);
      canvas.drawText(String.valueOf(s.stack_depth), 100, 50 * 16, paint);
      for (int i = 0; i < s.stack_depth; i++) {
         final int x = i * 16;
         final int y = 52 * 16;
         final int W = 14;
         final int H = 14;
         canvas.drawRect(x, y, x + W, y + H, paint); }
   }

   public void
   setSort(Sort sort)
   {
      s = sort;
   }
}

public class
MainAct
extends Activity
{
   Handler  handler = new Handler();
   MyView   view;
   Sort     sort;

   @Override
   protected void
   onCreate(Bundle b)
   {
      super.onCreate(b);

      sort = new Sort();
      view = new MyView(this);
      sort.setHandler(handler, view);
      view.setSort(sort);
      sort.start();
      setContentView(view);
   }
}
