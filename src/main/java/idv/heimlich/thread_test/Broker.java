package idv.heimlich.thread_test;

public interface Broker<T> {

	T take() throws InterruptedException;

	void put(T obj) throws InterruptedException;

}
