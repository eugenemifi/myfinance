package io.prozy.myfinance.repository;

import io.prozy.myfinance.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Repository Layer Tests")
public class DeveloperRepositoryTests {

    // Тестовые данные для BankRepository
    private static final String test_bankName = "ТЕСТ Банк 1";
    private static final String test_bicCode = "1122334455";
    private static final String test_senderBankName = "ТЕСТ Банк 2";
    private static final String test_senderBicCode = "123456789";
    private static final String test_recipientBankName = "ТЕСТ Банк 3";
    private static final String test_recipientBicCode = "987654321";

    // Тестовые данные для CategoryRepository
    private static final String test_categoryName = "Test Category";
    private static final String test_categoryType = "Доход";

    // Тестовые данные для TransactionRepository
    private static final String test_transactionType = "Оплата товара";
    private static final String test_transactionStatus = "Новая транзакция";
    private static final String test_categoryEntity = "Автомобиль";
    private static final LocalDateTime test_transactionDateTime = LocalDateTime.of(2025, 1, 1, 12, 0);
    private static final String test_comment = "Покупка нового авто";
    private static final BigDecimal test_amount = BigDecimal.valueOf(1500.75);
    private static final String test_recipientInn = "123456";
    private static final String test_recipientBankAccount = "112233445566";
    private static final String test_recipientPhone = "5578877837";
    private static final LocalDateTime test_createdAt = LocalDateTime.of(2025, 1, 1, 12, 0);
    private static final LocalDateTime test_updatedAt = LocalDateTime.of(2025, 1, 1, 12, 0);

    // Тестовые данные для TransactionStatusRepository
    private static final String test_status = "Покупка";

    // Тестовые данные для TransactionTypeRepository
    private static final String test_name_TransactionTypeRepository = "Test User Type";

    // Тестовые данные для UserRepository
    private static final String test_login = "testuser";
    private static final String test_passwordHash = "hashedpassword";
    private static final String test_firstName = "Иван";
    private static final String test_lastName = "Иванов";
    private static final String test_email = "user@example.com";
    private static final String test_userRole = "USER";
    private static final LocalDateTime test_createdAt_UserRepository = LocalDateTime.of(2024, 1, 1, 12, 0);
    private static final LocalDateTime test_updatedAt_UserRepository = LocalDateTime.of(2024, 1, 1, 12, 0);

    // Тестовые данные для UserTypeRepository
    private static final String test_name_UserTypeRepository = "Петров";

    @Autowired private BankRepository bankRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private TransactionStatusRepository transactionStatusRepository;
    @Autowired private TransactionTypeRepository transactionTypeRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private UserTypeRepository userTypeRepository;

    @BeforeEach
    public void setup() {
        bankRepository.deleteAll();
        categoryRepository.deleteAll();
        transactionRepository.deleteAll();
        transactionStatusRepository.deleteAll();
        transactionTypeRepository.deleteAll();
        userRepository.deleteAll();
        userTypeRepository.deleteAll();
    }

    private TransactionEntity createTestTransaction(
            UserEntity user,
            TransactionTypeEntity transactionType,
            TransactionStatusEntity status,
            CategoryEntity category,
            BankEntity senderBank,
            BankEntity recipientBank
    ) {
        return TransactionEntity.builder()
                .user(user)
                .transactionType(transactionType)
                .transactionStatus(status)
                .categoryEntity(category)
                .transactionDateTime(test_transactionDateTime)
                .comment(test_comment)
                .amount(test_amount)
                .senderBankEntity(senderBank)
                .recipientBankEntity(recipientBank)
                .recipientInn(test_recipientInn)
                .recipientBankAccount(test_recipientBankAccount)
                .recipientPhone(test_recipientPhone)
                .createdAt(test_createdAt)
                .updatedAt(test_updatedAt)
                .build();
    }

    // Тесты для BankRepository
    @Test
    @DisplayName("Test save bank functionality")
    public void testingBankRepository() {
        // Given
        BankEntity bankToSave = BankEntity.builder()
                .bankName(test_bankName)
                .bicCode(test_bicCode)
                .build();

        // When
        BankEntity savedBank = bankRepository.save(bankToSave);

        // Then
        assertThat(savedBank).isNotNull();
        assertThat(savedBank.getBankId()).isNotNull();
        assertThat(savedBank.getBankName()).isEqualTo(test_bankName);
        assertThat(savedBank.getBicCode()).isEqualTo(test_bicCode);
    }

