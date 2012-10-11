
package cn.gls.geocoding.engine.batch;


/**
 * @ClassName: GeoCodingEngineThread.java
 * @Description 真正执行任务的服务器端线程,在这个类是一个线程类，能够在Run方法里执行服务器功能。
 * @Date  2011-7-26
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-5-29
 */
public class GeoCodingEngineThread extends Thread {
	private boolean busy = false;
	private boolean stop = false;
	private GeoCodingTaskQueue queue;
	/**
	 * 定义一个GeoCodingEngine的线程
	 *@Title:GeoCodingEngineThread
	 *@Description:TODO
	 *@param: @param group
	 *@param: @param queue
	 *@return:
	 * @throws
	 */
	public GeoCodingEngineThread(ThreadGroup group, GeoCodingTaskQueue queue) {
		super(group, "work");
		this.queue = queue;
	}

	public void shutdown() {
		stop = true;
		this.interrupt();
		try {
			this.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isldle() {

		return !busy;
	}

	@Override
	public void run() {

		while (!stop) {
			 System.out.println("线程是否停止:" + stop);
			GeoCodingTaskInterface task = queue.getTask();
			if (task == null) {
				stop = true;
			}
			if (task != null) {
				busy = true;
				// System.out.println(getName() + "开始时的时间为"
				// + System.currentTimeMillis());
				task.execute();
				busy = false;
				// System.out.println(getName() + "结束时的时间为"
				// + System.currentTimeMillis());
			}
			// System.out.println(getName() + "end");
		}
		// }

	}

}
