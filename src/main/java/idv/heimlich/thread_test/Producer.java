package idv.heimlich.thread_test;

import java.util.List;
import java.util.Map;

public class Producer implements Runnable {

	private final Broker<List<Po>> broker;
	private final String name;
	private final Map<String, List<Po>> map; // 待處理的總表

	public Producer(Broker<List<Po>> broker, String name, Map<String, List<Po>> map) {
		this.broker = broker;
		this.name = name;
		this.map = map;
	}

	@Override
	public void run() {
		for (final String dataKey : this.map.keySet()) {
//			for (Po po : this.map.get(dataKey)) {
			try {
				System.out.format("%s: %s%n", this.name, dataKey);
				this.broker.put(this.map.get(dataKey));
				Thread.sleep(2000);
			} catch (final InterruptedException e) {
				System.out.format("%s: %s 執行緒被終止......%n", this.name, dataKey);
				e.printStackTrace();
			}
//			}
		}
		System.out.println("Producer 執行緒被終止.......");

	}

}
