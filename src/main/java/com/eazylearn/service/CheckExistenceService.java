package com.eazylearn.service;

import java.util.Collection;

public interface CheckExistenceService {

    boolean areCardSetsByIdsExist(Collection<String> cardSetIds);

    boolean isCardSetsByIdExist(String cardSetId);

    boolean isCardSetByNameExist(String cardSetName, String userId);

    boolean areCardsByIdsExist(Collection<String> cardIds);

    boolean isCardByIdExist(String cardId);
}
