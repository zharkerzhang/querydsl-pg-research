package com.zharker.database.service;

import com.zharker.database.data.VerbRepository;
import com.zharker.database.domain.IdEntity;
import com.zharker.database.domain.Verb;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VerbService {

    @Autowired
    private VerbRepository verbRepository;

    public void checkAndSaveVerbs(Set<Verb> verbs) {
        if(CollectionUtils.isEmpty(verbs)){
            return;
        }
        Set<String> verbIds = verbs.stream().map(IdEntity::getId).collect(Collectors.toSet());
        List<Verb> verbsStored = verbRepository.findByIdIn(verbIds.stream().toArray(String[]::new));
        Set<String> verbIdsStored = verbsStored.stream().map(IdEntity::getId).collect(Collectors.toSet());
        Set<Verb> needSaveVerbs = verbs.stream().filter(verb->!verbIdsStored.contains(verb.getId())).collect(Collectors.toSet());
        verbRepository.saveAll(needSaveVerbs);
    }
}
