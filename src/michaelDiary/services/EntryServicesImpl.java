package michaelDiary.services;

import michaelDiary.exceptions.EntryNotFoundException;
import michaelDiary.model.Entry;
import michaelDiary.repository.EntryRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntryServicesImpl implements EntryServices {
    @Autowired
    private EntryRepositories repository;

    @Override
    public void save(Entry entry) {
        repository.save(entry);
    }

    @Override
    public void deleteEntry(String id) {
        Optional<Entry> entry = repository.findById(id);
        if (entry.isEmpty()) throw new EntryNotFoundException("Entry not found");

        repository.deleteById(id);
    }

    @Override
    public Entry getEntry(String id) {
        Optional<Entry> entry = repository.findById(id);
        if (entry.isEmpty()) throw new EntryNotFoundException("Entry not found");

        return entry.get();
    }

    @Override
    public Optional<Entry> getEntriesFor(String username) {
        Optional<Entry> entries = repository.findById(username.toLowerCase());
        if (entries.isEmpty()) throw new EntryNotFoundException("No entry found");

        return entries;
    }
}
