package io.prozy.myfinance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "banks")
public class BankEntity {

    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "bank_id", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID bankId;

    @Column(name = "bank_name", length = 200, nullable = false)
    private String bankName;

    @Column(name = "bic_code", length = 20)
    private String bicCode;

}
