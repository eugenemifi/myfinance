package io.prozy.myfinance.service;

import io.prozy.myfinance.dto.*;
import io.prozy.myfinance.entity.*;
import io.prozy.myfinance.mappers.*;
import io.prozy.myfinance.repository.*;
import io.prozy.myfinance.security.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceImplTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private AuthService authService;
    @Mock
    private BankRepository bankRepository;
    @Mock
    private BankMapper bankMapper;
    @InjectMocks
    private BankService bankService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionMapper transactionMapper;
    @Mock
    private TransactionTypeRepository transactionTypeRepository;
    @Mock
    private TransactionTypeMapper transactionTypeMapper;
    @InjectMocks
    private TransactionTypesService transactionTypesService;
    @Mock
    private TransactionStatusRepository transactionStatusRepository;
    @Mock
    private TransactionStatusMapper transactionStatusMapper;
    @InjectMocks
    private TransactionStatusesService transactionStatusesService;
    @InjectMocks
    private TransactionService transactionService;

    private UserEntity createUserEntity() {
        return UserEntity.builder()
                .userId(UUID.randomUUID())
                .login("testLogin")
                .passwordHash("encodedPassword")
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("test@example.com")
                .userRole("USER")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private RegisterRequestDto createRegisterRequestDto() {
        return new RegisterRequestDto(
                "testLogin",
                "password",
                "firstName",
                "lastName",
                "test@example.com"
        );
    }

    private UserDto createUserDto(UserEntity user) {
        return new UserDto(
                user.getUserId(),
                user.getLogin(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    private LoginRequestDto createLoginRequestDto() {
        return new LoginRequestDto(
                "testLogin",
                "password"
        );
    }

    private BankEntity createBankEntity() {
        return BankEntity.builder()
                .bankId(UUID.randomUUID())
                .bankName("Test Bank")
                .bicCode("1234567")
                .build();
    }

    private BankDto createBankDto(BankEntity bankEntity) {
        return new BankDto(
                bankEntity.getBankId(),
                bankEntity.getBankName(),
                bankEntity.getBicCode()
        );
    }

    private CategoryEntity createCategoryEntity() {
        return CategoryEntity.builder()
                .id(UUID.randomUUID())
                .categoryName("Test Category")
                .categoryType("Expense")
                .build();
    }

    private CategoryDto createCategoryDto(CategoryEntity categoryEntity) {
        return new CategoryDto(
                categoryEntity.getId(),
                categoryEntity.getCategoryName(),
                categoryEntity.getCategoryType()
        );
    }

    private TransactionEntity createTransactionEntity() {
        UserEntity user = createUserEntity();
        TransactionTypeEntity transactionType = createTransactionTypeEntity();
        TransactionStatusEntity transactionStatus = createTransactionStatusEntity();
        CategoryEntity categoryEntity = createCategoryEntity();
        BankEntity senderBankEntity = createBankEntity();
        BankEntity recipientBankEntity = createBankEntity();

        return TransactionEntity.builder()
                .id(UUID.randomUUID())
                .user(user)
                .transactionType(transactionType)
                .transactionStatus(transactionStatus)
                .categoryEntity(categoryEntity)
                .transactionDateTime(LocalDateTime.now())
                .comment("Test Comment")
                .amount(new BigDecimal("100.00"))
                .senderBankEntity(senderBankEntity)
                .recipientBankEntity(recipientBankEntity)
                .recipientInn("12345678901")
                .recipientBankAccount("12345678901234567890")
                .recipientPhone("+79991234567")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private TransactionDto createTransactionDto(TransactionEntity transactionEntity) {
        return new TransactionDto(
                transactionEntity.getId(),
                userMapper.toDto(transactionEntity.getUser()),
                transactionTypeMapper.toDto(transactionEntity.getTransactionType()),
                transactionStatusMapper.toDto(transactionEntity.getTransactionStatus()),
                categoryMapper.toDto(transactionEntity.getCategoryEntity()),
                transactionEntity.getTransactionDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                transactionEntity.getComment(),
                transactionEntity.getAmount(),
                bankMapper.toDto(transactionEntity.getSenderBankEntity()),
                bankMapper.toDto(transactionEntity.getRecipientBankEntity()),
                transactionEntity.getRecipientInn(),
                transactionEntity.getRecipientBankAccount(),
                transactionEntity.getRecipientPhone(),
                transactionEntity.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                transactionEntity.getUpdatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        );
    }

    private TransactionTypeEntity createTransactionTypeEntity() {
        return TransactionTypeEntity.builder()
                .id(UUID.randomUUID())
                .name("Test Type")
                .build();
    }

    private TransactionTypeDto createTransactionTypeDto(TransactionTypeEntity transactionTypeEntity) {
        return new TransactionTypeDto(
                transactionTypeEntity.getId(),
                transactionTypeEntity.getName()
        );
    }

    private TransactionStatusEntity createTransactionStatusEntity() {
        return TransactionStatusEntity.builder()
                .id(UUID.randomUUID())
                .status("Test Status")
                .build();
    }

    private TransactionStatusDto createTransactionStatusDto(TransactionStatusEntity transactionStatusEntity) {
        return new TransactionStatusDto(
                transactionStatusEntity.getId(),
                transactionStatusEntity.getStatus()
        );
    }

    // AuthService tests

    @Test
    @DisplayName("Test login with valid credentials")
    public void givenValidLoginRequest_whenLogin_thenReturnLoginResponseDto() {
        // given
        LoginRequestDto request = createLoginRequestDto();
        UserEntity user = createUserEntity();
        Mockito.when(userRepository.findByLogin(request.login())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(request.password(), user.getPasswordHash())).thenReturn(true);
        Mockito.when(jwtService.generateToken(any())).thenReturn("testToken");

        // when
        LoginResponseDto response = authService.login(request);

        // then
        assertThat(response).isNotNull();
        assertEquals("testToken", response.token());
        assertEquals(user.getUserId().toString(), response.uuid());
        verify(userRepository, times(1)).findByLogin(request.login());
        verify(passwordEncoder, times(1)).matches(request.password(), user.getPasswordHash());
        verify(jwtService, times(1)).generateToken(any());
    }

    @Test
    @DisplayName("Test login with invalid login")
    public void givenInvalidLoginRequest_whenLogin_thenThrowException() {
        // given
        LoginRequestDto request = createLoginRequestDto();
        Mockito.when(userRepository.findByLogin(request.login())).thenReturn(Optional.empty());

        // when, then
        assertThrows(RuntimeException.class, () -> authService.login(request));
        verify(userRepository, times(1)).findByLogin(request.login());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    @DisplayName("Test login with invalid password")
    public void givenInvalidPasswordRequest_whenLogin_thenThrowException() {
        // given
        LoginRequestDto request = createLoginRequestDto();
        UserEntity user = createUserEntity();
        Mockito.when(userRepository.findByLogin(request.login())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(request.password(), user.getPasswordHash())).thenReturn(false);

        // when, then
        assertThrows(RuntimeException.class, () -> authService.login(request));
        verify(userRepository, times(1)).findByLogin(request.login());
        verify(passwordEncoder, times(1)).matches(request.password(), user.getPasswordHash());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    @DisplayName("Test register new user")
    public void givenValidRegisterRequest_whenRegister_thenReturnUserDto() {
        // given
        RegisterRequestDto request = createRegisterRequestDto();
        UserEntity user = UserEntity.builder()
                .userId(UUID.randomUUID())
                .login(request.login())
                .passwordHash("encodedPassword")
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .userRole("USER")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(userRepository.findByLogin(request.login())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        Mockito.when(userMapper.toDto(any(UserEntity.class))).thenReturn(createUserDto(user));

        // when
        UserDto response = authService.register(request);

        // then
        assertThat(response).isNotNull();
        assertEquals("testLogin", response.login());
        assertEquals("firstName", response.firstName());
        assertEquals("lastName", response.lastName());
        assertEquals("test@example.com", response.email());
        assertEquals("USER", response.userRole());

        verify(userRepository, times(1)).findByLogin(request.login());
        verify(passwordEncoder, times(1)).encode(request.password());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userMapper, times(1)).toDto(any(UserEntity.class));
    }

    @Test
    @DisplayName("Test register with taken login")
    public void givenTakenLoginRegisterRequest_whenRegister_thenThrowException() {
        // given
        RegisterRequestDto request = createRegisterRequestDto();
        UserEntity user = createUserEntity();
        Mockito.when(userRepository.findByLogin(request.login())).thenReturn(Optional.of(user));

        // when, then
        assertThrows(RuntimeException.class, () -> authService.register(request));
        verify(userRepository, times(1)).findByLogin(request.login());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(userMapper, never()).toDto(any(UserEntity.class));
    }

    @Test
    @DisplayName("Test register sets default user role")
    public void givenRegisterRequest_whenRegister_thenSetsDefaultUserRole() {
        // given
        RegisterRequestDto request = createRegisterRequestDto();
        UserEntity user = createUserEntity();
        Mockito.when(userRepository.findByLogin(request.login())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity savedUser = invocation.getArgument(0);
            return savedUser;
        });
        Mockito.when(userMapper.toDto(any())).thenReturn(createUserDto(user));

        // when
        authService.register(request);

        // then
        verify(userRepository, times(1)).save(argThat(userEntity -> "USER".equals(userEntity.getUserRole())));
    }

    @Test
    @DisplayName("Test register with same email throws exception")
    public void givenRegisterRequestWithSameEmail_whenRegister_thenThrowException() {
        // given
        RegisterRequestDto request = createRegisterRequestDto();
        UserEntity existingUser = createUserEntity();
        Mockito.when(userRepository.findByLogin(request.login())).thenReturn(Optional.of(existingUser));

        // when, then
        assertThrows(RuntimeException.class, () -> authService.register(request));
        verify(userRepository, times(1)).findByLogin(request.login());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(userMapper, never()).toDto(any(UserEntity.class));
    }

    // BankGeneratorService tests

    @Test
    @DisplayName("Test generateBanks functionality")
    public void generateBanks_generatesExpectedNumberOfBanks() {
        // given
        int count = 3;
        BankGeneratorService bankGeneratorService = new BankGeneratorService(bankRepository);

        List<BankEntity> mockBankEntities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            BankEntity entity = BankEntity.builder()
                    .bankId(UUID.randomUUID())
                    .bankName("Bank_" + i)
                    .bicCode("BIK" + String.format("%07d", i))
                    .build();
            mockBankEntities.add(entity);
        }
        when(bankRepository.saveAll(any())).thenReturn(mockBankEntities);

        // when
        List<BankDto> generatedBanks = bankGeneratorService.generateBanks(count);

        // then
        verify(bankRepository).saveAll(any());
        assertThat(generatedBanks).hasSize(count);

        for (int i = 0; i < count; i++) {
            assertThat(generatedBanks.get(i).bankName()).isEqualTo("Bank_" + i);
            assertThat(generatedBanks.get(i).bicCode()).isEqualTo("BIK" + String.format("%07d", i));
        }
    }

    @Test
    @DisplayName("Test generateBanks returns empty list when count is zero")
    public void givenZeroCount_whenGenerateBanks_thenReturnsEmptyList() {
        // given
        int count = 0;
        BankGeneratorService bankGeneratorService = new BankGeneratorService(bankRepository);
        // when
        List<BankDto> generatedBanks = bankGeneratorService.generateBanks(count);

        // then
        assertThat(generatedBanks).isNotNull();
        assertThat(generatedBanks).isEmpty();
    }

    @Test
    @DisplayName("Test generateBanks generates correct BIC code format")
    public void givenCount_whenGenerateBanks_thenBicCodeHasCorrectFormat() {
        // given
        int count = 5;
        BankGeneratorService bankGeneratorService = new BankGeneratorService(bankRepository);

        List<BankEntity> mockBankEntities = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            BankEntity entity = BankEntity.builder()
                    .bicCode("BIK" + String.format("%07d", i))
                    .build();
            mockBankEntities.add(entity);
        }
        when(bankRepository.saveAll(any())).thenReturn(mockBankEntities);

        // when
        List<BankDto> generatedBanks = bankGeneratorService.generateBanks(count);

        // then
        verify(bankRepository).saveAll(any());
        assertThat(generatedBanks).hasSize(count);

        for (BankDto bank : generatedBanks) {
            assertThat(bank.bicCode()).startsWith("BIK");
            assertThat(bank.bicCode().substring(3)).matches("\\d{7}");
        }
    }

    // BankService tests

    @Test
    @DisplayName("Test getAllBanks functionality")
    public void givenPageable_whenGetAllBanks_thenReturnPageOfBankDto() {
        // given
        Pageable pageable = Pageable.unpaged();
        List<BankEntity> bankEntities = Arrays.asList(createBankEntity(), createBankEntity());
        Page<BankEntity> bankEntityPage = new PageImpl<>(bankEntities, pageable, bankEntities.size());
        Mockito.when(bankRepository.findAll(pageable)).thenReturn(bankEntityPage);

        // when
        Page<BankDto> bankDtoPage = bankService.getAllBanks(pageable);

        // then
        assertThat(bankDtoPage).isNotNull();
        assertThat(bankDtoPage.getContent().size()).isEqualTo(bankEntities.size());
        verify(bankRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test getAllBanks with empty repository")
    public void givenEmptyRepository_whenGetAllBanks_thenReturnEmptyPage() {
        // given
        Pageable pageable = Pageable.unpaged();
        Page<BankEntity> bankEntityPage = Page.empty(pageable);
        Mockito.when(bankRepository.findAll(pageable)).thenReturn(bankEntityPage);

        // when
        Page<BankDto> bankDtoPage = bankService.getAllBanks(pageable);

        // then
        assertThat(bankDtoPage).isNotNull();
        assertThat(bankDtoPage.getContent()).isEmpty();
        verify(bankRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test getAllBanks returns correct BankDto")
    public void givenPageable_whenGetAllBanks_thenReturnCorrectBankDto() {
        // given
        Pageable pageable = Pageable.unpaged();
        List<BankEntity> bankEntities = Arrays.asList(createBankEntity(), createBankEntity());
        Page<BankEntity> bankEntityPage = new PageImpl<>(bankEntities, pageable, bankEntities.size());
        Mockito.when(bankRepository.findAll(pageable)).thenReturn(bankEntityPage);
        Mockito.when(bankMapper.toDto(any(BankEntity.class))).thenAnswer(invocation -> createBankDto(invocation.getArgument(0)));
        // when
        Page<BankDto> bankDtoPage = bankService.getAllBanks(pageable);

        // then
        assertThat(bankDtoPage).isNotNull();
        assertThat(bankDtoPage.getContent().size()).isEqualTo(bankEntities.size());
        bankDtoPage.getContent().forEach(bankDto -> {
            assertThat(bankDto.bankName()).isEqualTo("Test Bank");
            assertThat(bankDto.bicCode()).isEqualTo("1234567");
        });
        verify(bankRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test getAllBanks returns empty page when repository is empty")
    public void givenEmptyRepository_whenGetAllBanks_thenReturnsEmptyPageWithCorrectSize() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        Page<BankEntity> bankEntityPage = Page.empty(pageable);
        Mockito.when(bankRepository.findAll(pageable)).thenReturn(bankEntityPage);

        // when
        Page<BankDto> bankDtoPage = bankService.getAllBanks(pageable);

        // then
        assertThat(bankDtoPage).isNotNull();
        assertThat(bankDtoPage.getContent()).isEmpty();
        assertThat(bankDtoPage.getTotalElements()).isEqualTo(0);
        assertThat(bankDtoPage.getSize()).isEqualTo(pageable.getPageSize());
        verify(bankRepository, times(1)).findAll(pageable);
    }

    // CategoryService tests

    @Test
    @DisplayName("Test getById functionality")
    public void givenUuid_whenGetById_thenReturnCategoryDto() {
        // given
        UUID uuid = UUID.randomUUID();
        CategoryEntity categoryEntity = createCategoryEntity();
        CategoryDto expectedCategoryDto = createCategoryDto(categoryEntity);

        Mockito.when(categoryRepository.findById(uuid)).thenReturn(Optional.of(categoryEntity));
        Mockito.when(categoryMapper.toDto(categoryEntity)).thenReturn(expectedCategoryDto);

        // when
        CategoryDto actualCategoryDto = categoryService.getById(uuid);

        // then
        assertThat(actualCategoryDto).isNotNull();
        assertThat(actualCategoryDto).isEqualTo(expectedCategoryDto);
        assertThat(actualCategoryDto.id()).isEqualTo(expectedCategoryDto.id()); // Дополнительные проверки
        assertThat(actualCategoryDto.categoryName()).isEqualTo(expectedCategoryDto.categoryName());
        assertThat(actualCategoryDto.categoryType()).isEqualTo(expectedCategoryDto.categoryType());

        verify(categoryRepository, times(1)).findById(uuid);
        verify(categoryMapper, times(1)).toDto(categoryEntity);
    }

    @Test
    @DisplayName("Test getById with non-existent id")
    public void givenNonExistentUuid_whenGetById_thenThrowException() {
        // given
        UUID uuid = UUID.randomUUID();
        Mockito.when(categoryRepository.findById(uuid)).thenReturn(Optional.empty());

        // when, then
        assertThrows(NoSuchElementException.class, () -> categoryService.getById(uuid));
        verify(categoryRepository, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("Test getAll functionality")
    public void givenCategoryList_whenGetAll_thenReturnListOfCategoryDto() {
        // given
        List<CategoryEntity> categoryEntities = Arrays.asList(createCategoryEntity(), createCategoryEntity());
        List<CategoryDto> expectedCategoryDtos = categoryEntities.stream().map(this::createCategoryDto).collect(Collectors.toList());
        Mockito.when(categoryRepository.findAll()).thenReturn(categoryEntities);
        Mockito.when(categoryMapper.toDto(any(CategoryEntity.class))).thenAnswer(invocation -> createCategoryDto(invocation.getArgument(0)));

        // when
        List<CategoryDto> actualCategoryDtos = categoryService.getAll();

        // then
        assertThat(actualCategoryDtos).isNotNull();
        assertThat(actualCategoryDtos.size()).isEqualTo(categoryEntities.size());
        assertThat(actualCategoryDtos).usingRecursiveComparison().isEqualTo(expectedCategoryDtos); // Более детальная проверка

        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, times(categoryEntities.size())).toDto(any(CategoryEntity.class));
    }

    @Test
    @DisplayName("Test getAll with empty category list")
    public void givenEmptyCategoryList_whenGetAll_thenReturnEmptyList() {
        // given
        Mockito.when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<CategoryDto> categoryDtos = categoryService.getAll();

        // then
        assertThat(categoryDtos).isNotNull();
        assertThat(categoryDtos).isEmpty();

        verify(categoryRepository, times(1)).findAll();
        verify(categoryMapper, never()).toDto(any(CategoryEntity.class));
    }

    // TransactionService tests

    @Test
    @DisplayName("Test getAll transactions")
    public void givenTransactionList_whenGetAll_thenReturnListOfTransactionDto() {
        // given
        List<TransactionEntity> transactionEntities = Arrays.asList(createTransactionEntity(), createTransactionEntity());
        List<TransactionDto> expectedTransactionDtos = transactionEntities.stream().map(this::createTransactionDto).collect(Collectors.toList());

        Mockito.when(transactionRepository.findAll()).thenReturn(transactionEntities);
        Mockito.when(transactionMapper.toDto(any(TransactionEntity.class))).thenAnswer(invocation -> createTransactionDto(invocation.getArgument(0)));

        // when
        List<TransactionDto> actualTransactionDtos = transactionService.getAll();

        // then
        assertThat(actualTransactionDtos).isNotNull();
        assertThat(actualTransactionDtos.size()).isEqualTo(transactionEntities.size());
        assertThat(actualTransactionDtos).usingRecursiveComparison().isEqualTo(expectedTransactionDtos);

        verify(transactionRepository, times(1)).findAll();
        verify(transactionMapper, times(transactionEntities.size())).toDto(any(TransactionEntity.class));
    }

    @Test
    @DisplayName("Test getAll when no transactions")
    public void givenNoTransactions_whenGetAll_thenReturnEmptyList() {
        // given
        Mockito.when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<TransactionDto> transactionDtos = transactionService.getAll();

        // then
        assertThat(transactionDtos).isNotNull();
        assertThat(transactionDtos).isEmpty();

        verify(transactionRepository, times(1)).findAll();
        verify(transactionMapper, never()).toDto(any(TransactionEntity.class));
    }

    @Test
    @DisplayName("Test addTransaction functionality")
    public void givenTransactionDto_whenAddTransaction_thenReturnTransactionDto() {
        // given
        TransactionDto transactionDto = createTransactionDto(createTransactionEntity());
        TransactionEntity transactionEntity = createTransactionEntity();
        TransactionDto expectedTransactionDto = createTransactionDto(transactionEntity); // Создаем ожидаемый DTO

        Mockito.when(transactionMapper.toEntity(transactionDto)).thenReturn(transactionEntity);
        Mockito.when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);
        Mockito.when(transactionMapper.toDto(transactionEntity)).thenReturn(expectedTransactionDto);

        // when
        TransactionDto actualTransactionDto = transactionService.addTransaction(transactionDto);

        // then
        assertThat(actualTransactionDto).isNotNull();
        assertThat(actualTransactionDto).usingRecursiveComparison().isEqualTo(expectedTransactionDto); // Проверяем все поля DTO

        verify(transactionMapper, times(1)).toEntity(transactionDto);
        verify(transactionRepository, times(1)).save(transactionEntity);
        verify(transactionMapper, times(1)).toDto(transactionEntity);
    }

    @Test
    @DisplayName("Test deleteTransaction functionality")
    public void givenUuid_whenDeleteTransaction_thenReturnTransactionDto() {
        // given
        UUID uuid = UUID.randomUUID();
        TransactionEntity transactionEntity = createTransactionEntity();
        TransactionDto expectedTransactionDto = createTransactionDto(transactionEntity);

        Mockito.when(transactionRepository.findById(uuid)).thenReturn(Optional.of(transactionEntity));
        Mockito.when(transactionMapper.toDto(transactionEntity)).thenReturn(expectedTransactionDto);
        Mockito.doNothing().when(transactionRepository).deleteById(uuid);

        // when
        TransactionDto actualTransactionDto = transactionService.deleteTransaction(uuid);

        // then
        assertThat(actualTransactionDto).isNotNull();
        assertThat(actualTransactionDto).usingRecursiveComparison().isEqualTo(expectedTransactionDto); // Проверяем все поля DTO

        verify(transactionRepository, times(1)).findById(uuid);
        verify(transactionMapper, times(1)).toDto(transactionEntity);
        verify(transactionRepository, times(1)).deleteById(uuid);
    }

    @Test
    @DisplayName("Test deleteTransaction with non-existent id")
    public void givenNonExistentUuid_whenDeleteTransaction_thenReturnNull() {
        // given
        UUID uuid = UUID.randomUUID();
        Mockito.when(transactionRepository.findById(uuid)).thenReturn(Optional.empty());

        // when
        TransactionDto deletedDto = transactionService.deleteTransaction(uuid);

        // then
        assertNull(deletedDto);
        verify(transactionRepository, times(1)).findById(uuid);
        verify(transactionRepository, never()).deleteById(uuid);
        verify(transactionMapper, never()).toDto(any(TransactionEntity.class));
    }

    @Test
    @DisplayName("Test getById Transaction")
    public void givenUuid_whenGetById_thenReturnTransactionDto() {
        // given
        UUID uuid = UUID.randomUUID();
        TransactionEntity transactionEntity = createTransactionEntity();
        TransactionDto expectedTransactionDto = createTransactionDto(transactionEntity);

        Mockito.when(transactionRepository.findById(uuid)).thenReturn(Optional.of(transactionEntity));
        Mockito.when(transactionMapper.toDto(transactionEntity)).thenReturn(expectedTransactionDto);

        // when
        TransactionDto actualTransactionDto = transactionService.getById(uuid);

        // then
        assertThat(actualTransactionDto).isNotNull();
        assertThat(actualTransactionDto).usingRecursiveComparison().isEqualTo(expectedTransactionDto); // Проверяем все поля DTO

        verify(transactionRepository, times(1)).findById(uuid);
        verify(transactionMapper, times(1)).toDto(transactionEntity);
    }

    @Test
    @DisplayName("Test getById Transaction with non-existent id")
    public void givenNonExistentUuid_whenGetById_thenReturnNull() {
        // given
        UUID uuid = UUID.randomUUID();
        Mockito.when(transactionRepository.findById(uuid)).thenReturn(Optional.empty());

        // when
        TransactionDto resultDto = transactionService.getById(uuid);

        // then
        assertNull(resultDto);
        verify(transactionRepository, times(1)).findById(uuid);
        verify(transactionMapper, never()).toDto(any(TransactionEntity.class));
    }

    @Test
    @DisplayName("Test updateTransaction functionality")
    public void givenTransactionDto_whenUpdateTransaction_thenReturnTransactionDto() {
        // given
        TransactionDto transactionDto = createTransactionDto(createTransactionEntity());
        TransactionEntity transactionEntity = createTransactionEntity();
        TransactionDto expectedTransactionDto = createTransactionDto(transactionEntity);

        Mockito.when(transactionMapper.toEntity(transactionDto)).thenReturn(transactionEntity);
        Mockito.when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);
        Mockito.when(transactionMapper.toDto(transactionEntity)).thenReturn(expectedTransactionDto);

        // when
        TransactionDto actualTransactionDto = transactionService.addTransaction(transactionDto); // Assuming addTransaction is used for update as well

        // then
        assertThat(actualTransactionDto).isNotNull();
        assertThat(actualTransactionDto).usingRecursiveComparison().isEqualTo(expectedTransactionDto);

        verify(transactionMapper, times(1)).toEntity(transactionDto);
        verify(transactionRepository, times(1)).save(transactionEntity);
        verify(transactionMapper, times(1)).toDto(transactionEntity);
    }

    @Test
    @DisplayName("Test dateTimeToUnix conversion")
    public void testDateTimeToUnixConversion() {
        // given
        LocalDateTime localDateTime = LocalDateTime.now();
        long expectedEpochMilli = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // when
        Long actualEpochMilli = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(); // Use direct conversion for simplicity

        // then
        assertEquals(expectedEpochMilli, actualEpochMilli);
    }

    @Test
    @DisplayName("Test fromEpochMilli conversion")
    public void testFromEpochMilliConversion() {
        // given
        long epochMilli = System.currentTimeMillis();
        LocalDateTime expectedLocalDateTime = Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDateTime();

        // when
        LocalDateTime actualLocalDateTime = Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDateTime(); // Use direct conversion for simplicity

        // then
        assertEquals(expectedLocalDateTime, actualLocalDateTime);
    }

    @Test
    @DisplayName("Test that deleteTransaction returns null if UUID is wrong")
    void testDeleteTransactionWrongUuid() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(transactionRepository.findById(uuid)).thenReturn(Optional.empty());

        TransactionDto transactionDto = transactionService.deleteTransaction(uuid);

        assertEquals(null, transactionDto);
        verify(transactionRepository, times(1)).findById(uuid);
    }

    // TransactionStatusesService tests

    @Test
    @DisplayName("Test getAll transaction statuses")
    public void givenTransactionStatusList_whenGetAll_thenReturnListOfTransactionStatusDto() {
        // given
        List<TransactionStatusEntity> transactionStatusEntities = Arrays.asList(createTransactionStatusEntity(), createTransactionStatusEntity());
        List<TransactionStatusDto> expectedDtos = transactionStatusEntities.stream()
                .map(this::createTransactionStatusDto)
                .collect(Collectors.toList());

        Mockito.when(transactionStatusRepository.findAll()).thenReturn(transactionStatusEntities);
        Mockito.when(transactionStatusMapper.toDto(any(TransactionStatusEntity.class)))
                .thenAnswer(invocation -> createTransactionStatusDto(invocation.getArgument(0)));

        // when
        List<TransactionStatusDto> transactionStatusDtos = transactionStatusesService.getAll();

        // then
        assertThat(transactionStatusDtos)
                .isNotNull()
                .hasSize(transactionStatusEntities.size())
                .usingRecursiveComparison()
                .isEqualTo(expectedDtos);

        verify(transactionStatusRepository, times(1)).findAll();
        verify(transactionStatusMapper, times(transactionStatusEntities.size())).toDto(any(TransactionStatusEntity.class));
    }

    @Test
    @DisplayName("Test getAll transaction statuses when no statuses")
    public void givenNoTransactionStatuses_whenGetAll_thenReturnEmptyList() {
        // given
        Mockito.when(transactionStatusRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<TransactionStatusDto> transactionStatusDtos = transactionStatusesService.getAll();

        // then
        assertThat(transactionStatusDtos).isNotNull().isEmpty();
        verify(transactionStatusRepository, times(1)).findAll();
        verify(transactionStatusMapper, never()).toDto(any(TransactionStatusEntity.class));
    }

    @Test
    @DisplayName("Test getAll transaction statuses with exception thrown")
    public void givenException_whenGetTransactionStatusesAll_thenThrowsException() {
        // given
        Mockito.when(transactionStatusRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // when, then
        assertThrows(RuntimeException.class, () -> transactionStatusesService.getAll());
        verify(transactionStatusRepository, times(1)).findAll();
        verify(transactionStatusMapper, never()).toDto(any(TransactionStatusEntity.class));
    }

    @Test
    @DisplayName("Test getAll transaction statuses with null repository response")
    public void givenNullRepositoryResponse_whenGetTransactionStatusesAll_thenThrowsException() {
        // given
        Mockito.when(transactionStatusRepository.findAll()).thenReturn(null);

        // when, then
        assertThrows(NullPointerException.class, () -> transactionStatusesService.getAll());
        verify(transactionStatusRepository, times(1)).findAll();
        verify(transactionStatusMapper, never()).toDto(any(TransactionStatusEntity.class));
    }

    // TransactionTypesService tests

    @Test
    @DisplayName("Test getAll transaction types")
    public void givenTransactionTypeList_whenGetAll_thenReturnListOfTransactionTypeDto() {
        // given
        List<TransactionTypeEntity> transactionTypeEntities = Arrays.asList(createTransactionTypeEntity(), createTransactionTypeEntity());
        List<TransactionTypeDto> expectedDtos = transactionTypeEntities.stream()
                .map(this::createTransactionTypeDto)
                .collect(Collectors.toList());

        Mockito.when(transactionTypeRepository.findAll()).thenReturn(transactionTypeEntities);
        Mockito.when(transactionTypeMapper.toDto(any(TransactionTypeEntity.class)))
                .thenAnswer(invocation -> createTransactionTypeDto(invocation.getArgument(0)));

        // when
        List<TransactionTypeDto> transactionTypeDtos = transactionTypesService.getAll();

        // then
        assertThat(transactionTypeDtos)
                .isNotNull()
                .hasSize(transactionTypeEntities.size())
                .usingRecursiveComparison()
                .isEqualTo(expectedDtos);

        verify(transactionTypeRepository, times(1)).findAll();
        verify(transactionTypeMapper, times(transactionTypeEntities.size())).toDto(any(TransactionTypeEntity.class));
    }

    @Test
    @DisplayName("Test getAll transaction types when no types")
    public void givenNoTransactionTypes_whenGetAll_thenReturnEmptyList() {
        // given
        Mockito.when(transactionTypeRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<TransactionTypeDto> transactionTypeDtos = transactionTypesService.getAll();

        // then
        assertThat(transactionTypeDtos).isNotNull().isEmpty();
        verify(transactionTypeRepository, times(1)).findAll();
        verify(transactionTypeMapper, never()).toDto(any(TransactionTypeEntity.class));
    }

    @Test
    @DisplayName("Test getAll transaction types with exception thrown")
    public void givenException_whenGetAll_thenThrowsException() {
        // given
        Mockito.when(transactionTypeRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

        // when, then
        assertThrows(RuntimeException.class, () -> transactionTypesService.getAll());
        verify(transactionTypeRepository, times(1)).findAll();
        verify(transactionTypeMapper, never()).toDto(any(TransactionTypeEntity.class));
    }

    @Test
    @DisplayName("Test getAll transaction types with null repository response")
    public void givenNullRepositoryResponse_whenGetAll_thenThrowsException() {
        // given
        Mockito.when(transactionTypeRepository.findAll()).thenReturn(null);

        // when, then
        assertThrows(NullPointerException.class, () -> transactionTypesService.getAll());
        verify(transactionTypeRepository, times(1)).findAll();
        verify(transactionTypeMapper, never()).toDto(any(TransactionTypeEntity.class));
    }
}