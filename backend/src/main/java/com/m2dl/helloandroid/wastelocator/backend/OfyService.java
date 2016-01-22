package com.m2dl.helloandroid.wastelocator.backend;


import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.m2dl.helloandroid.wastelocator.backend.models.Interest;
import com.m2dl.helloandroid.wastelocator.backend.models.Tag;
import com.m2dl.helloandroid.wastelocator.backend.models.UserAccount;

/**
 * Created by flemoal on 21/01/16.
 */
public final class OfyService {
    /**
     * Default constructor, never called.
     */
    private OfyService() {
    }

    static {
        factory().register(Interest.class);
        factory().register(Tag.class);
        factory().register(UserAccount.class);
    }

    /**
     * Returns the Objectify service wrapper.
     * @return The Objectify service wrapper.
     */
    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    /**
     * Returns the Objectify factory service.
     * @return The factory service.
     */
    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}