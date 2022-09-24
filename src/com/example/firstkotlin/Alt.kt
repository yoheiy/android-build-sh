package com.example.firstkotlin

class Alt : Sub() {
   val the_language = "Kotlin"
   override fun lang() = the_language

   override fun say_hello() = super.say_hello() + countup(8) + fib(100)
   fun countup(n: Int) = (1 until n).fold(""){
      a, x -> a + "$x! = ${ fact(x) }\n"
   }
   fun fact(x: Int) = (1..x).fold(1){ a, n -> a * n }
   fun fib(n: Int): String {
      var a: Long = 0
      var b: Long = 1
      var ret = ""

      (0 until n).forEach {
         ret += " [$it]=$a ;"
         val new_a = b
         val new_b = a + b
         a = new_a
         b = new_b
      }
      return ret
   }
}
