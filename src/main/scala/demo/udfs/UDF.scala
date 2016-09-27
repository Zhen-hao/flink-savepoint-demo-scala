package demo.udfs

import demo.data.{DataPoint, DoubleNumber}
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.api.scala._

/**
  * Created by zhenhao.li on 23/09/16.
  */
object UDF {

    def sumInOnePerioRolling(stream: DataStream[(String, Long, Double)]): DataStream[DataPoint[DoubleNumber]]= {
            stream
              .keyBy(0)
              .timeWindow(Time.milliseconds(3600))
              .sum(2)
              .map(tuple => new DataPoint(tuple._2, new DoubleNumber(tuple._3)))
    }


    def sumTotalCount(stream: DataStream[(String, Long, Double)]): DataStream[DataPoint[DoubleNumber]]={
        stream.map(tuple => (tuple._1,tuple._2, 1))
          .keyBy(0)
          .reduce((t1, t2) => (t1._1, math.max(t1._2, t2._2), t1._3 + t2._3))
          .map(tuple => new DataPoint(tuple._2, new DoubleNumber(tuple._3)))

    }


}
