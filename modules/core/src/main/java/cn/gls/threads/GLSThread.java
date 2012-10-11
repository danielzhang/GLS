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
	private boolean busy = false;
	private volatile boolean stop = false;
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
//		 System.out.println(getName() + "start");
		// System.out.println("线程是否停止:"+stop);
		// synchronized (this) {

		while (!stop) {
			GLSTaskInterface task = queue.getTask();
			if (task == null) {
				stop = true;
			}
			if (task != null) {
				busy = true;
				task.execute();
				busy = false;
			}
			// System.out.println(getName() + "end");
		}
	}
}
