package io.prozy.myfinance.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(generator = "uuid2", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionTypeEntity transactionType;

    @ManyToOne
    @JoinColumn(name = "transaction_status_id", nullable = false)
    private TransactionStatusEntity transactionStatus;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @Column(name = "transaction_date_time", nullable = false)
    private LocalDateTime transactionDateTime;

    @Column(name = "comment")
    private String comment;

    @Column(name = "amount", precision = 15, scale = 5)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "sender_bank_id")
    private BankEntity senderBankEntity;

    @ManyToOne
    @JoinColumn(name = "recipient_bank_id")
    private BankEntity recipientBankEntity;

    @Column(name = "recipient_inn", length = 20)
    private String recipientInn;

    @Column(name = "recipient_bank_account", length = 50)
    private String recipientBankAccount;

    @Column(name = "recipient_phone", length = 20)
    private String recipientPhone;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

}
