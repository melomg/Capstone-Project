package com.projects.melih.wonderandwander.common;

import java.util.Collection;

/**
 * Created by Melih Gültekin on 22.04.2018
 */
public final class CollectionUtils {

    private CollectionUtils() {
        //no-op
    }

    public static boolean isNotEmpty(Collection<?> list) {
        return ((list != null) && !list.isEmpty());
    }

    public static int size(Collection<?> list) {
        return (list == null) ? 0 : list.size();
    }
}