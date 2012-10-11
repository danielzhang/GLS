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
		//System.out.println("线程的ID为:"+thread.getId());
		threads.add(thread);
		thread.start();
	}

	public synchronized void removeGLSThread() {
      if(this.threads.size()>0){
    	  GLSThread thread=(GLSThread)threads.remove(0);
    	  thread.shutdown();
      }
	}
//	public synchronized void currentStatus(){
//		System.out.println("Thread count="+threads.size());
//		Iterator iterator=threads.iterator();
//		while (iterator.hasNext()){
//			GeoCodingEngineThread thread = (GeoCodingEngineThread) iterator.next();
//			System.out.println(thread.getName()+":"+(thread.isldle()?"ldle":"busy"));
//		}
//	}

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
