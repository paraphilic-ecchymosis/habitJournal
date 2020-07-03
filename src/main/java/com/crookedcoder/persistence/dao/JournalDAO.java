package com.crookedcoder.persistence.dao;

import java.util.Optional;

import com.crookedcoder.persistence.entity.Journal;
import com.crookedcoder.persistence.entity.Journal.Entry;
import com.google.api.client.util.DateTime;

public interface JournalDAO {

    public Optional<Journal> get();
    
    public void update(Entry entry);

    public void delete(Entry entry);

    public Optional<Journal> getByMonth(DateTime date);
}