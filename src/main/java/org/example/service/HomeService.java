package org.example.service;

import org.example.model.Edital;
import org.example.repository.EditalRepository;

import java.util.List;

public class HomeService {
    private EditalRepository editalRepository;

    public HomeService () {
        editalRepository = new EditalRepository();
    }

    public List<Edital> retornarEditais () {
        return editalRepository.retornarTodosEditais();
    }
}
