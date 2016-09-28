package demo


/**
  * Created by zli01 on 16/08/2016.
  */


object JobRunner {

  def main(args: Array[String]): Unit = {

    val app = new Demo(args(0))
    app.run()

  }
}





// -topic test-events  -groupID demo -measurementName sensors

/*

val topic = if (args(0) == "-topic")  args(1) else throw new IllegalArgumentException("missing argument -topic")
val groupID = if (args(2) == "-groupID")  args(3) else throw new IllegalArgumentException("missing argument -groupID")
val measurementName = if (args(4) == "-measurementName")  args(5) else throw new IllegalArgumentException("missing argument -measurementName")
val app = new Demo

app.run(topic, groupID, measurementName)

*/
