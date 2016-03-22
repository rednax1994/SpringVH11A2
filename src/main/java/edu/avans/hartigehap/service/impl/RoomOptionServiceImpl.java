package edu.avans.hartigehap.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import edu.avans.hartigehap.domain.Room;
import edu.avans.hartigehap.domain.RoomOption;
import edu.avans.hartigehap.domain.exception.StateException;
import edu.avans.hartigehap.repository.RoomOptionRepository;
import edu.avans.hartigehap.repository.RoomRepository;
import edu.avans.hartigehap.service.RoomOptionService;
import lombok.extern.slf4j.Slf4j;

@Service("roomOptionService")
@Repository
@Transactional(rollbackFor = StateException.class)
@Slf4j
public class RoomOptionServiceImpl implements RoomOptionService {
	@Autowired
	private RoomOptionRepository roomOptionRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<RoomOption> findAll() {
		List<RoomOption> retval = Lists.newLinkedList(roomOptionRepository.findAll());
		LOGGER.info("" + retval);
		return retval;
	}

	@Override
	@Transactional(readOnly = true)
	public RoomOption findById(long id) {
		return roomOptionRepository.findOne(id);
	}

	@Override
	public RoomOption save(RoomOption roomOption) {
		return roomOptionRepository.save(roomOption);
	}

	@Override
	public void delete(RoomOption roomOption) {
		roomOptionRepository.delete(roomOption);
	}

	// to be able to follow associations outside the context of a transaction,
	// prefetch the associated entities by traversing the associations
	@Transactional(readOnly = true)
	public RoomOption fetchWarmedUp(Long id) {
		log.info("(fetchWarmedUp) room id: " + id);

		// finding an item using find
		RoomOption roomOption = roomOptionRepository.findOne(id);

		// the following code will deliberately cause a null pointer exception,
		// if something is wrong
		log.info("diningTable = " + roomOption.getId());

		return roomOption;
	}

}
