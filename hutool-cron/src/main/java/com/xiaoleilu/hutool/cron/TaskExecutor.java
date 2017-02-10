package com.xiaoleilu.hutool.cron;

import com.xiaoleilu.hutool.cron.task.Task;

/**
 * 作业执行器<br>
 * 执行具体的作业，执行完毕销毁
 * @author Looly
 *
 */
public class TaskExecutor extends Thread{
	
	private Scheduler scheduler;
	private Task task;
	
	public TaskExecutor(Scheduler scheduler, Task task) {
		this.scheduler = scheduler;
		this.task = task;
	}
	
	@Override
	public void run() {
		try {
			scheduler.listenerManager.notifyTaskStart(this);
			task.execute();
			scheduler.listenerManager.notifyTaskSucceeded(this);
		} catch (Exception e) {
			scheduler.listenerManager.notifyTaskFailed(this, e);
		}finally{
			scheduler.notifyExecutorCompleted(this);
		}
	}
}
