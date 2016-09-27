package demo.data;

public class KeyedDataPoint<T extends Number> extends DataPoint<T> {

  private String key;

  public KeyedDataPoint(){
    super();
    this.key = null;
  }

  public KeyedDataPoint(String key, long timeStampMs, T value) {
    super(timeStampMs, value);
    this.key = key;
  }

  @Override
  public String toString() {
    return getTimeStampMs() + "," + getKey() + "," + getValue();
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public <R extends Number> KeyedDataPoint<R> withNewValue(R newValue){
    return new KeyedDataPoint<>(this.getKey(), this.getTimeStampMs(), newValue);
  }

}
