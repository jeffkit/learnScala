package net.jf;

object HelloWorld {
  
  def totalResultOverRange(number:Int,codeBlock:Int => Int):Int = {
    var result = 0
    for (i <- 1 to number){
      result += codeBlock(i)
    }
    result
  }
  
  def inject(arr:Array[Int],initial:Int,operation:(Int,Int) => Int):Int = {
    var carryOver = initial
    arr.foreach(ele => carryOver = operation(carryOver,ele))
    carryOver
  }
  
  def inject_1(arr:Array[Int],initial:Int)(operation:(Int,Int) => Int):Int = {
    var carryOver = initial
    arr.foreach(ele => carryOver = operation(carryOver,ele))
    carryOver
  }
  
  def main(args:Array[String]) = {
    println("hello world");  
    println(totalResultOverRange(3,i => i))
    
    val array = Array(2,3,5,6,1,4)
    val sum = inject(array,0,(carryOver,ele) => carryOver + ele)
    val max = inject(array,0,(carryOver,ele) => Math.max(carryOver,ele))
    println("sum is: " + sum)
    println("max is:" + max)
    
    //要搞清楚(0 /: array)是啥意思！
    val sum_1 = (0 /: array) {(carryOver,ele) => carryOver + ele}
    val max_1 = (Integer.MIN_VALUE /:array) {
      (carryOver,ele) => Math.max(carryOver,ele)
     }
    
    println("sum is: " + sum_1)
    println("max is:" + max_1)
    
    val sum_2 = inject_1(array,0){(carryOver,ele) => carryOver + ele}
    println("sum is: " + sum_2)
    
    }
}