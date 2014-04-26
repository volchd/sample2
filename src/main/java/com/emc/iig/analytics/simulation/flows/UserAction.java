package com.emc.iig.analytics.simulation.flows;

import java.util.List;

import com.emc.iig.analytics.simulation.model.BusinessEvent;

public interface UserAction {
public List<BusinessEvent> generateFlow(String flowName) throws CloneNotSupportedException;

}
