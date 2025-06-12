package com.ApiRestMiBus.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "empresa")
public class EmpresaEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", unique = true)
    @NotBlank(message = "El nombre no puede estar vacío")
    @NotNull(message = "El nombre es requerido")
    private String nombre;

    @Column(name="nombre_corto")
    private String nombreCorto;

    @Column(name="tipo_documento")
    private String tipoDocumento;

    private String documento;

    private String logo;

    private String descripcion;

    private String email;

    private String telefono;

    private String celular1;

    private String celular2;

    @Column(name="nombre_representante")
    private String nombreRepresentante;

    @Column(name="documento_representante")
    private String documentoRepresentante;

    @Column(name="celular_representante")
    private String celularRepresentante;

    @Column(name="email_representante")
    private String emailRepresentante;

    @Column(name="cargo_representante")
    private String cargoRepresentante;

    @Column(name="envio_atu")
    private String envioAtu;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<RutaEntity> rutas;

}
