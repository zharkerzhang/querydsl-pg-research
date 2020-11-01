package com.zharker.database.service;

import com.zharker.database.data.ResourceRepository;
import com.zharker.database.data.ResourceVerbRelationRepository;
import com.zharker.database.data.VerbRepository;
import com.zharker.database.domain.IdEntity;
import com.zharker.database.domain.Resource;
import com.zharker.database.domain.ResourceVerbRelation;
import com.zharker.database.domain.Verb;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private VerbRepository verbRepository;

    @Autowired
    private VerbService verbService;

    @Autowired
    private ResourceVerbRelationRepository resourceVerbRelationRepository;

    public Resource getById(String id){

        Resource resource = resourceRepository.findById(id);
        if(resource==null){
            throw new RuntimeException("not found the resource by id: ["+id+"]");
        }
        return resource;
    }

    public String save(Resource resource) {

        verbService.checkAndSaveVerbs(resource.getVerbs());

        Set<Verb> verbs = resource.getVerbs();
        resource.setVerbs(null);
        resourceRepository.save(resource);

        if(CollectionUtils.isNotEmpty(verbs)){
            changeResourceVerbRelations(resource.getTId(), verbs);
        }

        return resource.getId();
    }

    public void update(Resource resource,Set<Verb> updateVerbs){

        verbService.checkAndSaveVerbs(updateVerbs);
        resourceRepository.save(resource);
        if(CollectionUtils.isNotEmpty(updateVerbs)){
            changeResourceVerbRelations(resource.getTId(), updateVerbs);
        }
    }

    private void changeResourceVerbRelations(UUID resourceTId, Set<Verb> verbs) {
        Set<String> verbIds = verbs.stream().map(IdEntity::getId).collect(Collectors.toSet());
        Set<UUID> verbIdsRequest = verbRepository.findTIdByIdIn(verbIds);
        Set<UUID> verbIdsStored = resourceVerbRelationRepository.findVerbIdByResourceId(resourceTId);

        Map<Boolean, Set<UUID>> map = verbIdsRequest.stream().collect(Collectors.partitioningBy(verbIdsStored::contains,Collectors.toSet()));

        Set<UUID> hasBindVerbs = map.get(Boolean.TRUE);
        Set<UUID> needBindVerbs = map.get(Boolean.FALSE);
        verbIdsStored.removeAll(hasBindVerbs);
        Set<UUID> needUnbindVerbs = verbIdsStored;

        bindVerbs(resourceTId,needBindVerbs.stream().toArray(UUID[]::new));
        unbindVerbs(resourceTId,needUnbindVerbs.stream().toArray(UUID[]::new));
    }

    private void unbindVerbs(UUID resourceTId, UUID... verbTId) {
        if(verbTId==null||verbTId.length==0||verbTId[0]==null){
            return;
        }
        Set<ResourceVerbRelation> removeRelations = resourceVerbRelationRepository.findByResourceIdAndVerbIdIn(resourceTId,verbTId);
        if(CollectionUtils.isNotEmpty(removeRelations)){
            resourceVerbRelationRepository.deleteInBatch(removeRelations);
        }
    }

    private void bindVerbs(UUID resourceTId, UUID... verbTId) {
        if(verbTId==null||verbTId.length==0||verbTId[0]==null){
            return;
        }
        Arrays.stream(verbTId).forEach(verTId_->{
            ResourceVerbRelation resourceVerbRelation = new ResourceVerbRelation();
            resourceVerbRelation.setResourceId(resourceTId);
            resourceVerbRelation.setVerbId(verTId_);
            resourceVerbRelationRepository.save(resourceVerbRelation);
        });
    }

    public void removeById(String resourceId) {
        Resource resource = resourceRepository.findById(resourceId);
        if(resource==null){
            throw new RuntimeException("not found the resource by id: ["+resourceId+"]");
        }
//        unbindVerbs(resource.getVerbs().stream().map(BaseEntity::getTId).toArray(UUID[]::new));
        resourceRepository.delete(resource);
    }
}
