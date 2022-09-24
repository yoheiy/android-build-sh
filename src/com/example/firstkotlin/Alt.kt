package com.example.firstkotlin

class Alt {
   val the_language = "Kotlin"

   fun say_hello() = "hello, I was written in $the_language.\n" + countup(8)
   fun countup(n: Int) = (1 until n).fold(""){
      a, x -> a + "$x! = ${ fact(x) }\n"
   }
   fun fact(x: Int) = (1..x).fold(1){ a, n -> a * n }
}
