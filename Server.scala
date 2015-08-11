import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
 
import com.mongodb.casbah.Imports._
import com.mongodb.util._

//Singleton
object DB {
  val conn = MongoClient("localhost", 27017)
  val db = conn("eec-news-dev")
  def apply(collection:String) = db(collection)
}

import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import scala.concurrent.Future

object Main extends App {
	implicit val system = ActorSystem()
	implicit val materializer = ActorMaterializer()
	 
	val serverSource = Http().bind(interface = "localhost", port = 8080)
	 
	val requestHandler: HttpRequest => HttpResponse = {

	  case HttpRequest(GET, Uri.Path("/data"), _, _, _) => {
	  	val data = DB("black_lists").find().toList
	    HttpResponse(entity = JSON.serialize(data))
	  }
	  
	  case _: HttpRequest =>
	    HttpResponse(404, entity = "Unknown resource!")
	}
	 
	val bindingFuture: Future[Http.ServerBinding] =
	  serverSource.to(Sink.foreach { connection =>
	    println("Accepted new connection from " + connection.remoteAddress)
	 
	    connection handleWithSyncHandler requestHandler
	    // this is equivalent to
	    // connection handleWith { Flow[HttpRequest] map requestHandler }
	  }).run()
}
