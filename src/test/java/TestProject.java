import org.example.application.ClientService;
import org.example.infrastructure.persistence.ClientRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TestProject {

    @Test
    public void transferToYourselfToTheSameAccount() {

        ClientRepository mockRepository = mock(ClientRepository.class);
        ClientService clientService = new ClientService(mockRepository);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                clientService.transfer(3, 500.00, 22391146, 22391146));

        assertEquals("Нельзя переводить деньги самому себе!", exception.getMessage());
        verify(mockRepository, never()).transfer(anyInt(), anyDouble(), anyInt(), anyInt());
    }

    @Test
    public void transferBetweenAccountsInDifferentCurrencies() {

        ClientRepository mockRepository = mock(ClientRepository.class);
        ClientService clientService = new ClientService(mockRepository);
        doThrow(new IllegalArgumentException("Несоответствие валют: USD → EUR"))
                .when(mockRepository)
                .transfer(3, 500.00, 22391146, 22391147);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(3, 500.00, 22391146, 22391147));

        assertEquals("Несоответствие валют: USD → EUR", exception.getMessage());
        verify(mockRepository).transfer(3, 500.00, 22391146, 22391147);


    }

    @Test
    public void transferFromOtherPeoplesAccounts() {

        ClientRepository mockRepository = mock(ClientRepository.class);
        ClientService clientService = new ClientService(mockRepository);
        doThrow(new IllegalArgumentException("Перевод не возможен. Вы не владелец счета."))
                .when(mockRepository)
                .transfer(4, 500.00, 22391146, 22391147);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(4, 500.00, 22391146, 22391147));

        assertEquals("Перевод не возможен. Вы не владелец счета.", exception.getMessage());
        verify(mockRepository).transfer(4, 500.00, 22391146, 22391147);

    }

    @Test
    public void goIntoTheRedOnAccount() {

        ClientRepository mockRepository = mock(ClientRepository.class);
        ClientService clientService = new ClientService(mockRepository);
        doThrow(new IllegalArgumentException("На счете недостаточно средств."))
                .when(mockRepository)
                .transfer(3, 5000.00, 22391146, 22391147);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(3, 5000.00, 22391146, 22391147));

        assertEquals("На счете недостаточно средств.", exception.getMessage());
        verify(mockRepository).transfer(3, 5000.00, 22391146, 22391147);

    }
}
