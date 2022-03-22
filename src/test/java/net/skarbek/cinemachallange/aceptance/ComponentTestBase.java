package net.skarbek.cinemachallange.aceptance;

import net.skarbek.cinemachallange.CinemaChallangeApplication;
import net.skarbek.cinemachallange.aceptance.helpers.WhenSupport;
import net.skarbek.cinemachallange.aceptance.configuration.TestContextConfiguration;
import net.skarbek.cinemachallange.aceptance.helpers.GivenSupport;
import net.skarbek.cinemachallange.aceptance.helpers.ThenSupport;
import net.skarbek.cinemachallange.infrastructure.fake.TestClearableSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestContextConfiguration.class, CinemaChallangeApplication.class})
public abstract class ComponentTestBase {

    @Autowired
    List<TestClearableSource> clearableSources;

    @Autowired
    protected GivenSupport given;
    @Autowired
    protected WhenSupport when;
    @Autowired
    protected ThenSupport then;

    @AfterEach
    public void cleanUp() {
        this.clearableSources.forEach(TestClearableSource::cleanUp);
    }

}
