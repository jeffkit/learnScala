package net.jf

class People(val name:String){
	
	def ask(who:People) = {
		println("asking " + who.name)
		who
	}
	
	def ask2(who:String)(_do:String)(sth:String) = {
		println(this.name + " asking " + who + " to " + _do + " " + sth)
	}
	
	def eat(sth:String) = {
		println("eating " + sth)
	}
	
	
	override def toString = {
		this.name
	}
}

object Hello {
	
	def hello(name:String)(op: => Unit) = {
		println("hello " + name)
		op
	}
	
	def ask(who:String)(_do:String)(sth:String) = {
		println("asking " + who + " to " + _do + " " + sth)
	}
	
	def ask2(who:String,_do:String,sth:String) = {
		println("asking " + who + " to " + _do + " " + sth)
	}
	
	def short(name:String) = {
		println("shor for")
	}
	
	def noparam() = {
		println("no param")
	}
	
	def noparam2 = {
		println("no param too")
	}
	
	def main(args : Array[String]) : Unit = {
		hello("jeff"){println("hello world")}
		ask("jeff")("eat")("rice")
		
		noparam
		noparam()
		
		noparam2
		
		val vera = new People("vera")
		val jeff = new People("jeff")
		vera  ask (jeff) eat ("rice")
	}
}
