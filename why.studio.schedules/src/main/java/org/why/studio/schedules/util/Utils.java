package org.why.studio.schedules.util;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static <I, C> Collection<C> convertCollection(Collection<I> initialList, ConversionService conversionService,
                                                         Class<I> classFrom, Class<C> classTo) {
        if (initialList.isEmpty()) {
            return Collections.emptyList();
        }
        Collection<C> convertedList = initialList.stream().map(e -> (C)conversionService.convert(
                e, TypeDescriptor.valueOf(classFrom), TypeDescriptor.valueOf(classTo)))
                .collect(Collectors.toSet());
        return convertedList;
    }

    public static <I, C> List<C> convertCollectionToList(Collection<I> initialList, ConversionService conversionService,
                                                         Class<I> classFrom, Class<C> classTo) {
        return new LinkedList<>(convertCollection(initialList, conversionService, classFrom, classTo));
    }
}
