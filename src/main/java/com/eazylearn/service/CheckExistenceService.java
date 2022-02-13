package com.eazylearn.service;

import java.util.Collection;

public interface CheckExistenceService {

    boolean areCardSetsByIdsExist(Collection<String> cardSetIds);

    boolean isCardSetsByIdExist(String cardSetId);

    boolean areCardsByIdsExist(Collection<String> cardIds);

    boolean isCardByIdExist(String cardId);
}
