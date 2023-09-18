package com.microservices.departmentservice.controller;

import com.microservices.departmentservice.domain.dto.DepartmentDTO;
import com.microservices.departmentservice.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @InjectMocks
    public DepartmentController departmentController;
    @Mock
    public DepartmentServiceImpl departmentService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getDepartmentCodeTest() {
        String departmentCode = "test";
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();
        when(departmentService.getDepartmentByCode(departmentCode)).thenReturn(departmentDTO);

        ResponseEntity<DepartmentDTO> actual = departmentController.getDepartment(departmentCode);
        assertEquals(departmentDTO, actual.getBody());
        verify(departmentService, times(1)).getDepartmentByCode(departmentCode);
    }

    @Test
    void createDepartmentTest() {
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();
        when(departmentService.saveDepartmnet(departmentDTO)).thenReturn(departmentDTO);

        ResponseEntity<DepartmentDTO> actual = departmentController.createDepartment(departmentDTO);
        assertEquals(departmentDTO, actual.getBody());
        verify(departmentService, times(1)).saveDepartmnet(departmentDTO);
    }

    @Test
    void getAllDepartmentsTest() {
        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(1L)
                .departmentCode("test")
                .departmentDescription("desc")
                .departmentName("name")
                .build();
        when(departmentService.getAllDepartments()).thenReturn(Collections.singletonList(departmentDTO));

        ResponseEntity<List<DepartmentDTO>> actualList = departmentController.getAllDepartments();
        assertEquals(Collections.singletonList(departmentDTO), actualList.getBody());
        verify(departmentService, times(1)).getAllDepartments();
    }
}
