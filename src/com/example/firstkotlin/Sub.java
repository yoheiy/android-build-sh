package com.example.firstkotlin;

public class Sub {
   private String the_language = "Java";
   public String lang() { return the_language; }

   public String say_hello() {
      return "hello, I was written in " + lang() + ".\n";
   }
}
