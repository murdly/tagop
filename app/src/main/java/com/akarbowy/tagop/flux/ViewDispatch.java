package com.akarbowy.tagop.flux;

import java.util.List;

public interface ViewDispatch {
    List<? extends Store> getStoresToRegister();
}
