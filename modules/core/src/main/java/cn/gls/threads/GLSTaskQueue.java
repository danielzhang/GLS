package cn.gls.threads;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: GLSTaskQueue.java
 * @Description 任务队列
 * @Date 2012-6-28
 * @author "Daniel Zhang"
 * @version V1.0
 * @update 2012-6-28
 */
public class GLSTaskQueue {
	private List<Object> queue = new LinkedList<Object>();

	public synchronized GLSTaskInterface getTask() {
		if (queue.size() == 0)
			return null;
		GLSTaskInterface task = (GLSTaskInterface) queue.remove(0);
		return task;
	}
	public synchronized void putTask(GLSTaskInterface task) {
		queue.add(task);
	}
}
