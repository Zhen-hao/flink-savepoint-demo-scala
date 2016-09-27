package demo.udfs

import org.apache.flink.streaming.api.scala.DataStream
/**
  * Created by zhenhao.li on 23/09/16.
  */
object UDFHelper {

    implicit class StreamForUDF[T,U](stream: DataStream[T]){

        def applyUDF(udf : DataStream[T] => DataStream[U]): DataStream[U] ={
            udf(stream)
        }

    }
}

