package cn.gls.geocoding.engine.batch;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @date 2012-8-14
 * @author "Daniel Zhang"
 * @update 2012-8-14
 * @description TODO
 *
 */
public class GeoCodingThreadGroup extends ThreadGroup {
	private List<Thread> threads = new LinkedList<Thread>();
	private GeoCodingTaskQueue queue;

	public GeoCodingThreadGroup(GeoCodingTaskQueue queue) {
		super("GeoCoding");
		this.queue = queue;
	}

	public synchronized void addGeoCodingEngineThread() {
		Thread thread = new GeoCodingEngineThread(this, queue);
		threads.add(thread);
		thread.start();
	}

	public synchronized void removeGeoCodingEngineThread() {
      if(this.threads.size()>0){
    	  GeoCodingEngineThread thread=(GeoCodingEngineThread)threads.remove(0);
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
	public GeoCodingThreadGroup(ThreadGroup parent, String name) {
		super(parent, name);

	}

}
