package com.eazylearn.service;

import java.util.Collection;

public interface CheckExistenceService {

    boolean areCardSetByIdsExist(Collection<String> cardSetIds);

    boolean areCardsByIdsExist(Collection<String> cardIds);
}
