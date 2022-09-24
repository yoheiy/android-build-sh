package com.example.firstkotlin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class
MainAct
extends Activity
{
   @Override
   protected void
   onCreate(Bundle b)
   {
      super.onCreate(b);
      setContentView(R.layout.main_act);

      Sub s = new Sub();
      Sub a = new Alt();
      TextView hello = (TextView)findViewById(R.id.text_hello);

      hello.setText(s.say_hello() +
                    a.say_hello());
   }
}
