package idv.heimlich.thread_test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueBroker<T> implements Broker<T> {

	private final BlockingQueue<T> queue;

	public BlockingQueueBroker(int num) {
		this.queue = new LinkedBlockingQueue<T>(num);
	}

	@Override
	public synchronized T take() throws InterruptedException {
		while (this.queue.size() <= 0) { // 沒待處理就等待
			this.wait();
		}
		final T obj = this.queue.remove();
		this.notifyAll();
		return obj;
	}

	@Override
	public synchronized void put(T obj) throws InterruptedException {
		while (this.queue.size() >= 2) { // 容量限制為 2
			this.wait();
		}
		this.queue.add(obj);
		this.notifyAll();
	}

}
