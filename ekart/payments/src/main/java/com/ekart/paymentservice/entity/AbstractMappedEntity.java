package com.ekart.paymentservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public abstract class AbstractMappedEntity implements Serializable {


    @CreatedDate
//    @JsonFormat(shape = Shape.STRING)
    @Column(name = "created_at")
    @JsonIgnore
    private LocalDateTime createAt;

    @LastModifiedDate
//    @JsonFormat(shape = Shape.STRING)
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updateAt;

}
