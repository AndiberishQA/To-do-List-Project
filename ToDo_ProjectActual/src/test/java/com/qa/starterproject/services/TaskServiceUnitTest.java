package com.qa.starterproject.services;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.starterproject.persistence.domain.TaskDomain;

import com.qa.starterproject.persistence.dtos.TaskDTO;
import com.qa.starterproject.persistence.repos.TaskRepo;

@SpringBootTest
public class TaskServiceUnitTest {

	@MockBean
	private ModelMapper mapper;

	@MockBean
	private TaskRepo mockedRepo;

	@Autowired
	private TaskService service;

	@Test
	public void create() {
		// Resources employed
		TaskDomain exampleTask = new TaskDomain(1L, "Laundry", true, null);
		TaskDTO exampleDTO = new TaskDTO(1L,"Laundry", true);

		// Rules set
		Mockito.when(this.mockedRepo.save(Mockito.any(TaskDomain.class))).thenReturn(exampleTask);
		Mockito.when(this.mapper.map(exampleTask, TaskDTO.class)).thenReturn(exampleDTO);

		// Actions performed
		TaskDTO result = this.service.create(exampleTask);

		// Assertions made
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(exampleDTO);
		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(exampleDTO);

		Mockito.verify(this.mockedRepo, Mockito.times(1)).save(Mockito.any(TaskDomain.class));
		Mockito.verify(this.mapper, Mockito.times(1)).map(exampleTask, TaskDTO.class);

	}

	@Test
	public void readById() {
		// Resources employed
		TaskDomain exampleTask = new TaskDomain(1L, "groceries", false, null);
		TaskDTO exampleDTO = new TaskDTO(1L,"groceries", false);

		// Rules set
		Mockito.when(this.mapper.map(exampleTask, TaskDTO.class)).thenReturn(exampleDTO);
		Mockito.when(this.mockedRepo.findById(exampleTask.getId())).thenReturn(Optional.of(exampleTask));
		// Actions performed
		TaskDTO result = this.service.readById(1L);

		// Assertions made
		Assertions.assertThat(result).isEqualTo(exampleDTO);
		Mockito.verify(this.mockedRepo, Mockito.times(1)).findById(1L);
	}
    
	@Test
	public void update() {
		// Resources employed
		TaskDomain exampleTask = new TaskDomain(1L, "OneTask", false, null);
		TaskDTO exampleDTO = new TaskDTO(1L, "OneTask", false);

		Long id = 1L;

		// Rules set
		Mockito.when(this.mockedRepo.findById(exampleTask.getId())).thenReturn(Optional.of(exampleTask));
		Mockito.when(this.mockedRepo.save(Mockito.any(TaskDomain.class))).thenReturn(exampleTask);
		Mockito.when(this.mapper.map(exampleTask, TaskDTO.class)).thenReturn(exampleDTO);

		// Actions performed
		TaskDTO result = this.service.update(id, exampleTask);

		// Assertions made
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(exampleDTO);
		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(exampleDTO);

		Mockito.verify(this.mockedRepo, Mockito.times(1)).findById(id);
		Mockito.verify(this.mockedRepo, Mockito.times(1)).save(exampleTask);
		Mockito.verify(this.mapper, Mockito.times(1)).map(exampleTask, TaskDTO.class);

	}
    
	@Test
	public void readAll() {
		// Resources employed
		TaskDomain exampleA = new TaskDomain(1L, "cooking", false, null);
		TaskDomain exampleB = new TaskDomain(2L, "dishes", true, null);
		List<TaskDomain> taskDomainList = new ArrayList<>();
		taskDomainList.add(exampleA);
		taskDomainList.add(exampleB);

		TaskDTO testTaskDTO1 = new TaskDTO(1L,"cooking", false);
		TaskDTO testTaskDTO2 = new TaskDTO(2L,"dishes", true);
		List<TaskDTO> taskListDTO = new ArrayList<>();
		taskListDTO.add(testTaskDTO1);
		taskListDTO.add(testTaskDTO2);

		// Rules set
		Mockito.when(this.mapper.map(exampleA, TaskDTO.class)).thenReturn(testTaskDTO1);
		Mockito.when(this.mapper.map(exampleB, TaskDTO.class)).thenReturn(testTaskDTO2);
		Mockito.when(this.mockedRepo.findAll()).thenReturn(taskDomainList); 

		// Actions performed
		List<TaskDTO> result = this.service.readAll();

		// Assertions made
		Assertions.assertThat(result).isNotNull();
		Assertions.assertThat(result).isEqualTo(taskListDTO);
		Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(taskListDTO);

		Mockito.verify(this.mapper, Mockito.times(1)).map(exampleA, TaskDTO.class);
		Mockito.verify(this.mapper, Mockito.times(1)).map(exampleB, TaskDTO.class);
		Mockito.verify(this.mockedRepo, Mockito.times(1)).findAll();}
	
	

		@Test
		public void delete() {
			// Resources employed
			TaskDomain exampleTask = new TaskDomain(1L, "hoovering", true, null);

			// Rules set
			Mockito.when(this.mockedRepo.existsById(1L)).thenReturn(false);

			// Actions performed
			boolean result = this.service.delete(1L);

			// Assertions made
			Assertions.assertThat(result).isTrue();

			Mockito.verify(this.mockedRepo, Mockito.times(1)).deleteById(1L);

		}
		
}