    @Test
    @DisplayName("Test update bank")
    public void testUpdateBank() {
        // Given
        BankEntity savedBank = bankRepository.save(BankEntity.builder()
                .bankName(test_bankName)
                .bicCode(test_bicCode)
                .build());

        // When
        savedBank.setBankName("Updated Name");
        savedBank.setBicCode("999999");
        BankEntity updatedBank = bankRepository.save(savedBank);

        // Then
        assertThat(updatedBank.getBankName()).isEqualTo("Updated Name");
        assertThat(updatedBank.getBicCode()).isEqualTo("999999");
    }

    @Test
    @DisplayName("Test find bank by ID - success")
    public void testFindBankByIdSuccess() {
        // Given
        BankEntity savedBank = bankRepository.save(BankEntity.builder()
                .bankName(test_bankName)
                .bicCode(test_bicCode)
                .build());

        // When
        Optional<BankEntity> foundBank = bankRepository.findById(savedBank.getBankId());

        // Then
        assertThat(foundBank).isPresent();
        assertThat(foundBank.get().getBankName()).isEqualTo(test_bankName);
    }

    @Test
    @DisplayName("Test save three banks")
    public void testSaveThreeBanks() {
        // Given
        BankEntity bank1 = BankEntity.builder().bankName("Bank1").bicCode("111").build();
        BankEntity bank2 = BankEntity.builder().bankName("Bank2").bicCode("222").build();
        BankEntity bank3 = BankEntity.builder().bankName("Bank3").bicCode("333").build();

        // When
        bankRepository.saveAll(List.of(bank1, bank2, bank3));

        // Then
        assertThat(bankRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("Test delete bank")
    public void testDeleteBank() {
        // Given
        BankEntity savedBank = bankRepository.save(BankEntity.builder()
                .bankName(test_bankName)
                .bicCode(test_bicCode)
                .build());

        // When
        bankRepository.delete(savedBank);

        // Then
        assertThat(bankRepository.findById(savedBank.getBankId())).isEmpty();
    }

    // Тесты для CategoryRepository
    @Test
    @DisplayName("Test save category functionality")
    public void testingCategoryRepository() {
        // Given
        CategoryEntity categoryToSave = CategoryEntity.builder()
                .categoryName(test_categoryName)
                .categoryType(test_categoryType)
                .build();

        // When
        CategoryEntity savedCategory = categoryRepository.save(categoryToSave);

        // Then
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getCategoryName()).isEqualTo(test_categoryName);
        assertThat(savedCategory.getCategoryType()).isEqualTo(test_categoryType);
    }

    @Test
    @DisplayName("Test update category")
    public void testUpdateCategory() {
        // Given
        CategoryEntity savedCategory = categoryRepository.save(CategoryEntity.builder()
                .categoryName(test_categoryName)
                .categoryType(test_categoryType)
                .build());

        // When
        savedCategory.setCategoryName("Updated Category");
        CategoryEntity updatedCategory = categoryRepository.save(savedCategory);

        // Then
        assertThat(updatedCategory.getCategoryName()).isEqualTo("Updated Category");
    }

    @Test
    @DisplayName("Test find category by ID - success")
    public void testFindCategoryByIdSuccess() {
        // Given
        CategoryEntity savedCategory = categoryRepository.save(CategoryEntity.builder()
                .categoryName(test_categoryName)
                .categoryType(test_categoryType)
                .build());

        // When
        Optional<CategoryEntity> foundCategory = categoryRepository.findById(savedCategory.getId());

        // Then
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getCategoryName()).isEqualTo(test_categoryName);
    }

