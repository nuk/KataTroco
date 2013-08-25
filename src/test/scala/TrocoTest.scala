import collection.mutable.Stack
import org.scalatest._

object Troco {
	def of (paid:Double, cost:Double) : List[Double] ={
		var result = List[Double]()
		var multiplier = 100 // avoid FP issues
		var remainder = paid*multiplier-cost*multiplier
		var values = List(100.0, 50.0, 10.0, 5.0, 1.0, 
								 0.50, 0.10, 0.05, 0.01)

		var a = values.foreach { case (ammount) =>
			while (remainder >= ammount*multiplier){
				result = result :+ ammount
				remainder -= ammount*multiplier
			}
		}
		
		return result
	}
}


class TrocoSpec extends FlatSpec with ShouldMatchers {
    
  {
    var values = List(
      (0.02,0.01,List(0.01)),
      (0.05,0.02,List(0.01,0.01,0.01)),
      (0.06,0.01,List(0.05)),
      (0.14,0.07,List(0.05,0.01,0.01)),
      (1.0,0.34,List(0.50,0.10,0.05,0.01)),
	  (200.0,34.0,List(100.0,50.0,10.0,5.0,1.0)),
	  (201.0,34.34,List(100.0,50.0,10.0,5.0,1.0,0.50,0.10,0.05,0.01))
      )
    values.foreach { case(paid, cost, result) => 
        it should ("Paid %s for %s worth of goods, receive %s" format (paid, cost, result)) in {
          Troco.of(paid, cost) should equal (result)
        } 
      }
   }
}