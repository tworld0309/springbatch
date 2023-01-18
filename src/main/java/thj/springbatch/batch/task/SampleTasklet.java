package thj.springbatch.batch.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
public class SampleTasklet {

	// 호출
	// java -jar build/libs/springbatch-1.0.jar --job.name=simpleJob
	// java -jar build/libs/springbatch-1.0.jar --job.name=sampleTaskletJob

	public final JobBuilderFactory jobBuilderFactory;
	public final StepBuilderFactory stepBuilderFactory;

	////// case 1
	@Bean
	public Job simpleJob() {
		log.debug("simpleJob");
		return jobBuilderFactory.get("simpleJob")
				.start(simpleStep1())
				.build();
	}

	public Step simpleStep1() {
		log.debug("simpleStep1");
		return stepBuilderFactory.get("simpleStep1")
				.tasklet((contribution, chunkContext) -> {
					log.info(">>>>> This is Step1");
					return RepeatStatus.FINISHED;
				})
				.build();
	}



	////// case 2

	@Bean
	public Job sampleTaskletJob() {
		return jobBuilderFactory.get("sampleTaskletJob")
				.incrementer(new RunIdIncrementer())
				.flow(sampleTaskletStep1())
				.next(sampleTaskletStep2())
				.end()
				.build();
	}
	
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
