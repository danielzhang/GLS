package cn.gls.threads;

/**
 * @ClassName: GLSThread.java
 * @Description  gls的线程
 * @Date  2012-6-28
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-28
 */
public class GLSThread extends Thread{
	private static int count = 0;
	private boolean busy = false;
	private boolean stop = false;
	private GLSTaskQueue queue;
	public GLSThread(ThreadGroup group, GLSTaskQueue queue) {
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
		// System.out.println(getName() + "start");
		// System.out.println("线程是否停止:"+stop);
		// synchronized (this) {

		while (!stop) {
			// System.out.println("线程是否停止:" + stop);
			GLSTaskInterface task = queue.getTask();
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
