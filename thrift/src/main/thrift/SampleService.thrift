namespace * com.sample.thrift

struct Data
{
    1: required string value
}

service SampleService {
  oneway void hi(1: required Data data);
}