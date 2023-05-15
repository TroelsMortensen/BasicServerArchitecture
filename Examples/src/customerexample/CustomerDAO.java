package customerexample;

import java.util.UUID;

public interface CustomerDAO {
    Customer find(UUID customerId);
}
