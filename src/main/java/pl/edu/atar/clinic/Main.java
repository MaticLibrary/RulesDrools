package pl.edu.atar.clinic;

import java.util.List;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.KieContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        List<Patient> patients = new Dataset().getPatients();

        KieServices kService = KieServices.Factory.get();
        KieContainer kContainer = kService.getKieClasspathContainer();
        KieBase kBase = kContainer.getKieBase("triage-basic");
        StatelessKieSession kSession = kBase.newStatelessKieSession();
        kSession.addEventListener(new DebugAgendaEventListener());
        kSession.addEventListener(new DebugRuleRuntimeEventListener());
        kService.getLoggers().newConsoleLogger(kSession);

        LOGGER.info("Utworzono bazę wiedzy. Przetwarzanie {} pacjentów...\n", patients.size());

        for (Patient patient : patients) {

            LOGGER.info("=== Przetwarzanie pacjenta o ID: {} ===", patient.getId());
            LOGGER.info("Informacje PRZED wnioskowaniem:\n{}", patient);
            KieRuntimeLogger fileLogger = kService.getLoggers().newFileLogger(kSession, "./logs/reasoning_fact_" + patient.getId());
            LOGGER.info("... ROZPOCZĘTO WNIOSKOWANIE ...");
            kSession.execute(patient);
            fileLogger.close();
            LOGGER.info("... ZAKOŃCZONO WNIOSKOWANIE ...\n");
            LOGGER.info("Informacje PO wnioskowaniu:\n{}", patient);
            LOGGER.info("====================================\n");
        }
    }
}