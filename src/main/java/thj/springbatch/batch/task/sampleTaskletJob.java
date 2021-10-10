package thj.springbatch.batch.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class sampleTaskletJob {
	
	private static final Logger log = LoggerFactory.getLogger(sampleTaskletJob.class);
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job sampleTaskletJob() {
		return jobBuilderFactory.get("sampleTaskletJob")
				.incrementer(new RunIdIncrementer())
				.flow(sampleTaskletStep1())
				.next(sampleTaskletStep2())
				.end()
				.build();
	}
	
	@Bean
	public Step sampleTaskletStep1() {
		log.info("sampleTaskletStep1");
		return stepBuilderFactory.get("sampleTaskletStep1")
				.tasklet((contribution, chunkContext)->{
					for(int idx = 0; idx < 20; idx++) {
						log.info("step : "+ idx);
					}
					return RepeatStatus.FINISHED;
				})
				.build();
	}
	
	@Bean
	public Step sampleTaskletStep2() {
		log.info("sampleTaskletStep2");
		return stepBuilderFactory.get("sampleTaskletStep2")
				.tasklet((contribution, chunkContext)->{
					for(int idx = 0; idx < 20; idx++) {
						log.info("step : "+ idx);
					}
					return RepeatStatus.FINISHED;
				})
				.build();
	}
}
