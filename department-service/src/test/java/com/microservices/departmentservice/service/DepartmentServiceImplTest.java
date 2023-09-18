package com.microservices.departmentservice.service;

import com.microservices.departmentservice.domain.Department;
import com.microservices.departmentservice.domain.dto.DepartmentDTO;
import com.microservices.departmentservice.exception.DepartmentCodeAlreadyExistsException;
import com.microservices.departmentservice.mapper.DepartmentMapper;
import com.microservices.departmentservice.repository.DepartmentRepository;
import com.microservices.departmentservice.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @InjectMocks
    public DepartmentServiceImpl departmentService;

    @Mock
    public DepartmentRepository departmentRepository;

    @Mock
    public DepartmentMapper departmentMapper;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDepartmentByCodeTest() {
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();

        Department department = Department.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();

        when(departmentRepository.findByDepartmentCode("test")).thenReturn(Optional.ofNullable(department));
        when(departmentMapper.toDepartmentDTO(department)).thenReturn(departmentDTO);

        DepartmentDTO actual = departmentService.getDepartmentByCode("test");

        assertEquals(departmentDTO, actual);

        verify(departmentRepository, times(1)).findByDepartmentCode("test");

    }

    @Test
    void getAllDepartmentsTest() {
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();


        Department department = Department.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();

        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(department));
        when(departmentMapper.toDepartmentDTO(department)).thenReturn(departmentDTO);

        List<DepartmentDTO> actualList = departmentService.getAllDepartments();
        assertEquals(Collections.singletonList(departmentDTO), actualList);

        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void saveDepartmentTest() {

        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();

        Department department = Department.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();

        when(departmentRepository.findByDepartmentCode("test")).thenReturn(Optional.empty());
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentMapper.toDepartment(departmentDTO)).thenReturn(department);
        when(departmentMapper.toDepartmentDTO(department)).thenReturn(departmentDTO);

        DepartmentDTO actual = departmentService.saveDepartmnet(departmentDTO);
        assertEquals(departmentDTO, actual);
        verify(departmentRepository, times(1)).save(department);
    }


    @Test
    void saveDepartment_codeAlreadyUsedTest(){
        //given
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();

        Department department = Department.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();
        when(departmentRepository.findByDepartmentCode("test")).thenReturn(Optional.of(department));
        //expect
        assertThrows(DepartmentCodeAlreadyExistsException.class, () -> departmentService.saveDepartmnet(departmentDTO));
        //then
        verify(departmentRepository).findByDepartmentCode("test");
    }

    @Test
    void updateDepartmentTest() {

        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();

        Department department = Department.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();

        when(departmentRepository.findById(1l)).thenReturn(Optional.of(department));
        when(departmentRepository.save(department)).thenReturn(department);
        when(departmentMapper.toDepartmentDTO(department)).thenReturn(departmentDTO);

        DepartmentDTO actual = departmentService.updateDepartment(departmentDTO);
        assertEquals(departmentDTO, actual);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void updateDepartment_codeAlreadyUsedTest(){
        //given
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();

        Department department = Department.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();
        when(departmentRepository.findById(1l)).thenReturn(Optional.of(department));
        when(departmentRepository.findByDepartmentCode("test")).thenReturn(Optional.of(department));
        //expect
        assertThrows(DepartmentCodeAlreadyExistsException.class, () -> departmentService.updateDepartment(departmentDTO));
        //then
        verify(departmentRepository).findByDepartmentCode("test");
        verify(departmentRepository).findById(1L);
    }

    @Test
    void deleteDepartmentTest() {
        Department department = Department.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();
        when(departmentRepository.findById(1l)).thenReturn(Optional.of(department));
        departmentService.deleteDepartment(department.getId());
        verify(departmentRepository).deleteById(1L);

    }
}
