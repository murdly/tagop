package com.akarbowy.tagop.parto;

import java.util.List;

public interface GroupPartDefinition<M> extends PartDefinition<M>{

    List<PartDefinition<M>> getChildren(M model);

}
