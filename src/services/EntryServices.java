package services;

import model.Entry;

import java.util.Optional;

public interface EntryServices {
    void save(Entry entry);
    void deleteEntry(String id);
    Entry getEntry(String id);
    Optional<Entry> getEntriesFor(String username);
}
