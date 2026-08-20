package nh.demo.plantify.billing;

import nh.demo.plantify.TestcontainersConfiguration;
import nh.demo.plantify.care.CareService;
import nh.demo.plantify.owner.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.modulith.test.ApplicationModuleTest;

import static org.assertj.core.api.Assertions.*;

@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@Import(TestcontainersConfiguration.class)
class BillingModuleTest {

    @Autowired
    ApplicationContext context;

    @Test
    void add_usage_record_with_setup_fee_for_registered_plant() {

        // 🔎 Welche Module sind jetzt hochgefahren?
        // care ist keine direkte Abhängigkeit -> nicht da
        assertThat(context.getBeansOfType(CareService.class)).isEmpty();

        // owner IST direkte Abhängigkeit -> da
        assertThat(context.getBeansOfType(OwnerRepository.class)).isNotEmpty();
    }

}
