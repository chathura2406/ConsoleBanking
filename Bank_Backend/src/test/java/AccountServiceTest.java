import com.banking.dto.AccountDto;
import com.banking.entity.Account;
import com.banking.entity.AccountType;
import com.banking.repository.AccountRepository;
import com.banking.repository.TransactionRepository;
import com.banking.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.banking.dto.TransactionDto;
import com.banking.entity.Transaction;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository; // Fake Database connection

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService; // Real Service

    @Test
    public void testDepositBalanceIncrease() {

        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);
        account.setAccountHolderName("Test User");
        account.setAccountType(AccountType.SAVINGS);


        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);


        AccountDto result = accountService.deposit(1L, 500.0);


        assertEquals(1500.0, result.getBalance());
    }

    @Test
    public void testGetAccountTransactions() {
        // 1. Arrange (Data laasthi kirima)
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);

        Transaction t1 = new Transaction();
        t1.setId(1L);
        t1.setAccountId(accountId);
        t1.setAmount(500.0);
        t1.setTransactionType("DEPOSIT");
        t1.setTimestamp(LocalDateTime.now());

        Transaction t2 = new Transaction();
        t2.setId(2L);
        t2.setAccountId(accountId);
        t2.setAmount(200.0);
        t2.setTransactionType("WITHDRAW");
        t2.setTimestamp(LocalDateTime.now());

        List<Transaction> transactionList = Arrays.asList(t1, t2);

        // Pagination settings (Spring data domain eken ena ewa)
        Pageable pageable = PageRequest.of(0, 5, Sort.by("timestamp").descending());
        Page<Transaction> transactionPage = new PageImpl<>(transactionList, pageable, transactionList.size());

        // Mocking repository calls (Database eken ahanne nathuwa me boru data tika denna kiyanawa)
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.findByAccountId(accountId, pageable)).thenReturn(transactionPage);

        // 2. Act (Method eka run kirima)
        Page<TransactionDto> result = accountService.getAccountTransactions(accountId, 0, 5);

        // 3. Assert (Hariyata wedada kiyala check kirima)
        assertEquals(2, result.getContent().size()); // Transactions 2k enna oni
        assertEquals(500.0, result.getContent().get(0).getAmount()); // Palamu eke amount eka 500 wenna oni
        assertEquals("DEPOSIT", result.getContent().get(0).getTransactionType()); // Palamu eka DEPOSIT wenna oni
    }

}