    @Test
    @DisplayName("Test save three categories")
    public void testSaveThreeCategories() {
        // Given
        CategoryEntity cat1 = CategoryEntity.builder().categoryName("Cat1").categoryType("Type1").build();
        CategoryEntity cat2 = CategoryEntity.builder().categoryName("Cat2").categoryType("Type2").build();
        CategoryEntity cat3 = CategoryEntity.builder().categoryName("Cat3").categoryType("Type3").build();

        // When
        categoryRepository.saveAll(List.of(cat1, cat2, cat3));

        // Then
        assertThat(categoryRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("Test delete category")
    public void testDeleteCategory() {
        // Given
        CategoryEntity savedCategory = categoryRepository.save(CategoryEntity.builder()
                .categoryName(test_categoryName)
                .categoryType(test_categoryType)
                .build());

        // When
        categoryRepository.delete(savedCategory);

        // Then
        assertThat(categoryRepository.findById(savedCategory.getId())).isEmpty();
    }

    // Тесты для TransactionRepository
    private TransactionEntity createTestTransaction() {
        UserEntity user = userRepository.save(UserEntity.builder()
                .login(test_login)
                .passwordHash(test_passwordHash)
                .firstName(test_firstName)
                .lastName(test_lastName)
                .email(test_email)
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build());

        TransactionTypeEntity transactionType = transactionTypeRepository.save(TransactionTypeEntity.builder()
                .name(test_transactionType)
                .build());

        TransactionStatusEntity status = transactionStatusRepository.save(TransactionStatusEntity.builder()
                .status(test_transactionStatus)
                .build());

        CategoryEntity category = categoryRepository.save(CategoryEntity.builder()
                .categoryName(test_categoryEntity)
                .categoryType(test_categoryType)
                .build());

        BankEntity senderBank = bankRepository.save(BankEntity.builder()
                .bankName(test_senderBankName)
                .bicCode(test_senderBicCode)
                .build());

        BankEntity recipientBank = bankRepository.save(BankEntity.builder()
                .bankName(test_recipientBankName)
                .bicCode(test_recipientBicCode)
                .build());

        return TransactionEntity.builder()
                .user(user)
                .transactionType(transactionType)
                .transactionStatus(status)
                .categoryEntity(category)
                .transactionDateTime(test_transactionDateTime)
                .comment(test_comment)
                .amount(test_amount)
                .senderBankEntity(senderBank)
                .recipientBankEntity(recipientBank)
                .recipientInn(test_recipientInn)
                .recipientBankAccount(test_recipientBankAccount)
                .recipientPhone(test_recipientPhone)
                .createdAt(test_createdAt)
                .updatedAt(test_updatedAt)
                .build();
    }

    @Test
    @DisplayName("Test save transaction functionality")
    public void testTransactionRepository() {
        // Given
        TransactionEntity transactionToSave = createTestTransaction();

        // When
        TransactionEntity savedTransaction = transactionRepository.save(transactionToSave);

        // Then
        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction.getId()).isNotNull();
        assertThat(savedTransaction.getComment()).isEqualTo(test_comment);
    }

    @Test
    @DisplayName("Test update transaction")
    public void testUpdateTransaction() {
        // Given
        TransactionEntity savedTransaction = transactionRepository.save(createTestTransaction());

        // When
        savedTransaction.setComment("Updated Comment");
        TransactionEntity updatedTransaction = transactionRepository.save(savedTransaction);

        // Then
        assertThat(updatedTransaction.getComment()).isEqualTo("Updated Comment");
    }

    @Test
    @DisplayName("Test find transaction by ID - success")
    public void testFindTransactionByIdSuccess() {
        // Given
        TransactionEntity savedTransaction = transactionRepository.save(createTestTransaction());

        // When
        Optional<TransactionEntity> foundTransaction = transactionRepository.findById(savedTransaction.getId());

        // Then
        assertThat(foundTransaction).isPresent();
        assertThat(foundTransaction.get().getComment()).isEqualTo(test_comment);
    }

    @Test
    @DisplayName("Test transaction without category")
    public void testTransactionWithoutCategory() {
        TransactionEntity transaction = createTestTransaction();
        transaction.setCategoryEntity(null); // Убираем категорию
        TransactionEntity saved = transactionRepository.save(transaction);

        assertThat(saved.getCategoryEntity()).isNull();
    }

    @Test
    @DisplayName("Test save three transactions")
    public void testSaveThreeTransactions() {
        // Given
        UserEntity testUser = userRepository.save(UserEntity.builder()
                .login(test_login)
                .passwordHash(test_passwordHash)
                .firstName(test_firstName)
                .lastName(test_lastName)
                .email(test_email)
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build());

        TransactionTypeEntity testTransactionType = transactionTypeRepository.save(
                TransactionTypeEntity.builder()
                        .name(test_transactionType)
                        .build());

        TransactionStatusEntity testStatus = transactionStatusRepository.save(
                TransactionStatusEntity.builder()
                        .status(test_transactionStatus)
                        .build());

        CategoryEntity testCategory = categoryRepository.save(
                CategoryEntity.builder()
                        .categoryName(test_categoryEntity)
                        .categoryType(test_categoryType)
                        .build());

        BankEntity testSenderBank = bankRepository.save(
                BankEntity.builder()
                        .bankName(test_senderBankName)
                        .bicCode(test_senderBicCode)
                        .build());

        BankEntity testRecipientBank = bankRepository.save(
                BankEntity.builder()
                        .bankName(test_recipientBankName)
                        .bicCode(test_recipientBicCode)
                        .build());

        TransactionEntity transaction1 = createTestTransaction(
                testUser,
                testTransactionType,
                testStatus,
                testCategory,
                testSenderBank,
                testRecipientBank
        );

        TransactionEntity transaction2 = createTestTransaction(
                testUser,
                testTransactionType,
                testStatus,
                testCategory,
                testSenderBank,
                testRecipientBank
        );

        TransactionEntity transaction3 = createTestTransaction(
                testUser,
                testTransactionType,
                testStatus,
                testCategory,
                testSenderBank,
                testRecipientBank
        );

        // When
        transactionRepository.saveAll(List.of(transaction1, transaction2, transaction3));

        // Then
        List<TransactionEntity> allTransactions = transactionRepository.findAll();
        assertThat(allTransactions)
                .hasSize(3)
                .allMatch(t -> t.getUser().getUserId().equals(testUser.getUserId()))
                .allMatch(t -> t.getTransactionType().getName().equals(test_transactionType))
                .allMatch(t -> t.getAmount().compareTo(test_amount) == 0);
    }

    @Test
    @DisplayName("Test delete transaction")
    public void testDeleteTransaction() {
        // Given
        TransactionEntity savedTransaction = transactionRepository.save(createTestTransaction());

        // When
        transactionRepository.delete(savedTransaction);

        // Then
        assertThat(transactionRepository.findById(savedTransaction.getId())).isEmpty();
    }

    // Тесты для TransactionStatusRepository
    @Test
    @DisplayName("Test save transaction status functionality")
    public void testTransactionStatusRepository() {
        // Given
        TransactionStatusEntity statusToSave = TransactionStatusEntity.builder()
                .status(test_status)
                .build();

        // When
        TransactionStatusEntity savedStatus = transactionStatusRepository.save(statusToSave);

        // Then
        assertThat(savedStatus).isNotNull();
        assertThat(savedStatus.getId()).isNotNull();
        assertThat(savedStatus.getStatus()).isEqualTo(test_status);
    }

    @Test
    @DisplayName("Test update transaction status")
    public void testUpdateTransactionStatus() {
        // Given
        TransactionStatusEntity savedStatus = transactionStatusRepository.save(
                TransactionStatusEntity.builder().status(test_status).build());

        // When
        savedStatus.setStatus("Updated Status");
        TransactionStatusEntity updatedStatus = transactionStatusRepository.save(savedStatus);

        // Then
        assertThat(updatedStatus.getStatus()).isEqualTo("Updated Status");
    }

    @Test
    @DisplayName("Test find transaction status by ID - success")
    public void testFindTransactionStatusByIdSuccess() {
        // Given
        TransactionStatusEntity savedStatus = transactionStatusRepository.save(
                TransactionStatusEntity.builder().status(test_status).build());

        // When
        Optional<TransactionStatusEntity> foundStatus = transactionStatusRepository.findById(savedStatus.getId());

        // Then
        assertThat(foundStatus).isPresent();
        assertThat(foundStatus.get().getStatus()).isEqualTo(test_status);
    }

    @Test
    @DisplayName("Test save three transaction statuses")
    public void testSaveThreeTransactionStatuses() {
        // Given
        TransactionStatusEntity s1 = TransactionStatusEntity.builder().status("Status1").build();
        TransactionStatusEntity s2 = TransactionStatusEntity.builder().status("Status2").build();
        TransactionStatusEntity s3 = TransactionStatusEntity.builder().status("Status3").build();

        // When
        transactionStatusRepository.saveAll(List.of(s1, s2, s3));

        // Then
        assertThat(transactionStatusRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("Test delete transaction status")
    public void testDeleteTransactionStatus() {
        // Given
        TransactionStatusEntity savedStatus = transactionStatusRepository.save(
                TransactionStatusEntity.builder().status(test_status).build());

        // When
        transactionStatusRepository.delete(savedStatus);

        // Then
        assertThat(transactionStatusRepository.findById(savedStatus.getId())).isEmpty();
    }

    // Тесты для TransactionTypeRepository
    @Test
    @DisplayName("Test save transaction type functionality")
    public void testTransactionTypeRepository() {
        // Given
        TransactionTypeEntity typeToSave = TransactionTypeEntity.builder()
                .name(test_name_TransactionTypeRepository)
                .build();

        // When
        TransactionTypeEntity savedType = transactionTypeRepository.save(typeToSave);

        // Then
        assertThat(savedType).isNotNull();
        assertThat(savedType.getId()).isNotNull();
        assertThat(savedType.getName()).isEqualTo(test_name_TransactionTypeRepository);
    }

    @Test
    @DisplayName("Test update transaction type")
    public void testUpdateTransactionType() {
        // Given
        TransactionTypeEntity savedType = transactionTypeRepository.save(
                TransactionTypeEntity.builder().name(test_name_TransactionTypeRepository).build());

        // When
        savedType.setName("Updated Type");
        TransactionTypeEntity updatedType = transactionTypeRepository.save(savedType);

        // Then
        assertThat(updatedType.getName()).isEqualTo("Updated Type");
    }

    @Test
    @DisplayName("Test find transaction type by ID - success")
    public void testFindTransactionTypeByIdSuccess() {
        // Given
        TransactionTypeEntity savedType = transactionTypeRepository.save(
                TransactionTypeEntity.builder().name(test_name_TransactionTypeRepository).build());

        // When
        Optional<TransactionTypeEntity> foundType = transactionTypeRepository.findById(savedType.getId());

        // Then
        assertThat(foundType).isPresent();
        assertThat(foundType.get().getName()).isEqualTo(test_name_TransactionTypeRepository);
    }

    @Test
    @DisplayName("Test save three transaction types")
    public void testSaveThreeTransactionTypes() {
        // Given
        TransactionTypeEntity t1 = TransactionTypeEntity.builder().name("Type1").build();
        TransactionTypeEntity t2 = TransactionTypeEntity.builder().name("Type2").build();
        TransactionTypeEntity t3 = TransactionTypeEntity.builder().name("Type3").build();

        // When
        transactionTypeRepository.saveAll(List.of(t1, t2, t3));

        // Then
        assertThat(transactionTypeRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("Test delete transaction type")
    public void testDeleteTransactionType() {
        // Given
        TransactionTypeEntity savedType = transactionTypeRepository.save(
                TransactionTypeEntity.builder().name(test_name_TransactionTypeRepository).build());

        // When
        transactionTypeRepository.delete(savedType);

        // Then
        assertThat(transactionTypeRepository.findById(savedType.getId())).isEmpty();
    }

    // Тесты для UserRepository
    @Test
    @DisplayName("Test save user functionality")
    public void testUserRepository() {
        // Given
        UserEntity userToSave = UserEntity.builder()
                .login(test_login)
                .passwordHash(test_passwordHash)
                .firstName(test_firstName)
                .lastName(test_lastName)
                .email(test_email)
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build();

        // When
        UserEntity savedUser = userRepository.save(userToSave);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserId()).isNotNull();
        assertThat(savedUser.getLogin()).isEqualTo(test_login);
    }

    @Test
    @DisplayName("Test update user")
    public void testUpdateUser() {
        // Given
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .login(test_login)
                .passwordHash(test_passwordHash)
                .firstName(test_firstName)
                .lastName(test_lastName)
                .email(test_email)
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build());

        // When
        savedUser.setFirstName("Updated Name");
        UserEntity updatedUser = userRepository.save(savedUser);

        // Then
        assertThat(updatedUser.getFirstName()).isEqualTo("Updated Name");
    }

    @Test
    @DisplayName("Test find user by ID - success")
    public void testFindUserByIdSuccess() {
        // Given
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .login(test_login)
                .passwordHash(test_passwordHash)
                .firstName(test_firstName)
                .lastName(test_lastName)
                .email(test_email)
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build());

        // When
        Optional<UserEntity> foundUser = userRepository.findById(savedUser.getUserId());

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getLogin()).isEqualTo(test_login);
    }

    @Test
    @DisplayName("Test save three users")
    public void testSaveThreeUsers() {
        // Given
        UserEntity u1 = UserEntity.builder()
                .login("user1")
                .passwordHash("hash1")
                .firstName("Name1")
                .lastName("Last1")
                .email("user1@example.com")
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build();

        UserEntity u2 = UserEntity.builder()
                .login("user2")
                .passwordHash("hash2")
                .firstName("Name2")
                .lastName("Last2")
                .email("user2@example.com")
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build();

        UserEntity u3 = UserEntity.builder()
                .login("user3")
                .passwordHash("hash3")
                .firstName("Name3")
                .lastName("Last3")
                .email("user3@example.com")
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build();

        // When
        userRepository.saveAll(List.of(u1, u2, u3));

        // Then
        assertThat(userRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("Test delete user")
    public void testDeleteUser() {
        // Given
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .login(test_login)
                .passwordHash(test_passwordHash)
                .firstName(test_firstName)
                .lastName(test_lastName)
                .email(test_email)
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build());

        // When
        userRepository.delete(savedUser);

        // Then
        assertThat(userRepository.findById(savedUser.getUserId())).isEmpty();
    }

    @Test
    @DisplayName("Test all user fields on save")
    public void testUserFieldsOnSave() {
        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .login(test_login)
                .passwordHash(test_passwordHash)
                .firstName(test_firstName)
                .lastName(test_lastName)
                .email(test_email)
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build());

        assertThat(savedUser.getEmail()).isEqualTo(test_email);
        assertThat(savedUser.getUserRole()).isEqualTo(test_userRole);
        assertThat(savedUser.getCreatedAt()).isNotNull();
    }

    // Тесты для UserTypeRepository
    @Test
    @DisplayName("Test save user type functionality")
    public void testUserTypeRepository() {
        // Given
        UserTypeEntity userTypeToSave = UserTypeEntity.builder()
                .name(test_name_UserTypeRepository)
                .build();

        // When
        UserTypeEntity savedUserType = userTypeRepository.save(userTypeToSave);

        // Then
        assertThat(savedUserType).isNotNull();
        assertThat(savedUserType.getId()).isNotNull();
        assertThat(savedUserType.getName()).isEqualTo(test_name_UserTypeRepository);
    }

    @Test
    @DisplayName("Test update user type")
    public void testUpdateUserType() {
        // Given
        UserTypeEntity savedUserType = userTypeRepository.save(
                UserTypeEntity.builder().name(test_name_UserTypeRepository).build());

        // When
        savedUserType.setName("Updated User Type");
        UserTypeEntity updatedUserType = userTypeRepository.save(savedUserType);

        // Then
        assertThat(updatedUserType.getName()).isEqualTo("Updated User Type");
    }

    @Test
    @DisplayName("Test find user type by ID - success")
    public void testFindUserTypeByIdSuccess() {
        // Given
        UserTypeEntity savedUserType = userTypeRepository.save(
                UserTypeEntity.builder().name(test_name_UserTypeRepository).build());

        // When
        Optional<UserTypeEntity> foundUserType = userTypeRepository.findById(savedUserType.getId());

        // Then
        assertThat(foundUserType).isPresent();
        assertThat(foundUserType.get().getName()).isEqualTo(test_name_UserTypeRepository);
    }

    @Test
    @DisplayName("Test save three user types")
    public void testSaveThreeUserTypes() {
        // Given
        UserTypeEntity ut1 = UserTypeEntity.builder().name("Type1").build();
        UserTypeEntity ut2 = UserTypeEntity.builder().name("Type2").build();
        UserTypeEntity ut3 = UserTypeEntity.builder().name("Type3").build();

        // When
        userTypeRepository.saveAll(List.of(ut1, ut2, ut3));

        // Then
        assertThat(userTypeRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("Test delete user type")
    public void testDeleteUserType() {
        // Given
        UserTypeEntity savedUserType = userTypeRepository.save(
                UserTypeEntity.builder().name(test_name_UserTypeRepository).build());

        // When
        userTypeRepository.delete(savedUserType);

        // Then
        assertThat(userTypeRepository.findById(savedUserType.getId())).isEmpty();
    }

    @Test
    @DisplayName("Test findByLogin - success")
    public void testFindByLoginSuccess() {
        // Given
        UserEntity user = userRepository.save(UserEntity.builder()
                .login(test_login)
                .passwordHash(test_passwordHash)
                .firstName(test_firstName)
                .lastName(test_lastName)
                .email(test_email)
                .userRole(test_userRole)
                .createdAt(test_createdAt_UserRepository)
                .updatedAt(test_updatedAt_UserRepository)
                .build());

        // When
        Optional<UserEntity> foundUser = userRepository.findByLogin(test_login);

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    @DisplayName("Test findByLogin - not found")
    public void testFindByLoginNotFound() {
        // When
        Optional<UserEntity> foundUser = userRepository.findByLogin("nonexistent");

        // Then
        assertThat(foundUser).isEmpty();
    }

    // Тесты для TransactionRepository.findByFilters()
    @Test
    @DisplayName("Test findByFilters with amount range")
    public void testFindByFiltersAmountRange() {
        // Given
        TransactionEntity transaction = createTestTransaction();
        transactionRepository.save(transaction);

        // When
        List<TransactionEntity> results = transactionRepository.findByFilters(
                new BigDecimal("1000.0"),
                new BigDecimal("2000.0"),
                null,
                null,
                null
        );

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getAmount()).isEqualByComparingTo(test_amount);
    }

    @Test
    @DisplayName("Test findByFilters with date range")
    public void testFindByFiltersDateRange() {
        // Given
        TransactionEntity transaction = createTestTransaction();
        transactionRepository.save(transaction);

        // When
        List<TransactionEntity> results = transactionRepository.findByFilters(
                null,
                null,
                test_transactionDateTime.minusDays(1),
                test_transactionDateTime.plusDays(1),
                null
        );

        // Then
        assertThat(results).hasSize(1);
    }

    @Test
    @DisplayName("Test findByFilters with category")
    public void testFindByFiltersCategory() {
        // Given
        TransactionEntity transaction = createTestTransaction();
        transactionRepository.save(transaction);

        // When
        List<TransactionEntity> results = transactionRepository.findByFilters(
                null,
                null,
                null,
                null,
                test_categoryEntity
        );

        // Then
        assertThat(results).hasSize(1);
    }

    // Тесты для недостающих полей TransactionEntity
    @Test
    @DisplayName("Test TransactionEntity recipient fields")
    public void testTransactionRecipientFields() {
        // Given
        TransactionEntity transaction = createTestTransaction();

        // When
        TransactionEntity saved = transactionRepository.save(transaction);

        // Then
        assertThat(saved.getRecipientInn()).isEqualTo(test_recipientInn);
        assertThat(saved.getRecipientBankAccount()).isEqualTo(test_recipientBankAccount);
        assertThat(saved.getRecipientPhone()).isEqualTo(test_recipientPhone);
    }

    // Тесты для nullable полей
    @Test
    @DisplayName("Test nullable bicCode in BankEntity")
    public void testNullableBicCode() {
        // Given
        BankEntity bank = BankEntity.builder()
                .bankName(test_bankName)
                .bicCode(null)
                .build();

        // When
        BankEntity saved = bankRepository.save(bank);

        // Then
        assertThat(saved.getBicCode()).isNull();
    }
}
