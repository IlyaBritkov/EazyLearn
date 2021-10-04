package com.eazylearn.service;

import java.util.Collection;
import java.util.UUID;

public interface CheckExistenceService {

    boolean areCardSetByIdsExist(Collection<UUID> cardSetIds);

}
