package com.databasir.core.domain.document.diff;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class BaseTypeFieldEqualFunction implements BiFunction<Object, Object, Boolean> {

    private final List<String> ignoreFields;

    @Override
    public Boolean apply(Object that, Object other) {
        if (Objects.equals(that, other)) {
            return true;
        }
        if (that == null || other == null) {
            return false;
        }
        try {
            BeanInfo thatBean = Introspector.getBeanInfo(that.getClass());
            BeanInfo otherBean = Introspector.getBeanInfo(other.getClass());
            Map<String, PropertyDescriptor> otherBeanPropertyMap = Arrays.stream(otherBean.getPropertyDescriptors())
                    .collect(Collectors.toMap(PropertyDescriptor::getName, p -> p));
            for (PropertyDescriptor thatProperty : thatBean.getPropertyDescriptors()) {
                if (thatProperty.getReadMethod() == null || thatProperty.getWriteMethod() == null) {
                    continue;
                }
                if (ignoreFields.contains(thatProperty.getName())) {
                    continue;
                }
                if (!otherBeanPropertyMap.containsKey(thatProperty.getName())) {
                    return false;
                }
                if (Collection.class.isAssignableFrom(thatProperty.getPropertyType())) {
                    Collection thatValue = (Collection) thatProperty.getReadMethod().invoke(that);
                    Collection otherValue = (Collection) otherBeanPropertyMap.get(thatProperty.getName())
                            .getReadMethod().invoke(other);
                    return handleCollection(thatValue, otherValue);
                }
                if (!thatProperty.getPropertyType().isPrimitive()) {
                    Object thatValue = thatProperty.getReadMethod().invoke(that);
                    Object otherValue = otherBeanPropertyMap.get(thatProperty.getName()).getReadMethod().invoke(other);
                    if (!apply(thatValue, otherValue)) {
                        return false;
                    }
                }
                Object thatValue = thatProperty.getReadMethod().invoke(that);
                Object otherValue = otherBeanPropertyMap.get(thatProperty.getName()).getReadMethod().invoke(other);
                if (!Objects.equals(thatValue, otherValue)) {
                    return false;
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            log.error("Error comparing objects", e);
            throw new RuntimeException(e);
        }
        return true;
    }

    private boolean handleCollection(Collection<Object> that, Collection<Object> other) {
        if (that.size() != other.size()) {
            return false;
        }
        for (Object thatObj : that) {
            boolean anyMatch = other.stream().anyMatch(otherObj -> this.apply(thatObj, otherObj));
            if (!anyMatch) {
                return false;
            }
        }
        return true;
    }
}
