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
import edu.avans.hartigehap.repository.RoomRepository;
import edu.avans.hartigehap.service.RoomService;

@Service("roomService")
@Repository
@Transactional
public class RoomServiceImpl implements RoomService {
	@Autowired
	private RoomRepository roomRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<Room> findAll() {
		List<Room> retval = Lists.newLinkedList(roomRepository.findAll());
		LOGGER.info("" + retval);
		return retval;
	}

	@Override
	@Transactional(readOnly = true)
	public Room findById(long id) {
		return roomRepository.findOne(id);
	}

	@Override
	public Room save(Room room) {
		return roomRepository.save(room);
	}

	@Override
	public void delete(Room room) {
		roomRepository.delete(room);
	}

}
