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
        // 1. Arrange (Data ලෑස්ති කරගන්නවා)
        Account account = new Account();
        account.setId(1L);
        account.setBalance(1000.0);
        account.setAccountHolderName("Test User");
        account.setAccountType(AccountType.SAVINGS);

        // Fake Database එකට කියනවා ID 1 ඉල්ලුවොත් මේ Account එක දෙන්න කියලා
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // 2. Act (Action එක කරනවා)
        AccountDto result = accountService.deposit(1L, 500.0);

        // 3. Assert (ප්‍රතිඵලය හරිද බලනවා)
        // 1000 + 500 = 1500 වෙන්න ඕන
        assertEquals(1500.0, result.getBalance());
    }
}