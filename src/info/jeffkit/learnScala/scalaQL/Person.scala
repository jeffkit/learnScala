package info.jeffkit.learnScala.scalaQL

class Person extends BaseQuery {
	object name extends StringField
	object city extends StringField
	object age extends IntegerField
	object gender extends StringField
	object isVip extends BooleanField
}

object Person extends Person

object OK extends Person

object Main {
    def main(args : Array[String]) : Unit = {
    	
    }
}