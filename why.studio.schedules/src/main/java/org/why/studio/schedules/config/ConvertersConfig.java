package org.why.studio.schedules.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.why.studio.schedules.converters.*;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
public class ConvertersConfig {

    private final ServiceToServiceEntityConverter serviceToServiceEntityConverter;
    private final ServiceEntityToServiceConverter serviceEntityToServiceConverter;
    private final ConsultationRequestInputToConsultationRequestEntityConverter
            consultationRequestInputToConsultationRequestEntityConverter;
    private final ConsultationRequestEntityConverterToConsultationRequestOutputUserConverter
            consultationRequestEntityConverterToConsultationRequestOutputUserConverter;
    private final ConsultationRequestEntityConverterToConsultationRequestOutputSpecConverter
            consultationRequestEntityConverterToConsultationRequestOutputSpecConverter;
    private final UserLogEntityToUserLogConverter userLogEntityToUserLogConverter;
    private final UserPrimarySpecialistToUserPrimarySpecialistEntityConverter
            userPrimarySpecialistToUserPrimarySpecialistEntityConverter;
    private final UserCalendarToUserCalendarEntityConverter userCalendarToUserCalendarEntityConverter;

    @Bean
    public ConversionService schedulesConverterService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(new HashSet<>(Arrays.asList(
                serviceToServiceEntityConverter,
                serviceEntityToServiceConverter,
                consultationRequestInputToConsultationRequestEntityConverter,
                consultationRequestEntityConverterToConsultationRequestOutputUserConverter,
                consultationRequestEntityConverterToConsultationRequestOutputSpecConverter,
                userLogEntityToUserLogConverter,
                userPrimarySpecialistToUserPrimarySpecialistEntityConverter,
                userCalendarToUserCalendarEntityConverter
        )));
        bean.afterPropertiesSet();
        return bean.getObject();
    }

}
