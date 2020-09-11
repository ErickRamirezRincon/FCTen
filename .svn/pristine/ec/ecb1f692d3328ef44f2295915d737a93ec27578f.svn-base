package mx.com.fincomun.tenderos.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ArticuloJob extends QuartzJobBean {
	
	private ArticuloTask articuloTask; 
	
//	private ArticuloTask articuloTask =  new ArticuloTask();
//
	public void setArticuloTask(ArticuloTask articuloTask) {
		this.articuloTask = articuloTask;
	}

	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		articuloTask.initActuzalizaStock();
	}
}