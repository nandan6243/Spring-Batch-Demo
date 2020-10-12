package com.app.batch.config;

import java.net.MalformedURLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import com.app.batch.model.UberEats;
import com.sun.el.parser.ParseException;

@Configuration
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Bean(name = "partitionerJob")
	public Job partitionerJob(ItemReader reader, ItemWriter writer)
			throws UnexpectedInputException, MalformedURLException, ParseException {
		return jobBuilderFactory.get("job").start(partitionStep(reader, writer)).build();
	}

	@Bean
	public Step partitionStep(ItemReader reader, ItemWriter writer)
			throws UnexpectedInputException, MalformedURLException, ParseException {
		return stepBuilderFactory.get("partitionStep").partitioner("slaveStep", partitioner())
				.step(childStep(reader, writer)).gridSize(2).taskExecutor(new SimpleAsyncTaskExecutor()).build();
	}

	@Bean
	public RangePartitioner partitioner() {
		RangePartitioner partitioner = new RangePartitioner();
		return partitioner;
	}

	@Bean
	public Step childStep(@SuppressWarnings("rawtypes") ItemReader reader,
			@SuppressWarnings("rawtypes") ItemWriter writer)
			throws UnexpectedInputException, MalformedURLException, ParseException {
		return stepBuilderFactory.get("slaveStep").<UberEats, UberEats>chunk(10).reader(reader).writer(writer).build();
	}

}
