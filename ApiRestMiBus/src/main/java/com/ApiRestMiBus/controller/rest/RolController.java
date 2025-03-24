package com.ApiRestMiBus.controller.rest;

import com.ApiRestMiBus.common.adapters.GenericDataAdapter;
import com.ApiRestMiBus.common.domain.GenericResponse;
import com.ApiRestMiBus.model.entity.RoleEntity;
import com.ApiRestMiBus.model.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
public class RolController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?>  getAll(@PathVariable Integer page){
        Page<RoleEntity> pageRoles;
        Pageable pageable = PageRequest.of(page, 5);
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            pageRoles = roleService.findAll(pageable);
            if (pageRoles.getContent().isEmpty()) {
                String str = " No existen roles en la base de datos";
                genericResponse = genericDataAdapter.createError("1", str);
                return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.NOT_FOUND);
            }
            genericResponse = genericDataAdapter.createData(pageRoles);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?>  getAll(){
        List<RoleEntity> lstRoles;
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            lstRoles = roleService.findAll();
            if (lstRoles.isEmpty()) {
                String str = " No existen roles en la base de datos";
                genericResponse = genericDataAdapter.createError("1", str);
                return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.NOT_FOUND);
            }
            genericResponse = genericDataAdapter.createData(lstRoles);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();
        try {
            Optional roleEntity = roleService.findById(id);
            genericResponse = genericDataAdapter.createData(roleEntity);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> getRoleByRoleName(@RequestParam String name){
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            List<RoleEntity> roleEntity = roleService.findByRoleName(name);
            genericResponse = genericDataAdapter.createData(roleEntity);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RoleEntity role, BindingResult result){
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append(". "));

            String str = errors.toString();
            genericResponse = genericDataAdapter.createError("2", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            RoleEntity nuevoRol = roleService.create(role);
            genericResponse = genericDataAdapter.createData(nuevoRol);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String str = "Error al realizar el insert en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public RoleEntity update(@PathVariable Long id,@RequestBody RoleEntity role){
        role.setId(id);
        return roleService.update(id,role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        roleService.deleteById(id);
    }

}
