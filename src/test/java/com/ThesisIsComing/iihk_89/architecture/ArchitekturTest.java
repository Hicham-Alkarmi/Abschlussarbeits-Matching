package com.ThesisIsComing.iihk_89.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;
import com.tngtech.archunit.core.importer.ImportOption;

@AnalyzeClasses(packages = "com.ThesisIsComing.iihk_89",
        importOptions = {
                ImportOption.DoNotIncludeTests.class
        })
public class ArchitekturTest {

    @ArchTest
    static final ArchRule onionArchitekturIstEingehalten =
            onionArchitecture()
                    .domainModels(
                            "..domain.betreuer..",
                            "..domain.thema..",
                            "..domain.datei..",
                            "..domain.shared..",
                            "..domain.matching..",
                            "..domain.student.."
                    )
                    .applicationServices(
                            "..application.betreuer..",
                            "..application.thema..",
                            "..application.Datei..",
                            "..application.matching.."
                    )
                    .domainServices(
                            "..domainService.."
                    )
                    .adapter("web",
                            "..infrastructure.aktivAdapter.web..")
                    .adapter("security",
                            "..infrastructure.aktivAdapter.secuirity..")
                    .adapter("persistence",
                            "..infrastructure.persistAdapter..");
}
