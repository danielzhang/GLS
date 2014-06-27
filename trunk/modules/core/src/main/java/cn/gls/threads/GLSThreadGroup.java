package cn.gls.threads;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: GLSThreadGroup.java
 * @Description  线程组
 * @Date  2012-6-28
 * @author "Daniel Zhang"
 * @version  V1.0
 * @update  2012-6-28
 */
public class GLSThreadGroup extends ThreadGroup{
	private List<Thread> threads = new LinkedList<Thread>();
	
	private GLSTaskQueue queue;

	public GLSThreadGroup(GLSTaskQueue queue) {
		super("GLS");
		this.queue = queue;
	}

	public synchronized void addGLSThread() {
		Thread thread = new GLSThread(this, queue);
		threads.add(thread);
		thread.start();
	}

	public synchronized void removeGLSThread() {
      if(this.threads.size()>0){
    	  GLSThread thread=(GLSThread)threads.remove(0);
    	  thread.shutdown();
      }
	}

	/**
	 * @param parent
	 * @param name
	 *            <li><li>
	 * 
	 */
	public GLSThreadGroup(ThreadGroup parent, String name) {
		super(parent, name);

	}
}
