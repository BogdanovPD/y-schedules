package org.why.studio.schedules.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.why.studio.schedules.converters.ServiceEntityToServiceConverter;
import org.why.studio.schedules.converters.ServiceToServiceEntityConverter;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
public class ConvertersConfig {

    private final ServiceToServiceEntityConverter serviceToServiceEntityConverter;
    private final ServiceEntityToServiceConverter serviceEntityToServiceConverter;

    @Bean
    public ConversionService schedulesConverterService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(new HashSet<>(Arrays.asList(
                serviceToServiceEntityConverter,
                serviceEntityToServiceConverter
        )));
        bean.afterPropertiesSet();
        return bean.getObject();
    }

}
