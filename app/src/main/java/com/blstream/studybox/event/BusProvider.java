package com.blstream.studybox.event;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Bartosz Kozajda on 08.03.2016.
 */
public class BusProvider {
    private static final Bus BUS = new Bus(ThreadEnforcer.ANY);

    public static Bus getInstance(){
        return BUS;
    }

    private BusProvider(){}
}
