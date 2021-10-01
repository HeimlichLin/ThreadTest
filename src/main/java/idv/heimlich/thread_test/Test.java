package idv.heimlich.thread_test;

import java.util.LinkedList;

public class Test {

	public static void main(String[] args) {
		final Store store = new Store();
		(new Thread(new TestProducer(store))).start();
		(new Thread(new TestConsumer(store))).start();
	}

}

class Store {
	private final LinkedList<Integer> products = new LinkedList<Integer>();

	synchronized void add(Integer product) {
		while (this.products.size() >= 2) { // 容量限制為 2
			try {
				this.wait();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.products.addLast(product);
		this.notifyAll();
	}

	synchronized Integer get() {
		while (this.products.size() <= 0) {
			try {
				this.wait();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		final Integer product = this.products.removeFirst();
		this.notifyAll();
		return product;
	}
}

class TestProducer implements Runnable {
	private final Store store;

	TestProducer(Store store) {
		this.store = store;
	}

	@Override
	public void run() {
		for (int product = 1; product <= 10; product++) {
			try {
				// wait for a random time
				Thread.sleep((int) Math.random() * 3000);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			this.store.add(product);
			System.out.println("Produce " + product);
		}
	}
}

class TestConsumer implements Runnable {
	private final Store store;

	TestConsumer(Store store) {
		this.store = store;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 10; i++) {
			try {
				// wait for a random time
				Thread.sleep((int) (Math.random() * 3000));
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Consume " + this.store.get());
		}
	}
}
