package idv.heimlich.thread_test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 訊息處理檔案服務實作
 */
public class App {

	public static void main(String[] args) {
		final App app = new App();
		app.doProcess();
	}

	public void doProcess() {
		final List<Po> list = this.getList();

		final Broker<List<Po>> broker = new BlockingQueueBroker<>(2);
		final Map<String, List<Po>> map = this.groupByName(list);
		final Producer producer = new Producer(broker, "producer", map);
		final ConsumerA consumerA = new ConsumerA(broker, "consumerA");
		final ConsumerB consumerB = new ConsumerB(broker, "consumerB");

		final Thread producerThread = new Thread(producer);
		final Thread consumerAThread = new Thread(consumerA);
		final Thread consumerBThread = new Thread(consumerB);

		producerThread.start();
		consumerAThread.start();
		consumerBThread.start();

		try {
			producerThread.join();
			while (!consumerAThread.getState().equals(Thread.State.WAITING)
					|| !consumerBThread.getState().equals(Thread.State.WAITING)) {
//				System.out.println("還在處理");
			}
			while (consumerAThread.getState().equals(Thread.State.WAITING)
					|| consumerBThread.getState().equals(Thread.State.WAITING)) {
				System.out.println("ConsumerAThread、ConsumerBThread 處理完成");
				consumerAThread.interrupt();
				consumerBThread.interrupt();
				Thread.sleep(3000);
			}
			System.out.println("ConsumerAThread " + consumerAThread.getState());
			System.out.println("ConsumerBThread " + consumerBThread.getState());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, List<Po>> groupByName(List<Po> list) {
		final Map<String, List<Po>> resultMap = new HashMap<String, List<Po>>();
		for (final Po po : list) {
			if (resultMap.containsKey(po.getName())) {
				resultMap.get(po.getName()).add(po);
			} else {
				final List<Po> newList = new ArrayList<Po>();
				newList.add(po);
				resultMap.put(po.getName(), newList);
			}
		}
		return resultMap;
	}

	private List<Po> getList() {
		final List<Po> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			final Po po = new Po();
			po.setName("A");
			po.setT1(String.valueOf(i));
			list.add(po);
		}
		for (int i = 0; i < 3; i++) {
			final Po po = new Po();
			po.setName("B");
			po.setT1(String.valueOf(i));
			list.add(po);
		}
		for (int i = 0; i < 1; i++) {
			final Po po = new Po();
			po.setName("C");
			po.setT1(String.valueOf(i));
			list.add(po);
		}
		for (int i = 0; i < 10; i++) {
			final Po po = new Po();
			po.setName("D");
			po.setT1(String.valueOf(i));
			list.add(po);
		}
		return list;
	}

}
