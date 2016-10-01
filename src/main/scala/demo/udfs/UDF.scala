package demo.udfs

import demo.data.{DataPoint, DoubleNumber}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * Created by zhenhao.li on 23/09/16.
  */
object UDF {

    type StreamMap = DataStream[(String, Long, Double)] => DataStream[DataPoint[DoubleNumber]]

    def sumInOnePerioRolling: StreamMap =
        _
          .keyBy(0)
          .timeWindow(Time.milliseconds(3600))
          .sum(2)
          .uid("sumInOnePerioRolling-m")
          .map(tuple => new DataPoint(tuple._2, new DoubleNumber(tuple._3)))



    def sumTotalCount: StreamMap =
        _
          .map(tuple => (tuple._1,tuple._2, 1))
          .keyBy(0)
          .reduce((t1, t2) => (t1._1, math.max(t1._2, t2._2), t1._3 + t2._3))
          .uid("sumTotalCount-m")
          .map(tuple => new DataPoint(tuple._2, new DoubleNumber(tuple._3)))


    def rawView: StreamMap =
        _
          .map(tuple => (tuple._1,tuple._2, tuple._3 * 2))
          .keyBy(0)
          .map(tuple => new DataPoint(tuple._2, new DoubleNumber(tuple._3)))



    def sumInOnePerioRolling2: StreamMap =
        _
          .keyBy(0)
          .timeWindow(Time.milliseconds(900))
          .sum(2)
          .uid("sumInOnePerioRolling-2")
          .map(tuple => new DataPoint(tuple._2, new DoubleNumber(tuple._3)))

}
