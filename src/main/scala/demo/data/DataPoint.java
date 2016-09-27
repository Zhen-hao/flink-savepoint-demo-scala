package demo.data;

public class DataPoint<T extends Number> {

  private long timeStampMs;
  private T value;

  public DataPoint() {
    this.timeStampMs = 0;
    this.value = null;
  }

  public DataPoint(long timeStampMs, T value) {
    this.timeStampMs = timeStampMs;
    this.value = value;
  }

  public long getTimeStampMs() {
    return timeStampMs;
  }

  public void setTimeStampMs(long timeStampMs) {
    this.timeStampMs = timeStampMs;
  }

  public double getValue() {
    return value.doubleValue();
  }

  public void setValue(T value) {
    this.value = value;
  }

  public <R extends Number> DataPoint<R> withNewValue(R newValue){
    return new DataPoint<>(this.getTimeStampMs(), newValue);
  }

  public <R extends Number> KeyedDataPoint<R> withNewKeyAndValue(String key, R newValue){
    return new KeyedDataPoint<>(key, this.getTimeStampMs(), newValue);
  }

  public KeyedDataPoint withKey(String key){
    return new KeyedDataPoint<>(key, this.getTimeStampMs(), this.getValue());
  }

  @Override
  public String toString() {
    return "DataPoint(timestamp=" + timeStampMs + ", value=" + getValue() + ")";
  }
}
