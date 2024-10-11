package com.luetek.academy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HibernateInterceptorRegistration  implements HibernatePropertiesCustomizer {
    @Autowired
    private HibernateInterceptor myInterceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", myInterceptor);
    }
}

