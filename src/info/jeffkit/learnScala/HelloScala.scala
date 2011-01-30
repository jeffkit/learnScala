package info.jeffkit.learnScala

import scala.actors.Actor._

class Group private (val name:String){
	def say(msg:String) = {
		println ("The Group " + this.name + " say : " + msg)
	}
	
	def play(game:String,members:Array[Member],perform:Member => String) = {
		println ("The group is playing a game : " + game )
		members.foreach{
			member => 
			val result = perform(member)
			if (result != null) println(result)
		}
	}
	
	def play_curry(game:String,members:Array[Member])(perform:Member => String) = {
		this.play(game,members,perform)
	}
	
	def search(condition:Any):String = {
		Thread.sleep(2000)
		return "the result is : their are too many people named jeff!"
	}
}

object Group{
	private val groups = Map(
		"techparty" -> new Group("techparty"),
		"barcamp" -> new Group("barcamp")
	)
	
	def getGroup(name:String) = {
		if (groups.contains(name)) groups(name) else null
	}
}

class Member(val name:String,val topic:String) extends Fan{
	def this(name:String){
		this(name,null)
	}
}

trait Fan{
	def like() = "techparty"
}
trait JavaFan extends Fan{
	override def like() = "java," + super.like()
}
trait PythonFan extends Fan{
	override def like() = "python," + super.like()
}

object HelloScala {
  def main(args : Array[String]) : Unit = {
	  val techparty = Group.getGroup("techparty")
	  techparty say "i'm the magic of DSL" 
	  
	  val members = Array(
		  new Member("laiyonghao","2010,My choices")with PythonFan,
		  new Member("jeff","learn scala in haft an hour") with JavaFan with PythonFan,
		  new Member("iceberg")
	  )
	  
	  techparty.play("introduce your self", members, member => "My name is " + member.name + ", and .. I likes " + member.like())
	  
	  def present(member:Member) = {
	 	  if (member.topic != null) member.name + " is presenting." else null
	  }
	  techparty.play("present the topic", members, present)
	  
	  val potluck = techparty.play("let's go potluck", members, _:Member => String)
	  potluck(_.name + " is eating")
	  
	  techparty.play_curry("dismiss", members){
	 	  member => member.name + " is leaving and going home"
	  }
	   
	  val receiver = self
	  actor {
	  	  receiver ! techparty.search("jeff",2010)
	  }
	  val jane = actor {
	     receiver ! (self,techparty.search("gfw"))
	  }
	  for(i <- 1 to 2){
	     receive {
	    	case (at,status:String) => println("hey,jane have her searching job done!")
	  	   	case result:String => println("search result: " + result)
	     }
	  }
  }
}
