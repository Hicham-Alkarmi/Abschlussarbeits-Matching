package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.secuirity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@Configuration // lies die klasse beim starten ein
@EnableMethodSecurity(securedEnabled = true) // damit man @secured(con) anwenden kann
public class MethodSecuirtyConfig {

}
