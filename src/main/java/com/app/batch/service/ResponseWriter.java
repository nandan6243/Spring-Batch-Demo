package com.app.batch.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.batch.model.PartnerJson;
import com.app.batch.repository.PartnerJsonRepository;

@Component
@StepScope
public class ResponseWriter implements ItemWriter<PartnerJson>, StepExecutionListener {

	@Autowired
	private PartnerJsonRepository partnerJsonRepository;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("Line Writer initialized.");
	}

	/**
	 * This method returns exit status after step execution
	 */
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return ExitStatus.COMPLETED;
	}

	/**
	 * This method is used to write the response JSON into the Database
	 */
	@Override
	public void write(List<? extends PartnerJson> partnerJson) {

		for (PartnerJson jsons : partnerJson) {
			Optional<PartnerJson> optPartnerData = partnerJsonRepository.findByPartnerName(jsons.getPartnerName());

			if (!optPartnerData.isPresent()) {
				partnerJsonRepository.saveAndFlush(jsons);
			} else {
				PartnerJson upadteJson = optPartnerData.get();
				upadteJson.setResponse(jsons.getResponse());
				upadteJson.setDateTime(new Timestamp(new Date().getTime()));

				partnerJsonRepository.saveAndFlush(upadteJson);
			}
		}

	}
}
