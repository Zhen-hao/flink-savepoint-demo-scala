package demo.data

/**
  * Created by zhenhao.li on 19/09/16.
  */
class DoubleNumber(val value: Double) extends java.lang.Number {
    def longValue =  value.toLong
    def floatValue = value.toFloat

    override def intValue(): Int = value.toInt

    override def doubleValue(): Double = value
}

