package com.app.batch.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.batch.repository.PartnerUrlRepository;

public class RangePartitioner implements Partitioner {
	@Autowired
	PartnerUrlRepository partnerUrlRepository;

	/**
	 * This method is used to create threads and map the url's to ExecutionContext
	 * for each thread
	 *
	 */
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		List<String> list = partnerUrlRepository.findAll().stream().map(n -> n.getPartnerUrl())
				.collect(Collectors.toList());

		List<String> first = new ArrayList<>(list.subList(0, (list.size()) / 2));
		List<String> second = new ArrayList<>(list.subList((list.size()) / 2, list.size()));

		Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();

		for (int i = 0; i < gridSize; i++) {
			ExecutionContext value = new ExecutionContext();

			System.out.println("\nStarting : Thread" + i);

			if (i == 0) {
				value.put("urlList", first);
			} else {
				value.put("urlList", second);
			}
			// give each thread a name, thread 1,2
			value.putString("name", "Thread" + i);

			result.put("partition" + i, value);

		}

		return result;
	}
}
