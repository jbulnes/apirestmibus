package com.ApiRestMiBus.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "rutas")
public class RutaEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String nombre;

    @Column(name = "codigo_ruta")
    private String codigoRuta;

    private String color;


    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    @NotNull(message = "El idEmpresa no puede ser nulo.")
    @JsonBackReference(value = "empresa-ruta")
    private EmpresaEntity empresa;

    @OneToMany(mappedBy = "ruta", fetch = FetchType.LAZY)
    private List<VehiculoEntity> vehiculos = new ArrayList<>();

}
