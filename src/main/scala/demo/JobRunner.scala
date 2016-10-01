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


