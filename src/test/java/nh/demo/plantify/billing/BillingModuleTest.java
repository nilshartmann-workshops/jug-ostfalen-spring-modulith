package nh.demo.plantify.billing;

import nh.demo.plantify.TestcontainersConfiguration;
import nh.demo.plantify.owner.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.*;

@ApplicationModuleTest
@Import(TestcontainersConfiguration.class)
class BillingModuleTest {

    @Test
    void add_usage_record_with_setup_fee_for_registered_plant() {

        // 🔎 Zeigen
        // Startet NUR Billing-Module
        //  das "used" aber owner
        // -> keine owner-Services hier
        // -> Fehler
    }

}
