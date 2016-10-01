package demo

import demo.data.{DataPoint, DoubleNumber}
import demo.sinks.InfluxDBSink
import demo.udfs.UDF._
import demo.udfs.UDFHelper._
import org.apache.flink.api.scala._
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer09
import org.apache.flink.streaming.util.serialization.SimpleStringSchema
/**
  * Created by zhenhao.li on 17/09/16.
  */
class Demo(configFilePath: String) extends JobSettings(configFilePath) {

    def run() = {

        val env = StreamExecutionEnvironment.getExecutionEnvironment

        /** Check Point */
        env.enableCheckpointing(2000)

        /** Save point and state backend */
        env.setStateBackend(new FsStateBackend("file:///Users/zhenhao.li/workspace/flink-checkpoint"))

        /** Process Time vs Event Time   */
        //env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)


        val rawStream = env
          .addSource(new FlinkKafkaConsumer09[String](sensorTopic, new SimpleStringSchema(), kafkaProperties))
          .uid("source")

        val keyedStream = rawStream
                            .map(string => string.split(","))
                            .map(array => ("key", array(0).toLong, array(1).toDouble))



        /** Extract Eevent Time */
        val keyedStreamWithEventTime = keyedStream.assignAscendingTimestamps(_._2)
          .startNewChain().uid("event-time-extracted")//to give a name to this operator


        keyedStreamWithEventTime
          .applyUDF(sumInOnePerioRolling)
          .addSink(new InfluxDBSink[DataPoint[DoubleNumber]]("window-sum"))

        keyedStreamWithEventTime
          .applyUDF(sumTotalCount)
          .addSink(new InfluxDBSink[DataPoint[DoubleNumber]]("total-sum"))

        keyedStreamWithEventTime
          .applyUDF(rawView)
          .addSink(new InfluxDBSink[DataPoint[DoubleNumber]]("rawView"))

        keyedStreamWithEventTime
          .applyUDF(sumInOnePerioRolling2)
          .addSink(new InfluxDBSink[DataPoint[DoubleNumber]]("rollingSum2"))


        env.execute("Flink Demo")

    }

}
