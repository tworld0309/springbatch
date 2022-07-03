package thj.springbatch.batch.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

@Slf4j
public class sampleTaskletJob {

	// 호출
	// java -jar batch-process-0.0.1-SNAPSHOT.jar --job.name=sampleTaskletJob

	 JobBuilderFactory jobBuilderFactory;
	 StepBuilderFactory stepBuilderFactory;

	public sampleTaskletJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory ) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

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
