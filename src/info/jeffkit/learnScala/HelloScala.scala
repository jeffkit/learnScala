package info.jeffkit.learnScala

import scala.actors.Actor._

class Group private (val name:String){
	def say(msg:String):Unit = {
		println ("The Group " + this.name + " say : " + msg)
	}
	
	def play(game:String,members:Array[Member],perform:Member => Unit) = {
		println ("The group is playing a game : " + game )
		members.foreach{
			member => perform(member)
		}
	}
	
	def play_curry(game:String,members:Array[Member])(perform:Member => String) = {
		println ("The group is playing a game : " + game )
		members.foreach(member => println(perform(member)))
	}
	
	def search(condition:Any):String = {
		condition match {
			case "sleepy" => 
				println("you look sleepy,sleep 1 second")
				Thread.sleep(1000)
			case q:String => println("search by a keyword :" + q)
			case ("name",name:String) => println("search by name : " + name)
			case (year:Int,month:Int) => println("search by year " + year + " and " + month)
			case _ => println("can't handle your search");return "fail"
		}
		return "success"
	}
}

object Group{
	private val groups = Map(
		"techparty" -> new Group("techparty"),
		"barcamp" -> new Group("barcamp"),
		"openparty" -> new Group("openparty")
	)
	
	def getGroup(name:String) = {
		if (groups.contains(name)) groups(name) else null
	}
}

//easy way to define a bean
class Member(val name:String, val gender:String,var committee:Boolean,val topic:String) extends Fan{
	def this(name:String){
		this(name,"male",false,null)
	}
}

trait Fan{
	def like() = "techparty"
}

trait JavaFan extends Fan{
	override def like() = "java " + super.like()
}

trait PythonFan extends Fan{
	override def like() = "python " + super.like()
}

object HelloScala {
	
  def main(args : Array[String]) : Unit = {
	  println("hello scala")
	  
	  val techparty = Group.getGroup("techparty")
	  techparty.say("hello scala")
	  techparty say "i'm the magic of DSL" //DSL的基础
	  
	  val barcamp = Group.getGroup("barcamp")
	  barcamp say "hello scala"
	  
	  val members = Array(
		  new Member("laiyonghao","male",true,"2010,My choices"),
		  new Member("panjunyong","male",true,"full text searching"),
		  new Member("xiaoxing","female",false,"About managememnt"),
		  new Member("jeff","male",true,"learn scala in haft an hour") with JavaFan with PythonFan,
		  new Member("iceberg"),
		  new Member("others")
	  )
	  
	  //lambda
	  techparty.play("introduce your self", members, member => println ("My name is " + member.name))
	  
	  //传递函数
	  def present(member:Member) = {
	 	  if (member.topic != null)
	 		  println (member.name + " is presenting.")
	  }
	  techparty.play("present", members, present)
	  
	  //偏应用函数,新函数比原函数多了一些业务性质
	  val potluck = techparty.play("potluck", members, _:Member => Unit)
	  potluck(_.name + " is eating")
	  
	  // ============ curry化 ===============
	  
	  techparty.play_curry("dismiss", members){
	 	  member => member.name + " is leaving and going home"
	  }
	   
	  // Trait,类似接口，可创建时加入。可做装饰器(使用super.method)
	  members.foreach{
	 	  member =>
	 	   	val like = member.like()
	 	   	println(member.name + " likes " + like)
	  }
	  
	  // 模式匹配
	   
	   techparty.search("jeff")
	   techparty.search("name","jeff")
	   techparty.search(2010,12)
	  
	  // 并发编程(内置框架的支持，可视为语言级别的支持）
	   
	   println ("now .. search service is open!")
	   
	   val receiver = self
	   
	   actor {
	  	   receiver ! techparty.search("jeff",2010)
	   }
	   
	   val at = actor { //相当于启动一条新的线程来做这件事
	  	   receiver ! (self,techparty.search("sleepy"))
	   }
	   
	   println("waiting for actor to finish")
	   //Thread.sleep(3000)
	   
	   for(i <- 1 to 2){
	  	   receive {
	  	   	case (at,status:String) => println("hey,at is finish his job,and the status is: " + status)
	  	   	case "success" => println("search finish")
	  	   	case "fail" => println("search fail")
	  	   }
	   }
	   
	   
	   println("done!")
	   
	  // DSL schedu mini_shalon 4 days later
  }
}
