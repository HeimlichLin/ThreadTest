package idv.heimlich.thread_test;

import java.util.List;

public class ConsumerB implements Runnable {

	private final Broker<List<Po>> broker;
	private final String name;
	public volatile boolean terminate = false;

	public ConsumerB(Broker<List<Po>> broker, String name) {
		this.broker = broker;
		this.name = name;
	}

	@Override
	public void run() {
		while (!this.terminate) {
			try {
				final List<Po> list = this.broker.take();
				for (final Po po : list) {
					System.out.format("%s : %s time: %s%n", this.name, po.getName() + po.getT1() + po.getT2(),
							DateUtlis.nowTime());
				}
				System.out.format("Consumer: %s sleep......%n", this.name);
				Thread.sleep(5000);
			} catch (final InterruptedException e) {
				System.out.format("Consumer: %s 執行緒被終止.......%n", this.name);
				e.printStackTrace();
				this.terminate = true;
				break;
			}
		}
	}

	public void terminate() {
		this.terminate = true;
	}

}
